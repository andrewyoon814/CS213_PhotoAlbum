package controller;

import controller.adminController;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

public class LoginController {

	@FXML TextField loginField;
	@FXML Button loginButton;

	
	public Stage stage;
	private ArrayList<User> db;
	
	/**
	 * This recieve the root and the "db" var which acts as our database and holds all the information.
	 * 
	 * @author Andrew Yoon
	 * @param root
	 * @param db
	 */
	public void start(PhotoAlbum root, ArrayList<User> db){
	
		this.db = db;
		
	}
	
	public void setDB(ArrayList<User> db){
		this.db = db;
	}
	
	/**
	 * 
	 * This method is called when the user logs in to the app and enters a username.
	 * If it is admin, they are redirected to admin page.
	 * If not, they are redirected to user home page.
	 * 
	 * @author Andrew Yoon
	 * 
	 */
	@FXML
	public void loginButtonHandler(ActionEvent event) throws IOException{
		
		//get username for textfield
		String username = loginField.getText();

		Parent root = null;
		
		FXMLLoader loader = new FXMLLoader();
		
		//if user is admin send to admin controller else home controller
		if(username.equals("admin")){
		
			loader.setLocation(getClass().getResource("../view/adminPage.fxml"));
			root = loader.load();
			adminController adminController = loader.getController();
			
			
			Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        
	        stage.setScene(scene);
	        stage.show();
	        adminController.adminPageSetup(event);
		
		}else{
			
			boolean found = false;
			
			int count = 0;
			
			//go through db array to see if it contains a user with the given username
			while(count < this.db.size()){
				f
				//check
				if( this.db.get(count).getName().equals(username)){
					
					loader.setLocation(getClass().getResource("../view/home.fxml"));
					root = loader.load();

					Scene scene = new Scene(root);
			        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			        
			        stage.setScene(scene);
			        stage.show();
			        
					homeController homeController = loader.getController();
			        homeController.setDB(this.db);
					homeController.homeSetup( this.db.get(count));
					
			        
			        found = true;
				}
				
				count++;
			}
			
			
			if( found == false){
				//show the error message.
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Login Error");
				alert.setHeaderText("Login Unsuccessful.");
				alert.setContentText("No such username exists.");
				alert.showAndWait();
				
				loginField.setText("");
			}
		}
		
	}
}
