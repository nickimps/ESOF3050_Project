/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * ViewCoursesController.java
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

public class ViewCoursesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="listScrollPane"
    private ScrollPane listScrollPane; // Value injected by FXMLLoader
    
    @FXML // fx:id="titleLabel"
    private Label titleLabel; // Value injected by FXMLLoader
    
    //Global Scenes
    private Main main;
    private Scene sceneAdminWelcomeScreen;
    private Scene sceneInstructorWelcomeScreen;
    private Scene sceneStudentWelcomeScreen;
    
    //Store the type of student
    private String type = "";
    
    // VBox to be used to display information in the scroll pane
  	private VBox vBox = new VBox();
    
  	/**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    /**
     * Stores the possible scenes to go back to depending on which user is using this screen
     * 
     * @param sceneAdminWelcomeScreen
     * @param sceneInstructorWelcomeScreen
     * @param sceneStudentWelcomeScreen
     */
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen, Scene sceneInstructorWelcomeScreen, Scene sceneStudentWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    	this.sceneInstructorWelcomeScreen = sceneInstructorWelcomeScreen;
    	this.sceneStudentWelcomeScreen = sceneStudentWelcomeScreen;
    }
    
    /**
     * Switches the scene, gets the user type and displays the appropriate information
     * 
     * @param memberID
     * @param viewGrades
     */
    public void sceneSwitched(String memberID, Boolean viewGrades) {
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
				
				//Queries all the members
				ResultSet getType = stmt.executeQuery("SELECT memberType FROM Login WHERE memberID = " + Integer.parseInt(memberID));
			    
				//Stores the type of user
			    if (getType.next() == false)
			    	System.out.println("No Classes Found.");
			    else {
				    type = getType.getString(1);
			    }
			    
			    getType.close();
			    
			    //Initialize rs
			    ResultSet rs = null;
			    
			    //Create the vBox
			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);
			    vBox.setPrefWidth(565);
			    
			    //If we are not looking at the view grades screen
			    if (!viewGrades) {
			    	//Get the type of user, if student then show enrolled courses
			    	//						if instructor then show the active courses for that instructor
			    	//						if administrator then show all the courses
				    if (type.equals("student")) {
				    	titleLabel.setText("Enrolled Courses");
				    	
				    	//Query and get all the course information for that student
				    	rs = stmt.executeQuery("SELECT CourseList.courseName, CourseList.courseCode, CourseList.courseSection, subject, firstName, lastName, time FROM CourseList "
								+ "INNER JOIN Course ON Course.courseName = CourseList.courseName AND Course.courseCode = CourseList.courseCode " 
				    			+ "INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection "
								+ "INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID "
				    			+ "WHERE CourseList.memberID = " + Integer.parseInt(memberID) 
				    			+ " ORDER BY Course.courseName, Course.courseCode, courseSection");
				    	
				    	//Create a label for each course and store in the vBox
				    	if (rs.next() == false) {
						    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
					    } else {
						    do {
						    	Label lb = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + " : " + rs.getString(4) + "\n\tLecture Time: " + rs.getString(7) + "\n\tInstructor: " + rs.getString(5) + " " + rs.getString(6)));
						    	vBox.getChildren().add(lb);
						    } while (rs.next());
					    }
				    }
				    else if (type.equals("instructor")) {
				    	titleLabel.setText("Your Active Courses");
				    	
				    	//Query and get all the courses being taught by the instructor logged in
				    	rs = stmt.executeQuery("SELECT Section.courseName, Section.courseCode, Section.courseSection, subject, Section.time, capacity, maxCapacity FROM Section "
								+ "INNER JOIN Course ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode "
				    			+ "WHERE Section.memberID = " + Integer.parseInt(memberID)
				    			+ " ORDER BY Section.courseName, Section.courseCode, Section.courseSection");
				    	
				    	//Create a label for each course and store in the vBox
				    	if (rs.next() == false) {
						    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
					    } else {
						    do {
						    	Label lb = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + " : " + rs.getString(4) + "\n\tLecture Time: " + rs.getString(5) + "\n\tCapacity: " + rs.getString(6) + "/" + rs.getString(7)));
						    	vBox.getChildren().add(lb);
						    } while (rs.next());
					    }
				    }
				    else if (type.equals("administrator")) {
				    	titleLabel.setText("All Courses");
				    	
				    	//Query and get all the courses in the university
				    	rs = stmt.executeQuery("SELECT Section.courseName, Section.courseCode, Section.courseSection, subject, Section.time, firstName, lastName FROM Section "
				    			+ "INNER JOIN Course ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode "
				    			+ "INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID "
				    			+ "ORDER BY Section.courseName, Section.courseCode, Section.courseSection");
				    	
				    	//Create a label for each course and store in vBox
				    	if (rs.next() == false) {
						    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
					    } else {
						    do {
						    	//Bold the name
						    	Label lbName = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3)));
						    	lbName.setPrefWidth(380);
						    	lbName.setWrapText(true);
						    	lbName.setStyle("-fx-font-weight: bold");
						    	vBox.getChildren().add(lbName);
						    	
						    	//Italize the title
						    	Label lbTitle = new Label(String.format(rs.getString(4)));
						    	lbTitle.setPrefWidth(380);
						    	lbTitle.setWrapText(true);
						    	lbTitle.setFont(Font.font(null, FontPosture.ITALIC, 12));
						    	vBox.getChildren().add(lbTitle);
						    	
						    	//Create a regular label for the rest of the information
						    	Label lb = new Label(String.format("\tLecture Time: " + rs.getString(5) + "\n\tInstructor: " + rs.getString(6) + " " + rs.getString(7) + "\n"));
						    	vBox.getChildren().add(lb);
						    	
						    	//Add a separator
						    	Separator sp = new Separator();
						    	sp.setOrientation(Orientation.HORIZONTAL);
						    	vBox.getChildren().add(sp);
						    } while (rs.next());
					    }
				    }
			    }
			    else {
			    	titleLabel.setText("Your Grades");
			    	
			    	//Query and get all the grades for that student
			    	rs = stmt.executeQuery("SELECT * FROM CourseGrades WHERE memberID = " + Integer.parseInt(memberID) + " ORDER BY courseName, courseCode, courseSection");
			    	
			    	//Iterate through and store each grade in a label in the vBox
			    	if (rs.next() == false) {
					    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
				    } else {
					    do {
					    	Label lb = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + "\n\tGrade: " + rs.getString(5)));
					    	vBox.getChildren().add(lb);
					    } while (rs.next());
				    }
			    }
			    
			    //Add the vBox from whichever to the scroll pane
			    listScrollPane.setContent(vBox);
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
     * When the back button is pressed, bring back to the appropriate screen
     * 
     * @param event
     */
    @FXML
    void backButtonPressed(ActionEvent event) {
		if (type.equals("student"))
	    	main.setScreen(sceneStudentWelcomeScreen);
	    else if (type.equals("instructor"))
	    	main.setScreen(sceneInstructorWelcomeScreen);
	    else if (type.equals("administrator"))
	    	main.setScreen(sceneAdminWelcomeScreen);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}

