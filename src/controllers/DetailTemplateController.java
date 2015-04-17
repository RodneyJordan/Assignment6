package controllers;

import models.DetailTemplateModel;
import models.PartsModel;
import models.ProductTemplate;
import models.ProductTemplatePartsModel;
import models.TemplateModel;
import views.DetailTemplateView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the detailed template view
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class DetailTemplateController implements ActionListener {
	
	/**
	 * The detail template view
	 */
	private DetailTemplateView detailTemplateView;
	
	/**
	 * The template
	 */
	private ProductTemplate template;
	
	/**
	 * The template model
	 */
	private TemplateModel model;
	
	/**
	 * The part model
	 */
	private PartsModel partsModel;
	
	/**
	 * the detail template model
	 */
	private DetailTemplateModel detailTemplateModel;
	
	/**
	 * The product template parts model
	 */
	private ProductTemplatePartsModel templatePartsModel;
	
	/**
	 * Constructor
	 */
	public DetailTemplateController(TemplateModel model, ProductTemplate template, PartsModel partsModel, ProductTemplatePartsModel templatePartsModel, DetailTemplateModel detailTemplateModel) {
		this.template = template;
		this.model = model;
		this.detailTemplateModel = detailTemplateModel;
		this.partsModel = partsModel;
		this.templatePartsModel = templatePartsModel;
		detailTemplateView = new DetailTemplateView(this.template);
		this.detailTemplateView.registerListeners(this);
	}
	
	/**
	 * Watches for buttons pressed on the detail template view
	 * @param e : button that has been pressed
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Ok")) {
			this.detailTemplateView.closeWindow();
		}
		else if(actionCommand.equals("Edit")) {
			this.detailTemplateView.closeWindow();
			EditTemplateController editTemplateController = new EditTemplateController(model, template);
		}
		else if(actionCommand.equals("Add Part")) {
			AddPartToTemplatePartController addPartToTemplatePartController = new AddPartToTemplatePartController(partsModel, templatePartsModel,template, detailTemplateModel);
			this.detailTemplateView.closeWindow();
		}
	}
	
}