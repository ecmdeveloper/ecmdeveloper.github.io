---
layout: single
author_profile: false
toc: true
sidebar:
  nav: "plugin"
---


# Actions

There are different actions you can perform while you are browsing and searching for objects.

## View

Documents can be viewed using the __View__ command of the context menu or by double clicking with the mouse. If the document class supports versioning then this menu contains three sub items:

* __Released Version__: this will show the released version of the document. If the latest major version is zero the current version is shown. Otherwise the version with a zero minor version and the highest major version is shown. If a document is double clicked then also the released version is shown.
* __Current Version__: this will show the version with the highest major and minor version.
* __View Document Version__: this will show specific versions of a document. First a dialog is shown, displaying all the versions of the document. All the versions selected in this dialog will be shown.

When viewing starts, first a read only local copy of the content of the document is made. Next the file is opened, either in the available Eclipse editor or in an external system editor. If the document has no content then a message is shown. If the document contains multiple content elements then specific content elements of the document can be selected. If possible the local copy will be deleted when the editor closes. If this is not possible for the specific editor then the file will be deleted when Eclipse terminates or restarts.

## Rename

An Object Store item can be renamed using either the __File > Rename__ command or the __Rename__ command of the context menu. A dialog will appear where a new name for the item can be entered:

![Rename]({{ site.url }}/plugin/images/rename.jpg)

A check is made if the entered name is correct.

## Refresh
An Object Store item can be refreshed using either the __File > Refresh__ command or the __Refresh__ command of the context menu. This command will get a fresh copy of the item on the server.

## Move

An Object Store item can be moved using either the __File > Move__ command or the __Move__ command of the context menu. A dialog will appear where a new folder location for the item can be selected:

![move]({{ site.url }}/plugin/images/move.jpg)

In case of a multiple selection the new location will apply for all the items selected.

## Add to Favorites

An Object Store item can be added to Content Engine Favorites view using the __Add to Favorites__ command of the context menu. The item is now also visible in the Object Store Favorites view directly under the Object Store the item belongs to. The __Remove from Favorites__ command of the context menu can be used to remove the item from the Object Store Favorites view.

## Compare

Documents can be compared using the __Compare__ command of the context menu. The Compare command is only available if your selection contains only document that are versioned or if you selection contains document that are only not versioned. If the documents you selected are not versioned then this menu contains two subitems:

* __Document With Local File__: this will compare the document with a local file in the Eclipse workspace.
* __Documents With Each Other__: this will compare two documents with each other.

If you selected documents that are versioned then this menu contains four sub items:

* __Released Version With Local File__: this will compare the released version of the document with a local file in the Eclipse workspace.
* __Other Versions With Local File__: this will compare other versions of the document with a local file in the Eclipse workspace. First a dialog is shown, displaying all the versions of the document. All the versions selected in this dialog will be compared with a local file.
* __Versions With Each Other__: this will compare two version of the same document with each other. First a dialog is shown, displaying all the versions of the document. If there are not two versions selected then an error message will be displayed.
* __Released Versions With Each Other__: this will compare the released versions of two documents with each other. If there are not two documents selected then an error message will be displayed.

The contents of the documents are compared using the standard text compare engine of Eclipse:

![text_compare]({{ site.url }}/plugin/images/text_compare.png)

## Delete

Object Store items can be deleted using either the __Edit &gt; Delete__ command, the delete key or the __Delete__ command of the context menu. This command will open the delete wizard. If the selection contains a folder then first the __Configure Delete__ wizard page is shown:

![configure_delete]({{ site.url }}/plugin/images/configure_delete.png)

Here the actions to perform with the contained objects can be configured. They can be either left unfiled or deleted along with the folder.

The next wizard page gives a preview of all the items being deleted with the current selection:

![preview_delete]({{ site.url }}/plugin/images/preview_delete.png)

When this wizard page is finished a delete confirmation is requested and the objects are being deleted. During the delete action this operation may be canceled. In this case there is no rollback of the items already deleted.
## Download
The content of documents can be downloaded using the __Download__ command of the context menu. This menu contains the same sub items as the <a title="View" href="/actions/view/">__View__ </a>command, but instead of opening a viewer the content can be downloaded into the current Eclipse workspace. The user is prompted for a filename and a location in the workspace. If a existing document in the workspace is about to be overwritten then a confirmation is asked.
