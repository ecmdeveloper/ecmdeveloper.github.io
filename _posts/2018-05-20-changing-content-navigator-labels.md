---
title: "Managing Content Navigator Labels"
date: 2018-05-01 12:00
layout: "single"
categories: "ContentNavigator"
excerpt: "Sometimes you may want to make small changes to the IBM Content Navigator interface. This article introduces a custom plugin which allows you to change the labels in a convenient manner."
---

<i class="fa fa-lg fa-download" aria-hidden="true"></i> [Download](https://github.com/ecmdeveloper/icn-plugins/releases/download/v1.0.0/labelmanager.jar)&nbsp;
<i class="fa fa-lg fa-github" aria-hidden="true"></i> [Code on github](https://github.com/ecmdeveloper/icn-plugins)

This article is the first one in a series of two where I will show you how to make small changes to the interface of Content Navigator. Although the changes are small, they can add value to the experience the user will have with the product. Sometimes a small change will significantly increase the usability for the user.

A common example is the wording of the user interface. Changing the wording a little bit might increase the understanding of the intentions. Take for example the term 'Class' as the label in front of a combobox where the user is supposed to select the document class. For some users the term ‘class‘ might sound a little bit abstract, but if you change it to ‘document type’ it might make more sense. This is especially the case in non-English environments, where the verbatim translation of the English terms is not always the best choice.

My colleague over at [www.ivojonker.nl](http://www.ivojonker.nl) created a [blog post](https://www.ivojonker.nl/?p=643) describing how to change the messages within the IBM Content Navigator interface. There is a JavaScript Dojo class called `ecm.Messages` containing all the messages coupled to a key. Internally, every time a message text is needed, a lookup is done using the key, fetching the message text coupled to the key. The class is populated at start-up from the client side.

As shown in the original blog post it only takes one line of code to change the original message to a new message. If you already have a custom Content Navigator plugin then you could easily squeeze these lines of code in the main file of your plugin. In that case stop reading and start coding!

However, if you want a way to accomplish this without writing a custom plugin yourself then the _labelmanager_ plugin presented in this article is for you. The different labels of the application can be registered at the bottom of the configuration page of this plugin:

![label-manager-Configuration]({{ site.url }}/img/posts/labelmanager-configuration-2.png)<br/>

In this page you can select a message key and change the existing text to a new text. Removing a line will restore the original text again. The result of this configuration is that this message:

![document-type-before]({{ site.url }}/img/posts/document-type-before.png)<br/>

is now changed to:

![document-type-after]({{ site.url }}/img/posts/document-type-after.png)<br/>

> No, I did not know in advance that the internal key of this message was actually document_type.

At this moment there is no support for localization, the entered text is used for all locales. Note that you have to restart the browser page for all the changes to be visible in the interface. Also make sure that the plugin is the first plugin that is loaded. In the list showing all the plugins you can change the order of the plugins.
