---
title: "Advanced data validation with EDS"
date: 2018-12-20 12:00
layout: "single"
categories: "EDS"
excerpt: "This article will show you some techniques to handle complex data validation. It shows how you can leverage transient properties to provide rich feedback to the user"
---

# Introduction

As an example we will us a `Customer` folder class, containing all the information for a specific customer _(I know, not a very original example)_. One of the properties of this class is [IBAN](https://en.wikipedia.org/wiki/International_Bank_Account_Number). IBAN stands for 'International Bank Account Number', a system used in a lot of countries to identify bank accounts. The general structure of this account number is as follows:

* A two uppercase character country code
* Two check digits
* An alphanumeric string with a maximum length of 30 characters.

The format of the later part depends on the [country](https://www.iban.com/structure). For example for the Netherlands the third part should contain 18 characters. To cater to all the specific needs of the different countries, we define a string property with a length of 34 characters. In the rest of this article you will see different ways to do IBAN validation. These techniques are not specific for this use case. You could use this also for instance if you have a value that is stored in a database, but contains to much values for showing in a choice list.

# Building the EDS component for Content Navigator

In a [previous article](http://todo.com") an API was introduced to simplify building an External Data Services (EDS). component. To get started you have to create a servlet with the `AbstractEDSServlet` class as superclass, implement the `handleRequest()` method and override the `getObjectTypeNames()` method:

``` java
@WebServlet(description = "An example of an EDS servlet.", 
            urlPatterns = { "/type/*", "/types", "/error", "/ping/*"})
public class EDSAdvancedValidationServlet extends AbstractEDSServlet {

	@Override
	public String[] getObjectTypeNames(String repositoryId) {
		return new String[] {"Customer" };
	}

	@Override
	public void handleRequest(ExternalDataRequest dataRequest, ExternalDataResponse dataResponse) {
         // TODO: add IBAN validation
   }
}		
```

We start out with the most basic form of validation, using a regular expression. The following code takes the `iban` property from the `dataRequest` object and uses the `setFormat()` method to set the regular expression _(don't forget to add the property back to the the response)_:

```java
Property iban = dataRequest.getProperty("DEV_IBAN");
if (iban != null) {
    iban.setFormat("([A-Z]{2})(\\d{2})([A-Za-z\\d]+)$");
    iban.setFormatDescription("<br><ul><li>Country code</li>"
            + "<li>Two check digits</li>"
            + "<li>Basic Bank Account Number</li></ul>");
    dataResponse.addProperty(iban);
}
```
To give feedback to the user we use the `setFormatDescription()` method to specify the format we expect (yes, HTML is supported). When you try to add a new folder, this will show up in the user interface as follows:

![Regular expressing validation]({{ site.url }}/img/posts/invalid-regex.png)

This first try is a nice beginning but would not qualify as "Advanced". As we saw above, actual IBAN validation is a lot more involved then simple regular expression validation. Fortunately there is a library which can do all the validation heavy lifting: [iban4j](https://github.com/arturmkrtchyan/iban4j). This is a Java library for generation and validation of International Bank Account Numbers. This library contains all the rules for the different countries and check digit validation. We will add this functionality to our project with the powers of Maven:

```xml
<dependency>
  <groupId>org.iban4j</groupId>
  <artifactId>iban4j</artifactId>
  <version>3.2.1</version>
</dependency>
```

A key requirement to use this library is to force a round trip to the EDS component every time an user fills in a value. This can be accomplished with the following piece of code:

```java
iban.setHasDependentProperties(true);
```

This is not very intuitive, as the property does not _actually_ has a dependent property, but is does the trick. The resulting code now looks like this:

```java
iban.setHasDependentProperties(true);
String ibanValue = (String)iban.getValue();
if ( ibanValue != null && !ibanValue.isEmpty() ) {
    try {
        IbanUtil.validate(ibanValue);
    } catch (IbanFormatException | InvalidCheckDigitException | 
             UnsupportedCountryException e ) {
        iban.setCustomValidationError(e.getMessage() );
    }
}
dataResponse.addProperty(iban);
```

The class `IbanUtil` contains a method `validate()` which will throw an exception when validation fails. We use the `setCustomValidationError()` method to capture the validation error and report it back to the user. We will now look how it shows up in the user interface. First a IBAN number is generated using [randomiban.com](http://randomiban.com). Next the last digit is changed from a one to a two, forcing a invalid check digit. In the user interface this will look as follows:

![Invalid checkdigit no feedback]({{ site.url }}/img/posts/invalid-cd-no-feedback.png)

This is correct, no feedback is given to the user at this point. Only after pressing the "Add" button the validation error is shown to the user:

![Invalid checkdigit add button]({{ site.url }}/img/posts/invalid-cd-add-button.png)

# More feedback please!


```json
{
   externalDataIdentifier: "EDS API",
   properties: [
      {
         symbolicName: "DEV_IBAN",
         value: "NL12$",
         customValidationError: "[$] length is 1, expected BBAN length is: 14",
         hasDependentProperties: true
      }
   ]
}
```

``` json
{
   externalDataIdentifier: "EDS API",
   properties: [
      {
         symbolicName: "DEV_IBAN",
         value: "NL58ABNA4492703412",
         customValidationError: "[NL58ABNA4492703412] has invalid check digit: 58, expected check digit is: 31",
         hasDependentProperties: true
      }
   ]
}
```

Account Number