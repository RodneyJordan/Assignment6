package controllers;

import models.DetailTemplateModel;
import models.ProductTemplatePartsModel;
import models.TemplateModel;
import views.AddProductTemplatePartsView;
import views.AddTemplateView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for adding template parts
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class AddProductTemplatePartsController implements ActionListener {

	/**
	 * an add product template parts view
	 */
	AddProductTemplatePartsView addView;
	
	/**
	 * The detail template model
	 */
	private DetailTemplateModel detailTemplateModel;
	
	/**
	 * A product template parts model
	 */
	ProductTemplatePartsModel model;
	
	/**
	 * Constructor
	 * @param templateModel
	 */
	public AddProductTemplatePartsController(ProductTemplatePartsModel model, DetailTemplateModel detailTemplateModel) {
		this.model = model;
		this.detailTemplateModel = detailTemplateModel;
		this.addView = new AddProductTemplatePartsView();
		addView.registerListeners(this);
	}
	
	/**
	 * Watches for a button pressed on the add template view
	 * @param e : a button being pressed on the add template view
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Add")) {
			if(!model.addTemplatePart(Integer.parseInt(addView.templateIdTextField.getText()), Integer.parseInt(addView.partIdTextField.getText()), 
					Integer.parseInt(addView.quantityTextField.getText()), detailTemplateModel)) {
				addView.closeWindow();
			}
		}
		else if(actionCommand.equals("Cancel")) {
			addView.closeWindow();
		}
	}
}
