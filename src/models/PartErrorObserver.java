package models;

import javax.swing.*;

/**
 * Watches for errors, and displays the error String that is generated form validation
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class PartErrorObserver implements PartsModelObserver {
	
	/**
	 * Constructor
	 */
	public PartErrorObserver() {
		
	}
	
	/**
	 * Updates the parts model
	 * @param partsModel
	 */
	@Override
	public void update(PartsModel partsModel) {
		
		if(partsModel.getHasError()) {
			JOptionPane.showMessageDialog(null, partsModel.getErrors(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
