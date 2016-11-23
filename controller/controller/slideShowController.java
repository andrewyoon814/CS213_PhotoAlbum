package controller;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Album;
import model.Photo;
import model.Tags;

public class slideShowController {
	
	@FXML ImageView pictureView;
	@FXML Label photoNumber;
	@FXML Button nextButton;
	@FXML Button prevButton;
	@FXML Button viewDetailsButton;
	
	private ArrayList<Photo> photoList;
	private int currentPhoto;
	
	private Photo showPhoto;
	
	//variables used by the info screen
	private int toggle = 0;
	private Stage infoPopUp = new Stage();
	private Scene infoscene;
	private boolean slideshow;
	private boolean first;
	private VBox info;
	private Label photoName;
	private Label photoCaption;
	private Label photoDate;
	private VBox tagList;
	private Label Tagtitle;


	/**
	 * This method is called on startup and it sets up the slide show with the first element in the array of photos.
	 * 
	 * @author Andrew Yoon
	 * @param album
	 */
	public void setUp(Album album){
		
		this.slideshow = true;
		this.first = true;
		
		//get the list of phootos to disply
		this.photoList = album.getPhotos();
		this.currentPhoto = 0;

		
		populateDialog();
	}
	
	/**
	 * This method is used when a single picture is given to the class and we only show that one picture. Hide all other buttons and labels.
	 * 
	 * @author Andrew Yoon
	 * @param photo
	 */
	public void setPhoto(Photo photo){
		
		this.slideshow = false;
	
		//put photo up in the image view
		File file = new File(photo.getPath());
		Image img;
		
		try {
		
			img = new Image(file.toURI().toURL().toExternalForm());
			pictureView.setImage(img);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		showPhoto = photo;
		
		nextButton.setVisible(false);
		prevButton.setVisible(false);
		
		
	}
	
	/**
	 * This method is called on whenever a photo on the image view is to be chnaged.
	 * 
	 * @author Andrew Yoon
	 * @param album
	 */
	public void populateDialog(){
		
		//put photo up in the image view
		File file = new File(this.photoList.get(currentPhoto).getPath());
		Image img;
		
		try {
		
			img = new Image(file.toURI().toURL().toExternalForm());
			pictureView.setImage(img);
			
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		
		//show which number the current photo is
		int tmp = currentPhoto;
		tmp++;
		photoNumber.setText(tmp + "/" + this.photoList.size());
		photoNumber.setAlignment(Pos.CENTER);

		slideShowSetup();
	}
	
	
	/**
	 * This method handles the photo metadata window when the data is a list of photos and not a single photo.
	 * The toggle variable signfies state. 0 means not set up. 1 means the window is closed and must be opened. 2 means the window
	 * is open and must be closed.
	 * 
	 * @author Andrew Yoon
	 */
	public void slideShowSetup(){
		
		//check if this is the first time opening the window
		if(this.first == true){
				
			//if so... set up all javafx elements but do not show
				Label title = new Label("Photo Metadata : ");
				title.setFont(new Font(20));
				
				//these are labels that hold the photo information
				photoName = new Label("Name : " + photoList.get(currentPhoto).getName());
				photoCaption = new Label("Caption : " + photoList.get(currentPhoto).getCaption());
				photoDate = new Label("Date Taken : " + photoList.get(currentPhoto).getDate());
				
				photoName.setFont(new Font(20));
				photoCaption.setFont(new Font(20));
				photoDate.setFont(new Font(20));

				ScrollPane root = new ScrollPane();

				//vbox holds all the labels
				info = new VBox();
				
				info.getChildren().add(title);
				info.getChildren().add(new Label());
				info.getChildren().add(photoName);
				info.getChildren().add(photoCaption);
				info.getChildren().add(photoDate);
				info.getChildren().add(new Label());
				
				//get all the tags of photo
				tagList = new VBox();
				Tagtitle = new Label("Tags : ");
				tagList.getChildren().add(Tagtitle);
				Tagtitle.setFont(new Font(20));
				
				for(Tags tags : photoList.get(currentPhoto).getTags()){
					Label tag = new Label(tags.toString());
					tag.setFont(new Font(20));
					tagList.getChildren().add(tag);
				}
				
				info.getChildren().add(tagList);

				
				root.setContent(info);
			    root.setStyle(
			    		"-fx-background-color: rgba(16,16,16,.5);" +
			    		"-fx-background: rgba(16,16,16,.5);" +
			    		"-fx-color: rgba(16,16,16,.5)"
			                );
			    
		
			    infoscene = new Scene(root, 400, 850, Color.BLACK);
			    infoscene.setFill(Color.TRANSPARENT);
			    infoPopUp.initStyle(StageStyle.TRANSPARENT);
			    infoPopUp.setScene(infoscene);
			    viewDetailsButton.setText("VIEW PHOTO DETAILS");
			    
			    toggle = 1;

			    //change first to false
			    this.first = false;
		}else{
			
			//from the second time on... use the toggle variable to show and hide
			if(toggle == 0){
				
				//these are labels that hold the photo information
				photoName.setText("Name : " + photoList.get(currentPhoto).getName());
				photoCaption.setText("Caption : " + photoList.get(currentPhoto).getCaption());
				photoDate.setText("Date Taken : " + photoList.get(currentPhoto).getDate());
				
				tagList.getChildren().clear();
				
				tagList.getChildren().add(Tagtitle);
				
				for(Tags tags : photoList.get(currentPhoto).getTags()){
					Label tag = new Label(tags.toString());
					tag.setFont(new Font(20));
					tagList.getChildren().add(tag);
				}
				
				toggle = 1;
				
			}else if(toggle == 1){	
			
				infoPopUp.show();
				viewDetailsButton.setText("HIDE PHOTO DETAILS");
				toggle = 2;
			}else if (toggle == 2){
				infoPopUp.close();
				viewDetailsButton.setText("VIEW PHOTO DETAILS");
				toggle = 1;
			}
		}
	}
	
	/**
	 * When the previous button is clicked. CurrentPhoto--'s photo is displayed.
	 * @author Andrew Yoon
	 */
	@FXML
	public void prevHandler(){
		
		// if we are at front of array, looop back to the last photo
		if(currentPhoto == 0 ){
			currentPhoto = photoList.size() - 1;
		}else{
			currentPhoto--;
		}
		
		infoPopUp.close();
		toggle = 0;
		populateDialog();

	}
	
	/**
	 * This method launches a dialog taht has all the photo information in it.
	 * The toggle variable signfies state. 0 means not set up. 1 means the window is closed and must be opened. 2 means the window
	 * is open and must be closed.
	 * @author Andrew Yoon
	 */
	@FXML
	public void viewDetailsHandler(){

		//first check if its slide show or not
		if(slideshow == false){
			
			//if not it is a single photo so use the toggle variable to show and hide
			if( toggle == 0){
				
				Label title = new Label("Photo Metadata : ");
				title.setFont(new Font(20));
				
				//these are labels that hold the photo information
				photoName = new Label("Name : " + this.showPhoto.getName());
				photoCaption = new Label("Caption : " + this.showPhoto.getCaption());
				photoDate = new Label("Date Taken : " + this.showPhoto.getDate());
				
				photoName.setFont(new Font(20));
				photoCaption.setFont(new Font(20));
				photoDate.setFont(new Font(20));
	
				ScrollPane root = new ScrollPane();
	
				//vbox holds all the labels
				info = new VBox();
				
				info.getChildren().add(title);
				info.getChildren().add(new Label());
				info.getChildren().add(photoName);
				info.getChildren().add(photoCaption);
				info.getChildren().add(photoDate);
				info.getChildren().add(new Label());
				
				//get all the tags of photo
				tagList = new VBox();
				Tagtitle = new Label("Tags : ");
				tagList.getChildren().add(Tagtitle);
				Tagtitle.setFont(new Font(20));
				
				for(Tags tags : this.showPhoto.getTags()){
					Label tag = new Label(tags.toString());
					tag.setFont(new Font(20));
					tagList.getChildren().add(tag);
				}
				
				info.getChildren().add(tagList);
	
				
				root.setContent(info);
			    root.setStyle(
			    		"-fx-background-color: rgba(16,16,16,.5);" +
			    		"-fx-background: rgba(16,16,16,.5);" +
			    		"-fx-color: rgba(16,16,16,.5)"
			                );
			    
		
			    infoscene = new Scene(root, 400, 850, Color.BLACK);
			    infoscene.setFill(Color.TRANSPARENT);
			    infoPopUp.initStyle(StageStyle.TRANSPARENT);
			    infoPopUp.setScene(infoscene);
			    infoPopUp.show();
			    viewDetailsButton.setText("HIDE PHOTO DETAILS");
			    
			    toggle = 2;
			}else if(toggle == 1){
	
				infoPopUp.show();
				viewDetailsButton.setText("HIDE PHOTO DETAILS");
				toggle = 2;
			}else if (toggle == 2){
				infoPopUp.close();
				viewDetailsButton.setText("VIEW PHOTO DETAILS");
				toggle = 1;
			}
		}else{
			//else let slideshowsetup do the work bc it isnt jsut a single photo
			slideShowSetup();
		}

	}
	
	/**
	 * This method is called from the caller method to close the photo's info pane. In case it wasnt closed by the user.
	 * 
	 * @author Andrew Yoon
	 */
	public void closeInfoPane(){
		infoPopUp.close();
	}
	
	
	/**
	 * When the next button is clicked. CurrentPhoto++'s photo is displayed.
	 * @author Andrew Yoon
	 */
	@FXML
	public void nextHandler(){
		
		// if we are at front of array, looop back to the last photo
		
		currentPhoto++;
		
		if(currentPhoto == photoList.size()){
			currentPhoto = 0;
		}
		
		infoPopUp.close();
		toggle = 0;
		populateDialog();
	}
}
