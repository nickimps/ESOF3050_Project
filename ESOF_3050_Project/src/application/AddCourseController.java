/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * AddCourseController.java
 */

package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
    private ChoiceBox<String> InstructorChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionTextField"
    private TextField descriptionTextField; // Value injected by FXMLLoader

    @FXML // fx:id="lectureTimeTextField"
    private TextField lectureTimeTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="capacityTextField"
    private TextField capacityTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader
    
   
    private int instructor = -1;								//Stores which instructor is being added with course
    private List<String> instructors = new ArrayList<>();		//List of instructors to display in the dropDown menu
    private List<String> instructorsMemIDs = new ArrayList<>();	//The Employee IDs of the instructors being displayed
    private Main main;											//The Main the scene to go back to if need be
    private Scene sceneAdminWelcomeScreen;						//The AdminWelcomeScreen to go back to when the back button is pressed
    
    /**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    /**
     * Sets which scene to go back to when the back button is pressed
     * 
     * @param sceneAdminWelcomeScreen The screen to go back to
     */
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    }
    
    /**
     * When the add button is pressed, checks to make sure all input is valid. Adds the information
     * into the Course and Section tables in the database and updates the status of the instructor if they 
     * change from part-time to full-time.
     * 
     * @param event	The event of the button being clicked
     */
    @FXML
    void addButtonPressed(ActionEvent event) {
    	//Get the index of the instructor that is chosen
    	instructor = InstructorChoiceBox.getSelectionModel().getSelectedIndex();
    	
    	//If all the input fields have valid input, then we will get their information and update the database accordingly
    	if (subjectTextField.getText() != "" && courseCodeTextField.getText() != "" && titleTextField.getText() != "" && sectionTextField.getText() != "" && descriptionTextField.getText() != "" && lectureTimeTextField.getText() != "" && instructor > -1 && capacityTextField.getText() != "" && Integer.parseInt(capacityTextField.getText()) > -1) {
	    	
    		//Try-Catch for the database connection
    		try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
    		//Try-Catch for the MySQL Query
			try {
				//Create the connection variable that allows us to create statements
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
				
				//Try-Catch for the query
				try {
					//Create our statement variable and then execute the INSERT queries in the database with the information from the textfields
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("INSERT INTO Course VALUES ('"+ subjectTextField.getText() + "', '" + courseCodeTextField.getText() + "', '" + titleTextField.getText() + "', '" + descriptionTextField.getText() + "')");
					stmt.executeUpdate("INSERT INTO Section VALUES ('"+ subjectTextField.getText() + "', '" + courseCodeTextField.getText() + "', '" + sectionTextField.getText() + "', " + instructorsMemIDs.get(instructor) + ", '" + lectureTimeTextField.getText() + "', " + Integer.parseInt(capacityTextField.getText()) + ", " + Integer.parseInt(capacityTextField.getText()) + ")");
					
					
					//Change part-time to full-time status if applicable
					ResultSet rs = stmt.executeQuery("SELECT * FROM Section WHERE memberID = " + instructorsMemIDs.get(instructor));
					
					int numOfRows = 0;
					while(rs.next())
						numOfRows++;

					//If the have more than 2 classes, switch them to full-time status
					if (numOfRows > 2)
						stmt.executeUpdate("UPDATE UniversityMember SET statusType = 'Full-Time' WHERE memberID = " + instructorsMemIDs.get(instructor));
					
					//Print success message
				    messageLabel.setText("Successfully Added!");
				    messageLabel.setVisible(true);
				} catch (SQLException e){
				    e.printStackTrace();
				}
				
				//Close connection
				conn.close();
			} catch (SQLException e) {
				//Print error message
				messageLabel.setText("Error!");
			    messageLabel.setVisible(true);
			}
	    	
			//Reset all the text fields to blank afterwards
	    	resetFields();
    	}
    	else {
    		//If there is incorrect input, then let the user know
    		messageLabel.setText("Error: Incorrect input.");
		    messageLabel.setVisible(true);
    	}
    }
    
    /**
     * When the back button is pressed, change the scene back to the admin welcome screen and reset the fields of this scene
     * 
     * @param event The event of the button being pressed
     */
    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneAdminWelcomeScreen);
    	resetFields();
    }
    
    /**
     * When the program is run, load the list of instructors for the drop box.
     * Have to do this otherwise the user will have to click the drop box 3 times for the instructors to load up
     */
    public void load() {
    	//Set the message label to not visible if it is not already
    	messageLabel.setVisible(false);
    	
    	//Clear the instructors list
    	instructors.clear();
    	
    	//Try-Catch for the database connection
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	//Try-Catch for the MySQL Connection
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			//Try-Catch for the Query
			try {
				//Create the connection variable that allows us to create statements
				Statement stmt = conn.createStatement();		    
				
				//Generate a query to get all the information from the UniversityMember table for all instructors and store this in rs
			    ResultSet rs = stmt.executeQuery("SELECT * FROM UniversityMember WHERE memberType = 'instructor'");
		    	
			    //If there is not results, display no instructors, otherwise add the instructor to the list
		    	if (rs.next() == false) {
				    instructors.add("No Instructors");
			    } else {
			    	//If there are instructors, add each one from rs to the instructors list
				    do {
				    	instructors.add(rs.getString(1) + "," + rs.getString(3) + "," + rs.getString(4));
				    } while (rs.next());
			    }
		    	
		    	//Create the list of instructors to go inside the drop down box
		    	ObservableList<String> choices = FXCollections.observableArrayList();
		    	
		    	//Iterate through each one in the list and add the memberID to another list for selection purposes and
		    	// add the instructors first name and last name and store those as a choice for the drop down
		    	for(int i = 0; i < instructors.size(); i++) {
		    		//Split the string to get the memberID, firstName, and lastName
		    		String[] split = instructors.get(i).split(",");
		    		instructorsMemIDs.add(split[0]);
		    		choices.add(String.format("%s %s", split[1], split[2]));
		    	}
		    	
		    	//Add the instructors to the drop down list
		    	InstructorChoiceBox.setItems(choices);
			} catch (SQLException e) {
			    e.printStackTrace();
			}

			//Close the connection
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Calls the load method on the click of the drop down being opened
     * 
     * @param event The event of the click
     */
    @FXML
    void loadInstructors(MouseEvent event) {
    	load();
    }

    /**
     * Set the message label to not visible whenever any field is pressed
     * 
     * @param event The event of a textfield or button being pressed
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
     * Resets all the fields in this UI
     */
    void resetFields() {
    	subjectTextField.setText("");
    	courseCodeTextField.setText("");
    	titleTextField.setText("");
    	sectionTextField.setText("");
    	InstructorChoiceBox.setValue(null);
    	descriptionTextField.setText("");
    	lectureTimeTextField.setText("");
    	capacityTextField.setText("");
    }
}
