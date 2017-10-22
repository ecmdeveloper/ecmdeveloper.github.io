---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

# Console Projects

Console projects give you the possibility to run Java methods using the current selection as the input. This can be either objects you are browsing with the Object Stores viewer or the result of a query. The output of these Java methods can be printed to the Eclipse console using a simple `System.out.println()` method, hence the name "Console Projects". This way you can add functionality that is at this moment not present in the ECM Developer plug-in. It also saves you from creating a full blown project every time you want to do something simple.

## Creating Console Projects
For the Console Project to work you have to use the New Project wizard to create a new project. Depending on your target you can either choose a new __Content Engine Console Project__ or a new __CMIS Console Project__. These wizards can be found in the __ECM Developer__ category:

![new_console_project]({{ site.url }}/plugin/images/new_console_project.png)

The wizards follow the standard steps you take when you create a new Java Project. When the wizard is finished it adds the infrastructure necessary for running your Console Project to the newly created project. This includes references to the relevant Java API.

## Creating Console Classes
The Java method that you will be calling must be encapsulated in a Java Class. You can either use the New Console Class wizard to generate the Java Class for you or create a new Java Class from scratch. There are two requirement for this class:

*  The class should have a no arguments constructor.
*  The class contains a public Java method which takes one argument with the type Object. You can cast this object in the Java method to your specific need.

A minimal Java class will look like this:

```java
package main;

public class MinimalConsoleClass {

	public void run(Object object) throws Exception {

		// TODO write code

	}
}
```

If you want to generate the Java Class then you can start the wizard using the standard New wizard of Eclipse. The wizards for Content Engine and CMIS client classes are both located in the ECM developer category.

* In the first page the basic characteristics of the Java class is configured:<br>
![new_console_project_class]({{ site.url }}/plugin/images/new_console_project_class.png)
* In the second wizard page the details of the method can be configured:
![configure_method]({{ site.url }}/plugin/images/configure_method.png)<br>
You can specify the name of the method and the type of the object you expect as the input. The generated method will contain code with a cast to the appropriate Java class. This can of course be changed to anything you like.

The generated code for a Content Engine Console Class targeting documents will look like this:

```
package client;

import com.filenet.api.core.Document;

/**
 * @author ricardo.belfor
 *
 */
public class MyConsoleClass {

	/**
	 * This method is called for every selected object. The signature of the
	 * method should not be changed. The name of the method and the rest of the
	 * code can be adjusted to your needs.
	 *
	 * @param object
	 *            the selected object
	 * @throws Exception
	 *             the exception. Exceptions are displayed in the console and
	 *             does not stop the execution of the rest of the selected
	 *             objects.
	 */
	public void run(Object object) throws Exception {
		Document document = (Document) object;

		// TODO Auto-generated method stub

	}

}
```

## Coding Console Classes

The Java API you are using for coding the console classes depends on your target:

* If you are targetting the __Content Engine__ then you write your Java code using the standard Content Engine Java API.
* If you are targetting a __CMIS__ repository then you can write your Java code using the Open CMIS client libraries. Details about this library can be found at <a title="chemistry.apache.org" href="http://chemistry.apache.org">chemistry.apache.org</a>.


When you are writing code for a Content Engine object store then the single object used as the input for the method can be used to access most of the Java domain model. From a `Document`, `Folder` and `CustomObject` object you can get the `ObjectStore` object, and from this object you can go almost anywhere.

## Running Console Projects

Before you execute the method make sure that the Java class is built by the Eclipse IDE. This will normally be the case if you are building the code automatically. Also make sure that you have placed a break point if you are executing in debug mode.

The method can be executed using the __Run Console Project__ command of the context menu. This will start a wizard:

* In the first page you have to specify the method and you configure if you want to start in debug mode:<br>
![select_method]({{ site.url }}/plugin/images/select_method.png)<br>
The method is specified using the method selection dialog which is activated by the __Browse__ button. The method selection dialog consists of two parts:<br>
![mehod_selection_dialog]({{ site.url }}/plugin/images/mehod_selection_dialog.png)<br>
The top part shows all the Console Project Classes in the workspace and the bottom part contains all the eligible methods of the selected class. If you don't see you class here you have probably not created the Console Project using the wizard.

* In the optional second page of the wizard you can specify if you want to use other credentials instead of the default credentials:<br>
![configure_launch_credentials]({{ site.url }}/plugin/images/configure_launch_credentials.png)

If the wizard is finished the current selection is used as the input for the method. Output can be shown in the Console using the standard `System.out.print()` methods. Errors are also shown in the Console. An exception thrown when a object is processed will not terminate execution. A exception thrown during the initialization of the class will stop execution.
