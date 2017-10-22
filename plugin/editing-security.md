---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

# Editing Security

The security of folders, documents and custom objects can be edited or viewed with the security editor. You can start the security editor by using the __Edit__ command from the objects context menu in the different views. The following editor window will be opened:

![security_editor]({{ site.url }}/plugin/images/security_editor.png)

The editor follows the master-detail principle. In the table on the left the permissions are shown for the different security principals. If an item is selected in the table, on the right side the appropriate editor will be shown based on the type of the selection. You have to use the __Save__ command to store the changes you have made to the object.

Editing, adding and removing access control entries is only enabled when the repository allows ACL modifications and when you have sufficient access rights to modify the security. You can only edit or delete access control entries which are directly applied to the object, inherited access control entries can only be viewed.
## Adding access control entries
You can add new access control entries with the __Add Entry__ command of the toolbar or the __Add__ command of the context menu. There are two different dialogs for the selection of the desired security principal:

* If the object is stored in a Content Engine Object Store then the following dialog is shown, allowing you to search the directory service for different groups and user:
<a href="http://www.ecmdeveloper.com/wp-content/uploads/2012/11/principel_selection_realm.png"><img src="http://192.168.142.129/wp-content/uploads/2012/11/principel_selection_realm.png" alt="" title="principal_selection_realm" width="395" height="420" class="alignnone size-full wp-image-658" /></a>

* If the object is stored in a CMIS repository the following dialog is shown. In this case you have to provide the name of the security principal:
<a href="http://www.ecmdeveloper.com/wp-content/uploads/2012/12/principal_selection_no_realm.png"><img src="http://www.ecmdeveloper.com/wp-content/uploads/2012/12/principal_selection_no_realm.png" alt="" title="principal_selection_no_realm" width="488" height="197" class="alignnone size-full wp-image-697" /></a>

## Deleting access control entries</h2>
You can delete access control entries with the __Delete Entry__ command of the toolbar or the __Delete__ command of the context menu. If you selected a security principal then all the access control entries belonging to the security principal are deleted. If you selected a specific access control entry, then only the selected entry is deleted. If there are no more access control entries coupled to the security principal then the security principal is also removed from the table.
<h2>Refreshing</h2>
You can refresh the editor with the __Refresh Security__ command of the toolbar. This command will fetch the access control entries stored in the repository. All the changes made to the object in the editor will be lost.
