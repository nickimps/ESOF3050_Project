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
    
    public void setMemberID(String memberID) {
    	this.memberID = memberID;
    	
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			try {
				Statement stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT firstName FROM UniversityMember WHERE memberID = " + Integer.parseInt(this.memberID));
			    rs.next();
				helloLabel.setText("Hello, " + rs.getString(1));
			    
			} catch (SQLException e) {
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void setViewCoursesController(ViewCoursesController vc) {
    	this.vc = vc;
    }
    
    public void setEmployeeListController(EmployeeListController el) {
    	this.el = el;
    }
    
    public void setStudentListController(StudentListController sl) {
    	this.sl = sl;
    }
    
    public void setModifyGradesController(ModifyGradesController mg) {
    	this.mg = mg;
    }
    
    @FXML
    void logoutButtonPressed(ActionEvent event) {
    	main.setScreen(sceneLogin);
    }

    @FXML
    void modifyGradesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneModifyGrades);
    	mg.showList(memberID);
    }

    @FXML
    void viewActiveCoursesButtonPressed(ActionEvent event) {
    	main.setScreen(sceneViewCourses);
    	vc.sceneSwitched(memberID, false);
    }

    @FXML
    void viewEmployeeListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneEmployeeList);
    	el.showList(memberID);
    }

    @FXML
    void viewStudentsListButtonPressed(ActionEvent event) {
    	main.setScreen(sceneStudentList);
    	sl.showList(memberID);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}

