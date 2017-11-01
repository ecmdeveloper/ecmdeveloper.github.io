---
title: "Implementing security proxy based security"
date: 2017-07-01 12:00
layout: "single"
categories: "ContentEngine"
excerpt: "This article will show how you can use change preprocessors to implement a security scheme based on security proxies for the FileNet P8 Content Engine."
---

This article will show how you can use change preprocessors to implement a security scheme based on security proxies. Security proxies are object store objects that control the security of other object store objects by means of security inheritance. Defining an object-valued property that is designated as pointing to a security proxy object can control the security of an object. When a security proxy is assigned to this object-valued property the inheritable access control entries will be added to or can replace the access control entries already on the object.

## Configuring the data model

We saw in the previous section that you have to do some configuration if you want to use security proxies. So first the data model surrounding our implementation of security proxies will be discussed. We will use a simple scheme depicted by the following diagram:

`yuml.me`
[DepartmentDocument|Department: String; DepartmentAccess: Custom Object]--[DepartmentAccess|Department: String]

The class `DepartmentAccess` defines a custom object containing the desired security of the documents of the different departments within the organization. For every department within the organization an instance of this class is created, identified by the name property `Department`. This property is coupled to a choice list containing all the departments. The access rights on the security proxy can be as complex as you like, for instance giving modification rights to the members of the department but only viewing rights for the rest of the organization. Make sure that you define access control entries that can be inherited.

On the other side the document class called `DepartmentDocument` is used for documents that are controlled by the security proxy. Two properties play a role controlling the security of the document:
* The property `Department` is a required string valued property that contains the department the document belongs to. This property is also coupled to the choice list containing all the departments.
* The property `DepartmentAccess` acts as the security proxy object. This is a hidden required object valued property accepting instances of `DepartmentAccess` as a value.

## Selecting a server side extension

Now that we have our static data model in place it is time to describe the dynamic behavior. The challenge in this situation is to keep the two property values defined for the `DepartmentDocument` synchronized. The value of the `Department` property is set by the client application and the server must update the value of the `DepartmentAccess` property accordingly. There are two ways to implement this behavior with a server side extension. Because a property value of the document must be updated we have a choice between an asynchronous event action and a change preprocessor. My opinion is that a change preprocessor is the better way to do it. The reasoning behind this is that with a change preprocessor the new security is immediately present on the new or changed document but with an asynchronous event action it will take a fraction before the security is applied. This fraction can become significantly larger if something goes wrong. Consider for instance the situation where a security proxy is missing from the object store for a specific department. An asynchronous event action handler can at most notify this to an administrator in a log-file on the server, but the document will still contain incorrect security. A change preprocessor runs synchronously and can report this condition directly to the client by throwing a Java exception. This way the integrity of the security is maintained because the whole transaction is now rolled back. In IBM Content Navigator the cause of the exception is reported to the end user with the following dialog:
*TODO*
This dialog clearly states the cause of the problem and can trigger quicker corrective actions then an exception occurring on the background.

Now that you are convinced that a change preprocessor can be used to solve this problem, we will now look at the actual implementation.

## Implementing the change preprocessor

The implementation of the change preprocessor is shown below. There are five different functions, each implementing a specific part of the requirements.

{% gist rickx1/069179d79872f7d7c6a89f2ba69241a3 %}

The main entry point is the `preprocessObjectChange()` method.  This is the method that is called by the server when the object is changed. This method calls the `getDepartment()` method  to get the property object containing the value of the department property. This `getDepartment()` method returns a null value if the department property is not present in the property collection. Next, the `preprocessObjectChange()` method performs a null check and a dirty check on the department property object before the `updateSecurityProxy()` method  is called. This method has two arguments, the source object and the value of the department property. The responsibility of this method is to set the value of the security proxy property `DepartmentAccess`. The value of this property is provided by the `getDepartmentAccess()` method, which takes the value of the department property and the object store as its arguments.  This method uses the `searchDepartmentAccess()` method  to query for a security proxy object with a matching department name. If no matching security proxy object is found a runtime exception is thrown, triggering a rollback of the whole transaction. Instead of a search the security proxy object can also be accessed by path. However in that case an exception is thrown when the object cannot be found. This exception must be caught to provide a more meaningful message to the user. Therefore a search is in this case a more elegant solution.

The search also provides opportunities for further expansion of this simple example. In this case, the value of the department property is used as the key to a specific security proxy. You can however use a more elaborate scheme where more properties of the source object play a role finding the corresponding security proxy. You could for instance add a status property to the source object’s class definition with “open” and “close” as accepted values. Now the inherited security of an object with the status “open” can differ from  an object with the status “closed”. Also in this extended case the change preprocessor handler will make sure that the source object will always have the correct security.

## Final Thoughts
