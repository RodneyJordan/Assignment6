package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import models.ConnectionGateway;
import models.InventoryModel;
import models.ItemConnectionGateway;
import models.PartsModel;
import models.ProductTemplatePartsGateway;
import models.Session;
import models.TemplateGateway;
import session.Authenticator;
import views.LoggedInView;
import views.LoginView;

/**
 * Controller for login
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class LoginController implements ActionListener {
	
	/**
	 * A login view
	 */
	LoginView loginView;
	
	Session session;
	
	/**
	 * Constructs a login controller
	 */
	public LoginController() {
		loginView = new LoginView();
		loginView.registerListeners(this);
	}
	
	public LoginController(Session session) {
		loginView = new LoginView();
		loginView.registerListeners(this);
	}
	
	/**
	 * Watches for a button press on the login view
	 */
	public void actionPerformed(ActionEvent e) {
		Authenticator authenticator = new Authenticator();
		try {
		session = authenticator.isAuthenticated(loginView.userNameTextField.getText(),
				loginView.passwordTextField.getText());
		} catch (Exception exception)
		{
			System.out.println(exception);
		}
		
		if(session != null){
			ConnectionGateway cg = new ConnectionGateway();
	    	ItemConnectionGateway icg = new ItemConnectionGateway();
	    	if(session.isCanViewInventoryItems()) {
	    		InventoryModel inventoryModel = new InventoryModel(icg);
	    		PartsModel partsModel = new PartsModel(inventoryModel, cg);
	    		 @SuppressWarnings("unused")
	 			InventoryController c = new InventoryController(partsModel, inventoryModel, session);
	    		loginView.closeWindow();
	    		@SuppressWarnings("unused")
				LoggedInView loggedInView = new LoggedInView(session);
	    	}
	    	else {
	    		if(JOptionPane.showConfirmDialog(null, "You are not authorized to view this inventory, would you like to sign in again?", "WARNING",
	                    JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
	    		loginView.closeWindow();
	    		}
	    	}
		}
	    else {
	    	if(JOptionPane.showConfirmDialog(null, "You are not authorized to do anything, would you like to sign in again?", "WARNING",
	                    JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
	    		loginView.closeWindow();
	    	}
	        
		}
	}
}
