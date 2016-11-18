package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

public class SearchController {
	
	private User session;
	private ArrayList<User> db;
	
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
}
