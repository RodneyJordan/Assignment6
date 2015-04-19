package views;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controllers.LoginController;
import session.Session;

/**
 * Displays the user that is currently logged in
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
@SuppressWarnings("serial")
public class LoggedInView extends JFrame {
	
	/**
	 * The panel for this view
	 */
	private JPanel userName;
	private JPanel buttonPanel;
	
	/**
	 * The button for this panel
	 */
	private JButton logout;
	
	/**
	 * Width of the window
	 */
	private final int WINDOW_WIDTH = 300;
	
	/**
	 * Height of the window
	 */
	private final int WINDOW_HEIGHT = 100;
	
	public LoggedInView(Session session) {
		
		super("User Logged In");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2, 1));
		
		setUpPanelUserName(session);
		add(userName);
		
		setUpButtonPanel();
		add(buttonPanel);
		
		setVisible(true);
	}
	
	/**
	 * Sets up the panel
	 */
	private void setUpPanelUserName(Session session) {
		JLabel userInfo = new JLabel("Logged in as:", SwingConstants.CENTER);
		JLabel user = new JLabel(session.getUser().getFullName(), SwingConstants.CENTER);
		
		userName = new JPanel();
		userName.setLayout(new GridLayout(1, 2));
		userName.add(userInfo);
		userName.add(user);
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		buttonPanel = new JPanel();
		
		logout = new JButton("Log Out");
		buttonPanel.add(logout);
	}
	
	/**
	 * Register Listener
	 */
	public void registerListeners(LoginController c) {
		logout.addActionListener(c);
	}
	
	/**
	 * Closes window
	 */
	public void closeWindow() {
		dispose();
	}
}
