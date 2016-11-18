package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class albumViewController {
	
	private ArrayList<User> db;
	private User session;
	private Album album;
	private int tmpPhotoIndex;
	
	@FXML Label welcome;
	@FXML ScrollPane mainPane;
	@FXML VBox photoVbox;
	
	
	/**
	 * Sets the db object to current state.
	 * @author Andrew Yoon
	 * @param db
	 */
	public void setDB(ArrayList<User> db){
		this.db = db;
	}
	
	/**
	 * Gets the db object that reflects the  current state.
	 * @author Andrew Yoon
	 * @param db
	 */
	public ArrayList<User> getDB(){
		return this.db;
	}
	
	/**
	 * Sets the session variable to the current user's user object
	 * @param session
	 */
	public void setSession(User session){
		this.session = session;
	}
	
	/**
	 * Setter for this albumView instances album.
	 * 
	 * @author Andrew yoon
	 * @param album
	 */
	public void setAlbum(Album album){
		this.album = album;
	}
	
	/**
	 * Sets up the album view page. Session is the user, and albumCount is the album to be viewed.
	 * @author Andrew Yoon
	 * @param session
	 * @param albumCount
	 */
	public void albumViewSetup(User session, int albumCount){
		
		mainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mainPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
		
		setSession(session);
		setAlbum(session.getAlbums().get(albumCount));
		
		welcome.setText(session.getAlbums().get(albumCount).getName());
		welcome.setAlignment(Pos.CENTER);
		
		//sort the photos by alphabetical order
		 Collections.sort(this.album.getPhotos(), new Comparator<Photo>(){
			 	@Override
			    public int compare(Photo p1, Photo p2) {
			 		
			 		return p1.getName().compareToIgnoreCase(p2.getName());
			    }
		 });
		
		int count = 0;
		while(count < this.album.getPhotos().size()){
			
			//Create an hbox that 
			HBox photoHbox = new HBox();
			photoHbox.setPadding(new Insets(15, 12, 15, 12));
			photoHbox.setSpacing(20);
			
			//create image view for image
			File file = new File(this.album.getPhotos().get(count).getPath());
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
			
			photoHbox.getChildren().add(iView);
			
			//label with photo name	
			Label photoName = new Label("Photo Name : \"" + this.album.getPhotos().get(count).getName() + "\"");
			photoName.setMinWidth(225);
			photoName.setMaxWidth(225);
			photoHbox.getChildren().add(photoName);
			
			//label with photo caption 
			Label photoCaption = new Label();
			photoCaption.setMinWidth(225);
			photoCaption.setMaxWidth(225);
			
			if(this.album.getPhotos().get(count).getCaption().equals("")){
				photoCaption.setText("Caption : _No Caption_");
				photoHbox.getChildren().add(photoCaption);
			}else{
				photoCaption.setText("Caption : " +  this.album.getPhotos().get(count).getCaption());
				photoHbox.getChildren().add(photoCaption);
			}
			
			//This hbox will hold all buttons for this photo
			HBox buttonHbox = new HBox();
			
			tmpPhotoIndex = count;
			
			//Create a button for deleting this photo, set listener, and add to button hbox
			Button deletePhoto = new Button("DELETE PHOTO");

			deletePhoto.setOnAction(new EventHandler<ActionEvent>() {
				
				int photoCount = tmpPhotoIndex;
				ArrayList<User> tmpdb = db;
				
	            @Override
	            public void handle(ActionEvent event) {
	            	
	            	//if album has more than 1 photo
	            	if(album.getPhotos().size() > 1){
		            	Alert alert = new Alert(AlertType.CONFIRMATION);
		            	alert.setTitle("Delete Photo");
		            	alert.setContentText("Are you sure you want to delete : " + album.getPhotos().get(photoCount).getName() + " ?");
	
		            	Optional<ButtonType> result = alert.showAndWait();
		            	if (result.get() == ButtonType.OK){
			            	//remove the session from db object
			            	//and album from session
		            		tmpdb.remove(session);
			            	session.remAlbum(album);
			            	
			            	//make changes and delete photo from session
			            	album.getPhotos().remove(photoCount);
			            	
			            	//add back session to db object 
			            	session.addAlbum(album);
			            	tmpdb.add(session);
			            	
			            	//housekeeping to keep all vars up to date
			            	setDB(tmpdb);
			            	setSession(session);
			            	setAlbum(album);

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
	            			
	            			photoVbox.getChildren().clear();
	            			albumViewSetup(session, albumCount);
			         
		            	}
		            }else{
		            	//this is case if album only has one photo
		            	Alert alert = new Alert(AlertType.CONFIRMATION);
		            	alert.setTitle("Delete Photo");
		            	alert.setContentText("This album only has one photo. Albums cannot be empty. Deleting this photo will delete this album. Would you Like to Proceed?");
	
		            	Optional<ButtonType> result = alert.showAndWait();
		            	if (result.get() == ButtonType.OK){
		            		
		            		//remove session from db 
		            		tmpdb.remove(session);
		            		
		            		//remove album from the session
		            		session.getAlbums().remove(album);
		            		
		            		//add session back to db
		            		tmpdb.add(session);
		            		
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
	            			
	            			//redirect to home page since this album has been deleted
	            			Parent root = null;
	            			FXMLLoader loader = new FXMLLoader();
	            			loader.setLocation(getClass().getResource("../view/home.fxml"));
	            			try {
								root = loader.load();
							} catch (IOException e) {
								e.printStackTrace();
							}
	            			homeController homeController = loader.getController();
	            			
	            			homeController.homeSetup(session);
	            			homeController.setDB(tmpdb);
	            			
	            			Scene scene = new Scene(root);
	            	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            	        
	            	        stage.setScene(scene);
	            	        stage.show();

		            	}
		            }
	            }
	            
	        });
			
			buttonHbox.getChildren().add(deletePhoto);
			
			//Create a button for modifying this photo, set listener, and add to button hbox
			Button modifyPhoto = new Button("MODIFY PHOTO");

			modifyPhoto.setOnAction(new EventHandler<ActionEvent>() {
				
				int photoCount = tmpPhotoIndex;
				ArrayList<User> tmpdb = db;
				
	            @Override
	            public void handle(ActionEvent event) {
	            	
	            	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            	
	            	Parent root = null;
	    			FXMLLoader loader = new FXMLLoader();
	    			
	    			loader.setLocation(getClass().getResource("../view/photoDialog.fxml"));
	    			try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
	    	        
	    	        // Create the dialog Stage.
	    	        Stage dialogStage = new Stage();
	    	        dialogStage.setTitle("Add Photo Information");
	    	        dialogStage.initModality(Modality.WINDOW_MODAL);
	    	        dialogStage.initOwner(stage);
	    	        Scene scene = new Scene(root);
	    	        dialogStage.setScene(scene);
	    	
	    	        // Set the person into the controller.
	    	       PhotoDialogController dialogController = loader.getController();
	    	       dialogController.setDialogStage(dialogStage);
	    	       dialogController.setDetails(album.getPhotos().get(photoCount));
	    	       dialogController.setAlbums(album);
	    	       
	    	       if(album.getPhotos().get(photoCount).getTags().isEmpty()){
	    	    	   dialogController.setTags();
	    	       }else{
	    	    	   dialogController.setTags(album.getPhotos().get(photoCount).getTags());
	    	       }    	       
	
	    	
	    	        // Show the dialog and wait until the user closes it
	    	        dialogStage.showAndWait();
	    	        
	    	        if(!dialogController.getDeleted()){
	    	        	
	    	        	//remove old photo
	    	        	album.getPhotos().remove(photoCount);
	    	        	
	    		        //get changes made by user
	    		        Photo newPhoto = dialogController.getPhoto();

	    		        
	    		        //add newly changed photos to album, add album backto session, and session to tmpdb
	    		        tmpdb.remove(session);
	    		        session.remAlbum(album);
	    		        album.getPhotos().add(newPhoto);
	    		        session.addAlbum(album);
	    		        tmpdb.add(session);
	    		        
	    		        //housekeeping to keep all vars up to date
		            	setDB(tmpdb);
		            	setSession(session);
		            	setAlbum(album);

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
	    		        
	    		        //clear and populate the vbox
	    		        photoVbox.getChildren().clear();
	    		        albumViewSetup(session, session.getAlbums().indexOf(album));
	    	        }
	            
	            }
	            
	        });
			
			buttonHbox.getChildren().add(modifyPhoto);
			
			
			//Create a button for copying this photo, set listener, and add to button hbox
			Button copyPhoto = new Button("COPY PHOTO");

			copyPhoto.setOnAction(new EventHandler<ActionEvent>() {
				
				int photoCount = tmpPhotoIndex;
				
	            @Override
	            public void handle(ActionEvent event) {
	            	System.out.println("copy" + photoCount);
	            }
	            
	        });
			
			buttonHbox.getChildren().add(copyPhoto);
			
			
			//Create a button for moving this photo, set listener, and add to button hbox
			Button movePhoto = new Button("MOVE PHOTO");

			movePhoto.setOnAction(new EventHandler<ActionEvent>() {
				
				int photoCount = tmpPhotoIndex;
				
	            @Override
	            public void handle(ActionEvent event) {
	            	System.out.println("move" + photoCount);
	            }
	            
	        });
			
			buttonHbox.getChildren().add(movePhoto);
			buttonHbox.setAlignment(Pos.CENTER);
			
			//add button hbox into the photohbox
			photoHbox.getChildren().add(buttonHbox);
			photoHbox.setAlignment(Pos.CENTER);
			
			//add photohbox to photovbox
			photoVbox.getChildren().add(photoHbox);
			
			count++;
		}
		
		
	}
	
	/**
	 * When clicked, calls slide show dialog and passes all the photos to it. 
	 * @author Andrew Yoon
	 * @throws IOException 
	 */
	@FXML
	public void slideShowHandler(ActionEvent event) throws IOException{
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();		
		Parent root;
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(getClass().getResource("../view/slideShowDialog.fxml"));
		root = loader.load();
        
		// Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Photo Information");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

       // Set the person into the controller.
       slideShowController slideShowController = loader.getController();
       slideShowController.setUp(this.album);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
	}
	
	/**
	 * On request to handle photo, allows you to choose photo and adds photo thumbnail to screen.
	 * 
	 * @param event
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@FXML public void addPhotoHandler(ActionEvent event) throws IOException, ParseException{
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		//set up the file chooser
		FileChooser fileChooser = new FileChooser();
		File img = fileChooser.showOpenDialog(stage);
		
		if(img != null){
		
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			
			//create new photo and pop upu a dialog
			Photo newPhoto = new Photo(img.getAbsolutePath(), sdf.format(img.lastModified()));
			
			//create new stage to show dialog
			 // Load the fxml file and create a new stage for the popup dialog.
			Parent root;
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(getClass().getResource("../view/photoDialog.fxml"));
			root = loader.load();
	        
	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Add Photo Information");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(stage);
	        Scene scene = new Scene(root);
	        dialogStage.setScene(scene);
	
	        // Set the person into the controller.
	       PhotoDialogController dialogController = loader.getController();
	       dialogController.setDialogStage(dialogStage);
	       dialogController.setDetails(newPhoto);
	       dialogController.setAlbums(album);
	
	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();
	        
	        if(!dialogController.getDeleted()){
	        	
		        //get changes made by user
		        newPhoto = dialogController.getPhoto();
		        
		        //add to arraylist of photos
		        album.getPhotos().add(newPhoto);
		        album.setDateRange();
		        
		        photoVbox.getChildren().clear();
		        
		        albumViewSetup(session, session.getAlbums().indexOf(album));
	        }
        }

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
		homeController.setDB(db);
		
		Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
	}
	
	/**
	 * No changes saved and simply ends session and redirects to login page.
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void logoutHandler(ActionEvent event) throws IOException{
		
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

}
