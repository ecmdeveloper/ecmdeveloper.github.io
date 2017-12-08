---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

## Version 2.3.0.0 11/11/2017

* Simplified installation instructions
* Dropped Content Engine version 4.5 support
* Dropped CMIS support
* Changed build process to Tycho
* Added Change Preprocessor support
* Small bug fixes
* Displaying also hidden properties in property editor

## Version 2.0.0.0 5/27/2012

* Support for CMIS repositories using the Apache Chemistry OpenCMIS client libraries.
* In the terminology the label "Content Engine" is in most places changed to "Object Store" or "ECM Developer".
* Console Projects for the execution of Java code with the selection as input.
* Creating Event Actions based on the Code Module being edited.
* Storing passwords is optional, each time a connection is made the credentials can be changed.
* Content search is temporarily disabled, did not work in the previous version.
* Added support for the IN keyword in the query designer.
* New properties dialog for Object Stores. The configuration of the connection can be edited here.
* Various small bug fixes.
* Tested with FileNet Content Engine 4.5, 4.5.1, 5.0 and 5.1 and various CMIS enabled repositories.
* Web site updated.

## Version 1.5.0.0 6/x/2011

* Searching of folders, documents and custom objects.
* Fixed bug with empty code module after initial creation.
* Multiple Java elements can be added at once to a code module.

## Version 1.4.0.0 2/6/2011

* Creation of new folders, documents and custom objects.
* Tracking of files checked out into the workspace.
* Favorites view.
* Content Engine Perspective, modified Class Diagram editing perspective.
* Double click also connects Object Stores and expands Folders.
* The code module editor is simplified, Java projects, packages or source files can be added as a whole
* The delete function is improved, allowing bulk deletion of objects with children
* Fixed bug accessing multiple Content Engines with different password.

## Version 1.3.0.0 7/26/2010

* Sorted Object Stores view
* Checkout, Checkin, Save and Cancel Checkout of a document.
* Double click opens a document in the viewer.
* Viewing documents with multiple content elements.
* Viewing specific versions of a document.
* Downloading specific version content.
* Deleting specific versions of a document.
* Comparing documents with each other.
* Comparing two specific versions of a document.
* Comparing versions of a document with a local file.
* Editing specific versions of a document.
* Adding and removing an object store was not reflected in the Object Store Classes view.

## Version 1.2.0.0 4/25/2010

* Added Content Engine 4.5.1 support.
* Added Class Diagram editor.

## Version 1.1.0.0 3/5/2010

* Added support for folder, document and custom object editing.
* Fixed bug in code module editor, code modules with multiple content elements where not correctly updated.
* Fixed bug in refresh, changes made outside the plug in caused an exception.
