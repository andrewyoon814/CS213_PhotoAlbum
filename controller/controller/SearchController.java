package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tags;
import model.User;

public class SearchController {
	
	private User session;
	private ArrayList<User> db;
	
	@FXML ListView<String> tagList;
	
	/**
	 * Sets the db object to entire, up to date, data.
	 * @author Andrew Yoon
	 * @param db
	 */
	public void setDB(ArrayList<User> db){
		this.db = db;
	}
	
	/**
	 * Sets the current session to the current user's user object
	 * @author Andrew Yoon
	 * @param session
	 */
	public void setUpSession(User session){
		this.session = session;
	}
	
	/**
	 * This method will set up the tags that appear in photos in the combobox.
	 * @author Andrew Yoon
	 */
	public void setUpTags(){
		
		ArrayList<String> allTags = new ArrayList<String>();
		
		//search every tag in every photo in every album and add it to the combo box
		for(Album album : session.getAlbums()){
			
			for(Photo photo : album.getPhotos()){
				
				for(Tags tag : photo.getTags()){
					
					if(!allTags.contains(tag.getKey())){
						allTags.add(tag.getKey());
					}
				}
			}
		}
		

		
		ObservableList<String> observableTags = FXCollections.observableArrayList(allTags);
		
		tagList.setItems(observableTags);
	}

	/**
	 * Sends back to home on click.
	 * 
	 * @author Andrew Yoon
	 * @throws IOException 
	 */
	@FXML
	public void goHomeHandler(ActionEvent event) throws IOException{
		
		//set fxmlloader and redirect to home
		Parent root = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/home.fxml"));
		root = loader.load();
		homeController homeController = loader.getController();
		
		homeController.homeSetup(session);
		homeController.setDB(this.db);
		
		Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
	}
}
