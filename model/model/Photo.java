package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	
	public String getPath(){
		return this.path;
	}
	
	public String getCaption(){
		return this.caption;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public ArrayList<Tags> getTags(){
		return this.tags;
	}
	
	public void setCaption(String caption){
		this.caption = caption;
	}
	
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
	
	public void remTag(Tags tag){
		tags.remove(tag);
	}
}
