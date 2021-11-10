# ESOF3050_Project

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
