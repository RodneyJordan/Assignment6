package models;

/**
 * Creates a Part
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class Part {
	
	/**
	 * The id number
	 */
	private int id;
	
	/**
	 * The part number
	 */
	private String partNumber;
	
	/**
	 * The part name
	 */
	private String partName;
	
	/**
	 * The vendor
	 */
	private String vendor;
	
	/**
	 * The Unit of Quantity
	 */
	private String unitOFQuantity;
	
	/**
	 * The external part number
	 */
	private String externalPartNumber;
	
	/**
	 * Constructor
	 */
	public Part()
	{}
	
	/**
	 * Constructor
	 * @param partNumber : The part Number
	 * @param partName : The part name
	 * @param vendor : The vendor
	 * @param unitOfQuantity : The unit of quantity
	 * @param exteranlPartNumber : The external part number
	 */
	public Part(String partNumber, String partName, String vendor, String unitOfQuantity, String externalPartNumber) {
		this.partNumber = partNumber;
		this.partName = partName;
		this.vendor = vendor;
		this.unitOFQuantity = unitOfQuantity;
		this.externalPartNumber = externalPartNumber;
	}
	
	public Part(int id, String partNumber, String partName, String vendor, String unitOfQuantity, 
			String externalPartNumber) {
		this.id = id;
		this.partNumber = partNumber;
		this.partName = partName;
		this.vendor = vendor;
		this.unitOFQuantity = unitOfQuantity;
		this.externalPartNumber = externalPartNumber;
	}
	
	/**
	 * Gets the id number
	 * @return id
	 */
	public int getIdNumber() {
		return this.id;
	}
	
	/**
	 * Sets the part number
	 * @param partNumber : the part number
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	/**
	 * Gets the part number
	 * @return partNumber
	 */
	public String getPartNumber() {
		return this.partNumber;
	}
	
	/**
	 * Sets the part name
	 * @param partName
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}
	
	/**
	 * Gets the part name
	 * @return partName
	 */
	public String getPartName() {
		return this.partName;
	}
	
	/**
	 * Sets the vendor
	 * @param vendor
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	
	/**
	 * Gets the vendor
	 * @return vendor
	 */
	public String getVendor() {
		return this.vendor;
	}
	
	/**
	 * Sets the Unit of Quantity
	 * @param unitOfQuantity
	 */
	public void setUnitOfQuantity(String unitOfQuantity) {
		this.unitOFQuantity = unitOfQuantity;
	}
	
	/**
	 * Gets the unit of quantity
	 * @return unitOfQuantity
	 */
	public String getUnitOfQuantity() {
		return this.unitOFQuantity;
	}
	
	/**
	 * Sets the external part number
	 * @param externalPartNumber
	 */
	public void setExternalPartNumber(String externalPartNumber) {
		this.externalPartNumber = externalPartNumber;
	}
	
	/**
	 * Gets the external part number
	 * @return externalPartNumber
	 */
	public String getExternalPartNumber() {
		return this.externalPartNumber;
	}
	
	/**
	 * Compares an object to a Part, checking for equality
	 * @param obj The object to compare
	 * @return true if the objects are equal, false otherwise
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof Part)) {
			return false;
		}
		
		Part other = (Part) obj;
		
		return this.partNumber.equalsIgnoreCase(other.partNumber);
	}
}
