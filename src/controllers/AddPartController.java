package controllers;

import models.PartsModel;
import views.AddPartView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for adding a part
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class AddPartController implements ActionListener {
	
	/**
	 * an add part view
	 */
	AddPartView addPartView;
	
	/**
	 * a parts model
	 */
	PartsModel partsModel;
	
	/**
	 * Constructs an add part controller
	 * @param partsModel
	 */
	public AddPartController(PartsModel partsModel) {
		this.addPartView = new AddPartView();
		this.partsModel = partsModel;
		addPartView.registerListeners(this);
	}
	
	/**
	 * Watches for a button press on the add part view
	 * @param e a button being pressed on the add part view
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(!partsModel.addPart(addPartView.partNumberTextField.getText(), addPartView.partNameTextField.getText(),
				addPartView.vendorTextField.getText(), addPartView.unitOfQuantityBox.getItemAt(addPartView.unitOfQuantityBox.getSelectedIndex()), 
				addPartView.externalPartNumberTextField.getText())) {
			addPartView.closeWindow();
		}
	}
}
