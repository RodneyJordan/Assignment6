package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.swing.JOptionPane;

import models.ConnectionGateway;
import models.InventoryModel;
import models.ItemConnectionGateway;
import models.PartsModel;
import models.ProductTemplatePartsGateway;
import models.TemplateGateway;
import session.AuthenticatorBeanRemote;
import session.Session;
import views.LoggedInView;
import views.LoginView;

/**
 * Controller for login
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class LoginController implements ActionListener, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A login view
	 */
	LoginView loginView;
	
	private AuthenticatorBeanRemote sessionState = null;
	
	Session session;
	
	private InventoryController c;
	
	private LoggedInView loggedInView;
	
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
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if(actionCommand.equalsIgnoreCase("Log In")) {
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
					c = new InventoryController(partsModel, inventoryModel, session);
					loginView.closeWindow();
					loggedInView = new LoggedInView(session);
					loggedInView.registerListeners(this);
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
		else if(actionCommand.equalsIgnoreCase("Cancel")) {
			loginView.userNameTextField.setText("");
			loginView.passwordTextField.setText("");
			loginView.userNameTextField.requestFocusInWindow();
		}
		else if(actionCommand.equalsIgnoreCase("Log Out")) {
			session = null;
			c.inventoryView.closeWindow();
			loggedInView.closeWindow();
			loginView = new LoginView();
			loginView.registerListeners(this);
		}
	}
	
	public void initSession() {
		try {
			Properties props = new Properties();
			props.put("org.omg.COBRA.ORBInitialHost", "localhost");
			props.put("org.omg.COBRA.ORBInitialPort", 3700);
			
			InitialContext itx = new InitialContext(props);
			sessionState = (AuthenticatorBeanRemote) itx.lookup("java:global/cs4743_session_bean/AuthenticatorBean!session.AuthenticatorBeanRemote");
		} catch(javax.naming.NamingException e1) {
			e1.printStackTrace();
		}
		//updateTitle();
	}
}
