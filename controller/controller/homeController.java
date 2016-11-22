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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.User;

public class homeController {
	
	@FXML ScrollPane mainPane;
	@FXML VBox albumVbox;
	@FXML Label welcome;
	private User session;
	private ArrayList<User> db;
	private int tmpAlbumIndex;
	
	/**
	 * Sets the db array in order to serialize at anytime.
	 * 
	 * @author Andrew Yoon
	 * @param db
	 */
	public void setDB(ArrayList<User> db){
		this.db = db;
	}
	
	
	/**
	 * This method recieves the user object and sets up the home page with the users information. 
	 * It also sets up the main pane with all the albums for this user
	 *
	 * @author Andrew Yoon
	 * 
	 * @param session
	 */
	public void homeSetup(User session){
		
		mainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mainPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
		
		//sets session state and db state
		this.session = session;	
		welcome.setText("Welcome " + session.getName() + "!");
		welcome.setAlignment(Pos.CENTER);
		
		//sort the albums by alphabetical order
		 Collections.sort(session.getAlbums(), new Comparator<Album>(){
			 	@Override
			    public int compare(Album a1, Album a2) {
			 		
			 		return a1.getName().compareToIgnoreCase(a2.getName());
			    }
		 });
		 
		//if there are no albums add a label saying so
		if(session.getAlbums().size() == 0){
			
			Label noAlbumLabel = new Label("Currently No Albums!");
			albumVbox.getChildren().add(noAlbumLabel);
			albumVbox.setAlignment(Pos.CENTER);
		}else{
			albumVbox.getChildren().clear();
		}
		
		//iterate through all albums
		int albumCount = 0;
		while(albumCount < session.getAlbums().size()){
			
			try{
			session.getAlbums().get(albumCount).setDateRange();
			}catch(ParseException e){
				e.printStackTrace();
			}
			
			//Create an hbox that 
			HBox albumHbox = new HBox();
			albumHbox.setPadding(new Insets(15, 12, 15, 12));
			albumHbox.setSpacing(20);
			
			//create image view for image
			File file = new File(session.getAlbums().get(albumCount).getPhotos().get(0).getPath());
			Image img;
			ImageView iView = new ImageView();
			
			try {
			
				img = new Image(file.toURI().toURL().toExternalForm());
				iView.setImage(img);
				
			} catch (MalformedURLException e) {

				e.printStackTrace();
			}
			
			iView.setFitHeight(200);
			iView.setFitWidth(200);
			iView.setPreserveRatio(true);
			
			albumHbox.getChildren().add(iView);
			
			//label with album name add to hbox
			Label albumName = new Label("Album Name : \"" + session.getAlbums().get(albumCount).getName() + "\"");
			albumName.setMinWidth(250);
			albumName.setMaxWidth(250);
			albumName.setAlignment(Pos.CENTER);
			albumHbox.getChildren().add(albumName);
			
			//label with number of photos in album
			albumHbox.getChildren().add(new Label("# of Photos : " + (session.getAlbums().get(albumCount).getPhotos().size() )));
			
			//show date range of photos
			VBox dateBox = new VBox();
			dateBox.getChildren().add(new Label("Range :" + session.getAlbums().get(albumCount).getEarlyDate()));
			dateBox.getChildren().add(new Label("--> " + session.getAlbums().get(albumCount).getLateDate()));
			dateBox.setAlignment(Pos.CENTER);
			albumHbox.getChildren().add(dateBox);
			
			
			//put album buttons together in a vbox
			VBox buttonVbox = new VBox();
			
			//Create a button for renaming the album and set up listener for this button
			Button renameAlbum = new Button("RENAME ALBUM");
			renameAlbum.setMaxWidth(Double.MAX_VALUE);
		
			tmpAlbumIndex = albumCount;
			renameAlbum.setOnAction(new EventHandler<ActionEvent>() {
				
				//gets current index that points to album and db arrray
				//save these variables so we can use inside of handler
				int albumCount = tmpAlbumIndex;
				ArrayList<User> tmpdb = db;
				
	            @Override
	            public void handle(ActionEvent event) {

	            	TextInputDialog dialog = new TextInputDialog();
	            	dialog.setTitle("Rename an Album");
	            	dialog.setHeaderText("Change album name from \"" + session.getAlbums().get(albumCount).getName() + "\" to" );
	            	dialog.setContentText("Please enter the new album name :");

	            	// Traditional way to get the response value.
	            	Optional<String> result = dialog.showAndWait();
	            	if (result.isPresent()){
	            		
	            		//check if new name is a valid entry
	            		if(session.albumNameCheck(result.get())){
	            			
	            			//remove the user
	            			tmpdb.remove(session);
	            			
	            			//make changes
	            			session.getAlbums().get(albumCount).setName(result.get());
	            			albumVbox.getChildren().clear();
	            			
	            			//add back to db
	            			tmpdb.add(session);
	            			setDB(tmpdb);

	            			homeSetup(session);
	            			
	            			//serialize the new data
	            			try {
	            				FileOutputStream outfile = new FileOutputStream("data/users.txt");
	            				ObjectOutputStream outStream = new ObjectOutputStream(outfile);
	            			
	            				//write the previously deserialized array
	            				outStream.writeObject(tmpdb);
	            				
	            				outfile.close();
	            				outStream.close();
	            			} catch (FileNotFoundException e) {
	            				e.printStackTrace();
	            			} catch (IOException e) {
	            				e.printStackTrace();
	            			}
	            		}else{
	            			Alert alert = new Alert(AlertType.ERROR);
	        				alert.setTitle("Album Rename Error");
	        				alert.setHeaderText("Something went wrong in the rename process.");
	        				alert.setContentText("Either you did not enter a name OR that album name is already in use!");
	        				alert.showAndWait();
	            		}
	            	}
	            	
	            }
	            
	        });
			
			//Create a button for deleting the album and set up listener for this button
			Button deleteAlbum = new Button("DELETE ALBUM");
			deleteAlbum.setMaxWidth(Double.MAX_VALUE);
			
			deleteAlbum.setOnAction(new EventHandler<ActionEvent>() {
				
				//gets current index that points to album and db arrray
				//save these variables so we can use inside of handler
				int albumCount = tmpAlbumIndex;
				ArrayList<User> tmpdb = db;
				
	            @Override
	            public void handle(ActionEvent event) {
	             
	            	Alert alert = new Alert(AlertType.CONFIRMATION);
	            	alert.setTitle("Delete Album");
	            	alert.setContentText("Are you sure you want to delete : " + session.getAlbums().get(albumCount).getName() + " ?");

	            	Optional<ButtonType> result = alert.showAndWait();
	            	if (result.get() == ButtonType.OK){
	            	    
	            		//removes the session from master db, removes album, adds session back to db
	            		tmpdb.remove(session);
	            		session.getAlbums().remove(albumCount);
	            	    tmpdb.add(session);
	            	    
	            	    albumVbox.getChildren().clear();
	            	    
	            	    //resets the local variables according to changes
	            	    setDB(tmpdb);
            			homeSetup(session);
            			
            			//serialize the new data
            			try {
            				FileOutputStream outfile = new FileOutputStream("data/users.txt");
            				ObjectOutputStream outStream = new ObjectOutputStream(outfile);
            			
            				//write the previously deserialized array
            				outStream.writeObject(tmpdb);
            				
            				outfile.close();
            				outStream.close();
            			} catch (FileNotFoundException e) {
            				e.printStackTrace();
            			} catch (IOException e) {
            				e.printStackTrace();
            			}
	            	
	            	} 
	            }
	        });
			
			//Create a button for viewing the album and set up listener for this button
			Button viewAlbum = new Button("VIEW ALBUM");
			viewAlbum.setMaxWidth(Double.MAX_VALUE);
			
			viewAlbum.setOnAction(new EventHandler<ActionEvent>() {
				
				int albumCount = tmpAlbumIndex;
				
	            @Override
	            public void handle(ActionEvent event) {
	                
	            	Parent root = null;
	        		FXMLLoader loader = new FXMLLoader();
	        		
	        		loader.setLocation(getClass().getResource("../view/albumView.fxml"));
	        		
	        		try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
	        		albumViewController albumViewController = loader.getController();
	        		
	        		Scene scene = new Scene(root);
	                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

	                stage.setScene(scene);
	                stage.show();
	                
	                albumViewController.setDB(db);
	        		albumViewController.albumViewSetup(session, albumCount);
	                
	                //resets the local variables according to changes
            	    setDB(albumViewController.getDB());
        			homeSetup(albumViewController.getSession());
	                
	            }
	            
	        });
			
			
			//addboth buttons to button vbox
			buttonVbox.getChildren().add(renameAlbum);
			buttonVbox.getChildren().add(deleteAlbum);
			buttonVbox.getChildren().add(viewAlbum);
			buttonVbox.setAlignment(Pos.CENTER);
			
			//add button vbox to album container hbox
			albumHbox.getChildren().add(buttonVbox);
			
			albumHbox.setAlignment(Pos.CENTER);
			
			//add album container into album pane
			albumVbox.getChildren().add(albumHbox);
			
			albumCount++;
		}

	
	}
	
	/**
	 * Goes to new album page to create a new album.
	 * 
	 * @author Kevin Bundschuh
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void createHandler(ActionEvent event) throws IOException{
		
		Parent root;
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(getClass().getResource("../view/newAlbum.fxml"));
		root = loader.load();
		
		newAlbumController newAlbumController = loader.getController();
		
		newAlbumController.setUp(session, this.db);
		
		Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
	}
	
	
	/**
	 * On logout, switches scene to the main login scene.
	 * 
	 * @author Kevin Bundschuh
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void homeLogoutButtonHandler(ActionEvent event) throws IOException{
		
		//set fxmlloader and redirect to home
		Parent root = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/login.fxml"));
		root = loader.load();
		LoginController loginController = loader.getController();
		
		loginController.setDB(db);
		
		Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();

	}
	
	@FXML 
	public void SearchButtonHandler(ActionEvent event) throws IOException{
		
		//set fxmlloader and redirect to home
		Parent root = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/searchPage.fxml"));
		root = loader.load();
		SearchController search = loader.getController();
		
		search.setUpSession(session);
		search.setDB(db);
		search.setUpTags();
		
		Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
	}
}
