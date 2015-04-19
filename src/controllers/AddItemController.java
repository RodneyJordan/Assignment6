package controllers;

import models.InventoryModel;
import models.PartsModel;
import models.Part;
import models.ProductTemplate;
import models.ProductTemplatePartsModel;
import models.TemplateModel;
import session.Session;
import views.AddItemView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * Controller for adding an item
 * @author Rodney Jordan
 * @author Jacob Pagano
 */


public class AddItemController implements ActionListener
{
    /**
     * an add item view
     */
    AddItemView addItemView;

    /**
     * an inventory model
     */
    InventoryModel inventoryModel;
    
    /**
     * the parts model
     */
    private PartsModel partsModel;
    
    /**
     * Product Template
     */
    private TemplateModel product;
    
    /**
     * Product template parts model
     */
    private ProductTemplatePartsModel ptpm;
    
    /**
     * The session
     */
    private Session session;

    /**
     * Constructs an add item controller
     * @param inventoryModel
     */
    public AddItemController(InventoryModel inventoryModel, PartsModel partsModel, TemplateModel product, 
    		ProductTemplatePartsModel productTemplatePartsModel, Session session)
    {
    	this.session = session;
    	this.ptpm = productTemplatePartsModel;
    	this.product = product;
    	this.partsModel = partsModel;
        this.addItemView = new AddItemView(partsModel, product);
        this.inventoryModel = inventoryModel;
        addItemView.registerListeners(this);
    }

    /**
     * Watches for and button press on the add item view
     * @param e a button being pressed on the add item view
     */
    public void actionPerformed(ActionEvent e)
    {
    	if(addItemView.option.getSelectedIndex() == 0) {
    		if(session.isCanAddInventoryItems()){
    			if(!inventoryModel.addItemPart((Part) partsModel.getPart(addItemView.part.getSelectedIndex()),
						(String) addItemView.partLocations.getItemAt(addItemView.partLocations.getSelectedIndex()), 
						addItemView.quantityTextField.getText())) { 
    				addItemView.closeWindow();
    			}
    		} else {
    			JOptionPane.showMessageDialog(null, "You are not authorized to add Inventory Items", "Error", JOptionPane.ERROR_MESSAGE);
    		}
    		
    	}
    	else if (addItemView.option.getSelectedIndex() == 1) {
    		if(session.isCanAddProductTemplates()) {
    			// This line is the problem : (ProductTemplate) product.getTemplateAtId(addItemView.product.getSelectedIndex());
    			if(!inventoryModel.addItemProduct(
    					(ProductTemplate) product.getTemplate(addItemView.product.getSelectedIndex()),
    					(String) addItemView.partLocations.getItemAt(addItemView.partLocations.getSelectedIndex()), 
    					ptpm,partsModel)) {
    				addItemView.closeWindow();
    			}
    		}
    		else {
    			JOptionPane.showMessageDialog(null, "You cannot add a template", "Error", JOptionPane.ERROR_MESSAGE);
    		}
    	}
    }
}
