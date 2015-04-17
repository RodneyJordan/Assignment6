package models;

import javax.swing.*;

/**
 * Watches for errors, and displays the error String that is generated from validation
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class ErrorObserver implements InventoryModelObserver {

    /**
     * Constructor
     */
    public ErrorObserver(){

    }

    /**
     * Updates the inventory model
     * @param inventoryModel
     */
    @Override
    public void update(InventoryModel inventoryModel) {

        if(inventoryModel.getHasError()){
            JOptionPane.showMessageDialog(null, inventoryModel.getErrors(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
