---
title: "Advanced data validation with EDS"
date: 2020-04-19 12:00
layout: "single"
categories: "EDS"
excerpt: "This article will show you some techniques to handle complex data validation. It shows how you can leverage transient properties to provide rich feedback to the user"
---

<i class="fa fa-lg fa-github" aria-hidden="true"></i> [Code on github](https://github.com/ecmdeveloper/eds-servlet/tree/master/eds-servlet-advanced-validation)

# Introduction

This article shows you how you can do advanced data validation using an External Data Services (EDS) component. EDS is a feature available in IBM Content Navigator and IBM Case Manager. It allows you to control the way the different properties in the application should behave and interact with each other. This article focusses on the data validation part. It starts with some simple validation techniques but at the end a more advanced use case is shown. In the advanced case you can give rich feedback to the user in case of a validation error.

As an example we will us a `Customer` folder class, containing all the information for a specific customer _(I know, not a very original example)_. One of the properties of this class is [IBAN](https://en.wikipedia.org/wiki/International_Bank_Account_Number). IBAN stands for 'International Bank Account Number', a system used in a lot of mostly European countries to identify bank account numbers. The general structure of this account number is as follows:

* Two uppercase characters as the country code
* Two check digits
* An alphanumeric string with a maximum length of 30 characters.

The format of the later part depends on the [country](https://www.iban.com/structure). For example for the Netherlands the third part should contain 18 characters. To cater to all the specific needs of the different countries, we define a string property with a length of 34 characters. In the rest of this article you will see different ways to do IBAN validation. These techniques are not specific for this use case. You could use this also for instance if you have a value that is stored in a database, but contains to much values for showing in a choice list.

# Building the EDS component for IBM Content Navigator

In a [previous article]({{ site.url }}/eds/implementing-EDS-with-java/) an API was introduced to simplify building an External Data Services (EDS) component. To get started you have to create a servlet with the `AbstractEDSServlet` class as superclass, implement the `handleRequest()` method and override the `getObjectTypeNames()` method:

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

The rest of this article will feature different implementations of the `handleRequest()` method. We start out with the most basic form of validation, using a regular expression. The following code takes the `iban` property from the `dataRequest` object and uses the `setFormat()` method to set the regular expression _(don't forget to add the property back to the the response)_:

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

|![Regular expressing validation]({{ site.url }}/img/posts/invalid-regex.png)|

This first try is a nice beginning but would not qualify as "Advanced". As we saw above, actual IBAN validation is a lot more involved then simple regular expression validation. Fortunately there is a library which can do all the validation heavy lifting: [iban4j](https://github.com/arturmkrtchyan/iban4j). This is a Java library for generation and validation of International Bank Account Numbers. This library contains all the rules for the different countries and check digit validation. We will add this functionality to our project with the powers of Maven:

```xml
<dependency>
  <groupId>org.iban4j</groupId>
  <artifactId>iban4j</artifactId>
  <version>3.2.1</version>
</dependency>
```

A key requirement to use this library is to force a round trip to the EDS component on the server every time an user fills in a value. This can be accomplished with the following piece of code:

```java
iban.setHasDependentProperties(true);
```

This is not very intuitive, as the property does not _actually_ has a dependent property, but it does the trick. The resulting code now looks like this:

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

|![Invalid checkdigit no feedback]({{ site.url }}/img/posts/invalid-cd-no-feedback.png)|

This is correct, no feedback is given to the user at this point. Only after pressing the "Add" button the validation error is shown to the user:

|![Invalid checkdigit add button]({{ site.url }}/img/posts/invalid-cd-add-button.png)|

# More feedback please!

In the ideal situation you would see the error message immediately after the value is supplied by the user. To accomplished this we add a _transient_ property with symbolic name `ValidationError` to the class definition. This property will display the validation error when editing the property value. However, the value of a transient property will never be saved to the database. The fact that a property is a transient property is configured in the _Additional Property Template Attributes_ section of the property template definition:

|![Validation error property]({{ site.url }}/img/posts/validation-error-template.png)|

To make sure that we have enough space to display the validation error, the maximum length of the property value is set to 1000 characters.

Now we have to change the EDS code to handle this new situation. The new code looks like this:

```java
Property iban = dataRequest.getProperty("DEV_IBAN");
Property validationError = dataRequest.getProperty("ValidationError");

if (iban != null && validationError != null) {

   iban.setHasDependentProperties(true);

   validationError.setLabel("IBAN validation");
   validationError.setDisplayMode(DisplayMode.readonly);
   validationError.setValue("");
   validationError.setFormat("^(?![\\s\\S])");
   dataResponse.addProperty(validationError);

   String ibanValue = (String)iban.getValue();
   if ( ibanValue != null && !ibanValue.isEmpty() ) {
      try {
         IbanUtil.validate(ibanValue);
      } catch (IbanFormatException |
            InvalidCheckDigitException | 
            UnsupportedCountryException e ) {
         iban.setCustomValidationError(e.getMessage() );
         validationError.setValue(e.getMessage());
      }
   }
   dataResponse.addProperty(iban);
}
```
The validation error property is changed in code to a readonly property with _IBAN validation_ as the label. The value of the property is initially empty but filled with the validation error if there is an error. The regular expression `^(?![\\s\\S])` dictates that a valid value is an empty string. So as as soon as the validation error is non-empty the validation error will be shown as an error. The validation is performed in the EDS code as soon as the `IBAN` field looses focus. In the user interface this looks as follows:

|![Validation error property]({{ site.url }}/img/posts/invalid-cd-advanced-feedback.png)|

# What about IBM Case Manager?

Up till now we saw examples using IBM Content Navigator. However, IBM Case Manager also supports External Data Service, with some differences. The good news is that validation errors are reported immediately back to the user:

|![Validation error property]({{ site.url }}/img/posts/invalid-cd-case-manager.png)|

A small detail is that the validation error is "hidden" in a tooltip. If you want direct feedback on the screen you can still use the validation error property to display the validation error. Note that both the methods `setLabel()` and `setFormat()` are not supported, therefore the appearance on the screen is a little bit different compared to Content Navigator case. By adding this line of code in the catch-block you can also get an exclamation point next to the validator error textbox:

```java
validationError.setCustomValidationError("Invalid IBAN");
```

# Conclusion

In this article you saw how you can do advanced data validation using External Data Services. You saw how you can give rich feedback to the user with a little bit of help from a transient property. The solutions presented here are not limited to single value input fields. In practice I have used this to cope with multiple input fields coupled to database records and also depending on each other.