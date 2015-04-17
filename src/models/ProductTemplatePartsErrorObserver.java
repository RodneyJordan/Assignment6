package models;

import javax.swing.*;

/**
 * Watches for errors, and displays the error String that is generated
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class ProductTemplatePartsErrorObserver implements ProductTemplatePartsModelObserver {
	
	/**
	 * Constructor
	 */
	public ProductTemplatePartsErrorObserver() {
		
	}
	
	/**
	 * Updates the product template parts model
	 * @param productTemplatePartsModel
	 */
	@Override
	public void update(ProductTemplatePartsModel productTemplatePartsModel) {
		if(productTemplatePartsModel.getHasError()) {
			JOptionPane.showMessageDialog(null, productTemplatePartsModel.getErrors(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
