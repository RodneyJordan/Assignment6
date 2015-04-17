package models;

/**
 * Observes the table and reflects changes in the inventory model
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public interface TableObserver {

    public abstract void update();
}
