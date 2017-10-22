---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

The properties of folders, documents and custom objects can be edited or viewed. The properties editor can be started using the Edit command from the objects context menu in the Object Stores view. The following window will be opened:

![document_editor]({{ site.url }}/plugin/images/document_editor.jpg)

The editor follows the master-detail principle. In the table on the left the properties are shown with their current value. If a property is selected in the table then on the right side the appropriate property editor will be shown based on the type of the property. The existing value can be edited or, in case of a not required value, an empty value can used. Changes are reflected directly in the view on the left side and the editor is marked as dirty.

The modified values can be saved using the __File > Save__ command. This command is only enabled if the editor is marked as dirty.

As a default only the editable properties are shown in the properties table. The  __Toggle Read Only Properties__ button can be used to show the rest of the non-hidden properties, including the system properties. The  __Refresh__ Properties button can be used to refresh the properties with the actual values stored in the Object Store.

Currently most of the common properties can be edited using the ECM Developer plug in. Property types not supported are binary, guid and object value types.
