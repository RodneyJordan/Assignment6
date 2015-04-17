package controllers;

import models.ProductTemplate;
import models.TemplateModel;
import views.EditTemplateView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller to edit a template
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class EditTemplateController implements ActionListener {
	
	/**
	 * The edit template view
	 */
	private EditTemplateView editTemplateView;
	
	/**
	 * The template model
	 */
	private TemplateModel templateModel;
	
	/**
	 * The template
	 */
	private ProductTemplate template;
	
	/**
	 * Constructor
	 * @param templateModel
	 * @param template
	 */
	public EditTemplateController(TemplateModel templateModel, ProductTemplate template) {
		this.template = template;
		editTemplateView = new EditTemplateView(this.template);
		this.templateModel = templateModel;
		editTemplateView.registerListeners(this);
	}
	
	/**
	 * Watches for buttons pressed on the edit template view
	 * @param e : action performed
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Edit")) {
			
		}
		else if(actionCommand.equals("Cancel")) {
			this.editTemplateView.closeWindow();
		}
	}
}