package controller;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Photo;

public class PhotoDialogController {

	@FXML ImageView pictureView;
	@FXML TextField nameField;
	@FXML TextField captionField;
	
	private Photo photo;
	private Stage dialogStage;
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	
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
	
	public Photo getPhoto(){
		return this.photo;
	}
	
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
