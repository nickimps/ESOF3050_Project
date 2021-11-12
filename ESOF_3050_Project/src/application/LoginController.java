package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userNameTextField"
    private TextField userNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="lastNameTextField"
    private TextField lastNameTextField; // Value injected by FXMLLoader
    
    private Main main;
    private Scene sceneAddCourse;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setLoginPressScene(Scene sceneAddCourse) {
    	this.sceneAddCourse = sceneAddCourse;
    }

    @FXML
    void loginButtonPressed(ActionEvent event) {
    	/* Will do a check on the login credentials and send to appropriate screen  */
    	main.setScreen(sceneAddCourse);
    	resetFields();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    void resetFields() {
    	userNameTextField.setText("");
    	lastNameTextField.setText("");
    }
}

