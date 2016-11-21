package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class copyController {

	@FXML ImageView image;
	@FXML ComboBox<String> menu;

	private int albumCount;
	private String prev;
	private ArrayList<User> db;
	private User session;
	private Photo photo;
	
	/**
	 * Sets the db object to entire data.
	 * @author Andrew Yoon
	 * @param db
	 */
	public void setDB(ArrayList<User> db){
		this.db = db;
	}
	
	/**
	 * This sets the previous album ( aka the one that it is currently located in).
	 * @author Andrew Yoon
	 */
	public void setPrev(String prev){
		this.prev = prev;
	}
	
	/**
	 * Sets current session variables.
	 * @param session
	 */
	public void setSession(User session, int albumCount){
		this.albumCount = albumCount;
		this.session = session;
	}
	
	/**
	 * Getter for the album count variable.
	 * @return
	 */
	public int getAlbumCount(){
		return this.albumCount;
	}
	
	/**
	 * Getter for the user variable.
	 * @return
	 */
	public User getSession(){
		return this.session;
	}
	/**
	 * 
	 * Sets up the main dialog.
	 * @param photo
	 */
	public void setUp(Photo photo){
		
		//checking if you have 2 or more albums ... disallow copying to same album
		if(session.getAlbums().size() == 1){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Currently Unable to copy");
			alert.setContentText("Your Photo Library only has 1 album! You cannot copy to the same album!");
			alert.showAndWait();
			
			return;
		}
		
		this.photo = photo;
		
		//set image up in image view
		File file = new File(photo.getPath());
		Image img = null;
		
		try {
			img = new Image(file.toURI().toURL().toExternalForm());	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		image.setImage(img);
		
		
		//set up the combo box by populating it with all albums
		for( Album albums : session.getAlbums()){	
			
			//do not include the current album in which the photo is located in
			if(albums.getName().equals(prev) == false){
				menu.getItems().add(albums.getName());
			}
		}
		
	}
	
	/**
	 * 
	 * Gets the selected album. And adds photo to it. Serializes and then returns.
	 * @param event
	 * @throws ParseException 
	 */
	@FXML
	public void copyHandler(ActionEvent event) throws ParseException {
		
		if(menu.getSelectionModel().getSelectedItem() == null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Please Select An Album to Copy to.");
			alert.setContentText("You must select an album to copy this photo to.");
			alert.showAndWait();
		}else{

			//get the newly chosen album and do some checks and copy it over
			String newAlbum = menu.getSelectionModel().getSelectedItem().toString();
			
			for( Album albums : session.getAlbums()){		
						
				//if album is found make changes.
				if(albums.getName().equals(newAlbum)){
					
					//remove the user, make changes, and add back
					db.remove(session);				
					albums.getPhotos().add(this.photo);
					db.add(session);
					
					//serialize the new data
        			try {
        				FileOutputStream outfile = new FileOutputStream("data/users.txt");
        				ObjectOutputStream outStream = new ObjectOutputStream(outfile);
        			
        				//write the previously deserialized array
        				outStream.writeObject(db);
        				
        				outfile.close();
        				outStream.close();

        			} catch (FileNotFoundException e) {
        				Alert alert = new Alert(AlertType.ERROR);
    					alert.setTitle("Oops, something went wrong.");
    					alert.setContentText("Something went wrong and you cant copy this photo. Sorry.");
    					alert.showAndWait();
    					
    					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    					stage.close();
				
        				e.printStackTrace();
        			} catch (IOException e) {   
        				
        				Alert alert = new Alert(AlertType.ERROR);
    					alert.setTitle("Oops, something went wrong.");
    					alert.setContentText("Something went wrong and you cant copy this photo. Sorry.");
    					alert.showAndWait();
    					
    					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    					stage.close();
        				e.printStackTrace();
        			}
        			
	        		//sort the albums by alphabetical order
	   				 Collections.sort(session.getAlbums(), new Comparator<Album>(){
	   					 	@Override
	   					    public int compare(Album a1, Album a2) {
	   					 		
	   					 		return a1.getName().compareToIgnoreCase(a2.getName());
	   					    }
	   				 });
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Copy success!");
					alert.setContentText("Successfully Copied!");
					alert.showAndWait();
					
					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					stage.close();
					
					return;
				}
			}
			
			//An alert just in case somethig happens.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Oops, something went wrong.");
			alert.setContentText("Something went wrong and you cant copy this photo. Sorry.");
			alert.showAndWait();
			
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
		}

	}

}
