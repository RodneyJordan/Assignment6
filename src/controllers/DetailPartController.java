package controllers;

import models.PartsModel;
import models.Part;
import views.DetailPartView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the parts detail
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class DetailPartController implements ActionListener {
	
	/**
	 * The detail parts view
	 */
	DetailPartView detailPartView;
	
	/**
	 * The parts model
	 */
	private PartsModel partsModel;
	
	/**
	 * The part
	 */
	private Part part;
	
	/**
	 * Constructor
	 * @param partsModel
	 * @param part
	 */
	public DetailPartController(PartsModel partsModel, Part part) {
		this.part = part;
		detailPartView = new DetailPartView(this.part);
		this.partsModel = partsModel;
		this.detailPartView.registerListeners(this);
	}
	
	/**
	 * Watches for buttons pressed on the detail part view
	 * @param e action performed
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("Ok")) {
			this.detailPartView.closeWindow();
		}
		else if(actionCommand.equals("Edit")) {
			this.detailPartView.closeWindow();
			@SuppressWarnings("unused")
			EditPartController editPartController = new EditPartController(partsModel, part);
		}
		else if(actionCommand.equals("Add")) {
			this.detailPartView.closeWindow();
			AddPartController addPartController = new AddPartController(partsModel);
		}
	}
}
