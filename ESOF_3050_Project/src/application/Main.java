package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Main extends Application {
	
	// Primary Stage
    Stage stage;
    // Two scenes
    Scene sceneLogin;
    Scene sceneAddCourse;
    Scene sceneAddEmployee;
    Scene sceneDropCourses;
    Scene sceneEmployeeList;
    Scene sceneViewCourses;
    Scene sceneModifyGrades;
    Scene sceneRegisterCourse;
    Scene scenesStudentList;
    Scene sceneSearchCourses;
    Scene sceneRemoveEmployee;
    Scene sceneRemoveCourse;
    Scene sceneStudentWelcomeScreen;
    Scene sceneInstructorWelcomeScreen;
    Scene sceneAdminWelcomeScreen;
    
    // The panes are associated with the respective .fxml files
    private Pane paneLogin;
    private Pane paneAddCourse;
    private Pane paneAddEmployee;
    private Pane paneDropCourses;
    private Pane paneEmployeeList;
    private Pane paneViewCourses;
    private Pane paneModifyGrades;
    private Pane paneRegisterCourse;
    private Pane paneStudentList;
    private Pane paneSearchCourses;
    private Pane paneRemoveEmployee;
    private Pane paneRemoveCourse;
    private Pane paneStudentWelcomeScreen;
    private Pane paneInstructorWelcomeScreen;
    private Pane paneAdminWelcomeScreen;
	
	@Override
	public void start(Stage mainStage) throws Exception {
		try {
			//Set stage as main stage
			stage = mainStage;
			
			//Load the FXML's and their specific controller classes
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("login.fxml"));
			paneLogin = fxmlloader.load();
			LoginController loginController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("addCourse.fxml"));
			paneAddCourse = fxmlloader.load();
			AddCourseController addCourseController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("addEmployee.fxml"));
			paneAddEmployee = fxmlloader.load();
			AddEmployeeController addEmployeeController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("dropCourses.fxml"));
			paneDropCourses = fxmlloader.load();
			DropCoursesController dropCoursesController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("employeeList.fxml"));
			paneEmployeeList = fxmlloader.load();
			EmployeeListController employeeListController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("enrolledCoursesViewGradesViewActiveCourses.fxml"));
			paneViewCourses = fxmlloader.load();
			ViewCoursesController viewCoursesController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("modifyGrades.fxml"));
			paneModifyGrades = fxmlloader.load();
			ModifyGradesController modifyGradesController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("registerCourse.fxml"));
			paneRegisterCourse = fxmlloader.load();
			RegisterForCoursesController registerForCoursesController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("studentList.fxml"));
			paneStudentList = fxmlloader.load();
			StudentListController studentListController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("searchCourses.fxml"));
			paneSearchCourses = fxmlloader.load();
			SearchCoursesController searchCoursesController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("removeEmployee.fxml"));
			paneRemoveEmployee = fxmlloader.load();
			RemoveEmployeeController removeEmployeeController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("removeCourse.fxml"));
			paneRemoveCourse = fxmlloader.load();
			RemoveCourseController removeCourseController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("optionScreenStud.fxml"));
			paneStudentWelcomeScreen = fxmlloader.load();
			StudentWelcomeScreenController studentWelcomeScreenController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("optionScreenInstr.fxml"));
			paneInstructorWelcomeScreen = fxmlloader.load();
			InstructorWelcomeScreenController instructorWelcomeScreenController = fxmlloader.getController();
			
			fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(Main.class.getResource("optionScreenAdmin.fxml"));
			paneAdminWelcomeScreen = fxmlloader.load();
			AdminWelcomeScreenController adminWelcomeScreenController = fxmlloader.getController();
			
			//Set Scenes to the loaded FXML's
			Scene sceneLogin = new Scene(paneLogin);					//done
			Scene sceneAddCourse = new Scene(paneAddCourse);			//done
			Scene sceneAddEmployee = new Scene(paneAddEmployee);		//done
			Scene sceneDropCourses = new Scene(paneDropCourses);
			Scene sceneEmployeeList = new Scene(paneEmployeeList);
			Scene sceneViewCourses = new Scene(paneViewCourses);
			Scene sceneModifyGrades = new Scene(paneModifyGrades);
			Scene sceneRegisterCourse = new Scene(paneRegisterCourse);
			Scene sceneStudentList = new Scene(paneStudentList);
			Scene sceneSearchCourses = new Scene(paneSearchCourses);
			Scene sceneRemoveEmployee = new Scene(paneRemoveEmployee);
			Scene sceneRemoveCourse = new Scene(paneRemoveCourse);
			Scene sceneStudentWelcomeScreen = new Scene(paneStudentWelcomeScreen);
			Scene sceneInstructorWelcomeScreen = new Scene(paneInstructorWelcomeScreen);
			Scene sceneAdminWelcomeScreen = new Scene(paneAdminWelcomeScreen);
			
			//Pass Reference to their controller classes
			loginController.setMainScene(this);
			loginController.setLoginPressScene(null);			
			
			/* need to check type of user here */
			
			addCourseController.setMainScene(this);
			addCourseController.setAddPressedScene(sceneAdminWelcomeScreen);
			addEmployeeController.setMainScene(this);
			addEmployeeController.setAddPressedScene(null); 	//Set to proper scene
			dropCoursesController.setMainScene(this);
			dropCoursesController.setAddPressedScene(null); 	//Set to proper scene
			employeeListController.setMainScene(this);
			employeeListController.setAddPressedScene(null); 	//Set to proper scene
			viewCoursesController.setMainScene(this);
			viewCoursesController.setAddPressedScene(null); 	//Set to proper scene
			modifyGradesController.setMainScene(this);
			modifyGradesController.setAddPressedScene(null); 	//Set to proper scene
			registerForCoursesController.setMainScene(this);
			registerForCoursesController.setAddPressedScene(null); //Set to proper scene
			studentListController.setMainScene(this);
			studentListController.setAddPressedScene(null); 	//Set to proper scene
			searchCoursesController.setMainScene(this);
			searchCoursesController.setAddPressedScene(null); 	//Set to proper scene
			removeEmployeeController.setMainScene(this);
			removeEmployeeController.setAddPressedScene(null); 	//Set to proper scene
			removeCourseController.setMainScene(this);
			removeCourseController.setAddPressedScene(null); 	//Set to proper scene
			studentWelcomeScreenController.setMainScene(this);
			studentWelcomeScreenController.setAddPressedScene(null); 	//Set to proper scene
			instructorWelcomeScreenController.setMainScene(this);
			instructorWelcomeScreenController.setAddPressedScene(null); //Set to proper scene
			adminWelcomeScreenController.setMainScene(this);
			adminWelcomeScreenController.setAddPressedScene(null); 		//Set to proper scene
			
			stage.setScene(sceneAddEmployee);
			stage.setTitle("Univeristy Registration System");
			stage.show();
			
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void setScreen(Scene sc) {
		stage.setScene(sc);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
