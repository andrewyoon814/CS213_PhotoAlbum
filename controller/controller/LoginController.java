package controller;

import controller.adminController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
	@FXML
	public void loginButtonHandler(ActionEvent event) throws IOException{
		
		String username = loginField.getText();
		
		Parent root;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/adminPage.fxml"));
		root = loader.load();
	
		if(username.equals("admin")){
			adminController adminController = loader.getController();
			adminController.adminPageSetup(event);
		}else{
			
			
		}
		
		Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
		
	}
}
