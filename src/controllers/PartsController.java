package controllers;

import models.PartsModel;
import models.Part;
import models.Session;
import views.PartsView;

import java.util.Timer;
import java.awt.event.*;
import java.util.TimerTask;

import javax.swing.JOptionPane;

/**
 * Controller for the Parts
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class PartsController implements ActionListener {
	
	/**
	 * The Parts View
	 */
	PartsView partsView;
	
	/**
	 * The parts model
	 */
	PartsModel partsModel;
	
	/**
	 * The session
	 */
	private Session session;
	
	/**
	 * Constructor
	 */
	public PartsController(PartsModel partsModel, Session session) {
		this.partsModel = partsModel;
		this.partsView = new PartsView(partsModel, partsMouseListener);
		this.partsView.registerListeners(this);
		this.session = session;
	}
	
	/**
	 * Watches for button presses on the parts view
	 * @param e : action performed
	 */
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand.equals("OK")) {
			partsView.closeWindow();
		}
		else if(actionCommand.equals("Add")) {
			if(session.isCanAddParts()){
				@SuppressWarnings("unused")
				AddPartController a = new AddPartController(partsModel);
			} else {
				JOptionPane.showMessageDialog(null, "You are not authorized to add Parts", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(actionCommand.equals("Edit")) {
			if(session.isCanEditParts()){
				int selectedRow = partsView.getSelectedRow();
				Part part = partsModel.getPart(selectedRow);
				EditPartController editPartController = new EditPartController(partsModel, part);
			} else {
				JOptionPane.showMessageDialog(null, "You are not authorized to edit Parts", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(actionCommand.equals("Delete")) {
			if(session.isCanEditParts()){
				if(partsView.deletePart())
	            {
	                int selectedRow = partsView.getSelectedRow();
	                partsModel.removePart(selectedRow);
	            }
			} else {
				JOptionPane.showMessageDialog(null, "You are not authorized to delete Parts", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Watches for mouse clicks on the parts view
	 */
	MouseListener partsMouseListener = new MouseAdapter() {
		boolean isAlreadyOneClick;
		@Override
		public void mouseClicked(MouseEvent e) {
			if(isAlreadyOneClick && (partsView.getSelectedRow() == partsView.getLastSelectedRow())) {
				DetailPartController detailPartController = new DetailPartController
						(partsModel, partsModel.getPart(partsView.getSelectedRow()));
				isAlreadyOneClick = false;
			}
			else {
				isAlreadyOneClick = true;
				Timer t = new Timer("doubleClickTimer", false);
				System.out.println("working");
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