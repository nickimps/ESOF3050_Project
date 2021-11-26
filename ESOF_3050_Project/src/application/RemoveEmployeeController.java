/*
 * ESOF 3050 Project
 * 
 * Nicholas Imperius
 * Sukhraj Deol
 * Jimmy Tsang
 * Kristopher Poulin
 * 
 * RemoveEmployeeController.java
 */

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

public class RemoveEmployeeController {

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
   	
   	//Global list of check boxes
   	private List<CheckBox> cbs = new ArrayList<>();

   	//Global scenes
    private Main main;
    private Scene sceneAdminWelcomeScreen;
    
    /**
     * Sets the main scene of the program
     * 
     * @param main The scene of the main
     */
    public void setMainScene(Main main) {
    	this.main = main;
    }
    
    /**
     * Sets the scene for the back button
     * 
     * @param sceneAdminWelcomeScreen
     */
    public void setBackPressedScene(Scene sceneAdminWelcomeScreen) {
    	this.sceneAdminWelcomeScreen = sceneAdminWelcomeScreen;
    }
    
    /**
     * Shows the list of employees to be removed
     */
    public void showList() {
    	//Try-Catch for connection
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	//Try-Catch for connection
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UniversityRegistrationSystem?" + "user=root");
			
			//Try-Catch for SQL
			try {
				Statement stmt = conn.createStatement();
			    
				//Create the vBox
			    vBox.getChildren().clear();
			    vBox.setPadding(new Insets(8, 8, 8, 8));
			    vBox.setSpacing(8.0);		    
			    
			    //Query the employees
			    ResultSet rs = stmt.executeQuery("SELECT * FROM UniversityMember WHERE memberType = 'instructor' OR memberType = 'administrator' ORDER BY lastName");
		    	
			    //Iterate through each results and create a checkbox for the scrollpane
			    if (rs.next() == false) {
				    vBox.getChildren().add(new Label(String.format("No Employees Found.")));
			    } else {
				    do {
				    	CheckBox cb = new CheckBox(String.format(rs.getString(4) + ", " + rs.getString(3) + " - " + rs.getString(1) + " - " + rs.getString(2)));
				    	cbs.add(cb);
				    } while (rs.next());
			    }
			    
			    //Add listeners for each check box
			    for (int i = 0; i < cbs.size(); i++) {
			    	vBox.getChildren().add(cbs.get(i));
			    	cbs.get(i).selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
			    		//On click, set info message to not visible
			    		messageLabel.setVisible(false);
			    	});
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
     * Bring the admin back to the welcome screen, clear the lists too
     * 
     * @param event
     */
    @FXML
    void backButtonPressed(ActionEvent event) {
    	messageLabel.setVisible(false);
    	main.setScreen(sceneAdminWelcomeScreen);
    	vBox.getChildren().clear();
    	cbs.clear();
    }

    /**
     * When the remove button is pressed, need to UPDATE database
     * 
     * @param event
     */
    @FXML
    void removeButtonPressed(ActionEvent event) {
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
				
				//Get the memberID from the check box
				String memberID = "";
				for (int i = 0; i < cbs.size(); i++) {
					if (cbs.get(i).isSelected()) {
						String[] splitArray = cbs.get(i).getText().split(" - ");
						memberID = splitArray[2];

						//DELETE the member from the database
						stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
						stmt.executeUpdate("DELETE FROM UniversityMember WHERE memberID = " + memberID);
						stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
					}
				}
				
				//Clear the list of check boxes
				cbs.clear();
			    
				//Let the admin know things went okay
				messageLabel.setText("Successfully Removed!");
			    messageLabel.setVisible(true);
				
			    //Show the updated list of employees
				showList();
			    
			} catch (SQLException e) {
				messageLabel.setText("Error!");
			    messageLabel.setVisible(true);
			    e.printStackTrace();
			}

			//Close connection
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

