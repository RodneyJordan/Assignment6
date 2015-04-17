package controllers;

import models.PartsModel;
import models.Part;
import views.EditPartView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller to edit a part
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class EditPartController implements ActionListener {
	
	/**
	 * The edit part view
	 */
	private EditPartView editPartView;
	
	/**
	 * The part model
	 */
	private PartsModel partsModel;
	
	/**
	 * The part
	 */
	private Part part;
	
	/**
	 * Constructor
	 * @param partsModel
	 * @param part
	 */
	public EditPartController(PartsModel partsModel, Part part) {
		this.part = part;
		editPartView = new EditPartView(part);
		this.partsModel = partsModel;
		editPartView.registerListeners(this);
	}
	
	/**
	 * Watches for buttons pressed on the edit part view
	 * @param e action performed
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Edit Part")) {
    		if(!partsModel.editPart(editPartView.partNameTextField.getText(),
    				editPartView.vendorTextField.getText(), 
    				(String) editPartView.unitOfQuantityCombo.getItemAt(editPartView.unitOfQuantityCombo.getSelectedIndex()),
    				 editPartView.externalPartNumberTextField.getText(), part)) {
    			editPartView.closeWindow();
    		}
    		else {
    			editPartView.partNumberTextField.setText(part.getPartNumber());
    		}
		}
	}
}