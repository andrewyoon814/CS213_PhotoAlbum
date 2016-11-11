package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.User;

public class homeController {
	
	@FXML ScrollPane mainPane;
	@FXML Label welcome;
	private User session;
	
	/**
	 * This method recieves the user object and sets up the home page with the users information. 
	 *
	 * @author Andrew Yoon
	 * 
	 * @param session
	 */
	public void homeSetup(User session){
		
		//sets session state
		this. session = session;	
		welcome.setText("Welcome " + session.getName() + "!");
		
		int albumCount = 0;
		while(albumCount < session.getAlbums().size()){
			
			System.out.println(session.getAlbums().get(albumCount).getName());
			
			int photocount = 0;
			
			while(photocount < session.getAlbums().get(albumCount).getPhotos().size()){
				
				System.out.println(session.getAlbums().get(albumCount).getPhotos().get(photocount).getName());
				
				photocount++;
			}
			System.out.println("");
			albumCount++;
		}

	
	}
	
	/**
	 * Goes to new album page to create a new album.
	 * 
	 * @author Kevin Bundschuh
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void createHandler(ActionEvent event) throws IOException{
		
		Parent root;
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(getClass().getResource("../view/newAlbum.fxml"));
		root = loader.load();
		
		newAlbumController newAlbumController = loader.getController();
		
		newAlbumController.setUp(session);
		
		Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
	}
	
	
	/**
	 * On logout, switches scene to the main login scene.
	 * 
	 * @author Kevin Bundschuh
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void homeLogoutButtonHandler(ActionEvent event) throws IOException{
		
		Parent root;
		root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       
		stage.setScene(scene);
		stage.show();

	}
}
