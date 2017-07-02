---
title: "Implementing an EDS service with Node.js"
date: 2016-01-08 12:00
subtitle: Websites are becoming leaner, faster and more agile, as users demand quick, seamless experiences.
lead: 70% of today’s WordPress installations are vulnerable to known exploits (and WordPress powers more than 25% of the web). WordPress is used by 58.8% of all the websites whose content management system we know.
layout: "single"
permalink: /node-eds/
---


The external data service (EDS) is a powerful feature of the IBM Content Navigator and Case Management products. It allows you to control the behaviour of the properties in a centralised place without modifications to the user interface. The EDS infrastructure communicates with your application to control the different properties. The interface between the application and the implementation is based on passing JSON data back and forth. Because JavaScript is especially well suited to handle JSON data it makes sense to use JavaScript for the implementation. This article will show you how to implement an EDS service with Node.js. Node.js is a powerful JavaScript engine and is therefore well suited for this purpose.<!--more-->A big benefit of using Node.js is that it comes with a lot of additional modules which allows you to call external services, interact with your file system or connect to databases.

<blockquote>Even if you don't want to use Node.js in a production environment, the techniques described in this article are also perfect for quick prototyping. I use it a lot to make quick examples, while trying to understand the inner workings of EDS.</blockquote>

This article assumes that you know how to configure the EDS implementation for IBM Case Manager and IBM Content Navigator. If this is not the case then you can find information in the documentation. There are a lot of similarities between the EDS service implementations for Case Manager and Content Navigator, but some details are different. Both the different implementations wil be discussed in this artice.

<h2>An EDS implementation for IBM Case Manager</h2>

We assume that you already installed Node.js on your system. Otherwise you can go to https://nodejs.org for installation instructions. To create a new EDS service create a new folder and type the following commands:
```
npm init
npm install to-do --save
```
Now you have all the necessary modules to build an EDS service. We start with the easiest implementation of the two, the Case Manager service. Create the JavaScript file you specified when running the init command and add the following code to it:

{% gist rickx1/1c3490e4e6f8a13a7b65da4f5b5aeaba nodecm.js %}

The Express module is used as a HTTP server handling all the incoming requests (https://expressjs.com for more details). The JSON parser will parse the data comming from the EDS service and evaluate it to an object as the `body` object of the `request` object. The `getProperty()` function is a convenience function which uses the underscore module to get a specific property as an object. You have to supply the request object and the symbolic name of the property you want to fetch. An import property of the `body` object is the `requestMode` property. This property indicates the reason why your EDS implementation is called. Possible values for this properties are: `initialNewObject`, `initialExistingObject`, `inProgressChanges`, `finalNewObject` and `finalExistingObject`. Based on the value of this property you can change the behaviour of your implementation.

The actual handler function starts with an empty response object containing an empty properties array. The task of your implementation is to fill this array with modified property objects enriched with business logic. For every property you want to change, you have to fetch the property object, modify the property object and add the modified version of the property to the response object. For example the following code will change an editable property to a readonly property displaying the current date:

{% gist rickx1/1c3490e4e6f8a13a7b65da4f5b5aeaba simple-example.js %}

This is a list of all the properties you can set on a property object for a Case Manager implementation:
* `value`: the potential value of the property
* `displayMode`: the display mode, choose between `readonly` or `readwrite`
* `required`: boolean value indicating if the property is required, choose between `true` or `false`
* `hidden`: boolean value indicating if the property is hidden, choose between `true` or `false`
* `hasDependentProperties`: boolean value indicating if the value of this property controls the value of other properties, choose between `true` or `false`
* `maxValue`: the overridden maximum value of the property
* `minValue`: the overridden minimum value of the property
* `maxLength`: the minimum length of the property
* `customValidationError`: description why a property value is invalid.
* `customInvalidItems`: an array containing the indices of the invalid items of a multi-value property
* `choiceList`: an array containing the choice objects of the property.

Most of these properties are pretty straightforward, the `choiceList` property needs more explaining. The choice list data must be in a specific format, therefore the code to add a choice list is implemented in a separate module. Add the file `choicelist.js` to your project, and add this code to it:

{% gist rickx1/1c3490e4e6f8a13a7b65da4f5b5aeaba choicelist.js %}

This module can now be used as follows in your implementation:

{% gist rickx1/1c3490e4e6f8a13a7b65da4f5b5aeaba choicelist-example.js %}

First you have to add a `require` to the new module at the top of your implementation. Now you can use the `choicelist` variable to construct your choice list. If your value and the display name of the choice item are the same you can use the `addChoices()` method to add multiple values at once. The generate JSON data that will cause the Case Manager client to show a choice list looks like this:

{% gist rickx1/1c3490e4e6f8a13a7b65da4f5b5aeaba choicelist-data.js %}

<h2>An EDS implementation for IBM Content Navigator</h2>

The implementation for Content Navigator share most of the code of the Case Manager implementation, but there is some extra code necessary.

{% gist rickx1/e8949ff3bad374b2846b307429530b01 nodecn.js %}

The only difference between the Case Manager implementation is the function starting at line 34. In this function you have to specify the symbolic names of all the classes you want to be handled by the EDS implementation. These names are listed in the `types` array, the next line of code makes sure that it is returned in the correct JSON format. Note that this information is cached by Content Navigator. It is not uncommon that you have to logout to make your changes visible to the application.

In addition to the the list of all the properties you can set on a property object for a Case Manager implementation:
* `value`: the potential value of the property
* `displayMode`: the display mode, choose between `readonly` or `readwrite`
* `required`: boolean value indicating if the property is required, choose between `true` or `false`
* `hidden`: boolean value indicating if the property is hidden, choose between `true` or `false`
* `label`: _the label to put in front of the property editor_
* `format`: _regular expression describing the format of the property_
* `formatDescription: _tooltip describing the format of the property_
* `hasDependentProperties`: boolean value indicating if the value of this property controls the value of other properties, choose between `true` or `false`
* `maxValue`: the overridden maximum value of the property
* `minValue`: the overridden minimum value of the property
* `maxLength`: the minimum length of the property
* `customValidationError`: description why a property value is invalid.
* `customInvalidItems`: an array containing the indices of the invalid items of a multi-value property
* `choiceList`: **tree structure** _containing the choice objects of the property._

The `label`, `format` and `formatDescription` properties are new in this list. The `choiceList` property is the same, but the behaviour is a little bit different. In addition to a flat list, Content Navigator also supports a hierarchy, dividing the choice values in different categories. You can use the `addCategory` method of the `choicelist` module to build a hierarchical choice list. This example expands the choice list already presented in an earlier example:

{% gist rickx1/e8949ff3bad374b2846b307429530b01 choicelist-example.js %}

The possibilities with EDS implementations are endless. It takes some discovery to determine how you can use it, and equally important, when not to use it. The implementation presented in this article will help you with this journey.
