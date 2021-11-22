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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddCourseController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="subjectTextField"
    private TextField subjectTextField; // Value injected by FXMLLoader

    @FXML // fx:id="courseCodeTextField"
    private TextField courseCodeTextField; // Value injected by FXMLLoader

    @FXML // fx:id="titleTextField"
    private TextField titleTextField; // Value injected by FXMLLoader

    @FXML // fx:id="sectionTextField"
    private TextField sectionTextField; // Value injected by FXMLLoader

    @FXML // fx:id="InstructorChoiceBox"
    private ChoiceBox<String> InstructorChoiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="descriptionTextField"
    private TextField descriptionTextField; // Value injected by FXMLLoader

    @FXML // fx:id="lectureTimeTextField"
    private TextField lectureTimeTextField; // Value injected by FXMLLoader
    
    @FXML // fx:id="messageLabel"
    private Label messageLabel; // Value injected by FXMLLoader
    
    private int instructor = -1;
    private List<String> instructors = new ArrayList<>();
    private List<String> instructorsMemIDs = new ArrayList<>();
    
    private Main main;
    private Scene sceneAdminWelcomeScreen;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    }
    
    @FXML
    void addButtonPressed(ActionEvent event) {
    	
    	instructor = InstructorChoiceBox.getSelectionModel().getSelectedIndex();
    	if (subjectTextField.getText() != "" && courseCodeTextField.getText() != "" && titleTextField.getText() != "" && sectionTextField.getText() != "" && descriptionTextField.getText() != "" && lectureTimeTextField.getText() != "" && instructor > -1 ) {
	    	try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
				
				try {
					Statement stmt = conn.createStatement();
					stmt.executeUpdate("INSERT INTO Course VALUES ('"+ subjectTextField.getText() + "', '" + courseCodeTextField.getText() + "', '" + titleTextField.getText() + "', '" + descriptionTextField.getText() + "')");
					stmt.executeUpdate("INSERT INTO Section VALUES ('"+ subjectTextField.getText() + "', '" + courseCodeTextField.getText() + "', '" + sectionTextField.getText() + "', " + instructorsMemIDs.get(instructor) + ", '" + lectureTimeTextField.getText() + "')");
					
				    messageLabel.setText("Successfully Added!");
				    messageLabel.setVisible(true);
				} catch (SQLException e){
				    e.printStackTrace();
				}
	
				conn.close();
			} catch (SQLException e) {
				messageLabel.setText("Error!");
			    messageLabel.setVisible(true);
			}
	    	
	    	resetFields();
    	}
    	else {
    		messageLabel.setText("Error!");
		    messageLabel.setVisible(true);
    	}
    	
    	
    	resetFields();
    }
    
    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneAdminWelcomeScreen);
    	resetFields();
    }
    
    @FXML
    void loadInstructors(MouseEvent event) {
    	messageLabel.setVisible(false);
    	instructors.clear();
    	
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			try {
				Statement stmt = conn.createStatement();		    
				
			    ResultSet rs = stmt.executeQuery("SELECT * FROM UniversityMember WHERE memberType = 'instructor'");
		    	
		    	if (rs.next() == false) {
				    instructors.add("No Instructors");
			    } else {
				    do {
				    	instructors.add(rs.getString(1) + "," + rs.getString(3) + "," + rs.getString(4));
				    } while (rs.next());
			    }
		    	
		    	ObservableList<String> choices = FXCollections.observableArrayList();
		    	for(int i = 0; i < instructors.size(); i++) {
		    		String[] split = instructors.get(i).split(",");
		    		instructorsMemIDs.add(split[0]);
		    		choices.add(String.format("%s %s", split[1], split[2]));
		    	}
		    	
		    	InstructorChoiceBox.setItems(choices);
			} catch (SQLException e) {
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void resetMessage(MouseEvent event) {
    	messageLabel.setVisible(false);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    void resetFields() {
    	subjectTextField.setText("");
    	courseCodeTextField.setText("");
    	titleTextField.setText("");
    	sectionTextField.setText("");
    	InstructorChoiceBox.setValue(null);
    	descriptionTextField.setText("");
    	lectureTimeTextField.setText("");    	
    }
}
