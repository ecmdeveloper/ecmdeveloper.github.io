---
title: "Developing Change Preprocessors for the FileNet P8 Content Engine"
date: 2017-12-27 12:00
layout: "single"
categories: "ContentEngine"
excerpt: "This article will show you how to benefit the most from Change Preprocessor Actions. It shows you when to use them and how to structure your code to suit your use case"
---

<i class="fa fa-lg fa-github" aria-hidden="true"></i> [Code on github](https://github.com/ecmdeveloper/server-extensions)

## Introduction

This article will show you how to benefit the most from Change Preprocessor Actions. The article can be viewed as a follow-up on the previous article I wrote called [Developing Event Actions for the FileNet P8 Content Engine]({{ site.url }}/contentengine/developing-event-actions/). In that article the focus was on Event Actions, but some of the issues discussed in that article applies to all Content Engine server extension. For instance logging and deployment are topics that fall into this category.

The previous article also showed how you can build a number dispenser that sets unique numbers on object that are created. This article will show you how you can use a change preprocessor action to improve this process.

## Change Preprocessor Actions Basics

Change preprocessors as a technology are very straightforward. When configured for a specific object class, every create, update or delete action will result in the synchronous execution of the change preprocessor handler coupled to the change preprocessor action. Note that this behavior is unconditional, besides enabling and disabling the change preprocessor, you have no additional control over the process. If you want to fine tune the behavior then you will have to do that in the implementation of the change preprocessor handler. In this article you will also learn how to do this.

You can create or change a change preprocessor with the *IBM Administrative Console for Content Platform Engine (ACCE)* or by writing custom code. The documentation contains a lot of code examples how to do this. Besides the configuration of the change preprocessor action handler there are not very much additional configuration operations that you can perform. The name, the description and the status (enabled or disabled) are the only configuration options.

Just like any server side extension you can write the code for the change preprocessor handler in either Java or JavaScript. A simple change preprocessor handler written in Java looks like this:

```java
import com.filenet.api.core.IndependentlyPersistableObject;
import com.filenet.api.engine.ChangePreprocessor;
import com.filenet.api.exception.EngineRuntimeException;

public class SimpleChangePreprocessor implements ChangePreprocessor {

  public boolean preprocessObjectChange(IndependentlyPersistableObject object)
    throws EngineRuntimeException {

    // Your code goes here

    return false;
  }
}
```

The change preprocessor handler implements the `ChangePreprocessor` interface. This interface contains only one method called `preprocessObjectChange()` which takes an object that implements the `IndependentlyPersistableObject` interface as an argument. The return value of this method is a boolean value, indicating if the changes the change preprocessor handler made to the property collection of the object must be persisted in the database. If the change preprocessors handler return false then the changes made by the change preprocessor handler to the source object are not saved. If an exception is thrown in the change preprocessor action handler then the whole transaction is rolled back.

Deploying also works the same as other server side extension. If you have written your change preprocessor handler as a Java class you can either deploy the compiled Java class using a code module or by adding the compiled Java class as a jar-file to the class path of your application server.

## 	Choosing the right server extension

Now that the basics of the change preprocessor are covered it is time to know when to use a change preprocessor. The infrastructure surrounding change preprocessor handlers is not as elaborate as the infrastructure available for event actions and subscriptions. Taking this into consideration change preprocessor actions should be viewed as an addition to event actions and not as a replacement. It is up to you the developer to make a choice between the different technologies based on the specifics of the problem you are trying to solve. However, in practice, most of the time you probably will choose event actions over change preprocessors.

In the documentation there is a nice table describing the difference between change preprocessors, synchronous event actions and asynchronous event actions: [Change Preprocessors versus Event Handlers](https://www.ibm.com/support/knowledgecenter/en/SSNW2F_5.5.0/com.ibm.p8.ce.dev.ce.doc/customHandlers_comparison.htm). Change preprocessors are closely related to synchronous event actions. They are executed during the transaction surrounding the save action on an object. However, change preprocessors are executed *before* the actions are persisted and synchronous event actions are executed *after* the actions are persisted. Therefore it is possible to modify the object that is about to be saved in a change preprocessor action handler. This is especially interesting for “settable-only-on-create” (SOOC) properties. This type of properties can only be set before and object is created, and change preprocessor action handlers can be used to set the value of these properties.

Note that despite its limitations, change preprocessor open up a whole new category of objects for server side processing. You can use event actions handlers only for objects implementing the `Subscribable` interface and change preprocessor can be used for all objects implementing the `IndependentlyPersistableObject` interface. There is some overlap between the objects implementing these interfaces, but the number of object implementing the `IndependentlyPersistableObject` interface is significantly larger. You can check the Java API documentation for details, and decide if implementing a change preprocessor for one of these objects can solve your problem.

## Number Dispenser Revisited

In the previous article written about server extensions, a number dispenser was introduced. With the event action presented in that article you were able to assign unique numbers to objects created in the object store. The unique number was assigned to a specific property of the object class. Because of the nature of event actions this property could not be defined as a SOOC property. However in practice you will usual would like to prohibit the change of such a property value. You can prohibit changing of this value in your client application. For instance, you can use an [External Data Services]({{ site.url }}/eds/implementing-an-EDS-service-with-node/) component to implement this behavior. A more elegant solution is to configure this property as SOOC and use a change preprocessor to set the value. As we already saw in the previous paragraph, this situation is exactly why change preprocessors were invented.

## Choosing the right moment
The change preprocessor handler is invoked every time the object changes. However, when we assign unique numbers we are only interested in the initial creation of the object. When we were using event actions, we could use a subscription filter to do this for us, but the infrastructure around change preprocessor does not provide this functionality. Fortunately this can be solved using Java code.

Every change to an object implementing the `IndependentlyPersistableObject` interface is first appended to an array of `PendingAction` objects associated with the object. You can access this array using the method with this signature:

```java
public PendingAction[] getPendingActions();
```

When the `save()` method of the object is invoked, the pending actions will be persisted only after all the change preprocessor actions are handled for the specific object class.

When an object is created the pending actions array will also contain the `Create` action we are interested in. If we encounter this action then we are in the middle of the creation of an object. Putting it into code we have the following method:

```java
boolean isCreate(IndependentlyPersistableObject object) {
  PendingAction actions[] = object.getPendingActions();
  for ( PendingAction action : actions) {
    if ( action instanceof Create ) {
      return true;
    }
  }
  return false;
}
```

For folders and custom objects our problem is now solved. For documents there is still an extra step to take. The problem with create actions is that a checkout of a document will also manifest itself as a create action, in this case the creation of the reservation document. A create action caused by a checkout can be detected by using the `getReservationType()` method of the `Create` action. For a checkout this method will return the value `ReservationType.EXCLUSIVE` and for an initial create followed by a checkin the reservation type is null. Putting it all together, the previous method can be modified to filter out the initial create of a document:

```java
boolean isCreate(IndependentlyPersistableObject object) {
  PendingAction actions[] = object.getPendingActions();
  for ( PendingAction action : actions) {
    if ( action instanceof Create ) {
      // These two lines are the new code
      ReservationType reservationType = ((Create) action).getReservationType();
      if ( reservationType == null ) return true;
    }
  }
  return false;
}
```

This method will walk through all the pending action of the object. When a Create action is encountered, the value of the reservation type is examined. If the value of the reservation type is null, then you are dealing with an initial create. You can use this technique also to implement alternative schemes. If you want to give every version of a document a unique number then you can focus on the `Checkin` action.

## Doing the actual work
Now that we know when to apply the unique number to the object, it is time to actually apply the unique number. Before we do that, first a reminder how this was done using an event action handler. In the *previous article*, using an Event Action, we used the following code to accomplish this task:

```java
public void onEvent(ObjectChangeEvent event, Id subscriptionId) throws
              EngineRuntimeException {

  String classId = event.get_SourceClassId().toString();
  Id objectId = event.get_SourceObjectId();
  IndependentlyPersistableObject object =
    (IndependentlyPersistableObject) objectStore.getObject(classId, objectId );
  String value = getValueFromDispenser(event.getObjectStore(),"/Dispensers/MyDispenser");
  object.getProperties().putValue("ObjectId", value );
  object.save( RefreshMode.NO_REFRESH );
}
```

In this code we assume that we have a custom object with a pathname `/Dispensers/MyDispenser` holding the last value of the unique number, and we are setting the value of a property called `ObjectId`. The source object provided by the event is a shallow copy of the object, so you have to go back to the object store object to get the real object. Next the `getValueFromDispenser()` method is used to obtain a new unique number. Finally the value is set and the object is saved using the `save()` method.

The implementation of the change preprocessor action handler is different in a number of ways:

```java
public boolean preprocessObjectChange(IndependentlyPersistableObject object)
                 throws EngineRuntimeException {

  if ( isCreate(object) ) {
    String value = getValueFromDispenser(
      ((RepositoryObject) object).getObjectStore(),"/Dispensers/MyDispenser" );
    object.getProperties().putValue("ObjectId", value );
    return true;
  }
  return false;
}
```

First the `isCreate()` method discussed earlier is used to check if this is the correct moment to apply the value. Fetching the unique number is done exactly the same way, by using the `getValueFromDispenser()` method. Next, we see the big difference in the code. Because the object we want to update is already supplied by the Content Engine, we don’t have to get the object we want to update. The value from the dispenser can be applied directly to the object. We also don’t have to call the `save()` method of the object (this method is actually forbidden in change preprocessors). The Content Engine will take care of this, if none of the additional change preprocessors will throw an exception. A return value of true is an indication to the Content Engine that the code changed the properties collection of the object. In all other situation a value of false is returned, indicating no change to the properties collection. One could argue if this code is easier than the code used in the event action handler, because of the extra `isCreate()` method we had to use to filter out the correct situation. However, remember that the real reason we are doing this is to be able to use a SOOC property, which cannot be modified after the object is created.

## Adding configuration

In the code examples we saw above there were some hard coded strings that will prevent easy re-use of you change preprocessor. The name of the property which will get the unique value and the path to the dispenser custom object are fixed. Especially the hard coded path is a problem because you may want to use a separate sequence of numbers for different object types. In the dispenser based on event actions and subscriptions we used the user string of the subscription to pass configuration information to the event action handler. However, this mechanism is not available for change preprocessors.

A prerequisite of a good configuration scheme is that the information is easily accessible. The only information passed to the change preprocessor is the object that is about to change, hence any information that this object carries can be used quickly. For instance, the class name of the object can act as a key to different dispensers. In code, it will then look like this:

```java
String dispenserPath = "/Dispensers/" + object.getClassName();
String value = getValueFromDispenser( ((RepositoryObject) object).getObjectStore(),
                                      dispenserPath );
```

In this case, you have to make sure that the title of the dispenser custom object matches the class name. A limitation of this method is that you cannot use the same dispenser for different object classes.

Another way to add configuration information to the object is to add a *transient* property to the class definition of the object and use the default value for that property as configuration information. Transient properties are properties that can be added to a class like regular properties. The big difference is that the values of these properties are not persisted in a database. The purpose of these properties is to have a mechanism to convey information from client side code to server side code, such as event action handlers and change preprocessor action handlers.

Just like “normal” properties, you can assign a default value to a transient property. This default value is included in the properties collection of the object just before it is about to be created, and this is exactly the situation when we want to set the value of a SOOC property. Using the *ACCE* you have to take the following steps to implement this mechanism:
* First you have to create a new string type property template called `DispenserPath`, make sure that in the wizard you select the option *Set other attributes* and change the value of the persistence from the default value of “System Table” to “Transient”:<br/><br/>
![transient-propertytemplate]({{ site.url }}/img/posts/transient-propertytemplate.png)<br/>
*	Next you need to couple this property template as a property to the class to which you want to add the configuration information. Use the properties dialog of this property to add configuration information in the form of a default value:
![transient-property]({{ site.url }}/img/posts/transient-property.png)<br/>
*	A good practice is to make this property hidden to prevent changing of the value by end users.

This default value is now included as a property value for every object that is created using this object class. You can now use the following code to access this default value:

```java
String dispenserPath = object.getProperties().getStringValue("DispenserPath");
String value = getValueFromDispenser( ((RepositoryObject) object).getObjectStore(),
                                      dispenserPath );
```

In the source code of this article this technique is also used to configure the property that gets the dispenser value [3]. Unfortunately this scheme only works for initial creates. Once an object is created the default value of the transient property is no longer included in the properties collection. One of the ways you can choose to solve this is to introduce a new custom object class carrying the configuration information. The class name of the object can act as a key in the path to the custom object coupled to a specific path.

<i class="fa fa-lg fa-github" aria-hidden="true"></i> [Code on github](https://github.com/ecmdeveloper/server-extensions)
