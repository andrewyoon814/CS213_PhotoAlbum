package controller;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

/**
 * 
 * Main class where the application starts up.
 * Class itself does not do any work.
 * 
 * @author Andrew Yoon
 * @author Kevin Bundshcuh
 *
 */
public class PhotoAlbum extends Application {

	private Stage primaryStage;
	private AnchorPane root;
	private ArrayList<User> db;

	/**
	 * On startup calls the login controller after setting up the stage and scene.
	 * 
	 * @author Kevin Bundschuh
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//read the input file
			File file = new File("data/users.txt");
			
			if(file.exists()){
				try {
			         FileInputStream fileIn = new FileInputStream(file);
			         ObjectInputStream in = new ObjectInputStream(fileIn);
			         db = (ArrayList<User>) in.readObject();
			         in.close();
			         fileIn.close();
			         
			 		
			      }catch(IOException | ClassNotFoundException i) {
			         i.printStackTrace();
			         return;
			      }
			}
			
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("A-Kassa");
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/login.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);
			
			LoginController loginController = loader.getController();
			loginController.start(this, this.db);
			
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}