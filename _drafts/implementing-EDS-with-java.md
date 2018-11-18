---
title: "Implementing an EDS service with Java"
date: 2017-07-01 12:00
layout: "single"
categories: "EDS"
excerpt: "This article shows you how to build an External Data Service with Java. It provides an API which takes care of all the JSON stuff, you just has to focus on the business rules."
---

<i class="fa fa-fw fa-github" aria-hidden="true"> </i>[Code on GitHub](https://github.com/ecmdeveloper/eds-servlet)

External Data Services (EDS) is a feature available in IBM Content Navigator and
IBM Case Manager. It allows you to control the way the different properties in
the application should behave and interact with each other. This is a very powerful
technique allowing you to accomplish the following tasks:
-   Use data from external systems to populate a choice list.
-   Enforce complex validation rules on property values.
-   Create dynamic choice list, where the values of the choice list depend on the value of another property
-   Pre-fill your property values when a new object is added.

These are just a few examples of the numerous possibilities. In this blog post a API is introduced allowing you to build all this functionality more easily. This article assumes that the reader knows how to create and deploy web applications based on servlets.

# The External Data Services contract

An EDS component is a web application which responds to specific GET and POST request from the EDS infrastructure. The interaction between the component and the infrastructure depends on the application you are using.

If you are using IBM Content Navigator from a browser or from a Microsoft Office application then the EDS infrastructure is implemented as a Content Navigator plugin. One of the configuration parameters of this plugin is the URL of the location where your EDS servlet is deployed. Relative to this URL there are two requests the EDS infrastructure will perform:
* `<rooturl>/types`: a GET request that returns a JSON object that describes the classes handled by the EDS servlet.
* `<rooturl>/type`: a POST request containing information in JSON format about the action that is performed
by the client. The response of this request is again a JSON object, describing how the client should behave
for certain properties. For instance the following response object notifies the client that in
this situation a property, which is writeable in other situations, should be readonly:

```json
{
   externalDataIdentifier: "EDS API",
   properties: [
      {
         symbolicName: "PropertyName",
         displayMode: "readonly"
      }
   ]
}
```

If you are using IBM Case Manager, then only the later call is used for every Case Type in your solution. You have to use
the Case Manager Configuration Tool to configure the EDS URL for your solution.
> Make sure that you redeploy your solution when you (re)configure your EDS URL

# Introducing the EDS Servlet API

Managing all this JSON data can be quite cumbersome in practice. Therefore this article
introduces the EDS Servlet API. This API is a very thin layer around the requests and responses, using the [Jackson libraries](https://github.com/FasterXML/jackson) to do all
the JSON heavy lifting. The incoming JSON is parsed into Java POJO classes. The output data is
constructed using Java classes and converted to JSON using Jackson. The deserializing
and serializing of the data is all handled in the Java class `AbstractEDSServlet`. If
you want to use this this API all you have to do is provide an implementation of a
servlet extending the `AbstractEDSServlet` class:

```java
@WebServlet(description = "An example of an EDS servlet.",
            urlPatterns = { "/type/*", "/types"})
public class EDSExampleServlet extends AbstractEDSServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void handleRequest(ExternalDataRequest dataRequest, ExternalDataResponse dataResponse) {

		  // TODO: add your implementation code here...
	}
}
```

The incoming data is represented by the class implementing the interface `ExternalDataRequest` and the
outgoing data is constructed using the `ExternalDataResponse` interface. A typical implementation will
use the `getProperty()` method to fetch an incoming property. Next the `Property` object is modified using the different getter and setters. Finally the modified property object is added to response object:

```java
Property property = dataRequest.getProperty("PropertyName");

// Add your business logic here...
property.setDisplayMode(DisplayMode.readonly);

dataResponse.addProperty(property)
```

The [documentation](https://github.com/ecmdeveloper/eds-servlet) of the API contains
examples how to implement the EDS component for both IBM Case Manager and IBM Content Navigator.

# But wait there is more!

Debugging the EDS component can be very cumbersome. The client contains some optimizations and caches
some of the information provided by the EDS component. It is therefore very handy to know the different
requests made from the client and the responses provided by your implementation. To accomplish this
the servlet serves a ping page. The ping page can be enable by adding an URL pattern for the ping page. The declaration of your class extending `AbstractEDSServlet` class should now look like this:

```java
@WebServlet(description = "An example of an EDS servlet.",
            urlPatterns = { "/type/*", "/types", "/ping"})
public class EDSExampleServlet extends AbstractEDSServlet {

	  // The rest is unchanged...
}
```

In the ping page you can now turn tracing on and off. If you turn tracing on then you can inspect the ten last requests made to your EDS component.

![ping_page]({{ site.url }}/img/posts/ping-page.png)

## Reference

The javadoc documentation for this API can be found [here](http://ecmdeveloper.com/eds-servlet/).


