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
import javafx.scene.control.TextField;

public class LoginController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userNameTextField"
    private TextField userNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="lastNameTextField"
    private TextField passwordTextField; // Value injected by FXMLLoader
    
    private Main main;
    private String memberID;
    private Scene sceneStudentWelcomeScreen;
    private Scene sceneAdminWelcomeScreen;
    private Scene sceneInstructorWelcomeScreen;
    
    public void setMainScene(Main main, String memberID) {
    	this.main = main;
    	this.memberID = memberID;
    }
    
    public void setLoginPressScene(Scene sceneStudentWelcomeScreen, Scene sceneAdminWelcomeScreen, Scene sceneInstructorWelcomeScreen) {
    	this.sceneStudentWelcomeScreen = sceneStudentWelcomeScreen;
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    	this.sceneInstructorWelcomeScreen = sceneInstructorWelcomeScreen;
    }

    @FXML
    void loginButtonPressed(ActionEvent event) {
    	
    	String type = "";
    	
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			try {
				Statement stmt = conn.createStatement();
			    
			    //Execute query and get number of columns
				ResultSet rs = stmt.executeQuery("SELECT * FROM Login WHERE memberID = " + Integer.parseInt(userNameTextField.getText()) + " AND password = '" + passwordTextField.getText() + "'");
			    
			    if (rs.next() == false)
			    	System.out.println("User doesn't exist.");	//no user, should print alert on gui
			    else {
				    type = rs.getString(3);
			    }
			} catch (SQLException e) {
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		main.setMemberID(userNameTextField.getText());
		
		//Go to appropriate screen
		if (type.equals("student"))
	    	main.setScreen(sceneStudentWelcomeScreen);
	    else if (type.equals("instructor"))
	    	main.setScreen(sceneInstructorWelcomeScreen);
	    else if (type.equals("administrator"))
	    	main.setScreen(sceneAdminWelcomeScreen);
		
		resetFields();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    void resetFields() {
    	userNameTextField.setText("");
    	passwordTextField.setText("");
    }
}

