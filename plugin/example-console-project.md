---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

## Console Project Example: Create document

As a developer there are situations where you quickly want to create test documents with arbitrary name and contents. This example will show you how you can create such a document using a Content Engine Console Project. I will discuss the various methods of the Java class here, the full source code can be downloaded at the bottom of the article. I assume that you have created your Content Engine Console Project with the special [new project wizard](../console-projects).

All the code is contained in a Java class called `CreateDocument`. The `create()` method is the public entry point to the class:

```java
public void create(Object parent) {
  Folder folder = (Folder) parent;
  Document document = createDocument(folder.getObjectStore());
  folder.file(document, AutoUniqueName.AUTO_UNIQUE, null,
    DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE).save(
    RefreshMode.NO_REFRESH);
}
```

This class assumes that the selection used as input to this method is a folder. The `createDocument()` method will create a new document object, which is afterwards filed in the selected folder. Note that if multiple folders are selected a document will be created in each folder.

The `createDocument()` method looks like this:

```java
private Document createDocument(ObjectStore objectStore) {
  Document doc = Factory.Document.createInstance(objectStore, "Document");
  doc.getProperties().putValue("DocumentTitle", getDocumentTitle() );
  doc.set_ContentElements( getContentElements() );
  doc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION );
  doc.save(RefreshMode.REFRESH);
  return doc;
}
```

A document is created of document class `Document` and the title is set using the generated title of the `getDocumentTitle()` method:

```java
private String getDocumentTitle() {
  return "Console Project Test " + (new Date()).getTime();
}
```

In this case a fixed string and a long number are concatenated. If you have another scheme for generating unique names then you have to change this method. If you use a document class with more custom properties, then the `createDocument()` method is a good place to set the value of these properties.

The content of the document is set using these two methods:

```java
private ContentElementList getContentElements() {
  ContentElementList contentElementList = Factory.ContentElement.createList();
  contentElementList.add( createStringContent("Hello, World!") );
  return contentElementList;
}

private ContentTransfer createStringContent(String data) {
  ContentTransfer content = Factory.ContentTransfer.createInstance();
  content.set_RetrievalName( "data.txt" );
  content.setCaptureSource( new ByteArrayInputStream( data.getBytes() ) );
  content.set_ContentType( "text/plain" );
  return content;
}
```

To create a document with no content, just skip the call to the `set_ContentElements()` method of the document object.

Running this code will create a new document, filed in the folder selected in one of the Object Store views or in the search result. Note that there is no automatic refresh mechanism from a Console Project to the different views. So you will have to use the __Refresh__ command to make the new document visible.

__Source Code Downloads:__ [CreateDocument.java](../CreateDocument.java)
