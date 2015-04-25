package models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * ProductTemplateParts model, this class keeps track of the current product template parts
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class ProductTemplatePartsModel extends AbstractTableModel {
	
	/**
	 * The form used for validation
	 */
	private ValidateForm validate;
	
	/**
	 * A template part
	 */
	private ProductTemplateParts templatePart;
	
	/**
	 * The connection gateway
	 */
	private ProductTemplatePartsGateway ptpg;
	
	/**
	 * The product template parts list
	 */
	private ArrayList<ProductTemplateParts> productTemplateParts;
	
	/**
	 * Column names for the table
	 */
	private String[] columnNames = {"Template Id", "Part Id", "Quantity"};
	
	/**
	 * A list of observers
	 */
	private List<TableObserver> observers = new ArrayList<TableObserver>();
	
	/**
	 * A list of product template parts model observer
	 */
	private List<ProductTemplatePartsModelObserver> templatePartsModelObserver = 
			new ArrayList<ProductTemplatePartsModelObserver>();
	
	/**
	 * An error observer
	 */
	private ProductTemplatePartsErrorObserver errorObserver;
	
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
	public ProductTemplatePartsModel( ProductTemplatePartsGateway ptpg) {
		this.validate = new ValidateForm();
		this.errorObserver = new ProductTemplatePartsErrorObserver();
		this.ptpg = ptpg;
		this.productTemplateParts = ptpg.getTemplateParts();
		this.registerProductTemplatePartsModelObserver(errorObserver);
	}
	
	/**
	 * Add a product template part
	 * @param productTemplateId
	 * @param partId
	 * @param quantity
	 */
	public boolean addTemplatePart(int productTemplateId, int partId, int quantity, DetailTemplateModel detailTemplateModel) {
		if(validate.isValidTemplatePart(quantity)) {
			ptpg.addPart(productTemplateId, partId, quantity);
			productTemplateParts = ptpg.getTemplateParts();
			detailTemplateModel.updateList();
			hasError = false;
		}
		else {
			hasError = true;
		}
		return hasError;
	}
	
	/**
	 * Removes a product template part
	 */
	public void removeTemplatePart(int templateId, int partId, DetailTemplateModel detailTemplateModel) {
		ptpg.deletePart(templateId, partId);
		productTemplateParts = ptpg.getTemplateParts();
		detailTemplateModel.updateList();
	}
	
	/**
	 * Gets the template model list
	 * @return an array list of template models
	 */
	public ArrayList<ProductTemplateParts> getList() {
		return this.productTemplateParts;
	}
	
	/**
	 * Gets the template model list for a product template
	 */
	
	
	/**
	 * Checks for template as a template part
	 */
	public boolean isTemplatePart(ProductTemplate template) {
		boolean isTemplatePart = false;
		int templateId = template.getId();
		for(i = 0; i < productTemplateParts.size(); i++) {
			if(productTemplateParts.get(i).getProductTemplateId() == templateId) {
				isTemplatePart = true;
			}
		}
		return isTemplatePart;
	}
	
	/**
	 * Gets a template part with a specific template id
	 */
	public ProductTemplateParts getTemplatePartWithId(int id) {
		for(i = 0; i < productTemplateParts.size(); i++) {
			if(productTemplateParts.get(i).getProductTemplateId() == id) {
				return productTemplateParts.get(i);
			}
		}
		return null;
	}
	
	public ArrayList<Integer> getQuantityListForTemplateId(int templateId) {
		ArrayList<Integer> quantities = new ArrayList<Integer>();
		for(i = 0; i < productTemplateParts.size(); i++) {
			if(productTemplateParts.get(i).getProductTemplateId() == templateId) {
				quantities.add(productTemplateParts.get(i).getQuantity());
			}
		}
		return quantities;
	}
	
	/**
	 * Gets the row count of the template parts
	 * @return int representing the number of rows in the template parts list 
	 */
	@Override
	public int getRowCount() {
		return this.productTemplateParts.size();
	}
	
	/**
	 * Gets the column count of the current template parts model
	 * @return int representing the number of columns in the template parts
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	/**
	 * Gets the value at the given row and column
	 * @param row
	 * @param col : column
	 * @return object located at the given row and column
	 */
	@Override
	public Object getValueAt(int row, int col) {
		ProductTemplateParts j = productTemplateParts.get(row);
		if(col == 0) {
			return j.getProductTemplateId();
		} else if (col == 1) {
			return j.getPartId();
		} else if(col == 2) {
			return j.getQuantity();
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
	 * Gets the template part at a given index in the array list
	 * @param row : the index
	 * @return template part at the given index
	 */
	public ProductTemplateParts getTemplatePart(int row) {
		return productTemplateParts.get(row);
	}
	
	/**
	 * Removes template part
	 */
	public void removeTemplatePart(ProductTemplateParts templatePart, int partId) {
		for(i = 0; i < productTemplateParts.size(); i++) {
			if(productTemplateParts.get(i).getProductTemplateId() == templatePart.getProductTemplateId() && 
					productTemplateParts.get(i).getPartId() == templatePart.getPartId()) {
				productTemplateParts.remove(i);
			}
		}
		updateProductTemplatePartsModelObserver();
		update();
	}
	
	/**
	 * Edits the quantity of a template part 
	 */
	public boolean editTemplatePartQuantity(ProductTemplateParts templatePart, int quantity) {
		if(quantity > 0) {
			ptpg.editPart(templatePart.getProductTemplateId(), templatePart.getPartId(), quantity);
			productTemplateParts = ptpg.getTemplateParts();
			updateProductTemplatePartsModelObserver();
			update();
			return true;
		}
		return false;
	}
	
	/**
	 * Registers a table observer
	 * @param observer
	 */
	public void registerObserver(TableObserver observer) {
		observers.add(observer);
	}
	
	/**
	 * Registers a product template parts model observer
	 * @param productTemplatePartsModelObserver
	 */
	public void registerProductTemplatePartsModelObserver(ProductTemplatePartsModelObserver ptpObserver) {
		templatePartsModelObserver.add(ptpObserver);
	}
	
	/**
	 * Updates observers
	 */
	public void update() {
		for (TableObserver observer : observers) {
			observer.update();
		}
	}
	
	/**
	 * Updates the product template parts model observer
	 */
	public void updateProductTemplatePartsModelObserver() {
		for(ProductTemplatePartsModelObserver observer : templatePartsModelObserver) {
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
	 * Gets a string representing errors in a product template part
	 * @return String representing errors
	 */
	public String getErrors() {
		return validate.getErrors();
	}
}
