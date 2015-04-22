package models;

import java.util.ArrayList;
import java.util.Date;


public class InventoryItemLog {
	
	/**
	 * A log entry
	 */
	private LogEntry logEntry;
	
	/**
	 * A list of log entries
	 */
	ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
	
	/**
	 * Constructor
	 */
	public InventoryItemLog(Date date, String description) {
		logEntry = new LogEntry(date, description);
		entries.add(logEntry);
	}
	
	/**
	 * Add log entry to list
	 */
	public void addLogEntry(Date date, String description) {
		logEntry = new LogEntry(date, description);
		entries.add(logEntry);
	}
	
	/**
	 * Get log entry by date
	 */
	public LogEntry getLogEntryByDate(Date date) {
		logEntry = null;
		for(int i = 0; i < entries.size(); i++) {
			if(date.equals(entries.get(i).getDate())) {
				logEntry = entries.get(i);
				break;
			}
		}
		return logEntry;
	}

}
