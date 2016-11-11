package model;

import java.io.Serializable;
import java.util.ArrayList;

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
	
	public void setname(String username){
		this.username = username;
	}
	
	public String getName(){
		return this.username;
	}
	public void addAlbum(Album album){
		albums.add(album);
	}
	
	public void remAlbum(Album album){
		albums.remove(album);
	}
	
	public ArrayList<Album> getAlbums(){
		return this.albums;
	}
}
