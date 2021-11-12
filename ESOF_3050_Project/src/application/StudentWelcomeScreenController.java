package application;

/**
 * Sample Skeleton for 'optionScreenStud.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class StudentWelcomeScreenController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    private Main main;
    private Scene sceneLogin;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setAddPressedScene(Scene sceneLogin) {
    	this.sceneLogin = sceneLogin;
    }

    @FXML
    void dropCoursesButtonPressed(ActionEvent event) {
    	
    }

    @FXML
    void logoutButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    }

    @FXML
    void registerForCoursesButtonPressed(ActionEvent event) {

    }

    @FXML
    void searchForCoursesButtonPressed(ActionEvent event) {

    }

    @FXML
    void viewEnrolledCoursesButtonPressed(ActionEvent event) {

    }

    @FXML
    void viewGradesButtonPressed(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}

