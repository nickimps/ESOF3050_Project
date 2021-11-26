/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * AddEmployeeController.java
 */

package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
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

    @FXML // fx:id="memberIDLabel"
    private Label memberIDLabel; // Value injected by FXMLLoader

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
    
    private int newMemberID;
    
    /**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    /**
     * Sets the scene for the back button
     * 
     * @param sceneAdminWelcomeScreen
     */
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    }
    
    /**
     * Creates a memberID for the employee
     */
    public void generateMemberID() {
    	//Try-Catch
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		//Create a list of memIDs
		ArrayList<String> memIDs = new ArrayList<>();

    	//Try-Catch
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
	    	//Try-Catch
			try {
				Statement stmt = conn.createStatement();

				//Get the memberIDs to check against
				ResultSet rs = stmt.executeQuery("SELECT memberID FROM UniversityMember");
				
				//Adds them to the list
				while(rs.next())
					memIDs.add(rs.getString(1));

			} catch (SQLException e){
			    e.printStackTrace();
			}

			//Close the connection
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Get random number and check if it exists already
		while(true) {
			Random r = new Random();
			newMemberID = 1000000 + r.nextInt(9000000);
			
			if (!memIDs.contains(String.format("%d", newMemberID)))
				break;
		}
		
		//Change the label to the memberId generated
		memberIDLabel.setText(String.format("%d", newMemberID));
    }
    
    /**
     * When the add button is pressed, get info and update database
     * 
     * @param event
     */
    @FXML
    void addButtonPressed(ActionEvent event) {
    	//If the fields are not blank
    	if (firstNameTextField.getText() != "" && lastNameTextField.getText() != "" && SINTextField.getText() != "" && dateOfBirthTextField.getText() != "" && addressTextField.getText() != "" && (partTimeRadioButton.isSelected() || fullTimeRadioButton.isSelected()) && (instructorRadioButton.isSelected() || administratorRadioButton.isSelected()) ) {
        	//Try-Catch
    		try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
    		
        	//Try-Catch
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");

				//Try-Catch
				try {
					//Get member type from the radio buttons
					String memberType = "";
					if (instructorRadioButton.isSelected())
						memberType = "Instructor";
					else if (administratorRadioButton.isSelected())
						memberType = "Administrator";
					
					//Get member status from the radio buttons
					String status = "";
					if (partTimeRadioButton.isSelected())
						status = "Part-Time";
					else if (fullTimeRadioButton.isSelected())
						status = "Full-Time";
					
					//Create the statement
					Statement stmt = conn.createStatement();

					//Insert the employee into the database
					stmt.executeUpdate("INSERT INTO UniversityMember VALUES ("+ newMemberID + ", '" + memberType + "', '" + firstNameTextField.getText() + "', '" + lastNameTextField.getText() + "', " + Integer.parseInt(SINTextField.getText()) + ", '" + dateOfBirthTextField.getText() + "', '" + addressTextField.getText() + "', '" + status + "')");
					
					//Let user know
				    messageLabel.setText("Successfully Added!");
				    messageLabel.setVisible(true);
				} catch (SQLException e){
				    e.printStackTrace();
				}
	
				//Close the connection
				conn.close();
			} catch (SQLException e) {
				messageLabel.setText("Error!");
			    messageLabel.setVisible(true);
			}
	    	
			//Reset the fields
	    	resetFields();
    	}
    	else {
    		messageLabel.setText("Error!");
		    messageLabel.setVisible(true);
    	}
    }
    
    /**
     * brings the user back to the welcome screen when back is pressed
     * 
     * @param event
     */
    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneAdminWelcomeScreen);
    	messageLabel.setVisible(false);
    	resetFields();
    }
    
    /**
     * Makes sure both cannot be selected at once
     * 
     * @param event
     */
    @FXML
    void fullTimeRadioButtonPressed(ActionEvent event) {
    	partTimeRadioButton.setSelected(false);
    }
    
    /**
     * Makes sure both cannot be selected at once
     * 
     * @param event
     */
    @FXML
    void partTimeRadioButtonPressed(ActionEvent event) {
    	fullTimeRadioButton.setSelected(false);
    }
    
    /**
     * Makes sure both cannot be selected at once
     * 
     * @param event
     */
    @FXML
    void instructorRadioButtonPressed(ActionEvent event) {
    	administratorRadioButton.setSelected(false);
    }
    
    /**
     * Makes sure both cannot be selected at once
     * 
     * @param event
     */
    @FXML
    void administratorRadioButtonPressed(ActionEvent event) {
    	instructorRadioButton.setSelected(false);
    }
    
    /**
     * Hides the info message
     * 
     * @param event
     */
    @FXML
    void resetMessage(MouseEvent event) {
    	messageLabel.setVisible(false);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    /**
     * Resets the fields
     */
    void resetFields() {
    	firstNameTextField.setText("");
    	lastNameTextField.setText("");
    	SINTextField.setText("");
    	dateOfBirthTextField.setText("");
    	addressTextField.setText("");
    	administratorRadioButton.setSelected(false);
    	instructorRadioButton.setSelected(false);
    	fullTimeRadioButton.setSelected(false);
    	partTimeRadioButton.setSelected(false);
    }
}

