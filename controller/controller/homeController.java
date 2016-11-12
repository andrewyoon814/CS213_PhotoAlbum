package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class homeController {
	
	@FXML ScrollPane mainPane;
	@FXML VBox albumVbox;
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
		
		mainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mainPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
		
		//sets session state
		this. session = session;	
		welcome.setText("Welcome " + session.getName() + "!");
		
		//iterate through all albums
		int albumCount = 0;
		while(albumCount < session.getAlbums().size()){
			
			//Create an hbox that 
			HBox albumHbox = new HBox();
			albumHbox.setPadding(new Insets(15, 12, 15, 12));
			albumHbox.setSpacing(10);
			
			//create image view for image
			File file = new File(session.getAlbums().get(albumCount).getPhotos().get(0).getPath());
			Image img;
			ImageView iView = new ImageView();
			
			try {
			
				img = new Image(file.toURI().toURL().toExternalForm());
				iView.setImage(img);
				
			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
			
			iView.setFitHeight(100);
			iView.setFitWidth(100);
			iView.setPreserveRatio(true);
			
			albumHbox.getChildren().add(iView);
			
			//Create a button for renaming the album
			Button renameAlbum = new Button("RENAME ALBUM");
			albumHbox.getChildren().add(renameAlbum);
			
			//Create a button for deleting the album
			Button deleteAlbum = new Button("DELETE ALBUM");
			albumHbox.getChildren().add(deleteAlbum);
			albumCount++;
			
			albumVbox.getChildren().add(albumHbox);
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
