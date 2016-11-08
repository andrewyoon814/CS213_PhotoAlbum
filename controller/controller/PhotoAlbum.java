package controller;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

	/**
	 * On startup calls the login controller after setting up the stage and scene.
	 * 
	 * @author Kevin Bundschuh
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("A-Kassa");
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../view/login.fxml"));
			root = loader.load();
			Scene scene = new Scene(root);
			
			LoginController loginController = new LoginController();
			loginController.start(this);
			
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