package controllers;
/*
 * THIS CLASS DOES NOT WORK... CHANGES MUST BE MADE TO USE THIS CONTROLLER
 */
import models.DetailTemplateModel;
import models.ProductTemplate;
import models.ProductTemplateParts;
/// imports models.someGateway
import models.ProductTemplatePartsModel;
import models.PartsModel;
import models.Part;
import models.TemplateModel;
import views.ProductTemplatePartsView;

import java.util.Timer;
import java.awt.event.*;
import java.util.TimerTask;

/**
 * Controller for the product template parts
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class ProductTemplatePartsController implements ActionListener {
	
	/**
	 * The product template parts view
	 */
	private ProductTemplatePartsView view;
	
	/**
	 * The product template parts model
	 */
	private ProductTemplatePartsModel model;
	
	/**
	 * The template part
	 */
	private ProductTemplateParts templatePart;
	
	/**
	 * A part
	 */
	private Part part;
	
	/**
	 * A template
	 */
	private ProductTemplate template;
	
	/**
	 * The template model
	 */
	private TemplateModel templateModel;
	
	/**
	 * The detail template model
	 */
	private DetailTemplateModel detailTemplateModel;
	
	/**
	 * The parts model
	 */
	private PartsModel partsModel;
	
	/**
	 * Constructor
	 */
	public ProductTemplatePartsController(TemplateModel productTemplateModel, PartsModel partsModel, DetailTemplateModel detailTemplateModel) {
		//this.model = new ProductTemplatePartsModel(/*some gateway*/);
		this.partsModel = partsModel;
		this.detailTemplateModel = detailTemplateModel;
		this.templateModel = productTemplateModel;
		this.view = new ProductTemplatePartsView(model, templatePartsMouseListener);
		this.view.registerListeners(this);
	}
	
	/**
	 * Watches for button presses on the template view
	 * @param e : action performed
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Ok")) {
			view.closeWindow();
		}
		else if(actionCommand.equals("Add")) {
			@SuppressWarnings("unused")
			AddProductTemplatePartsController c = new AddProductTemplatePartsController(this.model, detailTemplateModel);
		}
		else if(actionCommand.equals("Edit")) {
			int selectedRow = this.view.getSelectedRow();
			ProductTemplateParts templatePart = model.getTemplatePart(selectedRow);
			//EditProductTemplatePartsController editController = new EditProductTemplatePartsController(this.model, templatePart);
		}
		else if(actionCommand.equals("Delete")) {
			if(view.deleteTemplatePart()) {
				int selectedRow = this.view.getSelectedRow();
				//model.removeTemplatePart(selectedRow);
			}
		}
	}
	
	/**
	 * Watches for mouse clicks on the template view
	 */
	MouseListener templatePartsMouseListener = new MouseAdapter() {
		boolean isAlreadyOneClick;
		@Override
		public void mouseClicked(MouseEvent e) {
			if(isAlreadyOneClick && (view.getSelectedRow() == view.getLastRowSelected())) {
				templatePart = model.getTemplatePart(view.getSelectedRow());
				//part = partsModel.getPartAtId(templatePart.getPartId());
				template = templateModel.getTemplate(templatePart.getProductTemplateId());
				//DetailProductTemplatePartsController c = new DetailProductTemplatePartsController(model, 
				//		templatePart, template, part);
				isAlreadyOneClick = false;
			}
			else {
				isAlreadyOneClick = true;
				Timer t = new Timer("doubleClickTimer", false);
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						isAlreadyOneClick = false;
					}
				}, 500);
			}
		}
	};
}
