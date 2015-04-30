package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Template model, this class keeps track of the current product templates as well as validating
 * any newly created items, and edited items.
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class TemplateModel extends AbstractTableModel {
	
	/**
	 * The product template
	 */
	private ProductTemplate productTemplate;
	
	/**
	 * The form used for validation
	 */
	private ValidateForm validate;
	
	/**
	 * The template gateway
	 */
	private TemplateGateway templateGateway;
	
	/**
	 * The templates array list
	 */
	private ArrayList<ProductTemplate> templates;
	
	/**
	 * Column names for the table
	 */
	private String[] columnNames = {"ID", "Product Number", "Product Description"};
	
	/**
	 * A list of observers
	 */
	private List<TableObserver> observers = new ArrayList<TableObserver>();
	
	/**
	 * A list of template model observers
	 */
	private List<TemplateModelObserver> templateModelObservers = new ArrayList<TemplateModelObserver>();
	
	/**
	 * The error observer
	 */
	private TemplateErrorObserver errorObserver;
	
	/**
	 * used for iteration through loops
	 */
	private int i;
	
	/**
	 * true if errors, false otherwise
	 */
	private boolean hasError;
	
	/**
	 * Constructor
	 * @param tg
	 */
	public TemplateModel(TemplateGateway tg) {
		this.validate = new ValidateForm();
        this.templateGateway = tg;
        this.templates = templateGateway.getTemplates();
        this.errorObserver = new TemplateErrorObserver();
        this.registerTemplateModelObserver(errorObserver);
	}

	/**
     * Adds an item to the current template model
     * @param 
     * @return true if item has errors, false otherwise
     */

    public boolean addTemplate(String productNumber, String description) {
    	boolean sameName = createProductTemplate(productNumber, description);
    	if(sameName) {
    		System.out.println("same name");
    		return true;
    	}
        if(!hasError) {
        	templateGateway.addTemplateToDatabase(productTemplate);
        	templates = templateGateway.getTemplates();
        }

        updateTemplateModelObserver();
        update();
        return hasError;
    }
    
    public boolean editPart(String productNumber, String description, ProductTemplate templateToEdit) {
		String oldProductNumber = templateToEdit.getProductNumber();
		for(i = 0; i < templates.size(); i++) {
        	if(productNumber.equalsIgnoreCase(templates.get(i).getProductNumber()) && oldProductNumber != templates.get(i).getProductNumber()) {
    			hasError = true;
        		return hasError;
        	}
        }
		hasError = validate.isValidEditTemplate(productNumber, description);
		
		if(!hasError) {
			templateToEdit.setProductNumber(productNumber);
			templateToEdit.setDescription(description);
			templateGateway.editTemplate(templateToEdit);
			templates = templateGateway.getTemplates();
		
			updateTemplateModelObserver();
			update();
		}
		
		return hasError;
	}
    
    private boolean createProductTemplate(String productNumber, String description) {
    	hasError = true;
    	ProductTemplate temp = new ProductTemplate(productNumber, description);
    	for(i = 0; i < templates.size(); i++) {
    		// for checking duplicate product number
    		// may not be needed
    	}
    	if(validate.isValidTemplate(temp)) {
    		hasError = false;
    		System.out.println("temp validated");
    		for(i = 0; i < templates.size(); i++) {
    			if(temp.equals(templates.get(i))) {
    				hasError = true;
    				validate.addError("Template already exists");
    				i = templates.size();
    			}
    		}
    	}
    	if(!hasError) {
    		productTemplate = new ProductTemplate(productNumber, description); 
    	}
    	updateTemplateModelObserver();
        errorObserver.update(this);
    	update();
    	return hasError;
    }

    /**
     * Gets template list
     * @return an array list of product templates
     */
    public ArrayList<ProductTemplate> getList() {
        return this.templates;
    }
    
    /**
	 * Get template that has a specific id
	 * @param id
	 */
	public ProductTemplate getTemplate(int index) {
		return templates.get(index);
	}

    /**
     * Gets the row count of the template list
     * @return int representing the number of rows in the list
     */
    @Override
    public int getRowCount() {
        return this.templates.size();
    }

    /**
     * Gets the column count of the current template model
     * @return int representing the number of columns in the list
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
    	
        ProductTemplate j = templates.get(row);
        if (col == 0) {
            return j.getId();
        } else if (col == 1) {
            return j.getProductNumber();
        } else if (col == 2) {
        	return j.getDescription();
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
     * Gets the templates item at a given index in the array list
     * @param row : the index
     * @return templates item at the given index in the array list
     */
    public ProductTemplate gettemplatesItem(int row) {
        return templates.get(row);
    }
    
    /**
     * Removes template
     */
    public void removeTemplate(int row) {
		templateGateway.removeTemplate(this.gettemplatesItem(row));
		templates = templateGateway.getTemplates();
		
		updateTemplateModelObserver();
		update();
    }
    
    /**
     * Updates the template model observer
     */
    public void updateTemplateModelObserver() {
    	for (TemplateModelObserver observer : templateModelObservers) {
    		observer.update(this);
    	}
    }
    
    /**
     * updates observers
     */
    public void update() {
    	for(TableObserver observer : observers) {
    		observer.update();
    	}
    }
    
    /**
     * Registers a table observer
     */
    public void registerObserver(TableObserver observer) {
    	observers.add(observer);
    }
    
    /**
     * Registers a template model observer
     * @param templateModelObserver
     */
    public void registerTemplateModelObserver(TemplateModelObserver templateModelObserver) {
    	templateModelObservers.add(templateModelObserver);
    }
    
    /**
     * Gets bool value of true if there are errors, false otherwise
     * @return hasError
     */
    public boolean getHasError() {
    	return hasError;
    }
    
    /**
     * Gets a string representing errors in a template
     * @return String representing errors
     */
    public String getErrors() {
    	return validate.getErrors();
    }
}