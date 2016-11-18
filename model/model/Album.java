package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
	private String earliestDate;
	private String latestDate;
	public int photoNum = 0;
	
	/**
	 * 2 arg constructor for albums
	 * 
	 * @param name
	 * @param photos
	 */
	public Album(String name, ArrayList<Photo> photos){
		this.name = name;
		this.photos = photos;
		this.earliestDate = null;
		this.latestDate = null;
	}
	
	/**
	 * Sets the earlist photo by date and latest photo by date.
	 * 
	 * @author Andrew Yoon
	 * @throws ParseException 
	 */
	public void setDateRange() throws ParseException{
		
		int count = 0;
		
		//if there is only 1 photo set earliest date and latest date to same
		if(photos.size() == 1){
			this.earliestDate = photos.get(0).getDate();
			this.latestDate = photos.get(0).getDate();
			
			return;
		}
		
		Date early = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(photos.get(0).getDate());
		Date late = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(photos.get(1).getDate());
		
		Calendar earlyCal = Calendar.getInstance();
		earlyCal.setTime(early);
		earlyCal.set(Calendar.MILLISECOND,0);
		
		Calendar lateCal = Calendar.getInstance();
		lateCal.setTime(late);
		lateCal.set(Calendar.MILLISECOND,0);
		
		//if there are 2 photos, compare both and set appropritae
		if(photos.size() >= 2){
			
			
			if(earlyCal.compareTo(lateCal) == 0){
				this.earliestDate = photos.get(0).getDate();
				this.latestDate = photos.get(0).getDate();
			}else if(earlyCal.compareTo(lateCal) > 0){
				this.earliestDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(late);
				this.latestDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(early);
			}else{
				this.latestDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(late);
				this.earliestDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(early);
			}
			
			if(photos.size() == 2){
				return;
			}
			
		}
	
		
		//if bigger than 1 or 2 , compare all
		while(count < photos.size()){
			
			Date tmp = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(photos.get(count).getDate());
			
			Calendar tmpCal = Calendar.getInstance();
			tmpCal.setTime(tmp);
			tmpCal.set(Calendar.MILLISECOND,0);
			
			if(tmpCal.compareTo(earlyCal) < 0){
				this.earliestDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(tmp);
			}
			
			if(tmpCal.compareTo(lateCal) > 0){
				this.latestDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(tmp);
			}
			
			count++;
		}
	}
	
	/**
	 * Getter for the earliest photos date.
	 * @author Andrew Yoon
	 * @return
	 */
	public String getEarlyDate(){
			return this.earliestDate;

	}
	
	/**
	 * Getter for the latest photos date.
	 * @author Andrew Yoon
	 * @return
	 */
	public String getLateDate(){
		//return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());

		return this.latestDate;
	}
	
	/**
	 * Getter for the albums name.
	 * @author Andrew Yoon
	 * @return
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Getter for the list of photos in this album.
	 * @author Andrew Yoon
	 * @return
	 */
	public ArrayList<Photo> getPhotos(){
		return this.photos;
	}
	
	/**
	 * Adds a new photo to the photo list.
	 * @author Andrew Yoon
	 * @return
	 */
	public void addPhoto(Photo photo){
		this.photos.add(photo);
	}
	
	/**
	 * Removes photo from photo list.
	 * @author Andrew Yoon
	 * @return
	 */
	public void remPhoto(Photo photo){
		this.photos.remove(photo);
	}
	
	/**
	 * Setter for the album name.
	 * @author Andrew Yoon
	 * @param name
	 */
	public void setName(String name){
		
		this.name = name;
	}
	
	
	
}
