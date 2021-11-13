package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;

public class StudentListController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="listScrollPane"
    private ScrollPane listScrollPane; // Value injected by FXMLLoader

    private Main main;
    private Scene sceneAdminWelcomeScreen;
    private Scene sceneInstructorWelcomeScreen;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen, Scene sceneInstructorWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    	this.sceneInstructorWelcomeScreen = sceneInstructorWelcomeScreen;
    }
    
    @FXML
    void backButtonPressed(ActionEvent event) {
    	
    	/* need to remember which screen to go back to, admin or instr */
    	
    	//if admin
    	main.setScreen(sceneAdminWelcomeScreen);
    	
    	//else if instr
    	main.setScreen(sceneInstructorWelcomeScreen);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	
    }
}


