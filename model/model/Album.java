package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * This album class represents an album.
 * 
 * @author Andrew Yoon
 *
 */
public class Album implements Serializable{

	private static final long serialVersionUID = -4143935150417416554L;
	private String name;
	private ArrayList<Photo> photos;
	
	/**
	 * 2 arg constructor for albums
	 * 
	 * @param name
	 * @param photos
	 */
	public Album(String name, ArrayList<Photo> photos){
		this.name = name;
		this.photos = photos;
	}
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Photo> getPhotos(){
		return this.photos;
	}
	
	public void addPhoto(Photo photo){
		this.photos.add(photo);
	}
	
	public void remPhoto(Photo photo){
		this.photos.remove(photo);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	
	
}
