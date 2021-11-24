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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ModifyGradesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="leftScrollPane"
    private ScrollPane leftScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="middleScrollPane"
    private ScrollPane middleScrollPane; // Value injected by FXMLLoader

    @FXML // fx:id="newGradeTextField"
    private TextField newGradeTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader
    
    @FXML // fx:id="currentGradeLabel"
    private Label currentGradeLabel; // Value injected by FXMLLoader
    
    // VBox to be used to display information in the scroll pane
   	private VBox vBoxClasses = new VBox();
   	private VBox vBoxStudents = new VBox();
   	
   	private String memberID;
   	private String courseName;
   	private String courseCode;
   	private String courseSection;
   	
   	private Label lastClassLabel;
   	private Label lastStudLabel;
    
    private Main main;
    private Scene sceneInstructorWelcomeScreen;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setBackPressedScene(Scene sceneInstructorWelcomeScreen) {
    	this.sceneInstructorWelcomeScreen = sceneInstructorWelcomeScreen;
    }
    
    public void showList(String memberID) {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			try {
				Statement stmt = conn.createStatement();
			    
			    vBoxClasses.getChildren().clear();
			    vBoxClasses.setPadding(new Insets(8, 8, 8, 8));
			    vBoxClasses.setSpacing(8.0);
			    
			    ResultSet rs = stmt.executeQuery("SELECT * FROM Section WHERE memberID = " + memberID + " ORDER BY courseName, courseCode, courseSection");
		    	
		    	if (rs.next() == false) {
		    		vBoxClasses.getChildren().add(new Label(String.format("No Classes Found.")));
			    } else {
				    do {
				    	Label classLabel = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + " : " + rs.getString(5)));
				    	classLabel.setOnMouseClicked(selectEvent -> handleCourseClickEvent(classLabel));
				    	lastClassLabel = classLabel;
				    	vBoxClasses.getChildren().add(classLabel);
				    } while (rs.next());
			    }

		    	leftScrollPane.setContent(vBoxClasses);
			} catch (SQLException e) {
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void handleCourseClickEvent(Label classLabel) {
    	messageLabel.setVisible(false);
    	classLabel.setStyle("-fx-font-weight: bold");
    	if (lastClassLabel != classLabel) {
    		lastClassLabel.setStyle("<font-weight> regular");
    		lastClassLabel = classLabel;
    	}
    	
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			try {
				Statement stmt = conn.createStatement();
			    
				vBoxStudents.getChildren().clear();
		    	vBoxStudents.setPadding(new Insets(8, 8, 8, 8));
		    	vBoxStudents.setSpacing(8.0);
				
				String[] splitArray = classLabel.getText().split("-");
				String courseName = splitArray[0];
				String courseCode = splitArray[1];
				String[] newSplitArray = splitArray[2].split(" : ");
				String courseSection = newSplitArray[0];
				
			    ResultSet rs = stmt.executeQuery("SELECT * FROM CourseList INNER JOIN UniversityMember ON UniversityMember.memberID = CourseList.memberID WHERE courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' and courseSection = '" + courseSection + "'");
			    		
		    	
		    	if (rs.next() == false) {
		    		vBoxStudents.getChildren().add(new Label(String.format("No Students Found.")));
			    } else {
				    do {
				    	Label studLabel = new Label(String.format(rs.getString(1) + " - " + rs.getString(8) + ", " + rs.getString(7)));
				    	studLabel.setOnMouseClicked(selectEvent -> handleStudentClickEvent(studLabel, classLabel));
				    	lastStudLabel = studLabel;
				    	vBoxStudents.getChildren().add(studLabel);
				    } while (rs.next());
			    }

		    	middleScrollPane.setContent(vBoxStudents);
			} catch (SQLException e) {
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void handleStudentClickEvent(Label studLabel, Label classLabel) {
		messageLabel.setVisible(false);
		studLabel.setStyle("-fx-font-weight: bold");
		if (lastStudLabel != studLabel) {
			lastStudLabel.setStyle("<font-weight> regular");
			lastStudLabel = studLabel;	
		}
    	
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			try {
				Statement stmt = conn.createStatement();
				
				String[] memIdSplit = studLabel.getText().split(" - ");
				memberID = memIdSplit[0];
				
				String[] splitArray = classLabel.getText().split("-");
				courseName = splitArray[0];
				courseCode = splitArray[1];
				String[] newSplitArray = splitArray[2].split(" : ");
				courseSection = newSplitArray[0];
				
			    ResultSet rs = stmt.executeQuery("SELECT grade FROM CourseGrades INNER JOIN UniversityMember ON UniversityMember.memberID = CourseGrades.memberID WHERE courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' and courseSection = '" + courseSection + "' AND UniversityMember.memberID = '" + memberID + "'");
			    
		    	if (rs.next() == false) {
		    		currentGradeLabel.setText("Current Grade: 0%");
			    } else {
				    currentGradeLabel.setText("Current Grade: " + rs.getString(1) + "%");
			    }
			} catch (SQLException e) {
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
    void changeButtonPressed(ActionEvent event) {
		if (Integer.parseInt(newGradeTextField.getText()) > -1 && Integer.parseInt(newGradeTextField.getText()) < 101) {
			try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    	
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
				
				try {
					Statement stmt = conn.createStatement();
					
				    stmt.executeUpdate("UPDATE CourseGrades SET grade = " + Integer.parseInt(newGradeTextField.getText()) + " WHERE courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' and courseSection = '" + courseSection + "' AND memberID = '" + memberID + "'");
				    
				    messageLabel.setText("Successfully Changed!");
					messageLabel.setVisible(true);
					
					currentGradeLabel.setText("Current Grade: " + newGradeTextField.getText() + "%");
					newGradeTextField.setText("");
				} catch (SQLException e) {
				    e.printStackTrace();
				}
	
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
    
    void resetFields() {
    	messageLabel.setVisible(false);
    	newGradeTextField.setText("");
    	currentGradeLabel.setText("Current Grade: ");
    }
}

