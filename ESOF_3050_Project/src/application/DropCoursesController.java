package application;

/**
 * Sample Skeleton for 'dropCourses.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

public class DropCoursesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="leftSideScrollPane"
    private ScrollPane leftSideScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="rightSideScrollPane"
    private ScrollPane rightSideScrollPane; // Value injected by FXMLLoader
    
    private Main main;
    private Scene sceneLogin;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setAddPressedScene(Scene sceneLogin) {
    	this.sceneLogin = sceneLogin;
    }

    @FXML
    void dropButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    	resetFields();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        resetFields();

    }
    
    void resetFields() {
    	/* look at shapecontroller.java to see how to insert blank vboxs */
    }
}

