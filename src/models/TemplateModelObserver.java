package models;

/**
 * Updates the templates when the template model changes
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public interface TemplateModelObserver {
	
	public abstract void update(TemplateModel templateModel);
}
