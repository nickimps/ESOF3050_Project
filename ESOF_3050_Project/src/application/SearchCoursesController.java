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

    @FXML // fx:id="listScrollPane"
    private ScrollPane listScrollPane; // Value injected by FXMLLoader
    
    // VBox to be used to display information in the scroll pane
 	private VBox vBox = new VBox();

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
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode ORDER BY Course.courseName");
			    else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Course.courseName = '"+ courseNameTextField.getText().toUpperCase() + "' ORDER BY Course.courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Course.courseCode = '"+ courseCodeTextField.getText() + "' ORDER BY Course.courseName");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Section.courseSection = '" + sectionTextField.getText() + "' ORDER BY Course.courseName");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Course.courseCode = '" + courseCodeTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' ORDER BY Course.courseCode");
				else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' ORDER BY Course.courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' ORDER BY Course.courseCode");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode WHERE Section.courseSection = '" + sectionTextField.getText() + "' AND Course.courseName = '" + courseNameTextField.getText().toUpperCase() + "' AND Course.courseCode = '" + courseCodeTextField.getText() + "' ORDER BY Course.courseName");

			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);
			    
			    if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
			    } else {
				    do {
				    	Label lb = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(7) + "\n" + rs.getString(3) + "\n" + rs.getString(9) + "\n" + rs.getString(4)));
				    	lb.setPrefWidth(380);
				    	lb.setWrapText(true);
				    	vBox.getChildren().add(lb);
				    	
				    	Separator sp = new Separator();
				    	sp.setOrientation(Orientation.HORIZONTAL);
				    	vBox.getChildren().add(sp);
				    } while (rs.next());
			    }
			    
			    listScrollPane.setContent(vBox);
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
    	vBox.getChildren().clear();
    	courseCodeTextField.setText("");
    	courseNameTextField.setText("");
    	sectionTextField.setText("");
    }
}

