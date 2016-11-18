package model;

import java.io.Serializable;

/**
 * 
 * This class is an object that holds all tags with a key and value
 * 
 * @author Andrew Yoon
 *
 */
public class Tags implements Serializable{

	private static final long serialVersionUID = -878596573030680851L;
	private String key;
	private String val;
	
	/**
	 * 
	 * Constructor for tags.
	 * 
	 * @author Andrew Yoon
	 * @param key
	 * @param val
	 */
	public Tags(String key, String val){
		this.key = key;
		this.val = val;
	}
	
	/**
	 * 
	 * Get the type of tag (key).
	 * 
	 * @author Andrew Yoon
	 * @return
	 */
	public String getKey(){
		return this.key;
	}
	
	/**
	 * 
	 * Get the value of tag(val).
	 * 
	 * @author Andrew Yoon
	 * @return
	 */
	public String getVal(){
		return this.val;
	}
	
	@Override
	public String toString(){
		return "{" + this.key + "} : " + this.val;
	}
}
