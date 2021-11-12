package application;

/**
 * Sample Skeleton for 'modifyGrades.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class ModifyGradesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="leftScrollPane"
    private ScrollPane leftScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="middleScrollPane"
    private ScrollPane middleScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="newGradeTextField"
    private TextField newGradeTextField; // Value injected by FXMLLoader
    
    private Main main;
    private Scene sceneLogin;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setAddPressedScene(Scene sceneLogin) {
    	this.sceneLogin = sceneLogin;
    }

    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    	resetFields();
    }

    @FXML
    void changeButtonPressed(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    void resetFields() {
    	newGradeTextField.setText("");
    }
}

