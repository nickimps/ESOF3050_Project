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
import javafx.scene.layout.VBox;

public class EmployeeListController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="listScrollPane"
    private ScrollPane listScrollPane; // Value injected by FXMLLoader
    
    private Main main;
    private Scene sceneAdminWelcomeScreen;
    private Scene sceneInstructorWelcomeScreen;
    
    // VBox to be used to display information in the scroll pane
   	private VBox vBox = new VBox();
    
    private String type = "";
    
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen, Scene sceneInstructorWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
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
			    
			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);
			    
			    //Get member type for return screen
			    ResultSet getType = stmt.executeQuery("SELECT memberType FROM Login WHERE memberID = " + Integer.parseInt(memberID));
			    
			    if (getType.next())
				    type = getType.getString(1);
			    
			    getType.close();			    
			    
			    ResultSet rs = stmt.executeQuery("SELECT * FROM UniversityMember WHERE memberType != 'student' ORDER BY lastName");
		    	
		    	if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Employees Found.")));
			    } else {
				    do {
				    	Label lb = new Label(String.format(rs.getString(4) + ", " + rs.getString(3) + "\n\t" + rs.getString(2) + "\n\tID: " + rs.getString(1) + "\n\tSIN: " + rs.getString(5) + "\n\tDate of Birth: " + rs.getString(6) + "\n\tAddress: " + rs.getString(7) + "\n\tStatus: " + rs.getString(8)));
				    	vBox.getChildren().add(lb);
				    } while (rs.next());
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
	    if (type.equals("instructor"))
	    	main.setScreen(sceneInstructorWelcomeScreen);
	    else if (type.equals("administrator"))
	    	main.setScreen(sceneAdminWelcomeScreen);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	
    }
}

