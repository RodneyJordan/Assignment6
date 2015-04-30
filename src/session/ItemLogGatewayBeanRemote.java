package session;

import java.util.ArrayList;

import javax.ejb.Remote;

@Remote
public interface ItemLogGatewayBeanRemote {
	
	public void createConnection();
	
	public void addLogEntry(int Id, LogEntry logEntry);
	
	public LogEntry getLogEntry(int Id, String date);
	
	public ArrayList<LogEntry> getLogEntries(int Id);
	
	public void registerObserver(LogObserverRemote o);
	
	public void unregisterObserver(LogObserverRemote o);
	
	public void setEditText(String txt);
}
