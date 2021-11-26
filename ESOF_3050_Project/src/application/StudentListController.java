/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * StudentListController.java
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
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class StudentListController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="listScrollPane"
    private ScrollPane listScrollPane; // Value injected by FXMLLoader
    
    // VBox to be used to display information in the scroll pane
   	private VBox vBox = new VBox();
    
   	//Stores the user type
    private String type = "";

    //Global scenes
    private Main main;
    private Scene sceneAdminWelcomeScreen;
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
     * Sets the scene for the back buttons
     * 
     * @param sceneAdminWelcomeScreen
     * @param sceneInstructorWelcomeScreen
     */
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen, Scene sceneInstructorWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    	this.sceneInstructorWelcomeScreen = sceneInstructorWelcomeScreen;
    }
    
    /**
     * Shows the list of students 
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
			    
				//Creates the vBox
			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);
			    vBox.setPrefWidth(565);
			    
			    //Get member type for return screen
			    ResultSet getType = stmt.executeQuery("SELECT memberType FROM Login WHERE memberID = " + Integer.parseInt(memberID));
			    
			    //Stores the user type
			    if (getType.next())
				    type = getType.getString(1);
			    
			    getType.close();			    
			    
			    //Queries the table to get all the student
			    ResultSet rs = stmt.executeQuery("SELECT * FROM UniversityMember WHERE memberType != 'instructor' AND memberType != 'administrator' ORDER BY lastName");
		    	
			    //Iterate through the employees and create a label for each and store in the vBox
		    	if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Employees Found.")));
			    } else {
				    do {
				    	//Bold the name
				    	Label lbName = new Label(String.format(rs.getString(4) + ", " + rs.getString(3)));
				    	lbName.setStyle("-fx-font-weight: bold");
				    	vBox.getChildren().add(lbName);
				    	
				    	//Create a label for the rest of the information
				    	Label lb = new Label(String.format("\t" + rs.getString(2) + "\n\tID: " + rs.getString(1) + "\n\tSIN: " + rs.getString(5) + "\n\tDate of Birth: " + rs.getString(6) + "\n\tAddress: " + rs.getString(7) + "\n\tStatus: " + rs.getString(8)));
				    	vBox.getChildren().add(lb);
				    	
				    	//Create a separator
				    	Separator sp = new Separator();
				    	sp.setOrientation(Orientation.HORIZONTAL);
				    	vBox.getChildren().add(sp);
				    } while (rs.next());
			    }
		    	
		    	//Add the vBox to the scroll pane
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
     * Send the user back to the scene for whichever type they are
     * 
     * @param event
     */
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


