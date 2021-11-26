/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * InstructorWelcomeScreenController.java
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

public class InstructorWelcomeScreenController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    private Main main;
    private Scene sceneLogin;
    private Scene sceneViewCourses;
    private Scene sceneModifyGrades;
    private Scene sceneEmployeeList;
    private Scene sceneStudentList;
    
    private ViewCoursesController vc;
    private EmployeeListController el;
    private StudentListController sl;
    private ModifyGradesController mg;
    
    @FXML // fx:id="helloLabel"
    private Label helloLabel; // Value injected by FXMLLoader
    
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
     * Set the view courses Scene
     * 
     * @param sceneViewCourses
     */
    public void setViewActiveCoursesPressedScene(Scene sceneViewCourses) {
    	this.sceneViewCourses = sceneViewCourses;
    }
    
    /**
     * Set the modify grades Scene
     * 
     * @param sceneModifyGrades
     */
    public void setModifyGradesPressedScene(Scene sceneModifyGrades) {
    	this.sceneModifyGrades = sceneModifyGrades;
    }
   
    /**
     * Set the employee list Scene
     * 
     * @param sceneEmployeeList
     */
    public void setEmployeeListPressedScene(Scene sceneEmployeeList) {
    	this.sceneEmployeeList = sceneEmployeeList;
    }
    
    /**
     * set the student list Scene
     * 
     * @param sceneStudentList
     */
    public void setStudentListPressedScene(Scene sceneStudentList) {
    	this.sceneStudentList = sceneStudentList;
    }
    
    /**
     * Set the logout Scene
     * @param sceneLogin
     */
    public void setLogoutPressedScene(Scene sceneLogin) {
    	this.sceneLogin = sceneLogin;
    }
    
    /**
     * Set the member id and update the name
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
				//Get the name of who is logged in
				ResultSet rs = stmt.executeQuery("SELECT firstName FROM UniversityMember WHERE memberID = " + Integer.parseInt(this.memberID));
			    rs.next();
				helloLabel.setText("Hello, " + rs.getString(1));
			    
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
     * set the view courses controller
     * 
     * @param vc
     */
    public void setViewCoursesController(ViewCoursesController vc) {
    	this.vc = vc;
    }
    
    /**
     * Set the employee list controller
     * 
     * @param el
     */
    public void setEmployeeListController(EmployeeListController el) {
    	this.el = el;
    }
    
    /**
     * set the student list controller
     * 
     * @param sl
     */
    public void setStudentListController(StudentListController sl) {
    	this.sl = sl;
    }
    
    /**
     * Set the modify grades controller
     * 
     * @param mg
     */
    public void setModifyGradesController(ModifyGradesController mg) {
    	this.mg = mg;
    }
    
    /**
     * Brings the user to the login screen
     * 
     * @param event
     */
    @FXML
    void logoutButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    }

    /**
     * Bring the user to the modify grades screen
     * 
     * @param event
     */
    @FXML
    void modifyGradesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneModifyGrades);
    	mg.showList(memberID);
    }

    /**
     * Bring the user to the active courses screen
     * 
     * @param event
     */
    @FXML
    void viewActiveCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneViewCourses);
    	vc.sceneSwitched(memberID, false);
    }

    /**
     * Bring the user to the employee list screen
     * 
     * @param event
     */
    @FXML
    void viewEmployeeListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneEmployeeList);
    	el.showList(memberID);
    }

    /**
     * Bring the user to the student list screen
     * 
     * @param event
     */
    @FXML
    void viewStudentsListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneStudentList);
    	sl.showList(memberID);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}

