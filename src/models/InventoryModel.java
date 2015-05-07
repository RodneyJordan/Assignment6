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

/**
 * Inventory model, this class keeps track of the current inventory as well as validating
 * any newly created items, and edited items.
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class InventoryModel extends AbstractTableModel {
	
    /**
     * The inventory item
     */
    private InventoryItem inventoryItem;

    /**
     * The form used for validation
     */
    private ValidateForm validate;
    
    /**
     * The connection gateway
     */
    private ItemConnectionGateway itemConnectionGateway;

    /**
     * The inventory array list
     */
    private ArrayList<InventoryItem> inventory;

    /**
     * Column names for the table
     */
    private String[] columnNames = {"ID", "Part/Product", "Location", "Quantity"};

    /**
     * A list of observers
     */
    private List<TableObserver> observers = new ArrayList<TableObserver>();

    /**
     * A list of inventory model observers
     */
    private List<InventoryModelObserver> inventoryModelObservers = new ArrayList<InventoryModelObserver>();
    
    /**
     * A list of log view observers 
     */
    private List<LogViewObserver> logViewObservers = new ArrayList<LogViewObserver>();
    
    /**
     * A list of logs
     */
    private ArrayList<LogEntry> logs = new ArrayList<LogEntry>();

    /**
     * An error observer
     */
    private ErrorObserver errorObserver;

    /**
     * used for iteration through for loops
     */
    private int i;
    private int j;
    
    /**
     * The item log table model
     */
    ItemLogTableModel logTableModel;
    
    /**
     * Random number generator 
     */
    static Random r = new Random();

    /**
     * true if errors, false otherwise
     */
    private boolean hasError;
    
    /**
     * product template parts model
     */
    private ProductTemplatePartsModel productTemplatePartsModel;
    
    LogObserver logObserver;
    
    ItemLogGatewayBeanRemote gatewayRemote;
    
    private int id;
    
    /**
     * The parts model
     */
    private PartsModel partsModel;
    
    /**
     * Constructor
     */
    public InventoryModel(ItemConnectionGateway icg) {
        this.validate = new ValidateForm();
        this.errorObserver = new ErrorObserver();
        this.itemConnectionGateway = icg;
        this.inventory = icg.getItem();
        this.registerInventoryModelObserver(errorObserver);
        this.logs = new ArrayList<LogEntry>();
        initSession();
      /* try {
			this.logObserver = new LogObserver(this);
			gatewayRemote.registerObserver(logObserver);
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
				System.out.println("Unregistering observer...");
				gatewayRemote.unregisterObserver(logObserver);
				}
				});
	
		} catch (RemoteException e) {
			e.printStackTrace();
		} */ 
    }
    
    /**
     * Creates an abstract model for the log view
     * @param list
     */
    public ItemLogTableModel modelLogTable(ArrayList<LogEntry> list) {
    	return this.logTableModel = new ItemLogTableModel(list, this);
    }
    
    public void setTableModel(ItemLogTableModel logModel) {
    	this.logTableModel = logModel;
    }

    /**
     * Adds an item to the current inventory model
     * @param partNumber
     * @param partName
     * @param vendor
     * @param quantity
     * @param unitType 
     * @return true if item has errors, false otherwise
     */

    public boolean addItemPart(Part part, String location, String quantity) {
    	createInventoryItem(part, location, quantity);
    	id = 0;
        if(!hasError) {
        	id = itemConnectionGateway.addItemToDatabase(inventoryItem, 0);
        	inventoryItem.setId(id);
        	inventory.add(inventoryItem); //= itemConnectionGateway.getItem();
        }
        updateInventoryModelObserver();
        update();
        if(!hasError) {
        	LogEntry entry = new LogEntry("added");
        	inventoryItem.addLogEntry(entry);
        	gatewayRemote.addLogEntry(id, entry);
        }
        
        return hasError;
    }
    
    public boolean addItemProduct(ProductTemplate product, String location, ProductTemplatePartsModel productTemplatePartsModel, PartsModel partsModel) {
    	boolean duplicateProduct = true;
    	id = 0;
    	this.productTemplatePartsModel = productTemplatePartsModel;
    	this.partsModel = partsModel;
    	createProductInventoryItem(product, location);
    	if(!hasError){
    		if(duplicateProduct) {
    			duplicateProduct = false;
    			for(i = 0; i < inventory.size(); i++) {
    				if(inventory.get(i).equals(inventoryItem)) {
    					duplicateProduct = true;
    					try {
							inventory.get(i).setQuantity(inventory.get(i).getQuantity() + 1);
							id = itemConnectionGateway.addItemToDatabase(inventory.get(i), 2);
						} catch (Exception e) {
							e.printStackTrace();
						}
    				}
    			}
    		}
    		if(!duplicateProduct){
    			id = itemConnectionGateway.addItemToDatabase(inventoryItem, 1);
    			inventoryItem.setId(id);
    			inventory.add(inventoryItem); //= itemConnectionGateway.getItem();
    		}
    	}
    	updateInventoryModelObserver();
    	update();
    	if(id > 0) {
    		LogEntry entry = new LogEntry("added");
    		inventoryItem.addLogEntry(entry);
    		gatewayRemote.addLogEntry(id, entry);
    	}
    	
    	return hasError;
    }
    
    private boolean calculateQuantity(ProductTemplate product) {
    	ArrayList<ProductTemplateParts> templateParts = productTemplatePartsModel.getList();
    	ArrayList<ProductTemplateParts> usedParts = new ArrayList<ProductTemplateParts>();
    	boolean quantityNeeded = true;
    	boolean enoughStock = false;
    	for(ProductTemplateParts p : templateParts) {
    		if(product.getId() == p.getProductTemplateId()) {
    			usedParts.add(p);
    		}
    	}
    	
    	for(i = 0; i < usedParts.size(); i++) {
    		int needed = usedParts.get(i).getQuantity();
    		for(j = 0; j < inventory.size(); j++) {
    			Part tempPart = inventory.get(i).getPart();
    			if(tempPart.getIdNumber() == usedParts.get(i).getPartId()) {
    				//Facility 1 Warehouse 1
    				if(inventory.get(i).getLocation().equals("Facility 1 Warehouse 1")) {
    					if(inventory.get(i).getQuantity() >= needed) {
    						try {
								inventory.get(i).setQuantity(inventory.get(i).getQuantity() - needed);
								System.out.println(inventory.get(i).getQuantity());
								quantityNeeded = false;
							} catch (Exception e) {
								e.printStackTrace();
							}
    					}
    				}
    				//Facility 1 Warehouse 2
    				if(inventory.get(i).getLocation().equals("Facility 1 Warehouse 2") && quantityNeeded) {
    					if(inventory.get(i).getQuantity() >= needed) {
    						try {
								inventory.get(i).setQuantity(inventory.get(i).getQuantity() - needed);
								quantityNeeded = false;
							} catch (Exception e) {
								e.printStackTrace();
							}
    					}
    				}
    				//Facility 2
    				if(inventory.get(i).getLocation().equals("Facility 2") && quantityNeeded) {
    					if(inventory.get(i).getQuantity() >= needed) {
    						try {
								inventory.get(i).setQuantity(inventory.get(i).getQuantity() - needed);
								quantityNeeded = false;
							} catch (Exception e) {
								e.printStackTrace();
							}
    					}
    				}
    				if(quantityNeeded) {
    					enoughStock = true;
    				}
    			}
    		} 
    				
    	}
    	return enoughStock;
    }

    /**
     * Gets inventory list
     * @return an array list of inventory items
     */
    public ArrayList<InventoryItem> getList() {
        return this.inventory;
    }

    /**
     * Gets the row count of the inventory
     * @return int representing the number of rows in the inventory
     */
    @Override
    public int getRowCount() {
        return this.inventory.size();
    }

    /**
     * Gets the column count of the current inventory model
     * @return int representing the number of columns in the inventory
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Gets the value at the given row and column
     * @param row
     * @param col column
     * @return object located at the given row and column
     */
    @Override
    public Object getValueAt(int row, int col) {
    	
        InventoryItem j = inventory.get(row);
        if (col == 0) {
            return j.getIdNumber();
        } else if (col == 1) {
        	/*
        	 * This conditional of for the difference between a part and productTemplate setup
        	 */
        	if(j.getPart() == null){
        		return j.getProduct().getDescription();
        	} else {
        		return j.getPart().getPartName();
        	}
            
        } else if (col == 2) {
        	return j.getLocation();
        } else if (col == 3) {
            return j.getQuantityString();
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
     * Gets the inventory item at a given index in the array list
     * @param row : the index
     * @return inventory item at the given index in the array list
     */
    public InventoryItem getInventoryItem(int row) {
        return inventory.get(row);
    }

    /**
     * Removes item from the inventory
     * @param row
     */
    public void removeItem(int row) {
    	hasError = true;
    	if(validate.isValidDeleteItem(inventory.get(row))) {
    		itemConnectionGateway.removeItem(inventory.get(row));
    		inventory = itemConnectionGateway.getItem();
    		hasError = false;
    	}
    	updateInventoryModelObserver();
    	update();
    }
    
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
     * edits an item in the inventory
     * @param location
     * @param quantity
     * @param inventoryItem
     * @return hasError : true if errors in edited info, false otherwise
     */
    public boolean editInventoryItem(Part part, String location, int quantity, InventoryItem inventoryItem, ItemLogTableModel tableModel) {
    	id = inventoryItem.getIdNumber();
    	String oldLocation = inventoryItem.getLocation();
    	int oldQuantity = inventoryItem.getQuantity();
    	hasError = validate.isValidEditItem(location, quantity);
    	System.out.println(hasError);
    	if(!hasError) {
    		if(!oldLocation.equals(location)) {
    			for(i = 0; i < inventory.size(); i++) {
    				if(inventory.get(i).getLocation().equals(location)) {
    					if(inventory.get(i).getPart() != null) {
    						if(inventory.get(i).getPart().equals(inventoryItem.getPart())) {
    							hasError = true;
    							validate.addError("Part already exists at this location");
    							updateInventoryModelObserver();
    							update();
    							return hasError;
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	inventoryItem.setPart(part);
    	inventoryItem.setLocation(location);
    	inventoryItem.setEditedQuantity(quantity);
    	itemConnectionGateway.editItem(inventoryItem);
    	//inventory = itemConnectionGateway.getItem();
    	
    	updateInventoryModelObserver();
    	update();
    	if(oldQuantity != quantity) {
    		LogEntry entry = new LogEntry("Quantity changed from " + oldQuantity + " to " + quantity);
    		System.out.println("Adding entry description " + entry.getDescription());
    		System.out.println(inventoryItem.getIdNumber());
            inventoryItem.addLogEntry(entry);
            gatewayRemote.addLogEntry(id, entry);
    	}
    	if(!oldLocation.equals(location)) {
    		LogEntry entry = new LogEntry("Location changed from " + oldLocation + " to " + location);
    		System.out.println("Adding entry description " + entry.getDescription());
            inventoryItem.addLogEntry(entry);
            gatewayRemote.addLogEntry(id, entry);
    	}
    	updateItemLogTableModel(tableModel);
    	return hasError;
    }
    
    public boolean editInventoryItemProduct(ProductTemplate product, String location, int quantity, InventoryItem inventoryItem, ItemLogTableModel tableModel) {
    	id = inventoryItem.getIdNumber();
    	String oldLocation = inventoryItem.getLocation();
    	int oldQuantity = inventoryItem.getQuantity();
    	//System.out.println(product.getDescription() + " " + inventoryItem.getLocation() + " " + "new location " + location);
    	hasError = false; // need to validate the edited product
    	if(!hasError) {
    		if(!oldLocation.equals(location)) {
    			for(i = 0; i < inventory.size(); i++) {
    				if(inventory.get(i).getLocation().equals(location)) {
    					if(inventory.get(i).getProduct() != null) {
    						if(inventory.get(i).getProduct().equals(inventoryItem.getProduct())) {
    							hasError = true;
    							validate.addError("Product already exists at this location");
    							updateInventoryModelObserver();
    							update();
    							return hasError;
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	inventoryItem.setProduct(product);
    	inventoryItem.setLocation(location);
    	inventoryItem.setEditedQuantity(quantity);
    	itemConnectionGateway.editItemProduct(inventoryItem);
    	//inventory = itemConnectionGateway.getItem();
    	
    	updateInventoryModelObserver();
    	update();
    	if(oldQuantity != quantity) {
    		LogEntry entry = new LogEntry("Quantity changed from " + oldQuantity + " to " + quantity);
    		System.out.println("Adding entry description " + entry.getDescription());
            inventoryItem.addLogEntry(entry);
            gatewayRemote.addLogEntry(id, entry);
    	}
    	if(!oldLocation.equals(location)) {
    		LogEntry entry = new LogEntry("Location changed from " + oldLocation + " to " + location);
    		System.out.println("Adding entry description " + entry.getDescription());
    		inventoryItem.addLogEntry(entry);
    		gatewayRemote.addLogEntry(id, entry);
    	}
    	updateItemLogTableModel(tableModel);
    	return hasError;
    }
    
    /**
     * Creates an inventory item
     * @param part
     * @param location
     * @param quantity
     */
    private void createInventoryItem(Part part, String location, String quantity) {
    	hasError = true;
    	InventoryItem temp = new InventoryItem(part, location, quantity);
    	if(validate.isValidInventoryItem(inventory, temp)) {
    		hasError = false;
    		for(i = 0; i < inventory.size(); i++) {
    			if(inventory.get(i).getLocation().equalsIgnoreCase(location)) {
    				if(inventory.get(i).getPart() != null) {
    					if(inventory.get(i).getPart().equals(part)) {
    						hasError = true;
    						validate.addError("Part already exists at this location");
    						i = inventory.size();
    					}
    				}
    			}
    		}
    		
    	}
    	if(!hasError) {
			inventoryItem = temp;
    	}
    }
    
    /**
     * Creates a product inventory item;
     * Will combine this with the existing createInventoryItem() method later, but just getting this to work for now
     * @param part
     * @param location
     * @param name
     */
    private boolean createProductInventoryItem(ProductTemplate product, String location) {
    	hasError = true;
    	if(calculateQuantity(product)) {
    		validate.addError("Not enough inventory to create this product");
    		return hasError;
    	}
    	hasError = false;
    	InventoryItem temp = new InventoryItem(product, location, 1);
    	if(validate.isValidProductLocation(location)) {
    		inventoryItem = temp;
    		hasError = false;
    	}
    	else {
    		hasError = true;
    	}
    	return hasError;
    }
    
    public void updateLogList(ArrayList<LogEntry> list) {
    	System.out.println("the list at inventory model " + this.id);
    	for(i = 0; i < inventory.size(); i++) {
    		if(inventory.get(i).getIdNumber() == id) {
    			inventoryItem = new InventoryItem();
    			inventoryItem = inventory.get(i);
    		}
    	}
    	if(inventoryItem != null) {
    		//list = inventoryItem.getLogList();
    		inventoryItem.setList(list);
    		this.logTableModel.setList(list);
    		System.out.println("About to update the log table model, size of list " + list.size());
    		this.logTableModel.updateItemLogTableModel();
    	}
    }

    /**
     * Registers a table observer
     * @param observer
     */
    public void registerObserver(TableObserver observer) {
        observers.add(observer);
    }

    /**
     * Registers a inventory model observer
     * @param inventoryModelObserver
     */
    public void registerInventoryModelObserver(InventoryModelObserver inventoryModelObserver) {
        inventoryModelObservers.add(inventoryModelObserver);
    }
    
    public void registerLogViewObserver(LogViewObserver logViewObserver) {
    	logViewObservers.add(logViewObserver);
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
     * Updates the inventory model observer
     */
    public void updateInventoryModelObserver(){
        for (InventoryModelObserver observer : inventoryModelObservers) {
            observer.update(this);
        }
    }
    
    /**
     * Updates the item log table model
     */
    public void updateItemLogTableModel(ItemLogTableModel logModel) {
    	for(LogViewObserver observer : logViewObservers) {
    		observer.update(logModel);
    	}
    } 

    /**
     * Gets bool value of true if there are errors, false otherwise
     * @return hasError
     */
    public boolean getHasError(){
        return hasError;
    }

    /**
     * Gets a string representing errors in an item
     * @return String representing errors
     */
    public String getErrors() {
        return validate.getErrors();
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
