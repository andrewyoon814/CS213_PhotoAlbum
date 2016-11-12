package controller;

import controller.adminController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
	private PhotoAlbum mainApp;
	
	public void start(PhotoAlbum root){
		
		this.mainApp = root;
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
	@SuppressWarnings("unchecked")
	@FXML
	public void loginButtonHandler(ActionEvent event) throws IOException{
		
		//get username for textfield
		String username = loginField.getText();
		
		ArrayList<User> deserArr = null;
		Parent root = null;
		
		FXMLLoader loader = new FXMLLoader();
		
		//if user is admin send to admin controller else home controller
		if(username.equals("admin")){
		
			loader.setLocation(getClass().getResource("../view/adminPage.fxml"));
			root = loader.load();
			adminController adminController = loader.getController();
			adminController.adminPageSetup(event);
			
			Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        
	        stage.setScene(scene);
	        stage.show();
		
		}else{
			
			//read in the serialized data and setup the userlist.
			File file = new File("data/users.txt");
			boolean found = false;
			
			if(file.exists()){
				try {
			         FileInputStream fileIn = new FileInputStream(file);
			         ObjectInputStream in = new ObjectInputStream(fileIn);
			         deserArr = (ArrayList<User>) in.readObject();
			         in.close();
			         fileIn.close();
			         
			 		
			      }catch(IOException | ClassNotFoundException i) {
			         i.printStackTrace();
			         return;
			      }
			
			
			int count = 0;
			
			
			//go through deserialized array to see if it contains a user with the given username
			while(count < deserArr.size()){
				
				//check
				if(deserArr.get(count).getName().equals(username)){
					
					loader.setLocation(getClass().getResource("../view/home.fxml"));
					root = loader.load();
					homeController homeController = loader.getController();
					
					homeController.homeSetup(deserArr.get(count));
					
					Scene scene = new Scene(root);
			        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			        
			        stage.setScene(scene);
			        stage.show();
			        
			        found = true;
				}
				
				count++;
			}
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
