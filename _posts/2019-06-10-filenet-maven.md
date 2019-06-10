---
title: "Adding your FileNet dependencies to Maven"
date: 2019-06-10 12:00
layout: "single"
categories: "FileNet"
excerpt: "Most of the FileNet projects are build with Ant. This article shows how you can change your build process to Maven and manager your versions."
---

<i class="fa fa-lg fa-github" aria-hidden="true"></i> [Code on github](https://github.com/ecmdeveloper/filenet-maven)

When you are following the different tutorials for FileNet programming, you frequently
end up with a build.xml file which you have to execute with Ant. A recurring task is
setting up the different dependencies for your project. Usually I end up with a lot of
jar-files, with unknown version, scattered at different locations. Instead of building your project with Ant, you can also use Maven. Maven can be used to centralize the jar-files and simply add these to your projects. This short article will show you how to accomplish this task and at the same time fix potential version problems. It will require some initial investment from your part, but you will quickly enjoy the benefits.

<blockquote>As a side note, would it not be great if IBM would make the development artifacts more
Maven friendly, by for example adding a pom.xml file? Or even one step further, add the different
public API's to a central repository?</blockquote>

Installing the FileNet artifacts is also done using Maven. The [filenet-maven-ce](https://github.com/ecmdeveloper/filenet-maven) project contains the pom-file necessary to install the Content Engine (CE) API. The procedure to do this follows the Maven opinionated approach, requiring minimal configuration. 
 
* Checkout the project from GitHub.
* Copy the CE API files to the folder `src/main/resources/ce_api`. The Content Engine Client files can be downloaded using the _IBM Administrative Console for Content Platform Engine (ACCE)_ application. Go to __Domain > IBM FileNet Content Manager > Java CEWS client__ and download the client files.
* Now you have to obtain the version of your CE API. The easiest way to do this is to look in the `MANIFEST.MF` file of the `jace.jar` file and obtain the version information from there.
* next run the Maven command `mvn install -Djace.version=5.2.1.4` with the version number as the value of the `jace.jar` version property.  
* Let Maven do it's magic...

Now you are all set. Add the following dependency to your pom-file and the CE API and all it's dependencies are added to your project:
```xml
<dependency>
   <groupId>com.ibm.filenet</groupId>
   <artifactId>jace</artifactId>
   <version>5.2.1.4</version>
</dependency>
```
Make sure that every time you upgrade or patch your system you update your repository accordingly.

Also note that the jar-files you add this way are necessary if you want to connect using the WSI interface. If you want to use the EJB interface you need some more dependencies. These are not in the scope of this project.
