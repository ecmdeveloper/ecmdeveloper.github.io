---
layout: single
author_profile: false
---
# Download

This first step is to download the software for the plug-in. The project is hosted on [sourceforge.net](http://sourceforge.net/projects/ecmdeveloper). The plug-in can be downloaded [here](http://sourceforge.net/projects/ecmdeveloper/files/) or by using the Eclipse update site [http://www.ecmdeveloper.com/update](http://www.ecmdeveloper.com/update).

The next step is to install the plug-in. The steps necessary to do this depends on the repository your are using. If you are targeting the FileNet P8 Content Engine Make sure that you select the correct version of the plug-in. The version for the FileNet Content Engine 4.5 is not compatible with the version for the FileNet Content Engine 4.5.1 and beyond and they cannot coexist within the same Eclipse installation. The only way to work around this problem is to use multiple Eclipse installations.

# CE 4.5 Installation

The plug-in uses the Java API based on the WSI protocol to access the FileNet P8 Content Engine. Because the Java API is not distributed with this plug-in, using this plug-in requires access to the Java API. Therefore installing this plug-in is a little bit more complicated than a basic plug-in install.

Before you install this plugin you have to make sure that you have the Content Engine Client files available on your local machine. There a two ways to obtain these files:

* The Content Engine Client files are located in a folder named __CE_API__ in the FileNet installation folder on the Application Engine server. You can copy this folder to your local machine.
* If you don't have access to this folder the Content Engine Client files can also be installed using the Content Engine Client installer.

Refer to the documentation of your Eclipse version how to install plug-ins. After the plug-in is installed do not restart Eclipse immediately. For the plug-in to work properly first the Content Engine Client files must be made available within the Eclipse environment. First navigate to the <strong>plugins</strong> folder of your eclipse installation. The installation of the ECM Developer plug-in created a folder called __com.ecmdeveloper.plugin.lib_2.0.0__. This folder already contains some files. Now the content of the __CE_API__ folder must copied to this folder. The folder tree underneath the folder `com.ecmdeveloper.plugin.lib_2.0.0` should now look like this (the highlighted folders are the folders you have to add yourself):

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

# CMIS installation

The installation for CMIS repositories does not require any special actions. Refer to the documentation of your Eclipse version how to install plug-ins. If you are installing the plug-in in combination with support for Content Engine repositories, make sure that you perform the necessary actions described in the documentation.

# Import CE Object Store

To start using the ECM Developer plug-in you have to import the Object Stores you want to use with this plug-in in the workspace. The following actions have to be performed to accomplish this task:
* There are two ways how you can start the import of an Object Store. The first way is to  start the standard Eclipse import wizard using the __File > Import__ command:
* ![Import Object Store]({{ site.url }}/plugin/images/import_object_store.png)
