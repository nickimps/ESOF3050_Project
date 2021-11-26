/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * StudentWelcomeScreenController.java
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

public class StudentWelcomeScreenController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="helloLabel"
    private Label helloLabel; // Value injected by FXMLLoader
    
    //Global Variables
    private Main main;
    private Scene sceneLogin;
    private Scene sceneSearchCourses;
    private Scene sceneRegisterCourse;
    private Scene sceneViewCourses;
    private Scene sceneDropCourses;
    
    private ViewCoursesController vc;
    private DropCoursesController dc;
    
    private String memberID;
    
    /**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    /**
     * Sets the search courses scene
     * 
     * @param sceneSearchCourses
     */
    public void setSearchCoursesPressedScene(Scene sceneSearchCourses) {
    	this.sceneSearchCourses = sceneSearchCourses;
    }
    
    /**
     * Sets the register courses scene
     * 
     * @param sceneRegisterCourse
     */
    public void setRegisterCoursesPressedScene(Scene sceneRegisterCourse) {
    	this.sceneRegisterCourse = sceneRegisterCourse;
    }
    
    /**
     * Sets the view courses scene
     * 
     * @param sceneViewCourses
     */
    public void setViewCoursesPressedScene(Scene sceneViewCourses) {
    	this.sceneViewCourses = sceneViewCourses;
    }
    
    /**
     * Sets the view grades screen
     * 
     * @param sceneViewCourses
     */
    public void setViewGradesPressedScene(Scene sceneViewCourses) {
    	this.sceneViewCourses = sceneViewCourses;
    }
    
    /**
     * Sets the drop courses scene
     * 
     * @param sceneDropCourses
     */
    public void setDropCoursesPressedScene(Scene sceneDropCourses) {
    	this.sceneDropCourses = sceneDropCourses;
    }
    
    /**
     * Sets the logout screen
     * 
     * @param sceneLogin
     */
    public void setLogoutPressedScene(Scene sceneLogin) {
    	this.sceneLogin = sceneLogin;
    }
    
    /**
     * Sets the member ID
     * 
     * @param memberID
     */
    public void setMemberID(String memberID) {
    	this.memberID = memberID;
    	
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
				
				//Gets the name of the members
				ResultSet rs = stmt.executeQuery("SELECT firstName FROM UniversityMember WHERE memberID = " + Integer.parseInt(this.memberID));
			    rs.next();
			    
			    //Changes the label to persons name
				helloLabel.setText("Hello, " + rs.getString(1));
			    
			} catch (SQLException e) {
			    e.printStackTrace();
			}
			
			//Close connection
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Sets the view courses controller
     * 
     * @param vc
     */
    public void setViewCoursesController(ViewCoursesController vc) {
    	this.vc = vc;
    }
    
    /**
     * Sets the drop courses controller
     * 
     * @param dc
     */
    public void setDropCoursesController(DropCoursesController dc) {
    	this.dc = dc;
    }

    /**
     * Sets the drop courses controller
     * 
     * @param event
     */
    @FXML
    void dropCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneDropCourses);
    	dc.showList();
    }

    /**
     * Sets the logout controller
     * 
     * @param event
     */
    @FXML
    void logoutButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    }

    /**
     * Sets the register for courses controller
     * 
     * @param event
     */
    @FXML
    void registerForCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneRegisterCourse);
    }

    /**
     * Sets the search for courses controller
     * 
     * @param event
     */
    @FXML
    void searchForCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneSearchCourses);
    }

    /**
     * Sets the enrolled courses controller
     * 
     * @param event
     */
    @FXML
    void viewEnrolledCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneViewCourses);
    	vc.sceneSwitched(memberID, false);
    }

    /**
     * Sets the view grades controller
     * 
     * @param event
     */
    @FXML
    void viewGradesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneViewCourses);
    	vc.sceneSwitched(memberID, true);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}

