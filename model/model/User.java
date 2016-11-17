package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * User class is the class that holds all the albums ,photos, tags for a given user.
 * @author Andrew Yoon
 *
 */
public class User implements Serializable{


	private static final long serialVersionUID = 2190535018253653596L;
	private String username;
	private ArrayList<Album> albums;
	
	/**
	 * 1 arg constructor for user.
	 * @param username
	 */
	public User(String username){
		this.username = username;
		this.albums = new ArrayList<Album>();
	}
	
	/**
	 * This method sets name of the user.
	 * 
	 * @param username
	 */
	public void setname(String username){
		this.username = username;
	}
	
	/**
	 * Getter method for userName.
	 * 
	 * @return
	 */
	public String getName(){
		return this.username;
	}
	
	/**
	 * Adds an album to our album array list.
	 * 
	 * @param album
	 */
	public void addAlbum(Album album){
		albums.add(album);
	}
	
	/**
	 * Removes an album from album array list.
	 * 
	 * @param album
	 */
	public void remAlbum(Album album){
		albums.remove(album);
	}
	
	/**
	 * Called when an album is to be renamed.
	 * Checks for bad input or if there is already a name like that.
	 * 
	 * @param name
	 * @return
	 */
	public boolean albumNameCheck(String name){
		
		//null check
		if(name.equals("")){
			return false;
		}
		
		//duplicate name check
		int count = 0;
		while(count < this.albums.size()){
			
			if(this.albums.get(count).getName().equals(name)){
				return false;
			}
			
			count++;
		}
		
		
		return true;
	}
	
	/**
	 * Returns the albums arraylist for this user.
	 * @return
	 */
	public ArrayList<Album> getAlbums(){
		return this.albums;
	}
}
