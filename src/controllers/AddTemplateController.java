package controllers;

import models.TemplateModel;
import views.AddTemplateView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for adding a template
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class AddTemplateController implements ActionListener {
	
	/**
	 * an add template view
	 */
	AddTemplateView addTemplateView;
	
	/**
	 * A template model
	 */
	TemplateModel templateModel;
	
	/**
	 * Constructor
	 * @param templateModel
	 */
	public AddTemplateController(TemplateModel templateModel) {
		this.templateModel = templateModel;
		this.addTemplateView = new AddTemplateView();
		addTemplateView.registerListeners(this);
	}
	
	/**
	 * Watches for a button pressed on the add template view
	 * @param e : a button being pressed on the add template view
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Add")) {
			if(!templateModel.addTemplate(addTemplateView.productNumberTextField.getText(), addTemplateView.descriptionTextField.getText())) {
				addTemplateView.closeWindow();
			}
			else {
				System.out.println("Something went wrong... fix it");
			}
		}
		else if(actionCommand.equals("Cancel")) {
			addTemplateView.closeWindow();
		}
	}
}
