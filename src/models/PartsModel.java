package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 * Parts model, this class keeps track of the current list of parts as well
 * as validating a newly created or edited part
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
@SuppressWarnings("serial")
public class PartsModel extends AbstractTableModel {
	
	/**
	 * The part
	 */
	private Part part;
	
	/**
	 * The form used for validation
	 */
	private ValidateForm validate;
	
	/**
	 * The inventory model
	 */
	private InventoryModel inventoryModel;
	
	/**
	 * The parts array list
	 */
	private ArrayList<Part> parts;
	
	/**
	 * The connection to the database
	 */
	private ConnectionGateway connectionGateway;
	
	/**
	 * Column names for the table
	 */
	private String[] columnNames = {"ID", "Part Number", "PartName", "Vendor", "Unit of Quantity", "External Part Number"};
	
	/**
	 * A list of observers
	 */
	private List<TableObserver> observers = new ArrayList<TableObserver>();
	
	/**
	 * A list of parts model observers
	 */
	private List<PartsModelObserver> partsModelObservers = new ArrayList<PartsModelObserver>();
	
	/**
	 * An error observer
	 */
	private PartErrorObserver errorObserver;
	
	/**
	 * used for iteration through for loops
	 */
	private int i;
	
	/**
	 * true if errors, false otherwise
	 */
	private boolean hasError;
	
	/**
	 * Constructor
	 */
	public PartsModel(InventoryModel inventoryModel, ConnectionGateway cg) {;
		this.inventoryModel = inventoryModel;
		this.connectionGateway = cg;
		this.validate = new ValidateForm();
		this.parts = connectionGateway.getParts();
		this.errorObserver = new PartErrorObserver();
		this.registerPartsModelObserver(errorObserver);
	}
	
	/**
	 * Adds a part to the current part list
	 * @param partNumber
	 * @param partName
	 * @param vendor
	 * @param unitOfQuantity
	 * @param externalPartNumber
	 * @return hasError
	 */
	public boolean addPart(String partNumber, String partName, String vendor, String unitOfQuantity, String externalPartNumber) {
		boolean sameName = createPart(partNumber, partName, vendor, unitOfQuantity, externalPartNumber);
		if(sameName) {
			return true;
		}
		if(!hasError) {
			connectionGateway.addPartToDatabase(part);
			parts = connectionGateway.getParts();
			System.out.println("part added");
		}
		updatePartsModelObserver();
		update();
		return hasError;
	}
	
	/**
	 * Gets the parts list
	 * @return an array list of the parts
	 */
	public ArrayList<Part> getList() {
		return this.parts;
	}
	
	/**
	 * Get part that has a specific id
	 * @param id
	 */
	public Part getPartAtId(int id) {
		Part partAtId;
		for(i = 0; i < parts.size(); i++) {
			if(parts.get(i).getIdNumber() == id) {
				partAtId = parts.get(i);
				return partAtId;
			}
		}
		return null;
	}
	
	/**
	 * Gets an array list of parts id number
	 */
	public ArrayList<Integer> getListOfPartsId() {
		ArrayList<Integer> partIds = new ArrayList<Integer>();
		for(i = 0; i < parts.size(); i++) {
			partIds.add(parts.get(i).getIdNumber());
		}
		return partIds;
	}
	
	/**
	 * Gets the row count of the parts list
	 * @return int representing the number of rows in the parts list
	 */
	@Override
	public int getRowCount() {
		return this.parts.size();
	}
	
	/**
	 * Gets the column count of the parts list
	 * @return int representing the number of columns in the parts list
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	/**
	 * Gets the value at the given row and column
	 * @param row
	 * @param col
	 * @return object located at the given row and column
	 */
	@Override
	public Object getValueAt(int row, int col) {
		Part p = parts.get(row);
		if(col ==  0) {
			return p.getIdNumber();
		} else if(col ==  1) {
			return p.getPartNumber();
		} else if(col == 2) {
			return p.getPartName();
		} else if(col == 3) {
			return p.getVendor();
		} else if(col == 4) {
			return p.getUnitOfQuantity();
		} else if(col == 5) {
			return p.getExternalPartNumber();
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
	 * Gets the part at a given index in the array list
	 * @param row : the index
	 * @return part at the given index in the array list
	 */
	public Part getPart(int row) {
		return parts.get(row);
	}
	
	/**
	 * Gets the part with a certain id
	 * @param id : the id of the part to get
	 * @return part : the part to return
	 */
	public Part getPartWithId(int id) {
		for(i = 0; i < parts.size(); i++) {
			if(id == parts.get(i).getIdNumber()) {
				return parts.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Removes part from the parts list
	 * @param row
	 */
	public void removePart(int row) {
		hasError = true;
		if(validate.isValidDeletePart(this.getPart(row), inventoryModel)) {
			connectionGateway.removePart(this.getPart(row));
			parts = connectionGateway.getParts();
			hasError = false;
		}
		updatePartsModelObserver();
		update();
		
	}
	
	/**
	 * Edits a part
	 * @param partNumber
	 * @param partName
	 * @param vendor
	 * @param unitOfQuantity
	 * @param externalPartNumber
	 * @return true if edited info is valid, false otherwise
	 */
	public boolean editPart(String partName, String vendor, String unitOfQuantity, String externalPartNumber, Part partToEdit) {
		String oldPartName = partToEdit.getPartName();
		for(i = 0; i < parts.size(); i++) {
        	if(partName.equalsIgnoreCase(parts.get(i).getPartName()) && oldPartName != parts.get(i).getPartName()) {
        		if(JOptionPane.showConfirmDialog(null, "This part has the same name as another part", "WARNING",
    					JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) {
    				System.out.println("you pushed ok");
    				return true;
    			}
        	}
        }
		hasError = validate.isValidEditPart(partName, vendor, unitOfQuantity, externalPartNumber);
		System.out.println(hasError);
		
		partToEdit.setPartName(partName);
		partToEdit.setVendor(vendor);
		partToEdit.setUnitOfQuantity(unitOfQuantity);
		partToEdit.setExternalPartNumber(externalPartNumber);
		connectionGateway.editPart(partToEdit);
		parts = connectionGateway.getParts();
		
		updatePartsModelObserver();
		update();
		
		return hasError;
	}
	
	/**
	 * Creates a new part
	 * @param partNumber
	 * @param partName
	 * @param vendor
	 * @param unitOfQuantity
	 * @param externalPartNumber
	 */
	private boolean createPart(String partNumber, String partName, String vendor, String unitOfQuantity, String externalPartNumber) {
		hasError = true;
		Part temp = new Part(partNumber, partName, vendor, unitOfQuantity, externalPartNumber);
        for(i = 0; i < parts.size(); i++) {
        	if(partName.equalsIgnoreCase(parts.get(i).getPartName())) {
        		if(JOptionPane.showConfirmDialog(null, "This part has the same name as another part", "WARNING",
    					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
    				System.out.println("you pushed ok");
    			} else {
    				
    				System.out.println("you pushed cancel");
    				return true;
        		}
        	}
        }
			
		if(validate.isValidPart(parts, temp)) {
			hasError = false;
			for(i = 0; i < parts.size(); i++) {
				if(temp.equals(parts.get(i))) {
					hasError = true;
					validate.addError("Part already exists");
					i = parts.size();
				}
			}
		}
		if(!hasError) {
			part = new Part(partNumber, partName, vendor, unitOfQuantity, externalPartNumber);
		}
		return false;
	}
	
	/**
	 * Registers a part model observer
	 * @param partsModelObserver
	 */
	public void registerPartsModelObserver(PartsModelObserver partsModelObserver) {
		partsModelObservers.add(partsModelObserver);
	}
	
	/**
	 * Registers a table observer
	 * @param observer
	 */
	public void registerObserver(TableObserver observer) {
		observers.add(observer);
	}
	
	/**
	 * updates observers
	 */
	public void update() {
		for (TableObserver observer : observers) {
			observer.update();
		}
	}
	
	/**
	 * Updates the parts model observer
	 */
	public void updatePartsModelObserver() {
		for (PartsModelObserver observer : partsModelObservers) {
			observer.update(this);
		}
	}
	
	/**
	 * Gets bool value of true if there are errors, false otherwise
	 * @return hasError
	 */
	public boolean getHasError() {
		return hasError;
	}
	
	/**
	 * Gets a string representing errors in a part
	 * @return String representing errors
	 */
	public String getErrors() {
		return validate.getErrors();
	}
}
