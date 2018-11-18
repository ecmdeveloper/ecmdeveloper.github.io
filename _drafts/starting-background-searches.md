---
title: "Starting background searches on a schedule"
date: 2018-01-01 12:00
layout: "single"
categories: "ContentEngine"
excerpt: "Background Searches can execute on a schedule but are not started on a schedule.
This article will show how to use a JavaScript sweep action to periodically start background searches"
---

The great benefit of background searches is the ability to aggregate data stored in your object store. An important feature of background searches is the abbility to count "things", providing you with the opportunity to create usage reports of objects in the object store. Usealy your are not interested in one usage report, but you want to see how that information evolves over a period of time. This article will show you how you can accomplish this task. Spoiler alert, we will be using a sweep!

## Background search basics

The background search infrastructure contains different parts:

* A background search search result class. Because we are dealing with a _background_ search, the result of that search must be stored somewhere in the object store. Therefore a background search must be coupled to a subclass of the class ```TODO```, for each value that is returned from the background search a instance of this class is created.

> A background search can also create a CSV file containing the result of the search.

* A background search definition. The definition contains the actual query you want to perform. Note that a query can also be parameterized, these parameters are also part of the background search definition.

* The final part is the background search job. The job takes a background search definition as an input and produces background search result items as output.

You can create a background search job using the ACCE or programmaticaly. As we want to schedule this job, programmaticaly is the way to go. But if we go this way, we need a way to execute the code to start this job. 

## Sweeps to the rescue!

The solution is a little bit of a hack, but it works very effectively (credits to Ivo Jonker for the original idea). The Content Engine has the functionality to run a sweep. A sweep takes a target class and a filter as input and will process all the matching items. The entity processing the items is a sweep action. We can start the background search job from this action, but we have to make sure that we will only run it once! The trick we can use here is to use the sweep action as the trigger of the sweep. 

The following piece of Java code will create a new background search job:

```java
// Insert your Java code h
```