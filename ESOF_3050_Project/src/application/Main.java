package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Main extends Application {
	
	// Primary Stage
    Stage stage;
    // Two scenes
    Scene sceneLogin, sceneAddCourse;
    // The panes are associated with the respective .fxml files
    private Pane paneLogin;
    private Pane paneAddCourse;
	
	@Override
	public void start(Stage mainStage) throws Exception {
		/*Parent rootLogin = FXMLLoader.load(getClass().getResource("login.fxml"));
		Parent rootAddCourse = FXMLLoader.load(getClass().getResource("addCourse.fxml"));
		
		Scene sceneLogin = new Scene(rootLogin);
		Scene sceneAddCourse = new Scene(rootAddCourse);
		stage.setTitle("University Regristration System");
		stage.setScene(sceneLogin);
		stage.show();*/
		
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
			
			//Set Scenes to the loaded FXML's
			Scene sceneLogin = new Scene(paneLogin);
			Scene sceneAddCourse = new Scene(paneAddCourse);
			
			//Pass Reference to their controller classes
			loginController.setMainScene(this);
			loginController.setLoginPressScene(sceneAddCourse);
			addCourseController.setMainScene(this);
			addCourseController.setAddPressedScene(sceneLogin);
			
			stage.setScene(sceneLogin);
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
