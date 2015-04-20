package models;

import java.util.Date;

/**
 * Creates a log entry
 * @author Rodney Jordan
 */
public class LogEntry {
	
	/**
	 * The date of the entry
	 */
	private Date date;
	
	/**
	 * The description of the entry
	 */
	private String description;
	
	/**
	 * Constructor
	 */
	public LogEntry(Date date, String description) {
		this.date = date;
		this.description = description;
	}
	
	/**
	 * Gets the date of and entry
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Sets the date for this entry
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	/**
	 * Gets the description for this entry
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Sets the description for this entry
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
