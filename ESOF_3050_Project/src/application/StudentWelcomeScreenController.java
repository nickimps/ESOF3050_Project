package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

public class StudentWelcomeScreenController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    private Main main;
    private Scene sceneLogin;
    private Scene sceneSearchCourses;
    private Scene sceneRegisterCourse;
    private Scene sceneViewCourses;
    private Scene sceneDropCourses;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setSearchCoursesPressedScene(Scene sceneSearchCourses) {
    	this.sceneSearchCourses = sceneSearchCourses;
    }
    
    public void setRegisterCoursesPressedScene(Scene sceneRegisterCourse) {
    	this.sceneRegisterCourse = sceneRegisterCourse;
    }
    
    public void setViewCoursesPressedScene(Scene sceneViewCourses) {
    	this.sceneViewCourses = sceneViewCourses;
    }
    
    public void setViewGradesPressedScene(Scene sceneViewCourses) {
    	this.sceneViewCourses = sceneViewCourses;
    }
    
    public void setDropCoursesPressedScene(Scene sceneDropCourses) {
    	this.sceneDropCourses = sceneDropCourses;
    }
    
    public void setLogoutPressedScene(Scene sceneLogin) {
    	this.sceneLogin = sceneLogin;
    }

    @FXML
    void dropCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneDropCourses);
    }

    @FXML
    void logoutButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    }

    @FXML
    void registerForCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneRegisterCourse);
    }

    @FXML
    void searchForCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneSearchCourses);
    }

    @FXML
    void viewEnrolledCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneViewCourses);
    }

    @FXML
    void viewGradesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneViewCourses);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}

