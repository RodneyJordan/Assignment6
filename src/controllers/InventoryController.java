package controllers;

import models.InventoryItem;
import models.InventoryModel;
import models.PartsModel;
import models.ProductTemplatePartsGateway;
import models.ProductTemplatePartsModel;
import models.Session;
import models.TemplateGateway;
import models.TemplateModel;
import views.InventoryView;

import java.util.Timer;
import java.awt.event.*;
import java.util.TimerTask;

import javax.swing.JOptionPane;


/**
 * Controller for the Inventory
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class InventoryController implements ActionListener
{
    /**
     * The inventory view
     */
    InventoryView inventoryView;

    /**
     * The inventory model
     */
    InventoryModel inventoryModel;
    
    /**
     * The parts model
     */
    PartsModel partsModel;
    
    /**
     * Template Gateway
     */
    private TemplateGateway tg;
    
    /**
     * The template model
     */
    private TemplateModel templateModel;
    
    /**
     * Product Template Parts Gateway
     */
    private ProductTemplatePartsGateway ptpg;
    
    /**
     * Product Template Parts model
     */
    private ProductTemplatePartsModel productTemplatePartsModel;
    
    /**
     * The session
     */
    private Session session;

    /**
     * Constructor
     */
    public InventoryController(PartsModel partsModel, InventoryModel inventoryModel, Session session)
    {
    	this.session = session;
        this.inventoryModel = inventoryModel;
        this.partsModel = partsModel;
        this.inventoryView = new InventoryView(inventoryModel, inventoryMouseListener);
        inventoryView.registerListeners(this);
        this.tg = new TemplateGateway();
        this.templateModel = new TemplateModel(tg);
    	this.ptpg = new ProductTemplatePartsGateway();
    	productTemplatePartsModel = new ProductTemplatePartsModel(ptpg);
    }

    /**
     * Watches for button presses on the inventory view
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e)
    {
        String actionCommand = e.getActionCommand();

        if(actionCommand.equals("Add"))
        {
        	AddItemController addItemController = new AddItemController(inventoryModel, partsModel, templateModel, productTemplatePartsModel, session);
        }
        else if(actionCommand.equals("Edit"))
        {
        	if(session.isCanEditInventoryItems()){
        		int selectedRow = inventoryView.getSelectedRow();
                InventoryItem inventoryItem = inventoryModel.getInventoryItem(selectedRow);
                if(inventoryItem.getProduct() == null) {
                	EditItemController editItemController = new EditItemController(inventoryModel, inventoryItem, partsModel, templateModel);
                }
                else {
                	EditItemController editItemController = new EditItemController(inventoryModel, inventoryItem, partsModel, templateModel);
                }
        	} else {
        		JOptionPane.showMessageDialog(null, "You are not authorized to edit Inventory Items", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }
        else if(actionCommand.equals("Delete"))
        {
        	if(session.isCanDeleteInventoryItems()){
        		if(inventoryView.deleteItem())
                {
                    int selectedRow = inventoryView.getSelectedRow();
                    inventoryModel.removeItem(selectedRow);
                }
        	} else {
        		JOptionPane.showMessageDialog(null, "You are not authorized to delete Inventory Items", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }
        else if(actionCommand.equals("Parts")) {
        	if(session.isCanViewParts()){
        		PartsController partsController = new PartsController(partsModel, session);
        	} else {
        		JOptionPane.showMessageDialog(null, "You are not authorized to view Parts", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }
        else if(actionCommand.equals("Templates")) {
        	if(session.isCanViewProductTemplates()) {
        		TemplateController tc = new TemplateController(partsModel, templateModel, productTemplatePartsModel, session);
        	}
        	else {
        		JOptionPane.showMessageDialog(null, "You are not authorized to view the templates", "Error", JOptionPane.ERROR_MESSAGE);
        	}
        }

    }

    /**
     * Watches for mouse clicks on the inventory view
     */
    MouseListener inventoryMouseListener = new MouseAdapter()
    {
        boolean isAlreadyOneClick;
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if(isAlreadyOneClick && (inventoryView.getSelectedRow() == inventoryView.getLastSelectedRow()))
            {
                DetailController detailController = new DetailController
                        		(inventoryModel ,inventoryModel.getInventoryItem((inventoryView.getSelectedRow())), partsModel, 
                        		templateModel,productTemplatePartsModel, session);
                isAlreadyOneClick = false;
            }
            else
            {
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
