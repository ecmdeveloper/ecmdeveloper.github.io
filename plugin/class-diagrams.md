---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

The ECM Developer plug-in contains an editor that can be used to create a UML class diagram of the document, folder and custom object classes defined in the Object Store. The diagrams can be printed or exported to an image file in the JPEG, BMP or PNG format. Once a class is placed on the diagram an active connection to the Object Store is no longer necessary to edit the diagram. Only if the class must be refreshed, after a changed definition, a connection must be made.
## Object Store Class Diagram Perspective
For convenient editing of the Class Diagrams the Object Store Class Diagram perspective must be opened first. With the __Window > Open Perspective > Other__ command the perspective selection window is opened. In this window the __Object Store Class Diagram__ perspective can be selected. This perspective contains on the left side the Object Store Classes View. In this view for each defined connection and Object Store the classes can be browsed. Browsing can only be done is the Object Store is already connected. If the Object Store is not connected using one of the other views of the plug in then in the content menu of the Object Store a connection can be made to the Object Store.
## Creating Class Diagrams
The following actions have to be performed to accomplish this task:

*	If you are not in the Object Store Class Diagram perspective then you have to start the standard Eclipse New wizard by using the __File > New > Other__ command. Next select __Object Store Class Diagram__ in the ECM Developer section. If you are in the Object Store Class Diagram perspective then there is a shortcut to the new wizard in the __File > New__ menu.
*	In the next page of the wizard the location and the name of the Class Diagram must be supplied to start editing the new Class Diagram.
*	If the wizard is finished a new file with the extension classdiagram is created in the workspace and the Class Diagram editor is opened for this file.

## Editing the Class Diagram
To edit the Class Diagram the Object Store Classes view must be opened and connected to the Object Store. Use either drag-and-drop or the context menu of the class to add the class to the Class Diagram. A UML figure will be drawn of the class and all the attributes it contains:

![single_class]({{ site.url }}/plugin/images/single_class.jpg)

If a parent class or a child class is added to the diagram then they are connected to each other using an inherit relationship. The attributes of the child class defined in the parent class are now moved to the parent class:

![class_with_parent]({{ site.url }}/plugin/images/class_with_parent.jpg)

If a class is added to the diagram describing an object value property of another class then the classes are also connected using a association relationships. If there is also a reflective property defined then the arrows are shown on both editpoints, otherwise only one arrow is shown:

![class_with_association]({{ site.url }}/plugin/images/class_with_association.jpg)

As there are no object type property types in CMIS, this feature will not work for CMIS repositories.

If the relation between the two classes is marked as "cascade on delete" then a much stronger relation is assumed. In this case the classes are connected using an aggregation relation:

![class_with_aggregation1]({{ site.url }}/plugin/images/class_with_aggregation1.jpg)

With the palette on the right side of the editor a note can be added to the diagram. When the note icon is dropped on the diagram the user is prompted for a text and next the note will be placed on the diagram.

![class_diagram_note]({{ site.url }}/plugin/images/class_diagram_note.png)

## Editor Actions
The __delete__, __undo__ and __redo__ commands can be performed using the standard edit menu of Eclipse. The diagram can also be printed using the standard __File > Print__ command.

![class_diagram_context_menu]({{ site.url }}/plugin/images/class_diagram_context_menu.png)

Beside these actions the context menus supports some additional functionality:

*	__Refresh Class Diagram Class__ refreshes the class definition from the Content Engine and redraws the class.
*	__Show Properties__ shows the properties of the selected element. The editor uses the standard Eclipse properties view for editing properties. The content of the properties view depends on the selected element:
<table>
<tbody>
<tr>
<th>Selection</th>
<th>Properties</th>
</tr>
<tr>
<td>Diagram</td>
<td>Show Display Names<br>
Show Icons</td>
</tr>
<tr>
<td>Class</td>
<td>Visible Attributes<br>
Visible Relations<br>
Position</td>
</tr>
<tr>
<td>Note</td>
<td>Text<br>
Size<br>
Position</td>
</tr>
</tbody>
</table>

*	__Export diagram to image__ exports the entire diagram to an image file.
*	__Export class to image__ exports the selected class to an image file.
