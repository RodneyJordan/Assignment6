package session;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Creates a log entry
 * @author Rodney Jordan
 */
public class LogEntry implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	public LogEntry(String date, String description) {
		this.setDateFromString(date);
		this.description = description;
	}
	
	public LogEntry(String description) {
		this.date = new Date();
		this.description = description;
	}
	
	public LogEntry() {
		
	}
	
	/**
	 * Gets the date of and entry
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Gets the date as a String
	 */
	public String getDateString() {
		SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	    System.out.println(form.format(this.date));
	    String str = form.format(this.date);
	    return str;
	}
	
	/**
	 * Sets the date for this entry
	 */
	public void setDateFromString(String string) {
		//SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		try {
			this.date = format.parse(string);
			System.out.println(this.date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
