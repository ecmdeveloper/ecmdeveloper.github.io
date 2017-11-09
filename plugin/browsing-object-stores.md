---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

## Browsing Object Stores

There are two views that can be used for browsing the object stores: the __Object Stores__ view or the __Object Store Favorites__ view. Most of the actions are available for both views. Changes made in one view will also be visible in the other view.

You can open both views by switching to the Object Store perspective or by using the __Window > Show view >Other__ command. Both views are located in the __ECM Developer__ category. Selecting the Object Stores view will show the following window:

![object_store_browsing]({{ site.url }}/plugin/images/object_store_browsing.png)

If this window is empty you have to setup your connections and import an Object Store. See the getting started page for details.

To start browsing you first have to make a connection to the Object Store using either the context menu of the Object Store or the toolbar button of the view. Double clicking will also connect the Object Store. This will open the credentials dialog where you can change the credentials. If you specified that you did not want to save the password for this connection then this is the place to supply the password:

![credentials_dialog]({{ site.url }}/plugin/images/credentials_dialog.png)

When the Object Store is connected the Object Store can be browsed, showing folders, documents and custom objects. Also additional Object Stores can be added to this view using the toolbar button. In this view you can perform the operations possible on Object Store items.

The connection to the Object Store can be disconnected using the __Disconnect__ command of the content menu of the Object Store.
