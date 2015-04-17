package controllers;

import models.DetailTemplateModel;
import models.PartsModel;
import models.ProductTemplate;
import models.ProductTemplateParts;
import models.ProductTemplatePartsGateway;
import models.ProductTemplatePartsModel;
import models.Session;
import models.TemplateGateway;
import models.TemplateModel;
import views.TemplateView;

import java.util.ArrayList;
import java.util.Timer;
import java.awt.event.*;
import java.util.TimerTask;

import javax.swing.JOptionPane;

/**
 * Controller for the Templates
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class TemplateController implements ActionListener {
	
	/**
	 * The Template view
	 */
	private TemplateView templateView;
	
	/**
	 * The parts model
	 */
	private PartsModel partsModel;
	
	/**
	 * The template model
	 */
	private TemplateModel templateModel;
	
	/**
	 * A template
	 */
	private ProductTemplate template;
	
	/**
	 * A product template parts model
	 */
	private ProductTemplatePartsModel templatePartsModel;
	
	/**
	 * A product template part
	 */
	private ProductTemplateParts templatePart;
	
	/**
	 * The session
	 */
	private Session session;
	
	/**
	 * Constructor
	 */
	public TemplateController(PartsModel partsModel, TemplateModel templateModel, ProductTemplatePartsModel productTemplatePartsModel,
			Session session) {
		this.session = session;
		this.templateModel = templateModel;
		this.partsModel = partsModel;
		this.templatePartsModel = productTemplatePartsModel;
		this.templateView = new TemplateView(templateModel, templateMouseListener);
		this.templateView.registerListeners(this);
	}
	
	/**
	 * Watches for button presses on the template view
	 * @param e : action performed
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Ok")) {
			templateView.closeWindow();
		}
		else if(actionCommand.equals("Add")) {
			if(session.isCanAddProductTemplates()) {
				@SuppressWarnings("unused")
				AddTemplateController atc = new AddTemplateController(this.templateModel);
			}
			else {
				JOptionPane.showMessageDialog(null, "Not authorized to add templates", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(actionCommand.equals("Edit")) {
			int selectedRow = this.templateView.getSelectedRow();
			template = templateModel.gettemplatesItem(selectedRow);
			if(isTemplatePart() && session.isCanEditProductTemplates()) {
				templatePart = templatePartsModel.getTemplatePartWithId(template.getId());
				ArrayList<Integer>  quantityList = templatePartsModel.getQuantityListForTemplateId(template.getId());
				DetailTemplateModel detailTemplateModel = new DetailTemplateModel(template.getId(), templatePartsModel, partsModel, partsModel.getListOfPartsId(), templatePartsModel.getQuantityListForTemplateId(template.getId()));
				@SuppressWarnings("unused")
				EditProductTemplatePartsController editProductTemplatePartsController = new EditProductTemplatePartsController(this.templateModel, 
						template, detailTemplateModel, templatePartsModel, templatePart, this.partsModel);
			}
			else {
				if(session.isCanEditProductTemplates()) {
					@SuppressWarnings("unused")
					EditTemplateController editTemplateController = new EditTemplateController(templateModel, template);
	        	}
	        	else {
	        		JOptionPane.showMessageDialog(null, "You are not authorized to edit templates", "Error", JOptionPane.ERROR_MESSAGE);
	        	}
				
			}
		}
		else if(actionCommand.equals("Delete")) {
			if(templateView.deleteTemplate() && session.isCanDeleteProductTemplates()) {
				int selectedRow = this.templateView.getSelectedRow();
				templateModel.removeTemplate(selectedRow);
			} else {
				JOptionPane.showMessageDialog(null, "You are not authorized to delete templates", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(actionCommand.equals("Add Part")) {
			template = templateModel.gettemplatesItem(templateView.getSelectedRow());
			DetailTemplateModel detailTemplateModel = new DetailTemplateModel(template.getId(), templatePartsModel, partsModel, partsModel.getListOfPartsId(), templatePartsModel.getQuantityListForTemplateId(template.getId()));
			if(isTemplatePart()) {
				templatePart = templatePartsModel.getTemplatePartWithId(template.getId());
				AddPartToTemplatePartController addPartToTemplatePartController = new AddPartToTemplatePartController(partsModel, templatePartsModel,templatePart,
						detailTemplateModel);
			}
			else {
				AddPartToTemplatePartController addPartToTemplatePartController = new AddPartToTemplatePartController(partsModel, templatePartsModel,template,
						detailTemplateModel);
			}
		}
	}
	
	private boolean isTemplatePart() {
		template = templateModel.gettemplatesItem(this.templateView.getSelectedRow());
		return templatePartsModel.isTemplatePart(template);
		
	}
	
	/**
	 * Watches for mouse clicks on the template view
	 */
	MouseListener templateMouseListener = new MouseAdapter() {
		boolean isAlreadyOneClick;
		@Override
		public void mouseClicked(MouseEvent e) {
			if(isAlreadyOneClick && (templateView.getSelectedRow() == templateView.getLastRowSelected())) {
				if(session.isCanEditProductTemplates()){
					if(isTemplatePart()) {
						@SuppressWarnings("unused")
						DetailProductTemplatePartsController dptc = new DetailProductTemplatePartsController(templateModel.gettemplatesItem(templateView.getSelectedRow()),
								partsModel, templatePartsModel, templateModel);
					}
					else if(!(isTemplatePart())) {
						DetailTemplateModel detailTemplateModel = new DetailTemplateModel(template.getId(), templatePartsModel, partsModel, partsModel.getListOfPartsId(), templatePartsModel.getQuantityListForTemplateId(template.getId()));
						@SuppressWarnings("unused")
						DetailTemplateController dtc = new DetailTemplateController(templateModel,
								templateModel.gettemplatesItem(templateView.getSelectedRow()), partsModel, templatePartsModel, detailTemplateModel);
					}
				} else {
					JOptionPane.showMessageDialog(null, "You are not authorized to edit templates", "Error", JOptionPane.ERROR_MESSAGE);
				}
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
