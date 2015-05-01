package models;

import java.util.ArrayList;

import session.LogEntry;

/**
 * Creates an Inventory item
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class InventoryItem
{
	/**
	 * The id number
	 */
	private int id;
	
	/**
	 * The part
	 */
	private Part part;
	
	/**
	 * The product
	 */
	private ProductTemplate product;
    
    /**
     * The quantity of the item
     */
    private int quantity;
    
    /**
     * Location of the item
     */
    private String location;
    
    /**
     * Time stamp
     */
    private String timeStamp;
    
    private LogEntry log;
    
    ArrayList<LogEntry> logs;
    
    /**
     * for validation
     */
    private ValidateForm validate;
    
    /**
     * Constructor
     */
    public InventoryItem()
    {}

    /**
     * Constructor
     * @param part : The part for this inventory item
     * @param quantity : the quantity of this inventory item
     * @param partLocation : the location of this inventory item
     */
    public InventoryItem(Part part, String location, String quantity)
    {
        this.part = part;
        this.product = null;
    	validate = new ValidateForm();

        try{
        	this.quantity = Integer.parseInt(quantity);
        } catch(Exception e){
        	validate.addError("Quantity must be an integer");
        }
        this.location = location;
    }
    
    /**
     * Constructor
     * @param product : The product for this inventory item
     * @param location : the location for this inventory item
     * @param quantity : the quantity of the inventory item
     */
    public InventoryItem(ProductTemplate product, String location, int quantity) {
    	this.product = product;
    	this.part = null;
    	validate = new ValidateForm();
    	this.quantity = quantity;
    	this.location = location;
    }
    
    public InventoryItem(int id, ProductTemplate product, String location, int quantity) {
    	this.id = id;
    	this.product = product;
    	this.part = null;
    	validate = new ValidateForm();
    	try {
    		this.quantity = quantity;
    	} catch(Exception e) {
    		validate.addError("Quantity must be an integer");
    	}
    	this.location = location;
    }
    
    public InventoryItem(int id, Part part, String location, int quantity) {
    	this.id = id;
    	this.part = part;
    	this.quantity = quantity;
        this.location = location;
        this.timeStamp = null;
    }

    /**
     * Gets the id number
     * @return an id number
     */
    public int getIdNumber()
    {
    	return this.id;
    }

    /**
     * Sets a part 
     * @param part
     */
    public void setPart(Part part)
    {
    	if(this.part != null) {
    		this.part = part;
    	}
    	else {
    		validate.addError("This inventory item is for a product template. Cannot change to a part.");
    	}
    }

    /**
     * Gets a part 
     * @return a part 
     */
    public Part getPart()
    {
        return this.part;
    }
    
    /**
     * Set a product
     */
    public void setProduct(ProductTemplate product) {
    	if(this.product != null) {
    		this.product = product;
    	}
    	else {
    		validate.addError("This inventory item is for a part. Cannot change to a product template.");
    	}
    }
    
    /**
     * Gets a product
     * @return a product
     */
    public ProductTemplate getProduct() {
    	return this.product;
    }
    
    /**
     * Sets the quantity if conditions are met
     * @param quantity
     * @throws Exception value must be larger than 0
     */
    public void setQuantity(int quantity) throws Exception
    {
        if(quantity < 0)
        {
            throw new Exception("Value must be larger than 0");
        }
        else
        {
            this.quantity = quantity;
        }
    }

    /**
     * Sets an edited quantity
     * @param quantity
     */
    public void setEditedQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    /**
     * Gets the quantity of an item
     * @return a quantity
     */
    public int getQuantity()
    {
        return this.quantity;
    }
    
    /**
     * Sets location
     * @param location
     */
    public void setLocation(String location){
    	this.location = location;
    }
    
    /**
     * Gets unit of quantity type
     * @return a unit type
     */
    public String getLocation(){
    	return this.location;
    }
    
    /**
     * Set the time stamp
     */
    public void setTimeStamp(String time) {
    	this.timeStamp = time;
    }
    
    /**
     * Gets the time stamp
     */
    public String getTimeStamp() {
    	return this.timeStamp;
    }

    /**
     * Gets a string representation of the quantity
     * @return a String representation of the quantity
     */
    public String getQuantityString()
    {
        return Integer.toString(this.quantity);
    }

    /**
     * Compares an object to an inventory item, checking for equality
     * @param obj The object to compare
     * @return true if the objects are equal, false otherwise
     */
    public boolean equals(Object obj)
    {
        if(!(obj instanceof InventoryItem))
        {
            return false;
        }

        InventoryItem other = (InventoryItem) obj;
        if(this.part != null) {
        	return (this.part.equals(other.part) && this.location.equalsIgnoreCase(other.location));
        }
        else {
        	return (this.product.equals(other.product) && this.location.equalsIgnoreCase(other.location));
        }
    }
}
