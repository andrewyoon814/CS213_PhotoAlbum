package controller;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Album;
import model.Photo;

public class slideShowController {
	
	@FXML ImageView pictureView;
	@FXML Label photoNumber;
	private ArrayList<Photo> photoList;
	private int currentPhoto;

	/**
	 * This method is called on startup and it sets up the slide show with the first element in the array of photos.
	 * 
	 * @author Andrew Yoon
	 * @param album
	 */
	public void setUp(Album album){
		
		//get the list of phootos to disply
		this.photoList = album.getPhotos();
		this.currentPhoto = 0;
		
		populateDialog();
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
		
		populateDialog();
		
	}
	
	/**
	 * When the previous button is clicked. CurrentPhoto++'s photo is displayed.
	 * @author Andrew Yoon
	 */
	@FXML
	public void viewDetailsHandler(){
		
	}
	
	@FXML
	public void nextHandler(){
		
		// if we are at front of array, looop back to the last photo
		
		currentPhoto++;
		
		if(currentPhoto == photoList.size()){
			currentPhoto = 0;
		}
			
		populateDialog();
	}
}
