package controllers;

import views.AddPartToTemplatePartView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import models.DetailTemplateModel;
import models.Part;
import models.PartsModel;
import models.ProductTemplate;
import models.ProductTemplateParts;
import models.ProductTemplatePartsModel;

public class AddPartToTemplatePartController implements ActionListener{
	
	/**
	 * The add part to template part view
	 */
	private AddPartToTemplatePartView view;
	
	/**
	 * The product template parts model
	 */
	private ProductTemplatePartsModel model;
	
	/**
	 * The parts model
	 */
	private PartsModel partsModel;
	
	/**
	 * The product template part
	 */
	private ProductTemplateParts templatePart;
	
	/**
	 * The detail template model
	 */
	private DetailTemplateModel detailTemplateModel;
	
	/**
	 * The template
	 */
	private ProductTemplate template = null;
	
	private int i;
	
	/**
	 * Constructor
	 * @param parts
	 */
	public AddPartToTemplatePartController(PartsModel partsModel, ProductTemplatePartsModel model, ProductTemplateParts templatePart, DetailTemplateModel detailTemplateModel) {
		this.model = model;
		this.templatePart = templatePart;
		this.partsModel = partsModel;
		this.detailTemplateModel = detailTemplateModel;
		ArrayList<Part> parts = partsModel.getList();
		String[] partsInList = new String[parts.size()];
		for(i = 0; i < parts.size(); i++) {
			partsInList[i] = parts.get(i).getPartName();
		}
		view = new AddPartToTemplatePartView(partsInList);
		this.view.registerListeners(this);
	}
	
	public AddPartToTemplatePartController(PartsModel partsModel, ProductTemplatePartsModel model, ProductTemplate template, DetailTemplateModel detailTemplateModel) {
		this.model = model;
		this.template = template;
		this.partsModel = partsModel;
		this.detailTemplateModel = detailTemplateModel;
		ArrayList<Part> parts = partsModel.getList();
		String[] partsInList = new String[parts.size()];
		for(i = 0; i < parts.size(); i++) {
			partsInList[i] = parts.get(i).getPartName();
		}
		view = new AddPartToTemplatePartView(partsInList);
		this.view.registerListeners(this);
	}
	
	/**
	 * Watches for buttons pressed on the view
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Add")) {
			if(this.template == null) {
				int quantity = Integer.parseInt(view.quantityTextField.getText());
				model.addTemplatePart(templatePart.getProductTemplateId(), 
						this.partsModel.getPart(view.partsComboBox.getSelectedIndex()).getIdNumber(),
						quantity, detailTemplateModel);
				view.closeWindow();
			}
			else {
				int quantity = Integer.parseInt(view.quantityTextField.getText());
				model.addTemplatePart(this.template.getId(), this.partsModel.getPart(view.partsComboBox.getSelectedIndex()).getIdNumber(), quantity, detailTemplateModel);
				view.closeWindow();
			}
		}
		if(actionCommand.equals("Cancel")) {
			view.closeWindow();
		}
	}
}
