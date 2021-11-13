package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

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
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setViewActiveCoursesPressedScene(Scene sceneViewCourses) {
    	this.sceneViewCourses = sceneViewCourses;
    }
    
    public void setModifyGradesPressedScene(Scene sceneModifyGrades) {
    	this.sceneModifyGrades = sceneModifyGrades;
    }
    
    public void setEmployeeListPressedScene(Scene sceneEmployeeList) {
    	this.sceneEmployeeList = sceneEmployeeList;
    }
    
    public void setStudentListPressedScene(Scene sceneStudentList) {
    	this.sceneStudentList = sceneStudentList;
    }
    
    public void setLogoutPressedScene(Scene sceneLogin) {
    	this.sceneLogin = sceneLogin;
    }
    
    @FXML
    void logoutButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    }

    @FXML
    void modifyGradesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneModifyGrades);
    }

    @FXML
    void viewActiveCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneViewCourses);
    }

    @FXML
    void viewEmployeeListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneEmployeeList);
    }

    @FXML
    void viewStudentsListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneStudentList);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}

