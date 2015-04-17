package models;

/**
 * Updates the parts when the parts model changes
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public interface PartsModelObserver {

	public abstract void update(PartsModel partsModel);
}
