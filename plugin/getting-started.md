---
layout: single
author_profile: false
sidebar:
  nav: "plugin"
---

# Installation

[ ![Download](https://api.bintray.com/packages/ecmdeveloper/eclipse-plugin/ecmdeveloper/images/download.svg) ](https://bintray.com/ecmdeveloper/eclipse-plugin/ecmdeveloper/_latestVersion)

This first step is to download the software for the plug-in. The plug-in can be downloaded at [https://bintray.com/ecmdeveloper/eclipse-plugin/ecmdeveloper](https://bintray.com/ecmdeveloper/eclipse-plugin/ecmdeveloper) or by using the Eclipse update site [https://dl.bintray.com/ecmdeveloper/eclipse-plugin/ecmdeveloper/latest](#).

The plug-in uses the Java API based on the WSI protocol to access the FileNet P8 Content Engine. Because the Java API is not distributed with this plug-in, using this plug-in requires access to the Java API files. Therefore installing this plug-in is a little bit more complicated than a basic plug-in install.

Before you install this plugin you have to make sure that you have the Content Engine Client files available on your local machine. There a two ways to obtain these files:

* The Content Engine Client files are located in a folder named __CE_API__ in the FileNet installation folder on the Content Engine server. You can copy this folder to your local machine.
* If you don't have access to this folder the Content Engine Client files can also be downloaded using the _IBM Administrative Console for Content Platform Engine (ACCE)_ application. Go to __Domain > IBM FileNet Content Manager > Java CEWS client__ and download the client files.

Refer to the documentation of your Eclipse version how to install plug-ins. After the plug-in is installed do not restart Eclipse immediately. For the plug-in to work properly, first the Content Engine Client files must be made available within the Eclipse environment. First navigate to the __plugins__ folder of your eclipse installation. The installation of the ECM Developer plug-in created a folder called __com.ecmdeveloper.plugin.lib_2.3.0\lib__. Now the following files of the __CE_API__ folder must copied to this folder:
* `Jace.jar`
* `log4j.jar` or `log4j-1.2.14.jar`
* `stax-api.jar`
* `xlxpScanner.jar`
* `xlxpScannerUtils.jar`

After this the installation is completed and Eclipse must be restarted. If some of the jar-files are missing an error message will be shown, reporting the missing files. The next step is to import a Content Engine Object Store.

# Import CE Object Store

To start using the ECM Developer plug-in you have to import the Object Stores you want to use with this plug-in in the workspace. There are two ways how you can start the import of an Object Store.
* The first way is to  start the standard Eclipse import wizard using the __File > Import__ command:<br>
![Import Object Store]({{ site.url }}/plugin/images/import_object_store.png)<br>
In the ECM Developer category select __Object Store__ and continue the wizard.
* The second way is to go to __Window > Show View > Other...__, open the __ECM Developer__ category and select the __Object Stores__ view. In this view select the __Import Content Engine Object Store__ menu item.

From here the wizards follow the same path.

* If there are any existing connections then in the __Select Connection__ page
you can either choose to configure a new connection or use an existing connection:<br>
![Selected Connection page]({{ site.url }}/plugin/images/select_connection.jpg)<br>
Choose the appropriate option and continue the wizard.
* If a new connection is selected or if there are no connections configure yet, then the
__Configure Connection__ page will be shown:<br>
![Configure Connection page]({{ site.url }}/plugin/images/configure_connection.jpg)<br>
In this page you can enter the necessary connection parameters and use the
__Connect__ command to connect to the Content Engine. Every time you connect to
this Content Engine these connection parameters will be used. The correct format of the URL can be copied from the 'ping' page of your installation. This page can be found at the following URL: `http://ceserver:9080/P8CE/Health`. Note that if for some reason
the Content Engine cannot be reached using the URL supplied then it may take a while before
a timeout occurs. After a successful connection is made, the wizard can be continued.
* The final page of the wizard is the __Select Object Store__ page:<br>
![Select Object Store page]({{ site.url }}/plugin/images/select_object_store.jpg)<br>
Select the necessary Object Stores and finish the wizard. If there are no object stores
left to import an error message will be displayed.

After you have completed these steps you can start using the Object Stores using the
ECM developer plug-in. Browsing the Object Stores is described
<a href='{{ site.url }}/plugin/browsing-object-stores'>here</a>.

If you have configured a proxy server within Eclipse, add the Content Engine server as a
host for which no proxy server is necessary and restart Eclipse afterwards. This can be
done in the __Network Connections__ preference page using the
__Window &gt; Preferences &gt; General &gt; Network Connections__ command:

![Network Connections]({{ site.url }}/plugin/images/network_connections.jpg)<br>
