---
title: "Implementing an EDS service with Java"
date: 2017-07-01 12:00
layout: "single"
categories: "EDS"
excerpt: "This article shows you how to build an External Data Service with Java. It provides an API which takes care of all the JSON stuf, you just has to focus on the business rules."
---

<i class="fa fa-fw fa-github" aria-hidden="true"> </i>The Code on GitHub

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


# How to use the API

This implementation uses the [Jackson libraries](https://github.com/FasterXML/jackson) to do all
the JSON heavy lifting. The incoming JSON is parsed into Java POJO classes. The output data is
constructed using Java classes and converted to JSON using Jackson.

The incoming data is converted in a Java object of type

```java
public String getObjectId() // The ID of the object being edited
public Map<String, Property> getProperty() // A map of all the properties of the object
public RequestMode getRequestMode() // The reason why the EDS component is called
public String getRepositoryId() // The ID
public Map<String, Object> getClientContext()
public String getExternalDataIdentifier()
```

Method | Description
-------|------------
`public String getObjectId()` | The globally unique identifier (GUID) or persistent identifier (PID) that identifies the item that is being edited.
public Map<String, Property> getProperty() | An map that contains values for the properties that are defined for the class or item type. For each property, the request contains the symbolic name and the property value.


{% gist rickx1/1a384706dbb5ff9b4e035b2f0585286b %}


<div style="background-color: #fef4e6">
### `private List<Property> properties`

An array that contains values for the properties that are defined for the class or item type. For each property, the request contains the symbolic name and the property value.

## `private Map<String, Object> clientContext`

An array that contains a series of key value pairs that specify contextual information for a specific class or item type. This parameter is used to send information to an external data service when an IBM Content Navigator user begins to add a document, add a folder, use an entry template, or create a search.
</div>

* Deploy servlet
* Test the servlet
* Register the servlet url in the Case Manager configuration tool.
