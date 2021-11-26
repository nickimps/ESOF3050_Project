/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * LoginController.java
 */

package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userNameTextField"
    private TextField userNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTextField"
    private PasswordField passwordTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="incorrectLabel"
    private Label incorrectLabel; // Value injected by FXMLLoader
    
    //Global scenes
    private Main main;
    private Scene sceneStudentWelcomeScreen;
    private Scene sceneAdminWelcomeScreen;
    private Scene sceneInstructorWelcomeScreen;
    
    /**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main, String memberID) {
    	this.main = main;
    }
    
    /**
     * Sets the login screen scene
     * 
     * @param sceneStudentWelcomeScreen
     * @param sceneAdminWelcomeScreen
     * @param sceneInstructorWelcomeScreen
     */
    public void setLoginPressScene(Scene sceneStudentWelcomeScreen, Scene sceneAdminWelcomeScreen, Scene sceneInstructorWelcomeScreen) {
    	this.sceneStudentWelcomeScreen = sceneStudentWelcomeScreen;
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    	this.sceneInstructorWelcomeScreen = sceneInstructorWelcomeScreen;
    }

    /**
     * Checks the database for the user and allows them in or not
     * @param event
     */
    @FXML
    void loginButtonPressed(ActionEvent event) {
    	//Store the type of member
    	String type = "";
    	
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
				Statement stmt = conn.createStatement();
			    
			    //Execute query and get number of columns
				ResultSet rs = stmt.executeQuery("SELECT * FROM Login WHERE memberID = " + Integer.parseInt(userNameTextField.getText()) + " AND password = '" + passwordTextField.getText() + "'");
			    
				//If it is correct then store the type, otherwise show the incorrect label
			    if (rs.next() == false) {
			    	incorrectLabel.setVisible(true);
				}
			    else {
				    type = rs.getString(3);
			    	incorrectLabel.setVisible(false);
			    	//Set the main memberID 
					main.setMemberID(userNameTextField.getText());
			    }
			} catch (SQLException e) {
				incorrectLabel.setVisible(true);
			}

			//Close the connection
			conn.close();
		} catch (SQLException e) {
			incorrectLabel.setVisible(true);
		}

		//Go to appropriate screen
		if (type.equals("student"))
	    	main.setScreen(sceneStudentWelcomeScreen);
	    else if (type.equals("instructor"))
	    	main.setScreen(sceneInstructorWelcomeScreen);
	    else if (type.equals("administrator"))
	    	main.setScreen(sceneAdminWelcomeScreen);
		
		//Reset the fields
		resetFields();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    /**
     * Reset the fields
     */
    void resetFields() {
    	incorrectLabel.setVisible(false);
    	userNameTextField.setText("");
    	passwordTextField.setText("");
    }
}

