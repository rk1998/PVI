# PVI
Georgia Tech Junior Design Repo for Personal Virtual Inventories project.
Release Notes version Personal Virtual Inventories 1.0

# NEW FEATURES
- A Grocery List is now generated from the meals you select for your meal plan.
- Editing Grocery List: You can add and remove items from your list.
- Inventory Population is now available.
- Editing Inventory: You can add and remove items from your inventory.
- You can log out without exiting the app.
# BUG FIXES
- Miscellaneous items (drinks, seasonings) do not show up in initial recipe generation.
- Meal plan edit button no longer crashes the app when no meal plan is present.
- Image Loading is faster with Picasso library. The correct images show up in each meal plan cell adapter when the app first opens.
# KNOWN BUGS
- Grocery List does not regenerate; just keeps adding to the list every week.
- If all recipes are deleted from the meal plan and the user does not create a new meal plan directly afterward when prompted, the recipes sometimes remain on screen despite being deleted. Specifically, the screen refreshes such that it contains all the recipes that the user had upon login. If the screen is refreshed again, however, it updates properly.

# Install Guide Personal Virtual Inventories 1.0
## PRE- REQUISITES:
Must have an Android Device with at least Android 8.0 (preferably Android 8.1) installed on it.
If you are building from the source code you must have Android Studio; it will automatically install other necessary files and libraries. Look here for installation instructions for Android Studio: https://developer.android.com/studio/install
## DEPENDENCIES
There are no dependencies for this app.
If you are building from the source code, all dependent libraries will get installed automatically when you build the project.
## DOWNLOAD
You can download the code for this project by clicking on “Clone or Download”. From there, you can either clone the repository using or you can download a zip file.
## BUILD
If building from source, open the project in Android Studio and go to Build -> Make Project.
If using the pre-built app, no build is necessary
## INSTALLATION
Once the source code has been built, go to Run -> Run ‘app’. This will bring up a window asking you which device you want to run it on.
If you have developer options enabled on your device, you can plug it into your computer and Android Studio will install it on your phone.
Otherwise you can create an emulator in Android Studio to run the app on.  Look here for further instructions: https://docs.expo.io/versions/latest/workflow/android-studio-emulator/
If using the pre-built app, simply download it onto your device.
## RUNNING APPLICATION
If building from source, the app will automatically run after installation onto the emulator/device. In the future, simply find PVI in your app drawer and select it to open it.
If using the pre-built app, find PVI in your app drawer and select it to open it.
## TROUBLESHOOTING
Sometimes the app won’t install correctly when you try to run it on the emulator. If this happens just go to Run -> Run ‘app’ to try running it again.


#FUTURE WORK
Integrate e-receipt parsing with the app to populate the inventory
Implement subtracting from inventory when you check off meals from your meal plan
Implement “Reports” features which tracks user’s nutritional intake and purchase history
