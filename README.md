ModelFormAdapter
================

Simple class to update Android Forms based on Object datas and vice versa.

Usage:

The first parameter is the Object containing your data
formContainer is the root view of your layout that contains your "form".

ModelFormAdapter modelFormAdapter = new ModelFormAdapter(myData, formContainer);

It will update your EditText and other "form" components in the construction.

In your XML Layout, you just neen to add a tag matching the name of the object property that you want to assing.

You can call updateFormValues and updateModelValues whenever you need.