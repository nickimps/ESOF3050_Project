package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegisterForCoursesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="middleScrollPane"
    private ScrollPane middleScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="leftScrollPane"
    private ScrollPane leftScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="courseCodeTextField"
    private TextField courseCodeTextField; // Value injected by FXMLLoader

    @FXML // fx:id="courseNameTextField"
    private TextField courseNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="sectionTextField"
    private TextField sectionTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader
    
    // VBox to be used to display information in the scroll pane
  	private VBox vBox = new VBox();
  	private VBox vBoxEnroll = new VBox();
  	
	private List<CheckBox> cbs = new ArrayList<>();
	private List<String> enrollList = new ArrayList<>();

    private Main main;
    private Scene sceneStudentWelcomeScreen;
    
    private String memberID;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setBackPressedScene(Scene sceneStudentWelcomeScreen) {
    	this.sceneStudentWelcomeScreen = sceneStudentWelcomeScreen;
    }
    
    public void setMemberID(String memberID) {
    	this.memberID = memberID;
    }

    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneStudentWelcomeScreen);
    	resetFields();
    }

    @FXML
    void enrollButtonPressed(ActionEvent event) {
    	if (enrollList.size() > 0) {
	    	try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    	
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
	
				try {
					String courseName, courseCode, courseSection;
									
					for (int i = 0; i < enrollList.size(); i++) {
						String[] splitArray = enrollList.get(i).split("-");
						courseName = splitArray[0];
						courseCode = splitArray[1];
						courseSection = splitArray[2];
						
						Statement stmt = conn.createStatement();
						stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
					    stmt.executeUpdate("INSERT INTO CourseList VALUES ("+ Integer.parseInt(memberID) + ", '" + courseName + "', '" + courseCode + "', '" + courseSection + "')");
					    stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
					}
					
				    resetFields();
				    
				    messageLabel.setText("Successfully Enrolled!");
				    messageLabel.setVisible(true);
				    
				    enrollList.clear();
				} catch (SQLException e){
					messageLabel.setText("Error!");
				    messageLabel.setVisible(true);
				    e.printStackTrace();
				}
	
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	else {
    		messageLabel.setText("Error: No Courses to enroll.");
		    messageLabel.setVisible(true);
    	}
    	
    }

    @FXML
    void searchButtonPressed(ActionEvent event) {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		try {
//			for(int i = 0; i < enrollList.size(); i++)
//				System.out.println(enrollList.get(i));
//			System.out.println("--");
			
			
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");

			try {
				Statement stmt = conn.createStatement();
			    ResultSet rs = null;

			    //Execute query and get number of columns
			    if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
			    else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseName = '"+ courseNameTextField.getText().toUpperCase() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseCode = '"+ courseCodeTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseCode = '" + courseCodeTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
			    
			    vBox.getChildren().clear();
			    cbs.clear();
			    
			    
			    //12 13
			    
			    if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
			    } else {
				    do {
				    	CheckBox cb = new CheckBox(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(7) + "\n" + rs.getString(3) + "\nLecture Time: " + rs.getString(9) + "\nInstructor: " + rs.getString(12) + " " + rs.getString(13)));
				    	
				    	if (enrollList.contains(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(7)))) {
				    		cb.setSelected(true);
				    	}
				    	
				    	cbs.add(cb);
				    } while (rs.next());
			    }

			    for (CheckBox cb : cbs) {
			    	cb.setOnAction(selectEvent -> handleEvent(cb));
			    	
			    	vBox.getChildren().add(cb);
			    	
			    	Separator sp = new Separator();
			    	sp.setOrientation(Orientation.HORIZONTAL);
			    	vBox.getChildren().add(sp);
			    }
			    
			    leftScrollPane.setContent(vBoxEnroll);
			    middleScrollPane.setContent(vBox);
			} catch (SQLException e){
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void handleEvent(CheckBox cb) {
    	messageLabel.setVisible(false);
    	
    	if (cb.isSelected()) {
			String[] split = cb.getText().split("\n");
			if (!enrollList.contains(split[0])) {
				enrollList.add(String.format(split[0]));
				vBoxEnroll.getChildren().add(new Label(String.format(split[0])));
			}
    	}
    	else {
    		for(int i = 0; i < cbs.size(); i++) {
    			if (cbs.get(i).getText() == cb.getText()) {
    				vBoxEnroll.getChildren().remove(i);
    				enrollList.remove(i);
    			}
    		}
    	}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    	messageLabel.setVisible(false);
    	
    	vBox.getChildren().clear();
	    vBox.setPadding(new Insets(8, 8, 8, 8));
	    vBox.setSpacing(8.0);
	    
	    vBoxEnroll.getChildren().clear();
	    vBoxEnroll.setPadding(new Insets(8, 8, 8, 8));
	    vBoxEnroll.setSpacing(8.0);
    }
    
    void resetFields() {
    	vBox.getChildren().clear();
    	vBoxEnroll.getChildren().clear();
    	courseCodeTextField.setText("");
    	courseNameTextField.setText("");
    	sectionTextField.setText("");
    }
}

