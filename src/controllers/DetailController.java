package controllers;

import models.InventoryItem;
import models.InventoryModel;
import models.PartsModel;
import models.ProductTemplatePartsModel;
import models.Session;
import models.TemplateModel;
import views.DetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the details
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class DetailController implements ActionListener
{
    /**
     * The detail view
     */
    private DetailView detailView;
    
    /**
     * The inventory model
     */
    private InventoryModel inventoryModel;
    
    /**
     * The parts model
     */
    private PartsModel partsModel;
    
    /**
     * The template model
     */
    private TemplateModel templateModel;
    
    /**
     * The product template parts model
     */
    private ProductTemplatePartsModel productTemplatePartsModel;
    
    /**
     * The session
     */
    private Session session;
    

    /**
     * The inventory item
     */
    private InventoryItem inventoryItem;

    /**
     * Constructor
     * @param inventoryModel
     * @param inventoryItem
     */
    public DetailController(InventoryModel inventoryModel, InventoryItem inventoryItem, PartsModel partsModel, TemplateModel templateModel,
    		ProductTemplatePartsModel productTemplatePartsModel, Session session)
    {
    	this.session = session;
    	this.partsModel = partsModel;
        this.inventoryItem = inventoryItem;
        detailView = new DetailView(this.inventoryItem);
        this.inventoryModel = inventoryModel;
        this.templateModel = templateModel;
        this.productTemplatePartsModel = productTemplatePartsModel;
        this.detailView.registerListeners(this);
    }

    /**
     * Watches for buttons pressed on the detail view
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();

        if(actionCommand.equals("Ok"))
        {
            this.detailView.closeWindow();
        }
        else if(actionCommand.equals("Edit"))
        {
            this.detailView.closeWindow();
            //@SuppressWarnings("unused")
			//EditItemController editItemController = new EditItemController(inventoryModel, inventoryItem, partsModel);
        }
        else if(actionCommand.equals("Add")) {
        	this.detailView.closeWindow();
        	AddItemController addItemController = new AddItemController(inventoryModel, partsModel, templateModel, productTemplatePartsModel, session);
        }
    }
}
