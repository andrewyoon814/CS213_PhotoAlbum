package controller;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Album;
import model.Photo;
import model.Tags;

public class PhotoDialogController {

	@FXML ImageView pictureView;
	@FXML TextField nameField;
	@FXML TextField captionField;
	@FXML TextField tagTypeField;
	@FXML TextField tagValField;
	@FXML ListView<Tags> tagList;
	
	private Photo photo;
	private Album album = null;
	private ArrayList<Photo> newAlbum;
	private Stage dialogStage;
	private Boolean deleted = false;
	private ArrayList<Tags> tags;
	public ObservableList<Tags> observableTags;
	
	/**
	 * Sets the current album.
	 * @author Andrew Yoon
	 * @param album
	 */
	public void setAlbums(Album album){
		this.album = album;
		this.album.photoNum = album.getPhotos().size();
	}
	
	/**
	 * Sets the current album in the case that there is no album.
	 * @author Andrew Yoon
	 * @param album
	 */
	public void setAlbum(ArrayList<Photo> newAlbum){
		this.newAlbum = newAlbum;
	}
	
	/**
	 * This method is called from a method to set tags when tags already exist to set up the lsitview.
	 * @author Andrew Yoon
	 * @param tags
	 */
	public void setTags(ArrayList<Tags> tags){
		this.tags = tags;
		observableTags = FXCollections.observableArrayList(this.tags);
		tagList.setPlaceholder(new Label("No Tags yet."));
		tagList.setItems(observableTags);
	}
	
	/**
	 * This method sets the tags when there are not tags yet. When a new photo is being added.
	 * @author Andrew Yoon
	 */
	public void setTags(){
		this.tags = new ArrayList<Tags>();
		observableTags = FXCollections.observableArrayList(this.tags);
		tagList.setPlaceholder(new Label("No Tags yet."));
		tagList.setItems(observableTags);
	}

	
	/**
	 * Simple photo object getter. Called from the parent stage to get changes.
	 * 
	 * @author Andrew Yoon
	 * @return
	 */
	public Photo getPhoto(){
		return this.photo;
	}
	
	/**
	 * This method sets up the dialog and all the basic information such as the tag v box.
	 * @author Andrew Yoon
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		
		//set stage
        this.dialogStage = dialogStage;
        
        this.dialogStage.setOnCloseRequest(new EventHandler <WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {
				
				//gets users confirmation if they do not want to save
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Are You Sure?");
				alert.setHeaderText("Save?");
				alert.setContentText("Do you want to save current changes?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					saveButtonHandler();
				}
				
			}
        });
        
        //set up vbox title
        Label title = new Label("Tags Added :");
        title.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
        
	}
	
	/**
	 * Returns the deleted boolean. If true, the changes made and the photo will not be saved.
	 * 
	 * @author Andrew Yoon
	 * @return
	 */
	public Boolean getDeleted(){
		return this.deleted;
	}
	
	/**
	 * This method listens to the delete button. On click it will show and alert warning that all changes made will not be saved.
	 * If user clicks ok, go back without saving. Else dont do anything and go back to dialog.
	 * 
	 * @author Andrew Yoon
	 */
	@FXML
	public void deletePhotoDialogHandler(){
		
		
		//gets users confirmation if they do not want to save
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Are You Sure?");
		alert.setHeaderText("Information will not save!");
		alert.setContentText("This photo and its name, caption, tags etc will NOT be saved!");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			deleted = true;
			this.dialogStage.close();
		} 
	}
	
	/**
	 * This listens to the add tag button and when clicked it adds the tags to data structure as well as Vbox.
	 * 
	 * @author Andrew Yoon
	 */
	@FXML
	public void addTagHandler(){
		
		
		//get the inputted tags
		String tagType = tagTypeField.getText();
		String tagVal = tagValField.getText();
		
		if(tagType.equals("")){
			tagType = "General";
		}
		
		//create new tag object
		Tags newTag = new Tags(tagType, tagVal);
		
		//add tag to photoobject's tag list
		this.photo.addTag(newTag);
		
		//update the visible listview
		observableTags.add(newTag);

		//refresh the text fields
		tagValField.setText("");
		tagTypeField.setText("");
	}
	
	
	/**
	 * Sets up dialog with image and other information.
	 * 
	 * @author Andrew Yoon
	 * @param photo
	 */
	public void setDetails(Photo photo){
		
		this.photo = photo;
		
		//set the image
		File file = new File(photo.getPath());
		Image img;
		
		try {
		
			img = new Image(file.toURI().toURL().toExternalForm());
			pictureView.setImage(img);
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

		pictureView.setPreserveRatio(true);
		
		//set the name
		nameField.setText(photo.getName());
		
		//set the caption
		captionField.setText(photo.getCaption());

	}
	
	
	/**
	 * This listens to the save button and onclick, it saves all changes
	 * 
	 * @author Andrew Yoon
	 */
	@FXML
	public void saveButtonHandler(){
		
		if(nameField.getText() == null || nameField.getText().equals("")){
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.setTitle("Nameless Photo!");
        	alert.setContentText("Photo has no name. If you press 'ok' a default name will be created for it.");

        	Optional<ButtonType> result = alert.showAndWait();
        	if (result.get() == ButtonType.OK){
        		
        		if(album != null){
        			this.photo.setName("Photo" + album.photoNum++);
        		}else{
        			this.photo.setName("Photo" + newAlbum.size());
        		}
        		this.photo.setCaption(captionField.getText());
        	}else{
        		this.photo.setName(nameField.getText());
        		this.photo.setCaption(captionField.getText());
        	}
		}else{
			this.photo.setName(nameField.getText());
    		this.photo.setCaption(captionField.getText());
		}
		
		this.dialogStage.close();
	}
	
	/** 
	 * Allows you to delete the selected tag.
	 */
	@FXML
	public void deleteTagHandler(){
		
		//get what is to be selected
		Tags deleteTag = tagList.getSelectionModel().getSelectedItem();
		
		if(deleteTag != null){
			//remove the tag from the database
			this.photo.remTag(deleteTag);
			
			//remove from obserbale list
			observableTags.remove(deleteTag);
		}
		
	}
	
	
}
