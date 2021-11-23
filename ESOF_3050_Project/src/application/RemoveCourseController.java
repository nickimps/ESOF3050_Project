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

public class RemoveCourseController {

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
   	
   	private List<CheckBox> cbs = new ArrayList<>();

    private Main main;
    private Scene sceneAdminWelcomeScreen;
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    }

    public void showList() {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			try {
				Statement stmt = conn.createStatement();
			    
			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);		    
			    
			    ResultSet rs = stmt.executeQuery("SELECT Section.courseName, Section.courseCode, Section.courseSection, subject, Section.time, firstName, lastName FROM Section "
			    		+ "INNER JOIN Course ON Course.courseName = Section.courseName AND Course.courseCode = Section.courseCode "
			    		+ "INNER JOIN UniversityMember ON UniversityMember.memberID = Section.memberID "
			    		+ "ORDER BY Section.courseName, Section.courseCode, Section.courseSection");
		    	
			    if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Classes Found.")));
			    } else {
				    do {
				    	CheckBox cb = new CheckBox(String.format(rs.getString(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + " : " + rs.getString(4) + "\n\tInstructor: " + rs.getString(6) + " " + rs.getString(7) + "\n"));
				    	cbs.add(cb);
				    } while (rs.next());
			    }
			    
			    for (int i = 0; i < cbs.size(); i++) {
			    	vBox.getChildren().add(cbs.get(i));
			    	cbs.get(i).selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    		messageLabel.setVisible(false);
			    	});
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
    	messageLabel.setVisible(false);
    	main.setScreen(sceneAdminWelcomeScreen);
    	vBox.getChildren().clear();
    	cbs.clear();
    }

    @FXML
    void removeButtonPressed(ActionEvent event) {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			try {
				Statement stmt = conn.createStatement();
				
				String courseName = "";
				String courseCode = "";
				String courseSection = "";
				for (int i = 0; i < cbs.size(); i++) {
					if (cbs.get(i).isSelected()) {
						String[] splitArray = cbs.get(i).getText().split("-");
						courseName = splitArray[0];
						courseCode = splitArray[1];
						String[] newSplitArray = splitArray[2].split(" : ");
						courseSection = newSplitArray[0];

						stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
						stmt.executeUpdate("DELETE FROM Section WHERE courseName = '" + courseName + "' AND courseCode = '" + courseCode + "' AND courseSection = '" + courseSection + "'");
						stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
					}
				}
				
				cbs.clear();
			    
				messageLabel.setText("Successfully Removed!");
			    messageLabel.setVisible(true);
				
				showList();
			    
			} catch (SQLException e) {
				messageLabel.setText("Error!");
			    messageLabel.setVisible(true);
			    e.printStackTrace();
			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	messageLabel.setVisible(false);
    }
}

