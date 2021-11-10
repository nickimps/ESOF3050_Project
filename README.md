# ESOF3050_Project

## Overview
The objective of this project is to design & develop a University Registration System (URS) along with its database to manage student enrollment in classes, university employee roster, and previous course history in addition to many other features for the university, depending on the role of the user. The database would provide different permissions and functions depending on the user’s type of account, consisting of either students, instructors, or administrators. The problem with today's current registration and database systems is that they are not user friendly enough for university students and staff. We are seeking to create the easiest and least stressful experience possible for new and existing students as well as administrators, instructors and any other staff members that may be using the system.

## Installation Tips
May need to configure build path to run code:
 - Download the javafx SDK from https://gluonhq.com/products/javafx/
 - Save in a folder you can access
 - Right-click on the project > Build Path > Configure Build Path
 - In the Libraries Tab, click on Modulepath and then click the Add External JARs button
 - Select all the JARs from the sdk download in the lib folder (should have 8 JARs)
 - Apply and Close

If having issues running code:
 - Go to Run > Run As Configurations
 - Then go to Arguments tab and copy the command below into the VM Arguments spot
 ```shell
--module-path "\path\to\javafx-sdk-12.0.1\lib" --add-modules javafx.controls,javafx.fxml
```
 - Apply
 - Run
