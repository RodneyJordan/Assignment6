package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.swing.JOptionPane;

import models.ConnectionGateway;
import models.InventoryModel;
import models.ItemConnectionGateway;
import models.PartsModel;
import models.ProductTemplatePartsGateway;
import models.Session;
import models.TemplateGateway;
import session.AuthenticatorBeanRemote;
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
	
	private AuthenticatorBeanRemote sessionState = null;
	
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
		//Authenticator authenticator = new Authenticator();
		initSession();
		try {
		session = sessionState.isAuthenticated(loginView.userNameTextField.getText(),
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
	
	public void initSession() {
		try {
			Properties props = new Properties();
			props.put("org.omg.COBRA.ORBInitialHost", "localhost");
			props.put("org.omg.COBRA.ORBInitialPort", 3700);
			
			InitialContext itx = new InitialContext(props);
			sessionState = (AuthenticatorBeanRemote) itx.lookup("java:global/cs4743_session_bean/StateBean!session.AuthenticatorBeanRemote");
		} catch(javax.naming.NamingException e1) {
			e1.printStackTrace();
		}
		//updateTitle();
	}
}
