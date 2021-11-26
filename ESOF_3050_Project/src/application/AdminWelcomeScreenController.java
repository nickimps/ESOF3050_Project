/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * AdminWelcomeScreenController.java
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

public class AdminWelcomeScreenController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="helloLabel"
    private Label helloLabel; // Value injected by FXMLLoader
    
    //Global scenes to bring the user to which ever UI is applicable to them
    private Main main;
    private Scene sceneLogin;
    private Scene sceneAddCourse;
    private Scene sceneAddEmployee;
    private Scene sceneRemoveEmployee;
    private Scene sceneRemoveCourse;
    private Scene sceneEmployeeList;
    private Scene sceneStudentList;
    private Scene sceneViewCourses;
    
    //The controller classes for same purposes
    private ViewCoursesController vc;
    private EmployeeListController el;
    private StudentListController sl;
    private AddCourseController ac;
    private AddEmployeeController ae;
    private RemoveEmployeeController re;
    private RemoveCourseController rc;
    
    //Stores the memberID
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
     * Sets the scene for the add course button
     * 
     * @param sceneAddCourse The scene to be switched to upon press
     */
    public void setAddCoursePressedScene(Scene sceneAddCourse) {
    	this.sceneAddCourse = sceneAddCourse;
    }
    
    /**
     * Sets the scene for the remove course button
     * 
     * @param sceneRemoveCourse The scene to be switched to upon press
     */
    public void setRemoveCoursePressedScene(Scene sceneRemoveCourse) {
    	this.sceneRemoveCourse = sceneRemoveCourse;
    }
    
    /**
     * Sets the scene for the add employee button
     * 
     * @param sceneAddEmployee The scene to be switched to upon press
     */
    public void setAddEmployeePressedScene(Scene sceneAddEmployee) {
    	this.sceneAddEmployee = sceneAddEmployee;
    }
    
    /**
     * Sets the scene for the remove employee button
     * 
     * @param sceneRemoveEmployee The scene to be switched to upon press
     */
    public void setRemoveEmployeePressedScene(Scene sceneRemoveEmployee) {
    	this.sceneRemoveEmployee = sceneRemoveEmployee;
    }
    
    /**
     * Sets the scene for the employee list button
     * 
     * @param sceneEmployeeList The scene to be switched to upon press
     */
    public void setEmployeeListPressedScene(Scene sceneEmployeeList) {
    	this.sceneEmployeeList = sceneEmployeeList;
    }
    
    /**
     * Sets the scene for the student list button
     * 
     * @param sceneStudentList The scene to be switched to upon press
     */
    public void setStudentListPressedScene(Scene sceneStudentList) {
    	this.sceneStudentList = sceneStudentList;
    }
    
    /**
     * Sets the scene for the view courses button
     * 
     * @param sceneViewCourses The scene to be switched to upon press
     */
    public void setViewActiveCoursesPressedScene(Scene sceneViewCourses) {
    	this.sceneViewCourses = sceneViewCourses;
    }
    
    /**
     * Sets the scene for the logout button
     * 
     * @param sceneLogin The scene to be switched to upon press
     */
    public void setLogoutPressedScene(Scene sceneLogin) {
    	this.sceneLogin = sceneLogin;
    }
    
    /**
     * Sets the member ID of the and shows the name of the who's logged in
     * 
     * @param memberID The member ID to be set
     */
    public void setMemberID(String memberID) {
    	this.memberID = memberID;
    	
    	//Try-Catch for the database connection
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	//Try-Catch for the MySQL Connection
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			//Try-Catch for Query
			try {
				//Creates the statement to connect
				Statement stmt = conn.createStatement();
				
				//Gets the results from the query
				ResultSet rs = stmt.executeQuery("SELECT firstName FROM UniversityMember WHERE memberID = " + Integer.parseInt(this.memberID));
			    rs.next();
			    
			    //Changes the label to the name 
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
     * Sets the controller for the viewCourses
     * 
     * @param vc
     */
    public void setViewCoursesController(ViewCoursesController vc) {
    	this.vc = vc;
    }
    
    /**
     * Sets the controller for the employee list
     * 
     * @param el
     */
    public void setEmployeeListController(EmployeeListController el) {
    	this.el = el;
    }
    
    /**
     * Sets the controller for the student list
     * 
     * @param sl
     */
    public void setStudentListController(StudentListController sl) {
    	this.sl = sl;
    }
    
    /**
     * Sets the controller for the add course
     * 
     * @param ac
     */
    public void setAddCourseController(AddCourseController ac) {
    	this.ac = ac;
    }
    
    /**
     * Sets the controller for the add employee
     * 
     * @param ae
     */
    public void setAddEmployeeController(AddEmployeeController ae) {
    	this.ae = ae;
    }
    
    /**
     * Sets the controller for the remove employee
     * 
     * @param re
     */
    public void setRemoveEmployeeController(RemoveEmployeeController re) {
    	this.re = re;
    }
    
    /**
     * Sets the controller for the remove course
     * 
     * @param rc
     */
    public void setRemoveCourseController(RemoveCourseController rc) {
    	this.rc = rc;
    }
    
    /**
     * Loads the instructors for the drop down in the add course scene
     */
    public void loadInstructorsPre() {
    	ac.load();
    }

    /**
     * Changes the scene on add couse button press
     * 
     * @param event
     */
    @FXML
    void addCourseButtonPressed(ActionEvent event) {
    	main.setScreen(sceneAddCourse);
    	ac.load();
    }

    /**
     * Changes the scene to add employee
     * 
     * @param event
     */
    @FXML
    void addEmployeeButtonPressed(ActionEvent event) {
    	main.setScreen(sceneAddEmployee);
    	ae.generateMemberID();
    }

    /**
     * Changes the scene to the logout screen
     * 
     * @param event
     */
    @FXML
    void logoutButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    }

    /**
     * Changes the scene to the remove course screen
     * 
     * @param event
     */
    @FXML
    void removeCourseButtonPressed(ActionEvent event) {
    	main.setScreen(sceneRemoveCourse);
    	rc.showList();
    }

    /**
     * Changes the scene to the remove employee screen
     * 
     * @param event
     */
    @FXML
    void removeEmployeeButtonPressed(ActionEvent event) {
    	main.setScreen(sceneRemoveEmployee);
    	re.showList();
    }

    /**
     * Changes the scene to viewing active courses
     * 
     * @param event
     */
    @FXML
    void viewActiveCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneViewCourses);
    	vc.sceneSwitched(memberID, false);
    }

    /**
     * Changes the scene to viewing employee list
     * 
     * @param event
     */
    @FXML
    void viewEmployeeListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneEmployeeList);
    	el.showList(memberID);
    }

    /**
     * Changes the scene to view the student list
     * 
     * @param event
     */
    @FXML
    void viewStudentListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneStudentList);
    	sl.showList(memberID);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}

