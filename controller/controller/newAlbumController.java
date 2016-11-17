package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;

/**
 * This controller class contains all logic for the create a new album page.
 * 
 * @author Andrew Yoon
 *
 */
public class newAlbumController {
	
	@FXML ScrollPane photoPane;
	@FXML TilePane photoTile;
	@FXML TextField albumNameField;

	private ArrayList<User> db;
	private User session;
	private ArrayList<Photo> photos = new ArrayList<Photo>();

	/**
	 * Sets current sesssion variables.
	 * @param session
	 */
	public void setUp(User session, ArrayList<User> db){
		this.session = session;
		this.db = db;
		
		//main Tile pane setup;
		photoPane.setContent(photoTile);
		photoTile.setPadding(new Insets(15,15,15,15));
		
		photoPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		photoPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
		photoPane.setFitToWidth(true);
		
		photoTile.setHgap(25);
		photoTile.setVgap(40);
	}

	
	/**
	 * 
	 * On click of cancel button, simply redirects back to home page.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void cancelHandler(ActionEvent event) throws IOException{
		
		Parent root;
		FXMLLoader loader = new FXMLLoader();
		
		loader.setLocation(getClass().getResource("../view/home.fxml"));
		root = loader.load();
		homeController homeController = loader.getController();
		
		homeController.homeSetup(session);
		
		Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();
		
	}
	
	
	/**
	 * On request to handle photo, allows you to choose photo and adds photo thumbnail to screen.
	 * 
	 * @param event
	 * @throws IOException 
	 */
	@FXML public void addPhotoHandler(ActionEvent event) throws IOException{
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		//set up the file chooser
		FileChooser fileChooser = new FileChooser();
		File img = fileChooser.showOpenDialog(stage);
		
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
        dialogStage.setTitle("Edit Person");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        Scene scene = new Scene(root);
        dialogStage.setScene(scene);

        // Set the person into the controller.
       PhotoDialogController dialogController = loader.getController();
       dialogController.setDialogStage(dialogStage);
       dialogController.setDetails(newPhoto);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
        
        if(!dialogController.getDeleted()){
	        //get changes made by user
	        newPhoto = dialogController.getPhoto();
	        
	        //add to arraylist of photos
			photos.add(newPhoto);
	
			//create image from the filepath.
			Image image = new Image(img.toURI().toURL().toExternalForm());
			ImageView iView = new ImageView(image);
			iView.setFitHeight(300);
			iView.setFitWidth(300);
			iView.setPreserveRatio(true);
			
		
			photoTile.getChildren().add(iView);
        }

	}
	
	/**
	 * 
	 * No changes saved and simply ends session.
	 * 
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
	
	
	/**
	 * On click of create button, this method saves the newly changed data and serializes the data, and redirects to home
	 * 
	 * @author Andrew Yoon
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@FXML public void createHandler(ActionEvent event) throws IOException{
		
		boolean correctName = true;
		
		String albumName = albumNameField.getText();

		//check if album name is empty and force a name
		if(albumName.equals("")){
			
			correctName = false;
			//show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Creation Error");
			alert.setHeaderText("Missing Name.");
			alert.setContentText("Album does not have a name! Please make one!");
			alert.showAndWait();
		}
		
		//read in the serialized data and setup the userlist.
		//deserArr holds the newly deserialized data
		File file = new File("data/users.txt");
		ArrayList<User> deserArr = null;
		
		if(file.exists()){
			try {
		         FileInputStream fileIn = new FileInputStream(file);
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         deserArr = (ArrayList<User>) in.readObject();
		         in.close();
		         fileIn.close();
		         
		 		
		      }catch(IOException | ClassNotFoundException i) {
		         i.printStackTrace();
		         return;
		      }
		}
		
		int userCount = 0;
		
		//iterate through entire deserialized array
		while(userCount < deserArr.size()){
			
			User user = deserArr.get(userCount);
			
			//find user in deserialzed array whose name is equal to current sesssions user name
			if(user.getName().equals(session.getName())){
				
				int albumCount = 0;
				
				if(user.getAlbums().size() != 0){
					//iterate through this users albums
					while(albumCount < user.getAlbums().size()){
						Album tmpalbum = user.getAlbums().get(albumCount);
						
						//if there is such an album send popup telling user to sqitch the album name
						if(tmpalbum.getName().equals(albumName)){
							//show the error message.
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Creation Error");
							alert.setHeaderText("Duplicate Name.");
							alert.setContentText("Album with same name exists! Please make new one!");
							alert.showAndWait();
							
							correctName = false;
						}
						
						albumCount++;
					}
					
				}
			}
			
			userCount++;
		}
		
		//if we go here and correctName != false, no problems. Continue to serialize the data.
		
		if(correctName != false){
			Album album = new Album(albumName, photos);
			
			userCount = 0;
			
			//find current user and add the new album to the album list
			while(userCount < deserArr.size()){
				
				if(deserArr.get(userCount).getName().equals(session.getName())){
					deserArr.get(userCount).addAlbum(album);
					session = deserArr.get(userCount);
				}
				
				userCount++;
			}
			
			//serialize the new data
			try {
				FileOutputStream outfile = new FileOutputStream("data/users.txt");
				ObjectOutputStream outStream = new ObjectOutputStream(outfile);
			
				//write the previously deserialized array
				outStream.writeObject(deserArr);
				
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
			
			homeController.setDB(this.db);
			homeController.homeSetup(session);
			
			Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        
	        stage.setScene(scene);
	        stage.show();
		}
			
	
	}
}

