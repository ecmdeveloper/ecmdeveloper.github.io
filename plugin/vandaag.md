---
layout: default
author_profile: true
---

This first step is to download the software for the plug-in. The project is hosted on <a http://sourceforge.net/projects/ecmdeveloper">sourceforge.net</a>. The plug-in can be downloaded <a href="http://sourceforge.net/projects/ecmdeveloper/files/">here</a> or by using the Eclipse update site <strong>http://www.ecmdeveloper.com/update</strong>.

The next step is to install the plug-in. The steps necessary to do this depends on the repository your are using. If you are targeting the FileNet P8 Content Engine Make sure that you select the correct version of the plug-in. The version for the FileNet Content Engine 4.5 is not compatible with the version for the FileNet Content Engine 4.5.1 and beyond and they cannot coexist within the same Eclipse installation. The only way to work around this problem is to use multiple Eclipse installations.

# CE 4.5 Installation

The plug-in uses the Java API based on the WSI protocol to access the FileNet P8 Content Engine. Because the Java API is not distributed with this plug-in, using this plug-in requires access to the Java API. Therefore installing this plug-in is a little bit more complicated than a basic plug-in install.

Before you install this plugin you have to make sure that you have the Content Engine Client files available on your local machine. There a two ways to obtain these files:
<ul>
	<li>The Content Engine Client files are located in a folder named <strong>CE_API</strong> in the FileNet installation folder on the Application Engine server. You can copy this folder to your local machine.</li>
	<li>If you don't have access to this folder the Content Engine Client files can also be installed using the Content Engine Client installer.</li>
</ul>
Refer to the documentation of your Eclipse version how to install plug-ins. After the plug-in is installed do not restart Eclipse immediately. For the plug-in to work properly first the Content Engine Client files must be made available within the Eclipse environment. First navigate to the <strong>plugins</strong> folder of your eclipse installation. The installation of the ECM Developer plug-in created a folder called <strong>com.ecmdeveloper.plugin.lib_2.0.0</strong>. This folder already contains some files. Now the content of the <strong>CE_API</strong> folder must copied to this folder. The folder tree underneath the folder com.ecmdeveloper.plugin.lib_2.0.0 should now look like this (the highlighted folders are the folders you have to add yourself):

```
com.ecmdeveloper.plugin.lib_2.0.0
|
|   COPYING
|   COPYING.LESSER
|   
+---com
|   \---ecmdeveloper
|       \---plugin
|           \---lib
|                   Activator$1$1.class
|                   Activator$1.class
|                   Activator.class
|                   
+---config
|       jaas.conf.JBoss
|       jaas.conf.WebLogic
|       jaas.conf.WebSphere
|       jaas.conf.WSI
|       log4j.properties.client
|       log4j.xml.server
|       
+---lib
|       Jace.jar
|       log4j.jar
|       
+---lib2
|       javaapi.jar
|       listener.jar
|       log4j.jar
|       p8cjares.jar
|       WcmApiConfig.properties
|       
+---META-INF
|       MANIFEST.MF
|       
\---wsi
    |
    | <<The rest is skipped, you get the picture...>>
    |
    \---work
```

After this the installation is completed and Eclipse must be restarted. The next step is to import a Content Engine Object Store.

# CE 4.5.1 and 5.x installation

The plug-in uses the Java API based on the WSI protocol to access the FileNet P8 Content Engine. Because the Java API is not distributed with this plug-in, using this plug-in requires access to the Java API. Therefore installing this plug-in is a little bit more complicated than a basic plug-in install.

Before you install this plugin you have to make sure that you have the Content Engine Client files available on your local machine. There a two ways to obtain these files:
<ul>
	<li>The Content Engine Client files are located in a folder named <strong>CE_API</strong> in the FileNet installation folder on the Application Engine server. You can copy this folder to your local machine.</li>
	<li>If you don't have access to this folder the Content Engine Client files can also be installed using the Content Engine Client installer.</li>
</ul>
Refer to the documentation of your Eclipse version how to install plug-ins. After the plug-in is installed do not restart Eclipse immediately. For the plug-in to work properly first the Content Engine Client files must be made available within the Eclipse environment. First navigate to the <strong>plugins</strong> folder of your eclipse installation. The installation of the ECM Developer plug-in created a folder called <strong>com.ecmdeveloper.plugin.lib_2.1.0</strong>. This folder already contains some files. Now the content of the <strong>CE_API</strong> folder must copied to this folder. The folder tree underneath the folder com.ecmdeveloper.plugin.lib_2.1.0 should now look like this (the highlighted folders are the folders you have to add yourself):

[sourcecode highlight="14,22,30"]
com.ecmdeveloper.plugin.lib_2.1.0
|
|   COPYING
|   COPYING.LESSER
|
+---com
|   \---ecmdeveloper
|       \---plugin
|           \---lib
|                   Activator$1$1.class
|                   Activator$1.class
|                   Activator.class
|
+---config
|       jaas.conf.JBoss
|       jaas.conf.WebLogic
|       jaas.conf.WebSphere
|       jaas.conf.WSI
|       log4j.properties.client
|       log4j.xml.server
|
+---lib
|       Jace.jar
|       log4j.jar
|       stax-api.jar
|       xlxpScanner.jar
|       xlxpScannerUtils.jar
|
+---lib2
|       javaapi.jar
|       listener.jar
|       log4j.jar
|       p8cjares.jar
|       stax-api.jar
|       WcmApiConfig.properties
|       xlxpScanner.jar
|       xlxpScannerUtils.jar
|
\---META-INF
        MANIFEST.MF
[/sourcecode]

After this the installation is completed and Eclipse must be restarted. The next step is to import a Content Engine Object Store.

# CMIS installation

The installation for CMIS repositories does not require any special actions. Refer to the documentation of your Eclipse version how to install plug-ins. If you are installing the plug-in in combination with support for Content Engine repositories, make sure that you perform the necessary actions described in the documentation.

# Import CE Object Store

To start using the ECM Developer plug-in you have to import the Object Stores you want to use with this plug-in in the workspace. The following actions have to be performed to accomplish this task:
<ul>
	<li>There are two ways how you can start the import of an Object Store. The first way is to  start the standard Eclipse import wizard using the <strong>File &gt; Import</strong> command:
<img class="alignnone size-full wp-image-569" title="import_object_store" src="/wp-content/uploads/2012/03/import_object_store.png" alt="" width="454" height="359" />
In the <strong>ECM Developer</strong> category select <strong>Content Engine Object Store</strong> and continue the wizard. The second way is to use the toolbar or context menu of the Object Stores view.</li>
	<li>If there are any existing connections then in the Select Connection page you can either choose to configure a new connection or use an existing connection:
<img class="alignnone size-full wp-image-570" title="select_content_engine_connection" src="/wp-content/uploads/2012/03/select_content_engine_connection.png" alt="" width="454" height="359" />
Choose the appropriate option and continue the wizard.</li>
	<li>If a new connection is selected or if there are no connections configure yet, then the Configure Connection page is shown:
<img class="alignnone size-full wp-image-571" title="configure_content_engine_connection" src="/wp-content/uploads/2012/03/configure_content_engine_connection.png" alt="" width="454" height="359" />
In this page you can enter the necessary connection parameters and use the Connect command to connect to the Content Engine. Every time you connect to this Content Engine these connection parameters will be used. Note that if for some reason the Content Engine cannot be reached using the URL supplied then it may take a while before a timeout occurs. After a successful connection is made the wizard can be continued.Passwords are stored in a configuration file in the Eclipse workspace. If you don't want this then you should not select the <strong>Store password</strong>option. Every time you connect to the Content Engine you have to supply the password.If you have trouble connecting to the Content Engine then it may be because there is a proxy server configured within Eclipse. You can fix this by adding the Content Engine server as a host for which no proxy server is necessary and restart Eclipse afterwards. This can be done in the <strong>Network Connections</strong> preference page using the <strong>Window &gt; Preferences &gt; General &gt; Network Connections</strong> command</li>
	<li>The final page of the wizard is the Select Object Store page:
<img class="alignnone size-full wp-image-573" title="select_object_store" src="/wp-content/uploads/2012/03/select_object_store.jpg" alt="" width="456" height="369" />
Select the necessary Object Stores and finish the wizard. If there are no object stores left to import an error message will be displayed.</li>
</ul>
After you have completed these steps you can start using the Object Stores using the ECM developer plug-in.
