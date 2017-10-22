---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

The content of a document can be edited using the checkout and the checkin commands. If the content of the document is saved to the Workspace then the document can be tracked. In this case the document is coupled to the file in the Workspace. If the document is saved or checked in the the content of this file is used.
## Tracked Files View
All the documents that are tracked in the Workspace can be viewed in the Tracked Files View. This view can be opened by using the __Window &gt; Show view &gt;Other__ command. The view is located in the __ECM Developer__ category. If the __Object Store__ perspective is selected then there is a shortcut under the __Window &gt; Show view__ menu.

The Tracked Files View lists all the tracked files in the Workspace:

![tracked_files_viewer]({{ site.url }}/plugin/images/tracked_files_viewer.png)

Actions like Checkin, save and cancel checkout can also be performed using the context menu of this view. Additional actions from the context menu are Editing the tracked file in the available editor and removing the file from the tracked files list.
## Checkout
Documents can be checked out using the Checkout command of the context menu. A wizard will be shown allowing different checkout options to be set:

![configure_checkout]({{ site.url }}/plugin/images/configure_checkout.png)

If the content of the document is saved to a file in the Workspace then it can be tracked. This is the default setting. The file is now visible in the Tracked Files View.
## Save
The contents of a document can be saved using the __Save__ command of the context menu. For tracked documents saving can also be done using the __Object Store  &gt; Save__ command of the context menu of the file.

There is a difference in behavior between tracked and non-tracked documents. If the document is tracked then the content of the tracked file is used automatically and a dialog will appear allowing changing of the mime type of the document. This dialog will already contain the mime type of the existing document:

![save_tracked_file]({{ site.url }}/plugin/images/save_tracked_file.png)

When the document is not tracked then a wizard will be shown allowing the selection of the different content elements:

![select_document_content]({{ site.url }}/plugin/images/select_document_content.png)

Either files in the Eclipse workspace or external files may be added. The first file determines the mime type of the document. If the mime type cannot be determined or the mime type is different, then the mime type can be entered manually.
## Checkin
Documents can be checked in using the __Checkin__ command of the context menu. For tracked documents the check in can also be done using the __Object Store &gt; Checkin__ command of the context menu of the file. A wizard will be shown allowing different checkin options to be set. If the document was tracked then an extra option is added allowing the content of the tracked file to be used:

![configure_checkin]({{ site.url }}/plugin/images/configure_checkin.png)

If new content is necessary then after the first wizard page the content selection page shown for the __Save__ command is shown.
## Cancel Checkout
The checkout of document can be canceled using the __Cancel Checkout__ command of the context menu.
