---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

## Searching

Searching is completely integrated in the Eclipse IDE. You can start your search just as any other search is started using the __Search__ command or the Search button on the toolbar. The search result is shown in the standard Eclipse Search view and the result is also present in the Eclipse search history.
## Search Dialog
In the Search dialog you have to select the __Object Store Search__ tab:

![search_dialog]({{ site.url }}/plugin/images/search_dialog.png)

In this dialog you can choose what action you want to perform:

* Design New Query: this will open the graphical query editor to start designing a new query.
* Modify Existing Query: this will also open the query editor but initialize with a saved query.
* Execute Existing Query: this will execute the query without opening the query editor.

You can also use this dialog to delete a previously saved query or to show the SQL.
## Graphical Query Editor
The appearance of the query editor depends on the Object Store you are querying. If you are performing a query against a CMIS repository then searching subclasses is implied, but you cannot use the properties of the subclasses in your query.  The grammar is in this case also slightly different. For a CMIS query you can also not specify a time limit.

![query_editor]({{ site.url }}/plugin/images/query_editor.png)

The graphical query editor consist of three areas:

* The top part of the editor contains the toolbar. Here you can add or remove tables to the query and execute the query. Currently only queries with one table are supported.
* In the middle part of the editor you can specify the fields you want in your query result. You can also specify aliases for columns and the sort order of the query result. The __This__ field is a special field. If you include this field in query result then the object is also fetched allowing different actions from the context menu. From here you can also drag the fields to the bottom part of the editor.
*  In the bottom part of the editor you can specify the where clause of the query. The containers are used to group the query components together in an AND or an OR condition. The NOT container can contain only one query component. The different containers can be nested. The query components form the different conditions of the where clause. Every component will guide you through a wizard to configure the different values. There may be different query parts in this area, only on part is the main query. This gives you the ability to easily narrow or broaden you query by dragging parts in and out of the main part.

## Search Result

![query_result]({{ site.url }}/plugin/images/query_result.png)

The result of the query is shown in the standard Search view of Eclipse. If you specified the __This__ field then the context menu shows most of the actions also available when you are browsing. An extra menu item is the __Show In &gt; Parent Folder__ command. With this command a new browsing view is opened with the parents of the selected object as the root objects. This menu item will only work if you are in the __Object Stores__ perspective.

It is also possible to export the result of the query. This can be done in the CSV, HTML and XML format. A wizard will guide you through the different configuration options.
## Show SQL
Sometime you do not want to perform a query but use the query editor to design the SQL to use someplace else. There are two ways to show the SQL of the query. The SQL of the entire query can be shown using the toolbar button on the top. The SQL of each part of the query can be shown using the context menu of the query part:

![show_sql]({{ site.url }}/plugin/images/show_sql.png)
