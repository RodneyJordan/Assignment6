package models;

import java.sql.Date;

/**
 * Creates a product template 
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class ProductTemplate {

	private int id;
	private String productNumber;
	private String description;
	
	//added this for simplicity; will change later
	private String name;
	
	/*
	 * MYSql displays and retrieves DATE values in format YYYY-MM-DD
	 */
	private Date dateAdded;
	private Date lastModified;
	
	public ProductTemplate(String productNumber, String description) {
		this.productNumber = productNumber;
		this.description = description;
	}

	public ProductTemplate(int id, String productNumber, String description, String name) {
		this.id = id;
		this.productNumber = productNumber;
		this.description = description;
		this.name = name;
	}
	
	public ProductTemplate(int id, String productNumber, String description) {
		this.id = id;
		this.productNumber = productNumber;
		this.description = description;
	}

	public ProductTemplate(int id, String productNumber, String description, Date dateAdded, Date lastModified) {
		this.id = id;
		this.productNumber = productNumber;
		this.description = description;
		this.dateAdded = dateAdded;
		this.lastModified = lastModified;
	}

	public ProductTemplate(int id, String description) {
		// TODO Auto-generated constructor stub
	}

	/*
	 * ACCESSORS
	 */
	public int getId(){ return this.id; }
	public String getProductNumber(){ return this.productNumber; }
	public String getName(){ return this.name; }
	public String getDescription(){ return this.description; }
	public Date getDateAdded(){ return this.dateAdded; }
	public Date getLastModified(){ return this.lastModified; }

	/*
	 * MUTATORS
	 */
	public void setId(int id) { this.id = id; }
	public void setProductNumber(String productNumber) { this.productNumber = productNumber; }
	public void setDescription(String description) { this.description = description; }
	public void setDateAdded(Date dateAdded) { this.dateAdded = dateAdded; }
	public void setLastModified(Date lastModified) { this.lastModified = lastModified; }

	/**
	 * Returns the list of 
	 **/
	
	/**
	 * Compares an object to a product template
	 * @return true if the objects are equal, false otherwise
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof ProductTemplate))
        {
            return false;
        }
		ProductTemplate other = (ProductTemplate) obj;
		
		return (this.productNumber.equals(other.productNumber) && this.description.equals(other.description));
	}

}