package controller;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Photo;
import model.Tags;

public class PhotoDialogController {

	@FXML ImageView pictureView;
	@FXML TextField nameField;
	@FXML TextField captionField;
	@FXML TextField tagTypeField;
	@FXML TextField tagValField;
	@FXML VBox tagVBox;
	
	private Photo photo;
	private Stage dialogStage;
	private Boolean deleted = false;
	
	/**
	 * This method sets up the dialog and all the basic information such as the tag v box.
	 * @author Andrew Yoon
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		
		//set stage
        this.dialogStage = dialogStage;
        
        //set up vbox title
        Label title = new Label("Tags Added :");
        title.setFont(Font.font("Amble CN", FontWeight.BOLD, 24));
        tagVBox.getChildren().add(title);
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
		
		//create new tag object
		Tags newTag = new Tags(tagType, tagVal);
		
		//add tag to photoobject's tag list
		this.photo.addTag(newTag);
		
		Label tagLabel = new Label("{" + newTag.getKey() + "}: " + newTag.getVal());
	
		tagVBox.setSpacing(10);
		
		
		tagVBox.getChildren().add(tagLabel);
		
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
	 * Simple photo object getter. Called from the parent stage to get changes.
	 * 
	 * @author Andrew Yoon
	 * @return
	 */
	public Photo getPhoto(){
		return this.photo;
	}
	
	/**
	 * This listens to the save button and onclick, it saves all changes
	 * 
	 * @author Andrew Yoon
	 */
	@FXML
	public void saveButtonHandler(){
		
		if(nameField.getText() == null || nameField.getText().equals("")){
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Creation Error");
			alert.setHeaderText("No Name.");
			alert.setContentText("Photo has no name! Please make new one!");
			alert.showAndWait();
			return;
		}
		
		this.photo.setName(nameField.getText());
		this.photo.setCaption(captionField.getText());
		
		 this.dialogStage.close();
	}
	
	
}
