---
title: "Showing distinguished names in the security tabs of the ACCE"
date: 2020-05-10 12:00
layout: "single"
categories: "ContentEngine"
excerpt: "This quick trick will show you how to see the distinguished name of a group instead of the display name in the different security tabs in  the IBM Administrative Console for Content Engine (ACCE). This is useful if you have groups with the same display name."
---

I think that this quick trick is only relevant for a few people, but nevertheless very handy. I work in a project where we implemented a role based security mechanism. This means that we have a lot of security groups with the same name (the name of the role) but residing in different organizational units in the directory server. If you want to know who has access to a specific object using the IBM Administrative Console for Content Engine (ACCE) application then it will look like this:

|![Groups display name]({{ site.url }}/img/posts/groups-displayname.png)|

All the different groups look the same! This is because the application uses the display name to show the name of the group. In most cases an excellent choice, but it this specific situation not very convenient. A little bit of digging in the JavaScript of the webpage reveals that there is a JavaScript object called `acceUtil` containing a function called `getSecurityPrincipalDisplayName()`. This function is responsible for showing the group name on the webpage. It takes the distinguished name as the input and returns the display name as the output. So, we are almost there, we have the distinguished name of the group. 

Showing this instead of the display name can be solved with a trick called [Monkey patching](https://en.wikipedia.org/wiki/Monkey_patch). We replace the original function of the `acceUtil` javascript object with our own function and we are done. The replacement function will have one little twist. The reason why we often don't want to see the distinguished in the interface is because it can be long and boring. Therefore the replacement function will parse the distinguished name and show only the last three parts of the distinguished name. In practice this should be enough to show the difference between the different groups (you can change this number if you like). The following code will replace the original function with our new function:

```javascript
if ( acceUtil ) {
  if ( !acceUtil.originalGetSecurityPrincipalDisplayName ) {
    acceUtil.originalGetSecurityPrincipalDisplayName = acceUtil.getSecurityPrincipalDisplayName;
  }

  acceUtil.getSecurityPrincipalDisplayName = function(name)  { 

    // Change this value to show more (or less) parts:
    var partsToShow = 3;
  
    return name.match(/([^,]+)/g).slice(0,partsToShow).join(','); 
  }
  console.log("Showing the principals distinguished name");
}
```
At the top of the function there is some administration going on, allowing us to switch back to the original situation. Switching back to showing the display name is then done with this function:

```javascript
if ( acceUtil ) {
  if ( acceUtil.originalGetSecurityPrincipalDisplayName ) {
    acceUtil.getSecurityPrincipalDisplayName = acceUtil.originalGetSecurityPrincipalDisplayName;
    console.log("Showing the principals display name");
  }
}
```
The next challenge we have to solve, is to execute these methods in the context of the webpage. Initially I looked at a Chrome extension, but due to security reasons extensions have access to the DOM of the webpage but not to the JavaScript context of the webpage. When you open the _Developer Tools_ of your web browser, there is a section called _Snippets_:

|![Browser snippets section]({{ site.url }}/img/posts/browser-snippets.png)|

These two scripts can be placed there and executed when needed. If you run the first script then the new look of the permissions tab of you ACCE will now be like this (don't forget to press the _Refresh_ button):

|![Groups distinguished name]({{ site.url }}/img/posts/groups-distinguished-name.png)|

Running the second script will restore the original look.

In practice, opening the developer tools each time can still be to much work. A further simplification will sacrifice on script readability but will increase on useability. The trick is to transform the scripts to a bookmarklet. A [bookmarklet](https://en.wikipedia.org/wiki/Bookmarklet) is a bookmark stored in a web browser containing JavaScript code. A bookmarklet also has access to the JavaScript context of your webpabe. Now you have to creating bookmarklets for both scripts and for easy access place the on a handy place in your browser environment. I placed both scripts in a folder on the bookmarks tab:

|![ACCE bookmarklets]({{ site.url }}/img/posts/acce-bookmarklets.png)|

Now switching between the different modes is a breeze! As I said, readability will suffer because the scripts must be made more URL-like. I used [this handy online tool](https://mrcoles.com/bookmarklet/) to convert the two scripts to bookmarklets (removed the comment and as much spaces as possible). The bookmarklet code for the first script is now this long line:

```javascript
javascript:(function()%7Bif(acceUtil)%7Bif(!acceUtil.originalGetSecurityPrincipalDisplayName)%7BacceUtil.originalGetSecurityPrincipalDisplayName%3DacceUtil.getSecurityPrincipalDisplayName%3B%7DacceUtil.getSecurityPrincipalDisplayName%3Dfunction(name)%7Bvar%20partsToShow%3D3%3Breturn%20name.match(%2F(%5B%5E%2C%5D%2B)%2Fg).slice(0%2CpartsToShow).join('%2C')%3B%7D%3Bconsole.log(%22Showing%20the%20principals%20distinguished%20name%22)%3B%7D%7D)()
```

The code for the second script is now this piece of code:

```javascript
javascript:(function()%7Bif(acceUtil)%7Bif(acceUtil.originalGetSecurityPrincipalDisplayName)%7BacceUtil.getSecurityPrincipalDisplayName%3DacceUtil.originalGetSecurityPrincipalDisplayName%3Bconsole.log(%22Showing%20the%20principals%20display%20name%22)%3B%7D%7D%7D)()
```
As I mentioned at the top, not a problem for everyone, but handy I you ever encounter this situation. Perhaps these techniques may help you to "fix" other annoyances in web applications.