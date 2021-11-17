package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class SearchCoursesController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="subjectTextField"
    private TextField courseCodeTextField; // Value injected by FXMLLoader

    @FXML // fx:id="courseNumberTextField"
    private TextField courseNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="sectionTextField"
    private TextField sectionTextField; // Value injected by FXMLLoader

    @FXML // fx:id="listScrollPane"
    private ScrollPane listScrollPane; // Value injected by FXMLLoader

    private Main main;
    private Scene sceneStudentWelcomeScreen;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setBackPressedScene(Scene sceneStudentWelcomeScreen) {
    	this.sceneStudentWelcomeScreen = sceneStudentWelcomeScreen;
    }

    @FXML
    void backButtonPressed(ActionEvent event) {
    	main.setScreen(sceneStudentWelcomeScreen);
    	resetFields();
    }

    @FXML
    void searchButtonPressed(ActionEvent event) {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");

			try {
				Statement stmt = conn.createStatement();
			    ResultSet rs = null;
			    //Execute query and get number of columns
			    if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course ORDER BY courseName");
			    else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course WHERE courseName = '"+ courseNameTextField.getText() + "' ORDER BY courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course WHERE courseCode = '"+ courseCodeTextField.getText() + "' ORDER BY courseName");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Section.courseSection = '" + courseCodeTextField.getText() + "' ORDER BY Course.courseName");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course WHERE courseCode = '" + courseCodeTextField.getText() + "' AND courseName = '" + courseNameTextField.getText() + "' ORDER BY courseCode");
				else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Section.courseSection = '" + courseCodeTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText() + "' ORDER BY Course.courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Section.courseSection = '" + courseCodeTextField.getText() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' ORDER BY Course.courseCode");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Section.courseSection = '" + courseCodeTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' ORDER BY Course.courseName");
				
				
			    ResultSetMetaData rsmd = rs.getMetaData();
			    int columnsNumber = rsmd.getColumnCount();
			    
			    //print column names
			    for(int i = 1; i <= columnsNumber; i++)
		            System.out.print(rsmd.getColumnName(i) + "\t");
			    System.out.println();
			    
			    if (rs.next() == false) 
		    		System.out.println("no results");
			    else {
				    do {
				    	for(int i = 1; i <= columnsNumber; i++)
				            System.out.print(rs.getString(i) + "\t");
				        System.out.println();
				    } while (rs.next());
			    }
			} catch (SQLException e){
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	resetFields();
    }
    
    void resetFields() {
    	courseCodeTextField.setText("");
    	courseNameTextField.setText("");
    	sectionTextField.setText("");
    }
}

