---
title: "Implementing an EDS service with Java"
date: 2017-07-01 12:00
layout: "single"
categories: "EDS"
excerpt: "This article shows you how to build an External Data Service with Java. It provides an API which takes care of all the JSON stuf, you just has to focus on the business rules."
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

These are just a few examples of the numerous possibilities. In this blog post a
API is introduced to build all this functionality. This article assumes that the reader knows how to create and deploy web applications based on servlets.

# The External Data Services contract

An EDS component is a web application which responds to specific GET and POST request from the EDS infrastructure. The interaction between the component and the infrastructure depends on the application you are using.

If you are using IBM Content Navigator from a browser or from a Microsoft Office application then the EDS infrastructure is implemented as a Content Navigator plugin. One of the configuration parameters of this plugin is the URL of the location where your EDS component is deployed. Relative to this URL there are two requests the EDS infrastructure will perform:
* `<rooturl>/types`: a GET request that returns a JSON object that describes the classes handled by the EDS servlet.
* `<rooturl>/type`: a POST request containing information in JSON format about the action that is performed
by the client. The response of this request is again a JSON object, describing how the client should behave
for certain properties. For instance the following response object notifies the client that in
this situation a property, which is writeable in other situations, should be readonly:

TODO: more case manager info

If you are using IBM Case Manager, then only the later call is used for every Case Type in your solution.

# The EDS Servlet API

Managing all this JSON data can be quite cumbersome in practice. Therefore this article
introduces the EDS Servlet API. This API is a very thin layer around the requests and responses, using the [Jackson libraries](https://github.com/FasterXML/jackson) to do all
the JSON heavy lifting. The incoming JSON is parsed into Java POJO classes. The output data is
constructed using Java classes and converted to JSON using Jackson.

The incoming data is converted in a Java object of type `ExternalDataRequest`:

```java
String getObjectId() // The ID of the object being edited
String getObjectType() // The object type that is being edited
Property getProperty(String name) // A method to fetch a property object
RequestMode getRequestMode() // The reason why the EDS component is called
String getRepositoryId() // The ID of the repository
Map<String, Object> getClientContext() // A map containing contextual information about the request.
String getExternalDataIdentifier()
```

The outgoing data is constructed using a Java object of the type `ExternalDataResponse`:
```java
void addProperty(Property property); // Adds a Property object to the external data response.
                                     // The property object describes the
                                     // required behavior of the corresponding property.
```

The deserializing and serializing of the data is all handled in the Java class
`AbstractEDSServlet`. So to use this API all you have to do is provide an implementation
of a servlet extending the `AbstractEDSServlet`:

```java
@WebServlet(
		description = "An example of an EDS servlet.",
		urlPatterns = { "/type/*", "/types"
		})
public class EDSExampleServlet extends AbstractEDSServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void handleRequest(ExternalDataRequest dataRequest, ExternalDataResponse dataResponse) {

		  // TODO: add your implementation code here...
	}
}
```
The [documentation]((https://github.com/ecmdeveloper/eds-servlet)) of the API contains examples how to implement the EDS component for both IBM Case Manager and IBM Content Navigator.

# But what about debugging?
