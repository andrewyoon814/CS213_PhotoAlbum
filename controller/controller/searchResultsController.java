package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

public class searchResultsController {
	
	private ArrayList<User> db;
	private User session;
	private Album album;
	private ArrayList<Photo> results;
	
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
	 * Gets the session object that reflects the  current state.
	 * @author Andrew Yoon
	 * @param db
	 */
	public User getSession(){
		return this.session;
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
	public void searchResultSetup(User session, ArrayList<Photo> results){
		
		mainPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		mainPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
		
		//Sets welcome bar of text.
		welcome.setText("Search Results!");
		welcome.setAlignment(Pos.CENTER);
		
		this.album = new Album("SearchAlbum", results);
		
		for(Photo photo : results){
			
			//Create an hbox that 
			HBox photoHBox = new HBox();
			photoHBox.setPadding(new Insets(15, 12, 15, 12));
			photoHBox.setSpacing(20);
			
			//create image view for image
			File file = new File(photo.getPath());
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
			
			photoHBox.getChildren().add(iView);
			
			//label with album name add to hbox
			Label photoName = new Label("Photo Name : \"" + photo.getName() + "\"");
			photoName.setMinWidth(250);
			photoName.setMaxWidth(250);
			photoName.setAlignment(Pos.CENTER);
			photoHBox.getChildren().add(photoName);
			
			//label with album name add to hbox
			Label photoCaption = new Label("Photo Name : \"" + photo.getName() + "\"");
			photoCaption.setMinWidth(250);
			photoCaption.setMaxWidth(250);
			photoCaption.setAlignment(Pos.CENTER);
			photoHBox.getChildren().add(photoCaption);
			
			photoVbox.getChildren().add(photoHBox);
			
		}
	}
	
	/**
	 * This method creates an album from the search results.
	 * 
	 * @author Andrew Yoon
	 * @param event
	 * @throws IOException 
	 */
	public void createAlbumHandler(ActionEvent event) throws IOException{
		
		String name = "";
		
		TextInputDialog dialog = new TextInputDialog("Please Enter Album Name :");
		dialog.setTitle("Set Album Name.");
		dialog.setHeaderText("Set Search Result Album Name.");
		dialog.setContentText("Please enter a name for the album:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().equals("Please Enter Album Name :")){
		    name = result.get();
		}else{
			name = "SearchResultAlbum" + session.getAlbums().size();
		}
		
		//remove session from db
		db.remove(session);
		
		//reset name
		album.setName(name);

		//add album to session
		session.addAlbum(album);
		
		//add session back to db
		db.add(session);
		
		//serialize the new data
		try {
			FileOutputStream outfile = new FileOutputStream("data/users.txt");
			ObjectOutputStream outStream = new ObjectOutputStream(outfile);
		
			//write the previously deserialized array
			outStream.writeObject(db);
			
			outfile.close();
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//set fxmlloader and redirect to home
		Parent root = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/home.fxml"));
		root = loader.load();
		homeController homeController = loader.getController();
		
		homeController.setDB(db);
		homeController.homeSetup(session);
		
		Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
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
       slideShowController.setUp(album);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
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
		
		//set this objects session variables
		setDB(db);
		setSession(session);
		
		//set the home controllers session variables
		homeController.setDB(db);
		homeController.homeSetup(session);
				
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
