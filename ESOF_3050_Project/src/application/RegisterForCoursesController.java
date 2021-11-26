/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * RegisterForCoursesController.java
 */

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
    
    @FXML // fx:id="keywordTextField"
    private TextField keywordTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader
    
    // VBox to be used to display information in the scroll pane
  	private VBox vBox = new VBox();
  	private VBox vBoxEnroll = new VBox();
  	
  	//Global list for the check boxes and enrolled courses
	private List<CheckBox> cbs = new ArrayList<>();
	private List<String> enrollList = new ArrayList<>();

	//Global Scenes
    private Main main;
    private Scene sceneStudentWelcomeScreen;
    
    //Global memberId
    private String memberID;
    
    /**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    /**
     * Sets the scene for the back button
     * @param sceneStudentWelcomeScreen
     */
    public void setBackPressedScene(Scene sceneStudentWelcomeScreen) {
    	this.sceneStudentWelcomeScreen = sceneStudentWelcomeScreen;
    }
    
    /**
     * Sets the memberID
     * 
     * @param memberID
     */
    public void setMemberID(String memberID) {
    	this.memberID = memberID;
    }

    /**
     * Brings the user back to welcome screen when the back button is pressed
     * 
     * @param event
     */
    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneStudentWelcomeScreen);
    	messageLabel.setVisible(false);
    	resetFields();
    }

    /**
     * When enroll button is pressed, check conditions and update database
     * @param event
     */
    @FXML
    void enrollButtonPressed(ActionEvent event) {
    	//Make sure that there are classes to enroll in
    	if (enrollList.size() > 0) {
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
					String courseName, courseCode, courseSection;
					
					//Loop through each item in enroll list and get the course name, code, and section
					for (int i = 0; i < enrollList.size(); i++) {
						String[] splitArray = enrollList.get(i).split("-");
						courseName = splitArray[0];
						courseCode = splitArray[1];
						courseSection = splitArray[2];
						
						//INSERT the name, code, and section into the database and decrement the capacity of the class
						Statement stmt = conn.createStatement();
						stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
					    stmt.executeUpdate("INSERT INTO CourseList VALUES ("+ Integer.parseInt(memberID) + ", '" + courseName + "', '" + courseCode + "', '" + courseSection + "')");
					    stmt.executeUpdate("INSERT INTO CourseGrades VALUES ('"+ courseName + "', '" + courseCode + "', '" + courseSection + "', " + Integer.parseInt(memberID) + ", 0)");
					    stmt.executeUpdate("UPDATE Section SET capacity = capacity - 1 WHERE courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' AND courseSection = '" + courseSection + "'");
					    stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
					    
					    //Change part-time to full-time status if applicable
					    ResultSet rs = stmt.executeQuery("SELECT * FROM CourseList WHERE memberID = " + Integer.parseInt(memberID));
						
						int numOfRows = 0;
						while(rs.next())
							numOfRows++;

						//Change the status if enrolled in more than 2 courses
						if (numOfRows > 2)
							stmt.executeUpdate("UPDATE UniversityMember SET statusType = 'Full-Time' WHERE memberID = " + Integer.parseInt(memberID));
					}
					
					//Reset text fields
				    resetFields();
				    
				    //Let the user know they are successful
				    messageLabel.setText("Successfully Enrolled!");
				    messageLabel.setVisible(true);
				    
				    //Clear the enroll list
				    enrollList.clear();
				} catch (SQLException e){
					messageLabel.setText("Error!");
				    messageLabel.setVisible(true);
				    e.printStackTrace();
				}
	
				//Close connection
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

    /**
     * Display search results to user 
     * 
     * @param event
     */
    @FXML
    void searchButtonPressed(ActionEvent event) {
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
			    ResultSet rs = null;

			    //Get search results based on what fields have text in them
			    if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
			    else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseName = '"+ courseNameTextField.getText().toUpperCase() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseCode = '"+ courseCodeTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseCode = '" + courseCodeTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
			    else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Course.courseName = '"+ courseNameTextField.getText().toUpperCase() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Course.courseCode = '"+ courseCodeTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Section.courseSection = '" + sectionTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Course.courseCode = '" + courseCodeTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty() && !keywordTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE subject LIKE '%" + keywordTextField.getText() + "%' AND Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' AND NOT EXISTS (SELECT courseName, courseCode, courseSection, memberID FROM CourseList WHERE CourseList.courseName = Section.courseName AND CourseList.courseCode = Section.courseCode AND CourseList.courseSection = Section.courseSection AND CourseList.memberID = " + Integer.parseInt(memberID) + ") ORDER BY Course.courseName, Course.courseCode, courseSection");
			    
			    //Clear the current vBox and check boxes list
			    vBox.getChildren().clear();
			    cbs.clear();
			    
			    //Iterate through creating a check box item for each course and disable the course if there is no room left in it
			    if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
			    } else {
				    do {
				    	CheckBox cb = new CheckBox(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(7) + "\n" + rs.getString(3) + "\nLecture Time: " + rs.getString(9) + "\nInstructor: " + rs.getString(14) + " " + rs.getString(15) + "\nCapacity: " + rs.getString(10) + "/" + rs.getString(11)));
				    	
				    	//If no room, set disable
				    	if (Integer.parseInt(rs.getString(10)) == 0)
				    		cb.setDisable(true);
				    	
				    	//If student year level < course year level then don't allow selection of the course
				    	ResultSet studQ = stmt.executeQuery("SELECT memberID, yearLevel FROM UniversityMember WHERE memberID = " + Integer.parseInt(memberID));
				    	studQ.next();
				    	
				    	int studentYear = Integer.parseInt(studQ.getString(1));
				    	int courseYear = Integer.parseInt(String.format("%s", rs.getString(2).charAt(0)));
				    	
				    	if (studentYear < courseYear)
				    		cb.setDisable(true);
				    	
				    	
				    	//If the course is in the enroll list, automatically select it
				    	if (enrollList.contains(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(7))))
				    		cb.setSelected(true);
				    	
				    	//Add the checkboxes to the checkboxes array
				    	cbs.add(cb);
				    } while (rs.next());
			    }

			    //For each checkbox in checkboxes, set an action event and add the checkbox to the vBox to display
			    for (CheckBox cb : cbs) {
			    	cb.setOnAction(selectEvent -> handleEvent(cb));
			    	
			    	//Add cb to Vbox
			    	vBox.getChildren().add(cb);
			    	
			    	//Add separator
			    	Separator sp = new Separator();
			    	sp.setOrientation(Orientation.HORIZONTAL);
			    	vBox.getChildren().add(sp);
			    }
			    
			    //Show the results
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
    
    /**
     * When the check box is clicked, update the enroll list or remove from enroll list
     * 
     * @param cb
     */
    private void handleEvent(CheckBox cb) {
    	messageLabel.setVisible(false);
    	
    	//If selected, add to enroll list otherwise remove from the list
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
    				String[] split = cb.getText().split("\n");
	    			for (int j = 0; j < enrollList.size(); j++) {
	    				if (enrollList.get(j).equals(split[0])) {
	    					vBoxEnroll.getChildren().remove(j);
	        				enrollList.remove(j);
	    				}
	    			}
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
	    vBox.setPrefWidth(260);
	    
	    vBoxEnroll.getChildren().clear();
	    vBoxEnroll.setPadding(new Insets(8, 8, 8, 8));
	    vBoxEnroll.setSpacing(8.0);
    }
    
	/**
	 * Reset the fields
	 */
    void resetFields() {
    	vBox.getChildren().clear();
    	vBoxEnroll.getChildren().clear();
    	courseCodeTextField.setText("");
    	courseNameTextField.setText("");
    	sectionTextField.setText("");
    }
}

