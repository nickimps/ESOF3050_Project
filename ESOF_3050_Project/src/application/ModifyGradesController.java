/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * ModifyGradesController.java
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ModifyGradesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="leftScrollPane"
    private ScrollPane leftScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="middleScrollPane"
    private ScrollPane middleScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="newGradeLabel"
    private Label newGradeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="newGradeTextField"
    private TextField newGradeTextField; // Value injected by FXMLLoader

    @FXML // fx:id="separator"
    private Separator separator; // Value injected by FXMLLoader

    @FXML // fx:id="changeButton"
    private Button changeButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="currentGradeLabel"
    private Label currentGradeLabel; // Value injected by FXMLLoader
    
    // VBox to be used to display information in the scroll pane
   	private VBox vBoxClasses = new VBox();
   	private VBox vBoxStudents = new VBox();
   	
   	//Global variables
   	private String memberID;
   	private String courseName;
   	private String courseCode;
   	private String courseSection;
   	
   	//Global Labels
   	private Label lastClassLabel;
   	private Label lastStudLabel;
    
   	//Global Scenes
    private Main main;
    private Scene sceneInstructorWelcomeScreen;
    
    /**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    /**
     * Set the scene for the back button
     * 
     * @param sceneInstructorWelcomeScreen
     */
    public void setBackPressedScene(Scene sceneInstructorWelcomeScreen) {
    	this.sceneInstructorWelcomeScreen = sceneInstructorWelcomeScreen;
    }
    
    /**
     * Show the list of courses that the instructor teaches
     * 
     * @param memberID
     */
    public void showList(String memberID) {
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
			    
				//Create the vBox
			    vBoxClasses.getChildren().clear();
			    vBoxClasses.setPadding(new Insets(8, 8, 8, 8));
			    vBoxClasses.setSpacing(8.0);
			    
			    //Query through the courses that they teach
			    ResultSet rs = stmt.executeQuery("SELECT * FROM Section WHERE memberID = " + memberID + " ORDER BY courseName, courseCode, courseSection");
		    	
			    //Iterate through each and create a label
		    	if (rs.next() == false) {
		    		vBoxClasses.getChildren().add(new Label(String.format("No Classes Found.")));
			    } else {
				    do {
				    	//Create a label
				    	Label classLabel = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + " : " + rs.getString(5)));
				    	
				    	//Create an mouse click event
				    	classLabel.setOnMouseClicked(selectEvent -> handleCourseClickEvent(classLabel));
				    	
				    	//Store the class label to be used in the event
				    	lastClassLabel = classLabel;
				    	
				    	//Add to the vBox
				    	vBoxClasses.getChildren().add(classLabel);
				    } while (rs.next());
			    }

		    	//Add to the scroll pane
		    	leftScrollPane.setContent(vBoxClasses);
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
     * When course clicked, bold and show students in the class
     * 
     * @param classLabel
     */
    private void handleCourseClickEvent(Label classLabel) {
    	//Hide info message
    	messageLabel.setVisible(false);
    	newGradeLabel.setVisible(false);
    	currentGradeLabel.setVisible(false);
    	newGradeTextField.setVisible(false);
    	separator.setVisible(false);
    	changeButton.setVisible(false);

    	//Bold the label
    	classLabel.setStyle("-fx-font-weight: bold");
    	
    	//Un Bold the old label too
    	if (lastClassLabel != classLabel) {
    		lastClassLabel.setStyle("<font-weight> regular");
    		lastClassLabel = classLabel;
    	}
    	
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
			    
				//Create the vBox
				vBoxStudents.getChildren().clear();
		    	vBoxStudents.setPadding(new Insets(8, 8, 8, 8));
		    	vBoxStudents.setSpacing(8.0);
				
		    	//Get course name, code and section
				String[] splitArray = classLabel.getText().split("-");
				String courseName = splitArray[0];
				String courseCode = splitArray[1];
				String[] newSplitArray = splitArray[2].split(" : ");
				String courseSection = newSplitArray[0];
				
				//Query through and get results
			    ResultSet rs = stmt.executeQuery("SELECT * FROM CourseList INNER JOIN UniversityMember ON UniversityMember.memberID = CourseList.memberID WHERE courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' and courseSection = '" + courseSection + "'");
			    		
		    	//Iterate through each row and create a label and event
		    	if (rs.next() == false) {
		    		vBoxStudents.getChildren().add(new Label(String.format("No Students Found.")));
			    } else {
				    do {
				    	//Create a label
				    	Label studLabel = new Label(String.format(rs.getString(1) + " - " + rs.getString(8) + ", " + rs.getString(7)));
				    	
				    	//Create an mouse click event
				    	studLabel.setOnMouseClicked(selectEvent -> handleStudentClickEvent(studLabel, classLabel));
				    	
				    	//Store the last student label
				    	lastStudLabel = studLabel;
				    	
				    	//Add to the vBox
				    	vBoxStudents.getChildren().add(studLabel);
				    } while (rs.next());
			    }

		    	//Add to the scroll pane
		    	middleScrollPane.setContent(vBoxStudents);
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
     * When student is picked, show the grade in the side screen
     * 
     * @param studLabel
     * @param classLabel
     */
	private void handleStudentClickEvent(Label studLabel, Label classLabel) {
		//Hide the info label
		messageLabel.setVisible(false);
		newGradeLabel.setVisible(true);
    	currentGradeLabel.setVisible(true);
    	newGradeTextField.setVisible(true);
    	separator.setVisible(true);
    	changeButton.setVisible(true);
		
		//Bold the label
		studLabel.setStyle("-fx-font-weight: bold");
		
		//unbold if necessary
		if (lastStudLabel != studLabel) {
			lastStudLabel.setStyle("<font-weight> regular");
			lastStudLabel = studLabel;	
		}
    	
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
				
				//Get the memberID of the student selected
				String[] memIdSplit = studLabel.getText().split(" - ");
				memberID = memIdSplit[0];
				
				//Get the course name, code and section too
				String[] splitArray = classLabel.getText().split("-");
				courseName = splitArray[0];
				courseCode = splitArray[1];
				String[] newSplitArray = splitArray[2].split(" : ");
				courseSection = newSplitArray[0];
				
				//Query and get results
			    ResultSet rs = stmt.executeQuery("SELECT grade FROM CourseGrades INNER JOIN UniversityMember ON UniversityMember.memberID = CourseGrades.memberID WHERE courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' and courseSection = '" + courseSection + "' AND UniversityMember.memberID = '" + memberID + "'");
			    
			    //Get the current grade and update the label
		    	if (rs.next() == false) {
		    		currentGradeLabel.setText("Current Grade: 0%");
			    } else {
				    currentGradeLabel.setText("Current Grade: " + rs.getString(1) + "%");
			    }
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
	 * Update the grade in the database and the label
	 * 
	 * @param event
	 */
	@FXML
    void changeButtonPressed(ActionEvent event) {
		//Check if valid grade entry
		if (Integer.parseInt(newGradeTextField.getText()) > -1 && Integer.parseInt(newGradeTextField.getText()) < 101) {
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
					
					//Update the grade in the database
				    stmt.executeUpdate("UPDATE CourseGrades SET grade = " + Integer.parseInt(newGradeTextField.getText()) + " WHERE courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' and courseSection = '" + courseSection + "' AND memberID = '" + memberID + "'");
				    
				    //Show the success
				    messageLabel.setText("Successfully Changed!");
					messageLabel.setVisible(true);
					
					//Update the label with the newly changed grade
					currentGradeLabel.setText("Current Grade: " + newGradeTextField.getText() + "%");
					newGradeTextField.setText("");
				} catch (SQLException e) {
				    e.printStackTrace();
				}
	
				//Close the connection
				conn.close();
			} catch (SQLException e) {
				messageLabel.setText("Error!");
				messageLabel.setVisible(true);
				e.printStackTrace();
			}
		}
		else {
			messageLabel.setText("Error: Enter a grade from 0 - 100");
			messageLabel.setVisible(true);
		}
    	
    }
    
	/**
	 * Bring the user back to the welcome screen
	 * 
	 * @param event
	 */
    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneInstructorWelcomeScreen);
    	vBoxStudents.getChildren().clear();
    	resetFields();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    /**
     * Reset the fields
     */
    void resetFields() {
    	messageLabel.setVisible(false);
    	newGradeTextField.setText("");
    	currentGradeLabel.setText("Current Grade: ");
    	newGradeLabel.setVisible(false);
    	currentGradeLabel.setVisible(false);
    	newGradeTextField.setVisible(false);
    	separator.setVisible(false);
    	changeButton.setVisible(false);
    }
}

