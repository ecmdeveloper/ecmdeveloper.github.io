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

Installing the FileNet artifacts is done using Maven.
