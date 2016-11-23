package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import googleVision.AnnotateImageResponse;
import googleVision.EntityAnnotation;
import googleVision.VisionAPIResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
	
	private static final String TARGET_URL = "https://vision.googleapis.com/v1/images:annotate?key=AIzaSyBQSaazsdwWyhQdrAlP2qxr7h9AshzYpAo";
	
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
        /*
        this.dialogStage.setOnCloseRequest(new EventHandler <WindowEvent>(){

			@Override
			public void handle(WindowEvent event) {

				//on close, save
				saveButtonHandler();
				
			}
        });*/
        
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
	
	
	@FXML 
	public void visionHandler() throws IOException{
		
		//get the current image and get its byteImg
		byte[] byteImg = Files.readAllBytes(new File(photo.getPath()).toPath());

		//encode byteImg to a base64 string
		String base64Image = Base64.getEncoder().encodeToString(byteImg);
		
		
		//create the http connection with the target url
		URL serverUrl = new URL(TARGET_URL);
		URLConnection urlConnection = serverUrl.openConnection();
		HttpURLConnection httpConnection = (HttpURLConnection)urlConnection;
		
		//set up the post request
		httpConnection.setDoOutput(true);
		httpConnection.setRequestMethod("POST");
		
		httpConnection.setRequestProperty("Content-Type", "application/json");
		
		//write the post messgae body
		BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream()));
		httpRequestBodyWriter.write("{\"requests\":  [{ \"features\":  [ {\"type\": \"LABEL_DETECTION\""
		   +"}], \"image\": {\"content\":\""+ base64Image +"\"}}]}");
		 

			
		//httpRequestBodyWriter.write("{\"foo\":\"bar\"}");
		httpRequestBodyWriter.close();
		
		//Error with Connection
		if (httpConnection.getInputStream() == null) {
			//Connection Error
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Connection Error");
			alert.setHeaderText("Connection Error");
			alert.setContentText("Currently unable to connect to google vision api. Cannot get tags.");
			alert.showAndWait();
			System.out.println("No stream");
			return;
		}
		
		//get the response data
		Scanner httpResponseScanner = new Scanner(httpConnection.getInputStream());
		String resp = "";
		while (httpResponseScanner.hasNext()) {
			String line = httpResponseScanner.nextLine();
			resp += line;
		}
		
		httpResponseScanner.close();
		
		//use gson to get parse json data
		Gson gson = new GsonBuilder().create();
        VisionAPIResponse json = (VisionAPIResponse)gson.fromJson(resp, VisionAPIResponse.class);
       
		
        //if null... no tags were returned.
        if (json == null)  {
        	//Connection Error
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("No tags.");
			alert.setHeaderText("No tags.");
			alert.setContentText("The Google Vision API was not able to create any tags for this picture.");
			alert.showAndWait();
			
			return;
        }

        //this array will hold all google results
        ArrayList<Tags> googleResults = new ArrayList<Tags>();
        
        AnnotateImageResponse[] annotations= json.getResponses();
        
        //navigate through googles responses
        for (AnnotateImageResponse entities : annotations) {
        	for (EntityAnnotation entityAnnotation : entities.getLabelAnnotations()) {
        		
        		//create a tag from these and add to result list
        		Tags googleTag = new Tags("GoogleVision", entityAnnotation.getDescription());
        		googleResults.add(googleTag);
			}
		}
        
        //create a vbox with all the tags and set it in alert
        VBox googleTagVbox = new VBox();
        
        googleTagVbox.getChildren().add(new Label("Google generated tags : "));
        googleTagVbox.getChildren().add(new Label());
        
        for(Tags tag : googleResults){
        	googleTagVbox.getChildren().add(new Label("\"" + tag.getVal() + "\""));
        }
        
        googleTagVbox.setAlignment(Pos.CENTER);
       
        //ask if they want to add the tags to their list of tags
        Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Generate Tags");
    	alert.setHeaderText("Do you want to ADD these tags generated by google?");
    	alert.setGraphic(googleTagVbox);

    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    		
    		//go through each tag and see if it alrady exists in tag list... If not add them
    		for(Tags tag : googleResults){

    			observableTags.add(tag);
    			this.photo.addTag(tag);

            }
            
    	}
        
	}
}
