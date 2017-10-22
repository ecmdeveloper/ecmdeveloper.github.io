---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---
# Download

This first step is to download the software for the plug-in. The project is hosted on [sourceforge.net](http://sourceforge.net/projects/ecmdeveloper). The plug-in can be downloaded [here](http://sourceforge.net/projects/ecmdeveloper/files/) or by using the Eclipse update site [http://www.ecmdeveloper.com/update](http://www.ecmdeveloper.com/update).

The next step is to install the plug-in. The steps necessary to do this depends on the repository your are using. If you are targeting the FileNet P8 Content Engine Make sure that you select the correct version of the plug-in. The version for the FileNet Content Engine 4.5 is not compatible with the version for the FileNet Content Engine 4.5.1 and beyond and they cannot coexist within the same Eclipse installation. The only way to work around this problem is to use multiple Eclipse installations.

# Installation

The plug-in uses the Java API based on the WSI protocol to access the FileNet P8 Content Engine. Because the Java API is not distributed with this plug-in, using this plug-in requires access to the Java API. Therefore installing this plug-in is a little bit more complicated than a basic plug-in install.

Before you install this plugin you have to make sure that you have the Content Engine Client files available on your local machine. There a two ways to obtain these files:

* The Content Engine Client files are located in a folder named __CE_API__ in the FileNet installation folder on the Application Engine server. You can copy this folder to your local machine.
* If you don't have access to this folder the Content Engine Client files can also be installed using the Content Engine Client installer.

Refer to the documentation of your Eclipse version how to install plug-ins. After the plug-in is installed do not restart Eclipse immediately. For the plug-in to work properly first the Content Engine Client files must be made available within the Eclipse environment. First navigate to the <strong>plugins</strong> folder of your eclipse installation. The installation of the ECM Developer plug-in created a folder called <strong>com.ecmdeveloper.plugin.lib_2.1.0</strong>. This folder already contains some files. Now the content of the <strong>CE_API</strong> folder must copied to this folder. The folder tree underneath the folder com.ecmdeveloper.plugin.lib_2.1.0 should now look like this (the highlighted folders are the folders you have to add yourself):
```
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
```

After this the installation is completed and Eclipse must be restarted. The next step is to import a Content Engine Object Store.

# Import CE Object Store

To start using the ECM Developer plug-in you have to import the Object Stores you want to use with this plug-in in the workspace. The following actions have to be performed to accomplish this task:
* There are two ways how you can start the import of an Object Store. The first way is to  start the standard Eclipse import wizard using the __File > Import__ command:<br>
![Import Object Store]({{ site.url }}/plugin/images/import_object_store.png)
* In the Content Engine category select __Object Store__ and continue the wizard.
* If there are any existing connections then in the __Select Connection__ page
you can either choose to configure a new connection or use an existing connection:<br>
![Selected Connection page]({{ site.url }}/plugin/images/select_connection.jpg)<br>
Choose the appropriate option and continue the wizard.
* If a new connection is selected or if there are no connections configure yet, then the
__Configure Connection__ page will be shown:<br>
![Configure Connection page]({{ site.url }}/plugin/images/configure_connection.jpg)<br>
In this page you can enter the necessary connection parameters and use the
__Connect__ command to connect to the Content Engine. Every time you connect to
this Content Engine these connection parameters will be used. Note that if for some reason
the Content Engine cannot be reached using the URL supplied then it may take a while before
a timeout occurs. After a successful connection is made the wizard can be continued.

* The final page of the wizard is the <strong>Select Object Store</strong> page:<br>
![Select Object Store page]({{ site.url }}/plugin/images/select_object_store.jpg)<br>
Select the necessary Object Stores and finish the wizard. If there are no object stores
left to import an error message will be displayed.

After you have completed these steps you can start using the Object Stores using the
ECM developer plug-in. Browsing the Object Stores is described
<a href='browsing_object_stores.shtml'>here</a>.

If you have configured a proxy server within Eclipse, add the Content Engine server as a
host for which no proxy server is necessary and restart Eclipse afterwards. This can be
done in the __Network Connections__ preference page using the
__Window &gt; Preferences &gt; General &gt; Network Connections__ command:
![Network Connections]({{ site.url }}/plugin/images/network_connections.jpg)<br>
