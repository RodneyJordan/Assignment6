package models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import session.LogEntry;

@SuppressWarnings("serial")
public class ItemLogTableModel extends AbstractTableModel {
	
	/**
	 * Column Names
	 */
	private String[] columnNames = {"Date", "Description"};
	
	/**
	 * ArrayList to display
	 */
	private ArrayList<LogEntry> logEntries;
	
	/**
	 * Constructor
	 */
	public ItemLogTableModel(ArrayList<LogEntry> logEntries) {
		this.logEntries = logEntries;
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
    
    

}
