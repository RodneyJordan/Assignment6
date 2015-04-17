package models;

/**
 * Updates the inventory when the inventory model changes
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public interface InventoryModelObserver {

    public abstract void update(InventoryModel inventoryModel);
}
