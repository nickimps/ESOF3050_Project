/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * EmployeeListController.java
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

public class EmployeeListController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="listScrollPane"
    private ScrollPane listScrollPane; // Value injected by FXMLLoader
    
    //Global scenes
    private Main main;
    private Scene sceneAdminWelcomeScreen;
    private Scene sceneInstructorWelcomeScreen;
    
    // VBox to be used to display information in the scroll pane
   	private VBox vBox = new VBox();
    
   	//Stores the type of user 
    private String type = "";
    
    /**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    /**
     * Sets the scene to go back to when the back button is pressed
     * 
     * @param sceneAdminWelcomeScreen
     * @param sceneInstructorWelcomeScreen
     */
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen, Scene sceneInstructorWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    	this.sceneInstructorWelcomeScreen = sceneInstructorWelcomeScreen;
    }
    
    /**
     * Shows the employee list
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
			    
				//Create the vBox to be put in the scroll pane
			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);
			    
			    //Get member type for return screen
			    ResultSet getType = stmt.executeQuery("SELECT memberType FROM Login WHERE memberID = " + Integer.parseInt(memberID));
			    
			    //Store the type of user for the back button
			    if (getType.next())
				    type = getType.getString(1);
			    
			    getType.close();			    
			    
			    //Query the results for all employees
			    ResultSet rs = stmt.executeQuery("SELECT * FROM UniversityMember WHERE memberType = 'instructor' OR memberType = 'administrator' ORDER BY lastName");
		    	
			    //Iterate through and creates the label to insert in the vBox for each employee
		    	if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Employees Found.")));
			    } else {
				    do {
				    	//Get the first name and last name and set to bold
				    	Label lbName = new Label(String.format(rs.getString(4) + ", " + rs.getString(3)));
				    	lbName.setStyle("-fx-font-weight: bold");
				    	vBox.getChildren().add(lbName);
				    	
				    	//Get the rest of the information
				    	Label lb = new Label(String.format("\t" + rs.getString(2) + "\n\tID: " + rs.getString(1) + "\n\tSIN: " + rs.getString(5) + "\n\tDate of Birth: " + rs.getString(6) + "\n\tAddress: " + rs.getString(7) + "\n\tStatus: " + rs.getString(8)));
				    	vBox.getChildren().add(lb);
				    	
				    	//Add a line separator
				    	Separator sp = new Separator();
				    	sp.setOrientation(Orientation.HORIZONTAL);
				    	
				    	//Add all the above
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
     * Go back to the appropriate welcome screen
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

