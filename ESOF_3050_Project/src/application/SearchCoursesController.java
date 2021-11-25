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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

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
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID ORDER BY Course.courseName");
			    else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseName LIKE '%"+ courseNameTextField.getText().toUpperCase() + "%' ORDER BY Course.courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' ORDER BY Course.courseName");
				else if (courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection LIKE '%" + sectionTextField.getText() + "%' ORDER BY Course.courseName");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' AND Course.courseName LIKE '%" + courseNameTextField.getText().toUpperCase() + "%' ORDER BY Course.courseCode");
				else if (!courseNameTextField.getText().isEmpty() && courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection LIKE '%" + sectionTextField.getText() + "%' AND Course.courseName LIKE '%" + courseNameTextField.getText().toUpperCase() + "%' ORDER BY Course.courseCode");
				else if (courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection LIKE '%" + sectionTextField.getText() + "%' AND Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' ORDER BY Course.courseCode");
				else if (!courseNameTextField.getText().isEmpty() && !courseCodeTextField.getText().isEmpty() && !sectionTextField.getText().isEmpty())
					rs = stmt.executeQuery("SELECT * FROM Course INNER JOIN Section ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID WHERE Section.courseSection LIKE '%" + sectionTextField.getText() + "%' AND Course.courseName LIKE '%" + courseNameTextField.getText().toUpperCase() + "%' AND Course.courseCode LIKE '%" + courseCodeTextField.getText() + "%' ORDER BY Course.courseName");

			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);
			    
			    if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
			    } else {
				    do {				    	
				    	Label lbName = new Label(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(7)));
				    	lbName.setPrefWidth(380);
				    	lbName.setWrapText(true);
				    	lbName.setStyle("-fx-font-weight: bold");
				    	vBox.getChildren().add(lbName);
				    	
				    	Label lbTitle = new Label(String.format(rs.getString(3)));
				    	lbTitle.setPrefWidth(380);
				    	lbTitle.setWrapText(true);
				    	lbTitle.setFont(Font.font(null, FontPosture.ITALIC, 12));
				    	vBox.getChildren().add(lbTitle);
				    	
				    	Label lbInstructor = new Label(String.format("Instructor: " + rs.getString(14) + " " + rs.getString(15)));
				    	lbInstructor.setPrefWidth(380);
				    	lbInstructor.setWrapText(true);
				    	vBox.getChildren().add(lbInstructor);
				    	
				    	Label lbInfo = new Label(String.format(rs.getString(4)));
				    	lbInfo.setPrefWidth(380);
				    	lbInfo.setWrapText(true);
				    	vBox.getChildren().add(lbInfo);
				    	
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

