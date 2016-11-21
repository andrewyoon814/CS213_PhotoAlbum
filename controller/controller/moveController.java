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
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class moveController {

	@FXML ImageView image;
	@FXML ComboBox<String> menu;

	private int albumCount;
	private String prev;
	private ArrayList<User> db;
	private User session;
	private Photo photo;
	private boolean deleted = false;
	
	/**
	 * Sets the db object to entire data.
	 * @author Andrew Yoon
	 * @param db
	 */
	public void setDB(ArrayList<User> db){
		this.db = db;
	}
	
	public boolean getDeleted(){
		return this.deleted;
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
	 * Sets up the imageview and combo box with relevant information.
	 * @param photo
	 */
	public void setUp(Photo photo){
		
		
		//checking if you have 2 or more albums ... disallow copying to same album
		if(session.getAlbums().size() == 1){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Currently Unable to copy");
			alert.setContentText("Your Photo Library only has 1 album! You cannot copy to the move to the album!");
			alert.showAndWait();
			
			return;
		}
		
		this.photo = photo;
		
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
	 * Action Handler for the move button. Acts like a black box. Saves all changes and closes.
	 * 
	 * @author Andrew Yoon
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@FXML
	public void moveHandler(ActionEvent event) throws ParseException, IOException{
		
		// if nothing is selected
		if(menu.getSelectionModel().getSelectedItem() == null){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Please Select An Album to Copy to.");
			alert.setContentText("You must select an album to copy this photo to.");
			alert.showAndWait();
		}else{

			//get the newly chosen album and do some checks and copy it over
			String newAlbum = menu.getSelectionModel().getSelectedItem().toString();
			
			//check if current album only has on photo
			if(this.session.getAlbums().get(albumCount).getPhotos().size() == 1){
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
            	alert.setTitle("Move Photo?");
            	alert.setContentText("This album only has 1 photo. If you delete this photo, you will delete the entire album. Proceed?");

            	Optional<ButtonType> result = alert.showAndWait();
            	if (result.get() == ButtonType.OK){
            		
            		//call utility method to delete album
            		deleteAlbum(newAlbum);
            		
            		//close stage
            		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            		stage.close();
            	}else{
                	
        			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        			stage.close();
            	}
			}else{
				movePhoto(newAlbum);
        		
        		Alert alert2 = new Alert(AlertType.CONFIRMATION);
        		alert2.setTitle("Success");
        		alert2.setContentText("Move was successfull.");
            	
    			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    			stage.close();
			}
		}
	}
	
	/**
	 * This method is called to delete the album and move the photo.
	 */
	public void deleteAlbum(String newAlbum){
		
		this.deleted = true;
		
		//remove session from db
		db.remove(session);
		
		//remove the album with the photo about to be moved
		session.getAlbums().remove(albumCount);
		
		//find the newalbum and add
		for(Album albums : session.getAlbums()){
			
			if(albums.getName().equals(newAlbum)){
				albums.getPhotos().add(photo);
			}
		}
		
		//add back to db
		db.add(session);
		
		//sort the albums by alphabetical order
		 Collections.sort(session.getAlbums(), new Comparator<Album>(){
			 	@Override
			    public int compare(Album a1, Album a2) {
			 		
			 		return a1.getName().compareToIgnoreCase(a2.getName());
			    }
		 });
		 
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
				alert.setContentText("Something went wrong and you cant move this photo. Sorry.");
				alert.showAndWait();
		
				e.printStackTrace();
			} catch (IOException e) {   
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Oops, something went wrong.");
				alert.setContentText("Something went wrong and you cant move this photo. Sorry.");
				alert.showAndWait();
			}
	}
	
	
	public void movePhoto(String newAlbum){
		
		deleted = false;
		
		//remove the session from db
		db.remove(session);
		
		//remove photo from current album
		session.getAlbums().get(albumCount).remPhoto(photo);
		
		//add photo to new album
		for(Album album : session.getAlbums()){
			if(album.getName().equals(newAlbum)){
				album.getPhotos().add(photo);
			}
		}
		
		//add back to db
		db.add(session);
		
		//sort the albums by alphabetical order
		 Collections.sort(session.getAlbums(), new Comparator<Album>(){
			 	@Override
			    public int compare(Album a1, Album a2) {
			 		
			 		return a1.getName().compareToIgnoreCase(a2.getName());
			    }
		 });
		 
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
			alert.setContentText("Something went wrong and you cant move this photo. Sorry.");
			alert.showAndWait();
	
			e.printStackTrace();
		} catch (IOException e) {   
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Oops, something went wrong.");
			alert.setContentText("Something went wrong and you cant move this photo. Sorry.");
			alert.showAndWait();
		}
	}
}
