---
title: "Adding your FileNet dependencies to Maven"
date: 2017-07-01 12:00
layout: "single"
categories: "FileNet"
excerpt: "Most of the FileNet projects are build with Ant. This article shows how you can change your build process to Maven."
---

# FileNet ❤ Maven

When you are following the different tutorials for FileNet programming, you frequently
end up with a build.xml file which you have to execute with Ant. A recurring task is
setting up the different dependencies for your project. Usually I end up with a lot of
jar-files, with unknown version, scattered at different locations. Maven can be used to
centralize these jar-files and simply add these to your projects. This article will show
you how to accomplish this task and at the same time fix potential version problems.
It will require some initial investment from your part, but you will quickly enjoy the benefits.

<blockquote>As a side note, would it not be great if IBM would make the development artifacts more
Maven friendly, by for example adding a pom.xml file? Or even one step further, add the different
public API's to a central repository?</blockquote>

Installing the FileNet artifacts is done using Maven. The filenet-maven-ce project
contains the pom file necessary to install the CE API. The procedure to do this follows
the Maven opinionated approach, requiring minimal configuration.

* Checkout the project from Github
* Copy the CE API files to the folder TO DO.
* Now you have to obtain the version of your CE API. The easiest way to do this is to look in the manifest file of the jace.jar file and obtain the version from  there.
* next run the Maven command TO DO with the version number as the value of the jace.jar version property.  
* Let Maven do it's magic

Now you are all set. Add the following dependency to your pom file and the CE API and all it's dependencies are added to your project:
TO DO
Make sure that every time you upgrade or patch your system you update your repository accordingly. 

Also note that the jar files you add this way are necessary if you want to connect using the WSI interface. If you want to use the EJB interface you need some more dependencies. These are not in the scope of this project.
