package models;

import javax.swing.*;

/**
 * Watches for errors, and displays the error String that is generated from validation
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class TemplateErrorObserver implements TemplateModelObserver {
	
	/**
	 * Constructor
	 */
	public TemplateErrorObserver() {
		
	}
	
	/**
	 * Updates the template model
	 * @param templateModel
	 */
	@Override
	public void update(TemplateModel templateModel) {
		if(templateModel.getHasError()) {
			JOptionPane.showMessageDialog(null, templateModel.getErrors(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
