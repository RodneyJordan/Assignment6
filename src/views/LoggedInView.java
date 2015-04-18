package views;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
		setLayout(new GridLayout(1, 1));
		
		setUpPanelUserName(session);
		add(userName);
		
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
	
	
}
