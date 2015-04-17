package models;

import org.apache.commons.lang3.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Class that check the validity of an inventory item to be added or edited
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class ValidateForm
{

    /**
     * list to hold errors in the format of an inventory item being tested
     */
    private List<String> errors;

    /**
     * Constructs the Validate Form
     */
    public ValidateForm()
    {
        errors = new ArrayList<String>();
    }

    /**
     * Checks to an inventory item to see if it has entries for all fields
     * @param inventory
     * @param inventoryItem
     * @return true if inventory item is valid, false otherwise
     */
    public boolean isValidPart(ArrayList<Part> parts, Part part)
    {

        errors.clear();          //reset error list before each validation check

        if(part.getUnitOfQuantity().equals("Unknown")){
        	errors.add("Unit of quantity cannot be Unknown");
        	return false;
        }
        
        if(part.getPartNumber().equals(""))
        {
            errors.add("Part number cannot be empty");
            return false;
        }
        else if(part.getPartNumber().length() > 20) {
            errors.add("Part number cannot be greater than 20 characters");
            return false;
        }
        else if(part.getPartNumber().substring(0, 1).equals("P")) {
        	errors.add("Part number must start with 'P'");
        }
        
        if(part.getExternalPartNumber().length() > 50)
        {
        	errors.add("External part number cannot be greater than 50 characters");
        	return false;
        }

        if(part.getPartName().equals(""))
        {
            errors.add("Part name cannot be empty");
            return false;
        }
        else if(part.getPartName().length() > 255)
        {
            errors.add("Part name cannot be longer than 255 characters");
            return false;
        }

        if(part.getVendor().length() > 255)
        {
            errors.add("Vendor cannot be longer than 255 characters");
            return false;
        }

        return true;
    }
    
    public boolean isValidInventoryItem(ArrayList<InventoryItem> inventory, InventoryItem inventoryItem) {
    	
    	errors.clear();
    	
    	if(inventoryItem.getQuantity() < 1)
        {
            errors.add("Quantity cannot be less than 1");
        }

        if(errors.size() >= 1){
            return false;
        }
        return true;
    }
    
    /**
     * Validates a new template
     * @param productTemplate 
     * @return true if template is valid, false otherwise
     */
    public boolean isValidTemplate(ProductTemplate productTemplate) {
    	errors.clear();
    	
    	if(productTemplate.getProductNumber().length() > 20) {
    		errors.add("Product number cannot be over 20 characters");
    		return false;
    	}
    	if(!productTemplate.getProductNumber().substring(0, 1).equals("A")) {
    		errors.add("Product number must begin with 'A'");
    		return false;
    	}
    	if(productTemplate.getDescription().length() > 255) {
    		errors.add("Description cannot be over 255 characters");
    		return false;
    	}
    	
    	return true;
    }
    
    public boolean isValidEditTemplate(String productNumber, String description) {
    	errors.clear();
    	
    	if(productNumber.length() > 20) {
    		errors.add("Product number cannot be over 20 characters");
    		return true;
    	}
    	if(!productNumber.substring(0, 1).equals("A")) {
    		errors.add("Product number must begin with 'A'");
    		return true;
    	}
    	if(description.length() > 255) {
    		errors.add("Description cannot be over 255 characters");
    		return true;
    	}
    	
    	return false;
    }
    
    public boolean isValidTemplatePart(int quantity) {
    	errors.clear();
    	
    	if(quantity <= 0) {
    		errors.add("Quantity must be greater than zero");
    		return false;
    	}
    	return true;
    }

    /**
     * adds a String that represents a type of error to the error list.
     * @param error
     */
    public void addError(String error)
    {
        errors.add(error);
    }

    /**
     * Checks the validity of a inventory item that is being edited
     * @param partNumber
     * @param partName
     * @param vendor
     * @param quantity
     * @param partLocation 
     * @return true if edited part is valid, false otherwise
     */
    public boolean isValidEditPart(String partName, String vendor, String unitOfQuantity, String externalPartNumber)
    {
        errors.clear();
        isValidUnitOfQuantity(unitOfQuantity);
        isValidExternalPartNumber(externalPartNumber);
        isValidPartName(partName);
        isValidVendor(vendor);

        if(errors.size() >= 1){
            return true;
        }

        return false;
    }
    
    public boolean isValidDeletePart(Part part, InventoryModel inventoryModel) {
    	errors.clear();
    	
    	int i;
    	ArrayList<InventoryItem> items = inventoryModel.getList();
    	for(i = 0; i < items.size(); i++) {
    		//if(items.get(i).getPart().equals(part)) { //issue
    			//errors.add("Part is associated with an item");
    			//return false;
    		//}
    	}
    	return true;
    }
    
    public boolean isValidDeleteItem(InventoryItem inventoryItem) {
    	errors.clear();
    	
    	if(inventoryItem.getQuantity() > 0) {
    		errors.add("Quantity must be 0 to Delete item");
    		return false;
    	}
    	return true;
    }
    
    public boolean isValidEditItem(String location, int quantity) {
    	errors.clear();
    	isValidEditQuantity(quantity);
        isValidPartLocation(location);
        
        if(errors.size() >= 1) {
        	return true;
        }
        
        return false;
    }
    
    /**
     * Check unit of quantity
     * @param unitType
     */
    public void isValidUnitOfQuantity(String unitOfQuantity){
    	if(unitOfQuantity.equals("Unknown")){
        	errors.add("Unit of quantity cannot be Unknown");
        }
    }
    
    /**
     * Check part location
     * @param partLocation
     */
    public void isValidPartLocation(String partLocation){
    	if(partLocation.equals("Unknown")){
        	errors.add("Part location cannot be Unknown");
        }
    }
    
    /**
     * Check product location
     */
    public boolean isValidProductLocation(String location) {
    	if(location.equals("Unknown")) {
    		errors.add("Product location cannot be Unknown");
    		return false;
    	}
    	return true;
    }

    /**
     * Checks to see if a part number is valid
     * @param partNumber
     */
    public void isValidPartNumber(String partNumber)
    {
        System.out.println("in part #");
        if(partNumber.equals(""))
        {
            errors.add("Part number cannot be empty");
        }
        else if(partNumber.length() > 20) {
            errors.add("Part number cannot be greater than 20 characters");
        }
        else if(partNumber.substring(0, 1).equals("P")) {
        	errors.add("Part number must start with 'P'");
        }
    }
    
    /**
     * Checks to see if an external part number is valid
     * @param externalPartNumber
     */
    public void isValidExternalPartNumber(String externalPartNumber)
    {
    	if(externalPartNumber.equals(""))
    	{
    		errors.add("External part number cannot be empty");
    	}
    	else if(externalPartNumber.length() > 50) 
    	{
    		errors.add("External part number cannot be greater than 50 characters");
    	}
    }

    /**
     * Checks to see if a part name is valid
     * @param partName
     */
    public void isValidPartName(String partName)
    {
        System.out.println("in part name");
        if(partName.equals(""))
        {
            errors.add("Part name cannot be empty");
        }
        else if(partName.length() > 255)
        {
            errors.add("Part name cannot be longer than 255 characters");
        }
    }

    /**
     * Checks to see if a vendor name is valid
     * @param vendor
     */
    public void isValidVendor(String vendor)
    {
        System.out.println("in vendor");
        if(vendor.length() > 255)
        {
            errors.add("Vendor cannot be longer than 255 characters");
        }

    }

    /**
     * Checks to see if a quantity is valid
     * @param quantity
     */
    public void isValidQuantity(int quantity)
    {
        System.out.println("in qty");
        if(quantity < 1)
        {
            errors.add("Quantity cannot be less than 1");
        }

    }

    /**
     * Checks to see if an edited quantity is valid
     * @param quantity
     */
    public void isValidEditQuantity(int quantity)
    {
        System.out.println("in edit qty");
        if(quantity < 0)
        {
            errors.add("Quantity cannot be less than 0");
        }

    }

    /**
     * Checks to see if an inventory item already exists in an inventory
     * @param inventory
     * @param inventoryItem
     */
    public void alreadyExists(ArrayList<InventoryItem> inventory, InventoryItem inventoryItem){
        System.out.println("in duplicate check");
        for(InventoryItem item : inventory){
            if(inventoryItem.equals(item)){
                errors.add("Item Already exists");
            }
        }
    }

    /**
     * Gets a String representation of errors in the error list
     * @return A string representation of errors, blank string if no errors exist.
     */
    public String getErrors() {
        if (!errors.isEmpty()) {
            return StringUtils.join(errors, ", ");
        }
        return "";
    }

}
