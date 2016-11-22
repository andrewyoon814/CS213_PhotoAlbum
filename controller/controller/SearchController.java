package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.Tags;
import model.User;

public class SearchController {
	
	private User session;
	private ArrayList<User> db;
	private String from;
	private String to;
	
	@FXML ListView<String> tagList;
	@FXML DatePicker fromDatePicker;
	@FXML DatePicker toDatePicker;
	
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
		
		tagList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
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
	
	/**
	 * Handles the tag Search button.
	 * @author Andrew Yoon
	 */
	public void tagSearchButtonHandler(ActionEvent event){
		
		//get selected tags
		ObservableList<String> selTags = tagList.getSelectionModel().getSelectedItems();
		//will hold photos that match critera
		ArrayList<Photo> selPhotos = new ArrayList<Photo>();
		
		//go through all photos and select photos with matching tags
		for(Album album : session.getAlbums()){
			
			for(Photo photo : album.getPhotos()){
				
				for(Tags tag : photo.getTags()){
					
					if(selTags.contains(tag.getKey())){
						selPhotos.add(photo);
						break;
					}
				}
			}
		}
		
		if(selPhotos.size() != 0){
			//send results to the results page
			Parent root = null;
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("../view/searchResults.fxml"));
			
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			searchResultsController searchResultsController = loader.getController();
			
			searchResultsController.setDB(db);
			searchResultsController.setSession(session);
			
			Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	
	        stage.setScene(scene);
	        stage.show();
	        
	        searchResultsController.setDB(db);
	        searchResultsController.searchResultSetup(session, selPhotos);
		}else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Search Result");
			alert.setHeaderText("Nothing");
			alert.setContentText("No results were found related to search criteria.");
			alert.showAndWait();
		}
 
	}
	
	/**
	 * Handles the date Search button.
	 * @author Andrew Yoon
	 * @throws ParseException 
	 */
	public void dateSearchButtonHandler(ActionEvent event) throws ParseException{
		
		//date conversions and formatting
		LocalDate fromDate = fromDatePicker.getValue();
		LocalDate toDate = toDatePicker.getValue();
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		from = dateFormatter.format(fromDate);
		to = dateFormatter.format(toDate);
		
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(java.sql.Date.valueOf(fromDate));
		fromCal.set(Calendar.MILLISECOND,0);
		
		Calendar toCal = Calendar.getInstance();
		toCal.setTime(java.sql.Date.valueOf(toDate));
		toCal.set(Calendar.MILLISECOND,0);
		
		if(fromCal.compareTo(toCal) != -1){
			//show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Date Picking Error");
			alert.setHeaderText("Incorrect Date Choice.");
			alert.setContentText("The 'From' date must be earlier than that of the 'To' date.");
			alert.showAndWait();
			
			//reset the datepicker and dates
			fromDatePicker.setValue(LocalDate.now());
			from = null;
			toDatePicker.setValue(fromDatePicker.getValue().plusDays(1));
			to = null;
			
		}else{
			
			ArrayList<Photo> selPhotos = new ArrayList<Photo>();
			
			//go through all photos and select photos with matching tags
			for(Album album : session.getAlbums()){
				
				for(Photo photo : album.getPhotos()){
					
					Date tmp = new SimpleDateFormat("MM/dd/yyyy").parse(photo.getDate());
					
					Calendar tmpCal = Calendar.getInstance();
					tmpCal.setTime(tmp);
					tmpCal.set(Calendar.MILLISECOND,0);

					//if photo date is in between from and to.. add to result list
					if(fromCal.compareTo(tmpCal) == -1){
						if(toCal.compareTo(tmpCal) == 1)
							
						if(!selPhotos.contains(photo)){
							selPhotos.add(photo);
						}
					}
				}
			}
			
			if(selPhotos.size() != 0){
				//send results to the results page
				Parent root = null;
				FXMLLoader loader = new FXMLLoader();
				
				loader.setLocation(getClass().getResource("../view/searchResults.fxml"));
				
				try {
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				searchResultsController searchResultsController = loader.getController();
				
				searchResultsController.setDB(db);
				searchResultsController.setSession(session);
				
				Scene scene = new Scene(root);
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	
		        stage.setScene(scene);
		        stage.show();
		        
		        searchResultsController.setDB(db);
		        searchResultsController.searchResultSetup(session, selPhotos);
			}else{
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Search Result");
				alert.setHeaderText("Nothing");
				alert.setContentText("No results were found related to search criteria.");
				alert.showAndWait();
			}
			
		}	
		
	}
}
