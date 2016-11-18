package model;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * 
 * Photo object that holds photo information.
 * 
 * @author Andrew Yoon
 *
 */
public class Photo implements Serializable{

	private static final long serialVersionUID = -5059700367775462787L;
	private String path;
	private String caption;
	private String name;
	private String date;
	private ArrayList<Tags> tags;
	
	/**
	 * 41arg Constructor for photo objects.
	 * 
	 * @author Andrew Yoon
	 */
	public Photo(String path, String date){
		
		this.path = path;
		this.date = date;
		this.caption = "";
		this.tags = new ArrayList<Tags>();
	}
	
	
	/**
	 * 4 arg Constructor for photo objects.
	 * 
	 * @author Andrew Yoon
	 */
	public Photo(String path, String caption, String name, String date){
		this.path = path;
		this.caption = caption;
		this.name = name;
		this.date = date;
		this.tags = new ArrayList<Tags>();
	}
	
	
	/**
	 * Getter for the file path for the photo.
	 * @author Andrew Yoon
	 * @return
	 */
	public String getPath(){
		return this.path;
	}
	
	/**
	 * Getter for the photo caption.
	 * 
	 * @author
	 * @return
	 */
	public String getCaption(){
		return this.caption;
	}
	
	/**
	 * Getter for the photo's name.
	 * @author Andrew Yoon
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Getter for the photo's date.
	 * @author Andrew Yoon
	 * @return
	 */
	public String getDate(){
		return this.date;
	}
	
	/**
	 * Gets the list of all tags related to this photo.
	 *@author Andrew Yoon 
	 * @return
	 */
	public ArrayList<Tags> getTags(){
		return this.tags;
	}
	
	/**
	 * Setter for the photo's caption.
	 * @author Andrew Yoon
	 * @return
	 */
	public void setCaption(String caption){
		this.caption = caption;
	}
	
	/**
	 * Setter for the photo's name.
	 * @author Andrew Yoon
	 * @return
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Adds tag to the arraylist
	 * 
	 * @author Andrew Yoon
	 * @param tag
	 */
	public void addTag(Tags tag){
		tags.add(tag);
	}
	
	/**
	 * Removes given tag from the arraylist.
	 * @author Andrew Yoon
	 * @return
	 */
	public void remTag(Tags tag){
		tags.remove(tag);
	}
}
