package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddCourseController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="subjectTextField"
    private TextField subjectTextField; // Value injected by FXMLLoader

    @FXML // fx:id="courseCodeTextField"
    private TextField courseCodeTextField; // Value injected by FXMLLoader

    @FXML // fx:id="titleTextField"
    private TextField titleTextField; // Value injected by FXMLLoader

    @FXML // fx:id="sectionTextField"
    private TextField sectionTextField; // Value injected by FXMLLoader

    @FXML // fx:id="InstructorChoiceBox"
    private ChoiceBox<?> InstructorChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionTextField"
    private TextField descriptionTextField; // Value injected by FXMLLoader

    @FXML // fx:id="lectureTimeTextField"
    private TextField lectureTimeTextField; // Value injected by FXMLLoader
    
    private Main main;
    private Scene sceneAdminWelcomeScreen;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setAddPressedScene(Scene sceneAdminWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    }
    
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    }
    
    @FXML
    void addButtonPressed(ActionEvent event) {
    	
    	/* capture values and store in database here */
    	
    	main.setScreen(sceneAdminWelcomeScreen);
    	resetFields();
    }
    
    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneAdminWelcomeScreen);
    	resetFields();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    void resetFields() {
    	subjectTextField.setText("");
    	courseCodeTextField.setText("");
    	titleTextField.setText("");
    	sectionTextField.setText("");
    	InstructorChoiceBox.setValue(null);
    	descriptionTextField.setText("");
    	lectureTimeTextField.setText("");    	
    }
}
