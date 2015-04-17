package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Model for the detailed template view
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
@SuppressWarnings("serial")
public class DetailTemplateModel extends AbstractTableModel {
	
	/**
	 * The parts model
	 */
	private ProductTemplatePartsModel templatePartsModel;
	
	/**
	 * The List of part id's
	 */
	private ArrayList<Integer> partsIds = new ArrayList<Integer>();
	private ArrayList<Integer> quantity = new ArrayList<Integer>();
	/**
	 * List of parts associated with a template
	 */
	ArrayList<Part> parts = new ArrayList<Part>();
	
	/**
	 * Template id
	 */
	private int templateId;
	
	/**
	 * the parts model
	 */
	private PartsModel partsModel;
	
	/**
	 * Column names for the table
	 */
	private String[] columnNames = {"Id", "Part Number", "Part Name", "Quantity"};
	
	/**
	 * A list of observers
	 */
	private List<TableObserver> observers = new ArrayList<TableObserver>();
	
	/**
	 * used for iteration through for loops
	 */
	private int i;
	private int j;
	
	/**
	 * Constructor
	 */
	public DetailTemplateModel(int templateId, ProductTemplatePartsModel templatePartsModel, PartsModel partsModel, ArrayList<Integer> partsIds, ArrayList<Integer> quantity) {
		this.partsIds = partsIds;
		this.quantity = quantity;
		this.partsModel = partsModel;
		this.templatePartsModel = templatePartsModel;
		this.templateId = templateId;
		buildList();
	}
	
	public void buildList() {
		parts.clear();
		for(i = 0; i < templatePartsModel.getList().size(); i++) {
			if(templateId == templatePartsModel.getList().get(i).getProductTemplateId()) {
				for(j = 0; j < partsModel.getList().size(); j++) {
					if(partsModel.getList().get(j).getIdNumber() == templatePartsModel.getList().get(i).getPartId()) {
						parts.add(partsModel.getPart(j));
					}
				}
			}
		}
			quantity.clear();
			quantity = templatePartsModel.getQuantityListForTemplateId(templateId);
	}
	
	public ArrayList<Part> getParts()
	{
		return this.parts;
	}
	
	public void updateList() {
		buildList();
		update();
	}
	
	public int getPartId(int index) {
		return parts.get(index).getIdNumber();
	}
	
	public void removeQuantity(int index) {
		quantity.remove(index);
	}
	
	/**
	 * Gets the column count of the current detail template model
	 * @return int representing the number of columns
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	/**
	 * Gets the row count of the parts
	 * @return int representing the number of rows in the parts list
	 */
	@Override
	public int getRowCount() {
		return this.parts.size();
	}
	
	/**
	 * Get the value at the given row and column
	 * @param row
	 * @param col : column
	 * @return object located at the given row and column
	 */
	@Override
	public Object getValueAt(int row, int col) {
		Part j = parts.get(row);
		if(col == 0) {
			return j.getIdNumber();
		} else if (col == 1) {
			return j.getPartNumber();
		} else if(col == 2) {
			return j.getPartName();
		} else if(col == 3) {
			if(quantity.size() > row) {
				return quantity.get(row);
			}
			return null;
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
	
	/**
	 * Registers a table observer
	 * @param observer
	 */
	public void registerObserver(TableObserver observer) {
		observers.add(observer);
	}
	
	/**
	 * Update observer
	 */
	public void update() {
		for (TableObserver observer : observers) {
			observer.update();
		}
	}
}
