/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * SearchCoursesController.java
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
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class SearchCoursesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="courseCodeTextField"
    private TextField courseCodeTextField; // Value injected by FXMLLoader

    @FXML // fx:id="courseNameTextField"
    private TextField courseNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="sectionTextField"
    private TextField sectionTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="keywordTextField"
    private TextField keywordTextField; // Value injected by FXMLLoader

    @FXML // fx:id="listScrollPane"
    private ScrollPane listScrollPane; // Value injected by FXMLLoader
    
    // VBox to be used to display information in the scroll pane
 	private VBox vBox = new VBox();

 	//Global Scenes
    private Main main;
    private Scene sceneStudentWelcomeScreen;
    
    /**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    /**
     * Sets the scene for the back to be pressed
     * 
     * @param sceneStudentWelcomeScreen
     */
    public void setBackPressedScene(Scene sceneStudentWelcomeScreen) {
    	this.sceneStudentWelcomeScreen = sceneStudentWelcomeScreen;
    }

    /**
     * Sends student back to the welcome screen and resets the fields
     * @param event
     */
    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneStudentWelcomeScreen);
    	resetFields();
    }

    /**
     * Shows results for whichever filters the student has
     * 
     * @param event
     */
    @FXML
    void searchButtonPressed(ActionEvent event) {
    	//Try-Catch for connection
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

    	//Try-Catch for connection
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");

			//Try-Catch for query
			try {
				//Create statement connection
				Statement stmt = conn.createStatement();
			    ResultSet rs = null;
			    
			    //Execute query depending on which text fields have content
			    if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID ORDER BY Course.courseName");
			    else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseName LIKE '%"+ courseNameTextField.getText().toUpperCase() + "%' ORDER BY Course.courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' ORDER BY Course.courseName");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection LIKE '%" + sectionTextField.getText() + "%' ORDER BY Course.courseName");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' AND Course.courseName LIKE '%" + courseNameTextField.getText().toUpperCase() + "%' ORDER BY Course.courseCode");
				else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection LIKE '%" + sectionTextField.getText() + "%' AND Course.courseName LIKE '%" + courseNameTextField.getText().toUpperCase() + "%' ORDER BY Course.courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection LIKE '%" + sectionTextField.getText() + "%' AND Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' ORDER BY Course.courseCode");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection LIKE '%" + sectionTextField.getText() + "%' AND Course.courseName LIKE '%" + courseNameTextField.getText().toUpperCase() + "%' AND Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' ORDER BY Course.courseName");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' ORDER BY Course.courseName");
			    else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Course.courseName LIKE '%"+ courseNameTextField.getText().toUpperCase() + "%' ORDER BY Course.courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' ORDER BY Course.courseName");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Section.courseSection LIKE '%" + sectionTextField.getText() + "%' ORDER BY Course.courseName");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' AND Course.courseName LIKE '%" + courseNameTextField.getText().toUpperCase() + "%' ORDER BY Course.courseCode");
				else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Section.courseSection LIKE '%" + sectionTextField.getText() + "%' AND Course.courseName LIKE '%" + courseNameTextField.getText().toUpperCase() + "%' ORDER BY Course.courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Section.courseSection LIKE '%" + sectionTextField.getText() + "%' AND Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' ORDER BY Course.courseCode");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Section.courseSection LIKE '%" + sectionTextField.getText() + "%' AND Course.courseName LIKE '%" + courseNameTextField.getText().toUpperCase() + "%' AND Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' ORDER BY Course.courseName");
			    
			    //Create the vBox
			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);
			    
			    //Go through each results and add the labels to the vBox
			    if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
			    } else {
				    do {
				    	//Bold the name
				    	Label lbName = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(7)));
				    	lbName.setPrefWidth(380);
				    	lbName.setWrapText(true);
				    	lbName.setStyle("-fx-font-weight: bold");
				    	vBox.getChildren().add(lbName);
				    	
				    	//Title is italics
				    	Label lbTitle = new Label(String.format(rs.getString(3)));
				    	lbTitle.setPrefWidth(380);
				    	lbTitle.setWrapText(true);
				    	lbTitle.setFont(Font.font(null, FontPosture.ITALIC, 12));
				    	vBox.getChildren().add(lbTitle);
				    	
				    	//Put the instructor there
				    	Label lbInstructor = new Label(String.format("Instructor: " + rs.getString(14) + " " + rs.getString(15)));
				    	lbInstructor.setPrefWidth(380);
				    	lbInstructor.setWrapText(true);
				    	vBox.getChildren().add(lbInstructor);
				    	
				    	//Put the course description
				    	Label lbInfo = new Label(String.format(rs.getString(4)));
				    	lbInfo.setPrefWidth(380);
				    	lbInfo.setWrapText(true);
				    	vBox.getChildren().add(lbInfo);
				    	
				    	//Add a separator
				    	Separator sp = new Separator();
				    	sp.setOrientation(Orientation.HORIZONTAL);
				    	vBox.getChildren().add(sp);
				    } while (rs.next());
			    }
			    
			    //Add the vBox to the scroll pane
			    listScrollPane.setContent(vBox);
			} catch (SQLException e){
			    e.printStackTrace();
			}

			//Close the connection
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    /**
     * Reset the fields on the UI
     */
    void resetFields() {
    	vBox.getChildren().clear();
    	courseCodeTextField.setText("");
    	courseNameTextField.setText("");
    	sectionTextField.setText("");
    	keywordTextField.setText("");
    }
}

