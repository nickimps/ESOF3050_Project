package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class AdminWelcomeScreenController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    private Main main;
    private Scene sceneLogin;
    private Scene sceneAddCourse;
    private Scene sceneAddEmployee;
    private Scene sceneRemoveEmployee;
    private Scene sceneRemoveCourse;
    private Scene sceneEmployeeList;
    private Scene sceneStudentList;
    private Scene sceneViewCourses;
    
    private ViewCoursesController vc;
    
    private String memberID;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setAddCoursePressedScene(Scene sceneAddCourse) {
    	this.sceneAddCourse = sceneAddCourse;
    }
    
    public void setRemoveCoursePressedScene(Scene sceneRemoveCourse) {
    	this.sceneRemoveCourse = sceneRemoveCourse;
    }
    
    public void setAddEmployeePressedScene(Scene sceneAddEmployee) {
    	this.sceneAddEmployee = sceneAddEmployee;
    }
    
    public void setRemoveEmployeePressedScene(Scene sceneRemoveEmployee) {
    	this.sceneRemoveEmployee = sceneRemoveEmployee;
    }
    
    public void setEmployeeListPressedScene(Scene sceneEmployeeList) {
    	this.sceneEmployeeList = sceneEmployeeList;
    }
    
    public void setStudentListPressedScene(Scene sceneStudentList) {
    	this.sceneStudentList = sceneStudentList;
    }
    
    public void setViewActiveCoursesPressedScene(Scene sceneViewCourses) {
    	this.sceneViewCourses = sceneViewCourses;
    }
    
    public void setLogoutPressedScene(Scene sceneLogin) {
    	this.sceneLogin = sceneLogin;
    }
    
    public void setMemberID(String memberID) {
    	this.memberID = memberID;
    }
    
    public void setViewCoursesController(ViewCoursesController vc) {
    	this.vc = vc;
    }

    @FXML
    void addCourseButtonPressed(ActionEvent event) {
    	main.setScreen(sceneAddCourse);
    }

    @FXML
    void addEmployeeButtonPressed(ActionEvent event) {
    	main.setScreen(sceneAddEmployee);
    }

    @FXML
    void logoutButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    }

    @FXML
    void removeCourseButtonPressed(ActionEvent event) {
    	main.setScreen(sceneRemoveCourse);
    }

    @FXML
    void removeEmployeeButtonPressed(ActionEvent event) {
    	main.setScreen(sceneRemoveEmployee);
    }

    @FXML
    void viewActiveCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneViewCourses);
    	vc.sceneSwitched(memberID, false);
    }

    @FXML
    void viewEmployeeListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneEmployeeList);
    }

    @FXML
    void viewStudentListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneStudentList);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}

