package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.User;

/**
 * Admin controller takes care of all the admin screen based actions.
 * 
 * @author Kevin Bundschuh
 *
 */
public class adminController {

	/**
	 * Java FX and collections elements that are used for management of all the users.
	 * 
	 * @param users
	 * @param userList
	 */
	public ObservableList<String> users;
	@FXML ListView<String> userList;
	@FXML TextField userAdd;
	
	/**
	 * Called by the login controller to set up all usernames , sort , and put into listview.
	 * 
	 * @author Kevin Bundschuh
	 * @param event
	 * @throws IOException
	 */
	public void adminPageSetup(ActionEvent event) throws IOException{
		
        //sets up users list
        readUsers();

        //sort the names of users
		 Collections.sort(users, new Comparator<String>(){
			 	@Override
			    public int compare(String s1, String s2) {
			 		
			 		return s1.compareToIgnoreCase(s2);
			    }
		 });
		 
		 userList.setPlaceholder(new Label("No Content In List"));
		 userList.setItems(users);

		 // select the first item and save the name to selectedSong
		 userList.getSelectionModel().select(0);
		 
		 userAdd.setText("Add a new User...");

	}
	
	
	/**
	 * 
	 * Reads serialized user data and sets up the user list.
	 * 
	 * @author Kevin Bundschuh
	 * @throws ClassNotFoundException 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void readUsers(){
		
		users = FXCollections.observableArrayList();
		
		//de-serialize the data and populate users list
		
		File file = new File("data/users.txt");
		
		if(file.exists()){
			try {
		         FileInputStream fileIn = new FileInputStream(file);
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         ArrayList<User> deserArr = (ArrayList<User>) in.readObject();
		         in.close();
		         fileIn.close();
		         
		         int count = 0;
		 		
		 		while(count < deserArr.size()){
		 			users.add(deserArr.get(count).getName());
		 			count++;
		 		}
		 		
		      }catch(IOException | ClassNotFoundException i) {
		         i.printStackTrace();
		         return;
		      }
		}
        
	}
	
	
	/**
	 * 
	 * This is the handler for the delete button.
	 * Function gets currently selected user and delete the user.
	 * 
	 * @author kevin Bundschuh
	 * 
	 */
	@FXML 
	public void deleteUserHandler(){
		
		ObservableList<String> tmpList = userList.getItems();
    	
    	//allows a delete if the size of the list is not 0
    	if(tmpList.size() != 0){
        
	    	//this gets the index of the currently selected item and removes it
	        int index = tmpList.indexOf(userList.getSelectionModel().getSelectedItem());
	        users.remove(index);
	        
	      //Where you serialize the data
	      //creates an array of strings with all names so that it can be serialized
			try {
				FileOutputStream outfile = new FileOutputStream("data/users.txt");
				ObjectOutputStream outStream = new ObjectOutputStream(outfile);
				
				int count = 0;
				ArrayList<User> serList = new ArrayList<>();

				//iterate through userList and add to serArray in order to serialize and save state.
				while(count < users.size()){
					
					User tmpUser = new User(users.get(count));
					serList.add(tmpUser);
					count++;
				}
				
				outStream.writeObject(serList);
				
				outfile.close();
				outStream.close();
				
				Alert alert = new Alert(AlertType.INFORMATION);
   				alert.setTitle("Success!");
   				alert.setHeaderText("Delete was successfull!");
   				alert.setContentText("User was deleted!");
   				alert.showAndWait();
   				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
			
    	}else{
    		userList.setPlaceholder(new Label("No Songs To Display!"));
    	}
	}
	
	/**
	 * Called by the add user button. 
	 * Checks uniqueness of username, adds to list, and serializes the new data in model.
	 * 
	 * @author Kevin Bundschuh
	 */
	@FXML
	public void addUserButtonHandler(){
		
		String add = userAdd.getText();
		
    	if (!add.equals("")){
    		
    		//check if username exists
   		 	if(users.contains(add)){
   		 		
   		 		// Show the error message.
   				Alert alert = new Alert(AlertType.ERROR);
   				alert.setTitle("Error");
   				alert.setHeaderText("Add Unsuccessfull.");
   				alert.setContentText("Username already exists");
   				alert.showAndWait();
   				
   				userAdd.setText("Add a new User...");
   		 	}else{
    		
	    		//no error messages generated, OK to add to objlist
	    		users.add(add); 		
	    		
	    		 //sort the names of users
	   		 	Collections.sort(users, new Comparator<String>(){
	   			 	@Override
	   			    public int compare(String s1, String s2) {
	   			 		
	   			 		return s1.compareToIgnoreCase(s2);
	   			    }
	   		 	});
	    		
	   		 	
	    		userList.getSelectionModel().select(add);
	    			        	
	        	// Show the confirmation message.
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Add Complete");
				alert.setHeaderText("Add Complete");
				alert.setContentText("Add was Successfull.");
				alert.showAndWait();
		      
				userAdd.setText("Add a new User...");
				
				//creates an array of strings with all names so that it can be serialized
				try {
					FileOutputStream outfile = new FileOutputStream("data/users.txt");
					ObjectOutputStream outStream = new ObjectOutputStream(outfile);
					
					int count = 0;
					ArrayList<User> serList = new ArrayList<>();
					
					//populate serList to save state
					while(count < users.size()){
						User newUser = new User(users.get(count));
						serList.add(newUser);
						count++;
					}
					
					outStream.writeObject(serList);
					
					outfile.close();
					outStream.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
   		 	}
   		 	
    	}else{
    		
    		// Show the confirmation message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not Add !");
			alert.setContentText("No text was passed!.");
			alert.showAndWait();
    		
    	}
	}
	
	
	/**
	 * On logout, switches scene to the main login scene.
	 * 
	 * @author Kevin Bundschuh
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void adminLogoutButtonHandler(ActionEvent event) throws IOException{
		
		Parent root;
		root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        stage.setScene(scene);
        stage.show();

	}
}
