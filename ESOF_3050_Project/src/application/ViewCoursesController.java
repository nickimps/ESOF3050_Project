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
    
    private Main main;
    private Scene sceneAdminWelcomeScreen;
    private Scene sceneInstructorWelcomeScreen;
    private Scene sceneStudentWelcomeScreen;
    
    private String type = "";
    
    // VBox to be used to display information in the scroll pane
  	private VBox vBox = new VBox();
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen, Scene sceneInstructorWelcomeScreen, Scene sceneStudentWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    	this.sceneInstructorWelcomeScreen = sceneInstructorWelcomeScreen;
    	this.sceneStudentWelcomeScreen = sceneStudentWelcomeScreen;
    }
    
    public void sceneSwitched(String memberID, Boolean viewGrades) {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			try {
				Statement stmt = conn.createStatement();
				
				ResultSet getType = stmt.executeQuery("SELECT memberType FROM Login WHERE memberID = " + Integer.parseInt(memberID));
			    
			    if (getType.next() == false)
			    	System.out.println("No Classes Found.");
			    else {
				    type = getType.getString(1);
			    }
			    
			    getType.close();
			    
			    ResultSet rs = null;
			    
			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);
			    vBox.setPrefWidth(565);
			    
			    if (!viewGrades) {
				    if (type.equals("student")) {
				    	titleLabel.setText("Enrolled Courses");
				    	
				    	rs = stmt.executeQuery("SELECT CourseList.courseName, CourseList.courseCode, CourseList.courseSection, subject, firstName, lastName, time FROM CourseList "
								+ "INNER JOIN Course ON Course.courseName = CourseList.courseName AND Course.courseCode = CourseList.courseCode " 
				    			+ "INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection "
								+ "INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID "
				    			+ "WHERE CourseList.memberID = " + Integer.parseInt(memberID) 
				    			+ " ORDER BY Course.courseName, Course.courseCode, courseSection");
				    	
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
				    	
				    	rs = stmt.executeQuery("SELECT Section.courseName, Section.courseCode, Section.courseSection, subject, Section.time, capacity, maxCapacity FROM Section "
								+ "INNER JOIN Course ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode "
				    			+ "WHERE Section.memberID = " + Integer.parseInt(memberID)
				    			+ " ORDER BY Section.courseName, Section.courseCode, Section.courseSection");
				    	
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
				    	
				    	rs = stmt.executeQuery("SELECT Section.courseName, Section.courseCode, Section.courseSection, subject, Section.time, firstName, lastName FROM Section "
				    			+ "INNER JOIN Course ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode "
				    			+ "INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID "
				    			+ "ORDER BY Section.courseName, Section.courseCode, Section.courseSection");
				    	
				    	if (rs.next() == false) {
						    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
					    } else {
						    do {
						    	Label lbName = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3)));
						    	lbName.setPrefWidth(380);
						    	lbName.setWrapText(true);
						    	lbName.setStyle("-fx-font-weight: bold");
						    	vBox.getChildren().add(lbName);
						    	
						    	Label lbTitle = new Label(String.format(rs.getString(4)));
						    	lbTitle.setPrefWidth(380);
						    	lbTitle.setWrapText(true);
						    	lbTitle.setFont(Font.font(null, FontPosture.ITALIC, 12));
						    	vBox.getChildren().add(lbTitle);
						    	
						    	Label lb = new Label(String.format("\tLecture Time: " + rs.getString(5) + "\n\tInstructor: " + rs.getString(6) + " " + rs.getString(7) + "\n"));
						    	vBox.getChildren().add(lb);
						    	
						    	Separator sp = new Separator();
						    	sp.setOrientation(Orientation.HORIZONTAL);
						    	vBox.getChildren().add(sp);
						    } while (rs.next());
					    }
				    }
			    }
			    else {
			    	titleLabel.setText("Your Grades");
			    	
			    	rs = stmt.executeQuery("SELECT * FROM CourseGrades WHERE memberID = " + Integer.parseInt(memberID) + " ORDER BY courseName, courseCode, courseSection");
			    	
			    	if (rs.next() == false) {
					    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
				    } else {
					    do {
					    	Label lb = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + "\n\tGrade: " + rs.getString(5)));
					    	vBox.getChildren().add(lb);
					    } while (rs.next());
				    }
			    }

			    listScrollPane.setContent(vBox);
			    
			    
			} catch (SQLException e) {
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
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

