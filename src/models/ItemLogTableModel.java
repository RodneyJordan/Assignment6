package models;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.naming.InitialContext;
import javax.swing.table.AbstractTableModel;

import session.ItemLogGatewayBeanRemote;
import session.LogEntry;
import session.LogObserver;
import session.LogObserverRemote;

@SuppressWarnings("serial")
public class ItemLogTableModel extends AbstractTableModel {
	
	/**
	 * Column Names
	 */
	private String[] columnNames = {"Date", "Description"};
	
	/**
	 * ArrayList to display
	 */
	private ArrayList<LogEntry> logEntries = new ArrayList<LogEntry>();
	
	/**
     * A list of log view observers 
     */
    private List<LogViewObserver> logViewObservers = new ArrayList<LogViewObserver>();
    
    ItemLogGatewayBeanRemote gatewayRemote;
    
    LogObserver logObserver;
    
    LogViewObserver logView;
    
	/**
     * Random number generator 
     */
    static Random r = new Random();
	
	/**
	 * Constructor
	 */
	public ItemLogTableModel(ArrayList<LogEntry> logEntries, InventoryModel inventoryModel) {
		this.logEntries = logEntries;
		initSession();
		try {
			this.logObserver = new LogObserver(this);
			gatewayRemote.registerObserver(logObserver);
			Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
			System.out.println("Unregistering observer...");
			gatewayRemote.unregisterObserver(logObserver);
			}
			});
		} catch (RemoteException e){
			e.printStackTrace();
		}
	}
	

	@Override
	public int getRowCount() {
		return this.logEntries.size();
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LogEntry l = logEntries.get(rowIndex);
		if(columnIndex == 0) {
			return l.getDateString();
		}
		else if(columnIndex == 1) {
			return l.getDescription();
		}
		return null;
	}
	
	/**
     * Gets the column name at a given index in the array
     * @param index
     * @return String representing the name of a column at the given index
     */
    public String getColumnName(int index) {
        return columnNames[index];
    }
    
    public void setList(ArrayList<LogEntry> list) {
    	this.logEntries = sort(list);
    }
    
    /**
     * Sorts a list of Log Entries
     * @param list the list to sort
     * @return a sorted list
     */
    public static ArrayList<LogEntry> sort(ArrayList<LogEntry> list) {
        if (list.size() <= 1) 
            return list;
        int rotationplacement = r.nextInt(list.size());
        LogEntry rotation = list.get(rotationplacement);
        list.remove(rotationplacement);
        ArrayList<LogEntry> lower = new ArrayList<LogEntry>();
        ArrayList<LogEntry> higher = new ArrayList<LogEntry>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDate().before(rotation.getDate()) || list.get(i).getDate().equals(rotation.getDate())) {
            	lower.add(list.get(i));
            }
            else
                higher.add(list.get(i));
        }
        sort(lower);
        sort(higher);

        list.clear();
        list.addAll(lower);
        list.add(rotation);
        list.addAll(higher);
        return list;
    }
    
    /**
     * Updates the item log table model
     */
   public void updateItemLogTableModel() {
    	for(LogViewObserver observer : logViewObservers) {
    		observer.update(this);
    	}
    } 
    
    public void updateLogList(ArrayList<LogEntry> list) {
    	this.logEntries = sort(list);
    	//updateItemLogTableModel(this);
    }
    
    /**
     * Initializes the session with the ItemLogGatewayBean
     */
    public void initSession() {
		try {
			Properties props = new Properties();
			props.put("org.omg.COBRA.ORBInitialHost", "localhost");
			props.put("org.omg.COBRA.ORBInitialPort", 3700);
			
			InitialContext itx = new InitialContext(props);
			gatewayRemote = (ItemLogGatewayBeanRemote) itx.lookup("java:global/cs4743_session_bean/ItemLogGatewayBean!session.ItemLogGatewayBeanRemote");
			
		} catch(javax.naming.NamingException e1) {
			e1.printStackTrace();
		} 
		//updateTitle();
	}

}
