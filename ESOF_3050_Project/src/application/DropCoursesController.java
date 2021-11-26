/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * DropCoursesController.java
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

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class DropCoursesController {

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="listScrollPane"
    private ScrollPane listScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader
    
    //VBox to be used to display information in the scroll pane
   	private VBox vBox = new VBox();
   	
   	//Global check boxes list
   	private List<CheckBox> cbs = new ArrayList<>();
    
   	//Global scenes
    private Main main;
    private Scene sceneStudentWelcomeScreen;
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
     * Sets the welcome screen scene
     * 
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
     * Shows the list of courses that can be dropped
     */
    public void showList() {
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
			    
				//Create vBox
			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);		    
			    
			    //Query and get the courses that the student is enrolled in
			    ResultSet rs = stmt.executeQuery("SELECT CourseList.courseName, CourseList.courseCode, CourseList.courseSection, subject, time, firstName, lastName FROM CourseList "
						+ "INNER JOIN Course ON Course.courseName = CourseList.courseName AND Course.courseCode = CourseList.courseCode " 
			    		+ "INNER JOIN Section ON Section.courseName = CourseList.courseName AND Section.courseCode = Course.courseCode AND Section.courseSection = CourseList.courseSection "
			    		+ "INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID "
		    			+ "WHERE CourseList.memberID = " + Integer.parseInt(memberID) 
		    			+ " ORDER BY Course.courseName, Course.courseCode, courseSection");
		    	
			    //Iterate through and create a check box for each course
			    if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
			    } else {
				    do {
				    	CheckBox cb = new CheckBox(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + " : " + rs.getString(4) + "\n\tLecture Time: " + rs.getString(5) + "\n\tInstructor: " + rs.getString(6) + " " + rs.getString(7) + "\n"));
				    	cbs.add(cb);
				    } while (rs.next());
			    }
			    
			    //Add listeners for each check box
			    for (int i = 0; i < cbs.size(); i++) {
			    	vBox.getChildren().add(cbs.get(i));
			    	cbs.get(i).selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    		messageLabel.setVisible(false);
			    	});
			    }

			    //add the vBox
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
     * Bring the user back to the welcome screen when back is pressed
     * 
     * @param event
     */
    @FXML
    void backButtonPressed(ActionEvent event) {
    	messageLabel.setVisible(false);
    	main.setScreen(sceneStudentWelcomeScreen);
    	vBox.getChildren().clear();
    	cbs.clear();
    }

    /**
     * When drop is pressed, need to drop the courses and update the screen
     * 
     * @param event
     */
    @FXML
    void dropButtonPressed(ActionEvent event) {
    	//Make sure there are some selected
    	if (cbs.size() > 0) {
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
					
					//Get the course name, code and section
					String courseName = "";
					String courseCode = "";
					String courseSection = "";
					for (int i = 0; i < cbs.size(); i++) {
						//if selected, then we add to list of stuff to drop
						if (cbs.get(i).isSelected()) {
							String[] splitArray = cbs.get(i).getText().split("-");
							courseName = splitArray[0];
							courseCode = splitArray[1];
							String[] newSplitArray = splitArray[2].split(" : ");
							courseSection = newSplitArray[0];
							
							//Change full-time to part-time status if applicable
							ResultSet rs = stmt.executeQuery("SELECT * FROM CourseList WHERE memberID = " + Integer.parseInt(memberID));
							int numOfRows = 0;
							
							while(rs.next())
								numOfRows++;
							
							//Change to part-time status if needed
							if (numOfRows < 3)
								stmt.executeUpdate("UPDATE UniversityMember SET statusType = 'Part-Time' WHERE memberID = " + Integer.parseInt(memberID));
							
							//Delete from the tables and decrement the capacity
							stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
							stmt.executeUpdate("DELETE FROM CourseList WHERE memberID = " + Integer.parseInt(memberID) + " AND courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' AND courseSection = '" + courseSection + "'");
							stmt.executeUpdate("DELETE FROM CourseGrades WHERE memberID = " + Integer.parseInt(memberID) + " AND courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' AND courseSection = '" + courseSection + "'");
							stmt.executeUpdate("UPDATE Section SET capacity = capacity + 1 WHERE courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' AND courseSection = '" + courseSection + "'");
							stmt.execute("SET FOREIGN_KEY_CHECKS = 1");

						}
					}
					
					//Clear the list
					cbs.clear();
				    
					//Let user know
					messageLabel.setText("Successfully Removed!");
				    messageLabel.setVisible(true);
					
				    //show updated list
					showList();
				    
				} catch (SQLException e) {
					messageLabel.setText("Error!");
				    messageLabel.setVisible(true);
				    e.printStackTrace();
				}
	
				//Close the connection
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	else {
    		messageLabel.setText("Error: must choose at least one course");
		    messageLabel.setVisible(true);
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	
    }
}

