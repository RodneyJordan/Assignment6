package controllers;

import models.InventoryItem;
import models.InventoryModel;
import models.ItemLogTableModel;
import models.Part;
import models.PartsModel;
import models.ProductTemplate;
import models.TemplateModel;
import views.EditItemView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller to edit an item
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class EditItemController implements ActionListener
{
    /**
     * The edit item views
     */
    private EditItemView editItemView;

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
    
    ItemLogTableModel tableModel;

    /**
     * The inventory item
     */
    private InventoryItem inventoryItem;
    
    /**
     * Constructor
     * @param inventoryModel
     * @param inventoryItem
     */
    public EditItemController(InventoryModel inventoryModel, InventoryItem inventoryItem, PartsModel partsModel, TemplateModel templateModel,
    		 ItemLogTableModel tableModel)
    {
        this.inventoryItem = inventoryItem;
        this.templateModel = templateModel;
        this.tableModel = tableModel;
        this.partsModel = partsModel;
        if(inventoryItem.getPart() != null) {
        	editItemView = new EditItemView(inventoryItem, partsModel);
        }
        else {
        	editItemView = new EditItemView(inventoryItem, templateModel);
        }
        this.inventoryModel = inventoryModel;
        editItemView.registerListeners(this);
    }
    
    /**
     * Watches for buttons pressed on the edit item view
     * @param e action performed
     */
    public void actionPerformed(ActionEvent e)
    {
    	String actionCommand = e.getActionCommand();
    	
    	if(actionCommand.equals("Edit")) {
    		if(inventoryItem.getPart() != null) {
    			if(!inventoryModel.editInventoryItem((Part) partsModel.getPart(editItemView.part.getSelectedIndex()),
    				(String) editItemView.partLocations.getItemAt(editItemView.partLocations.getSelectedIndex()),
    				Integer.parseInt(editItemView.quantityTextField.getText()), inventoryItem, tableModel)) {
    				editItemView.closeWindow();
    			}
    		}
    		else {
    			if(!inventoryModel.editInventoryItemProduct((ProductTemplate) templateModel.getTemplate(editItemView.product.getSelectedIndex()),
        				(String) editItemView.partLocations.getItemAt(editItemView.partLocations.getSelectedIndex()),
        				Integer.parseInt(editItemView.quantityTextField.getText()), inventoryItem, tableModel)) {
        				editItemView.closeWindow();
    			}
    		}
    	}
    	//Integer.parseInt(editItemView.quantityTextField.getText())
    }
}
