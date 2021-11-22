package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddEmployeeController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="firstNameTextField"
    private TextField firstNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="lastNameTextField"
    private TextField lastNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="SINTextField"
    private TextField SINTextField; // Value injected by FXMLLoader

    @FXML // fx:id="dateOfBirthTextField"
    private TextField dateOfBirthTextField; // Value injected by FXMLLoader

    @FXML // fx:id="addressTextField"
    private TextField addressTextField; // Value injected by FXMLLoader

    @FXML // fx:id="employeeIDTextField"
    private TextField employeeIDTextField; // Value injected by FXMLLoader

    @FXML // fx:id="statusChoiceBox"
    private ChoiceBox<?> statusChoiceBox; // Value injected by FXMLLoader
    
    @FXML // fx:id="partTimeRadioButton"
    private RadioButton partTimeRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="fullTimeRadioButton"
    private RadioButton fullTimeRadioButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="instructorRadioButton"
    private RadioButton instructorRadioButton; // Value injected by FXMLLoader

    @FXML // fx:id="administratorRadioButton"
    private RadioButton administratorRadioButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader

    private Main main;
    private Scene sceneAdminWelcomeScreen;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    }
    
    @FXML
    void addButtonPressed(ActionEvent event) {
    	if (employeeIDTextField.getText() != "" && firstNameTextField.getText() != "" && lastNameTextField.getText() != "" && SINTextField.getText() != "" && dateOfBirthTextField.getText() != "" && addressTextField.getText() != "" && employeeIDTextField.getText() != "" && (partTimeRadioButton.isSelected() || fullTimeRadioButton.isSelected()) && (instructorRadioButton.isSelected() || administratorRadioButton.isSelected()) ) {
	    	try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
				
				try {
					String memberType = "";
					if (instructorRadioButton.isSelected())
						memberType = "Instructor";
					else if (administratorRadioButton.isSelected())
						memberType = "Administrator";
					
					String status = "";
					if (partTimeRadioButton.isSelected())
						status = "Part-Time";
					else if (fullTimeRadioButton.isSelected())
						status = "Full-Time";
					
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("INSERT INTO UniversityMember VALUES ("+ Integer.parseInt(employeeIDTextField.getText()) + ", '" + memberType + "', '" + firstNameTextField.getText() + "', '" + lastNameTextField.getText() + "', " + Integer.parseInt(SINTextField.getText()) + ", '" + dateOfBirthTextField.getText() + "', '" + addressTextField.getText() + "', '" + status + "')");
					
				    messageLabel.setText("Successfully Added!");
				    messageLabel.setVisible(true);
				} catch (SQLException e){
				    e.printStackTrace();
				}
	
				conn.close();
			} catch (SQLException e) {
				messageLabel.setText("Error!");
			    messageLabel.setVisible(true);
			}
	    	
	    	resetFields();
    	}
    	else {
    		messageLabel.setText("Error!");
		    messageLabel.setVisible(true);
    	}
    }
    
    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneAdminWelcomeScreen);
    	messageLabel.setVisible(false);
    	resetFields();
    }
    
    @FXML
    void fullTimeRadioButtonPressed(ActionEvent event) {
    	partTimeRadioButton.setSelected(false);
    }
    
    @FXML
    void partTimeRadioButtonPressed(ActionEvent event) {
    	fullTimeRadioButton.setSelected(false);
    }
    
    @FXML
    void instructorRadioButtonPressed(ActionEvent event) {
    	administratorRadioButton.setSelected(false);
    }
    
    @FXML
    void administratorRadioButtonPressed(ActionEvent event) {
    	instructorRadioButton.setSelected(false);
    }
    
    @FXML
    void resetMessage(MouseEvent event) {
    	messageLabel.setVisible(false);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    void resetFields() {
    	firstNameTextField.setText("");
    	lastNameTextField.setText("");
    	SINTextField.setText("");
    	dateOfBirthTextField.setText("");
    	addressTextField.setText("");
    	employeeIDTextField.setText(""); 
    	administratorRadioButton.setSelected(false);
    	instructorRadioButton.setSelected(false);
    	fullTimeRadioButton.setSelected(false);
    	partTimeRadioButton.setSelected(false);
    }
}

