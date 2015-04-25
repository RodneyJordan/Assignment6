package views;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.LoginController;

/**
 * Generates a login view window
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
@SuppressWarnings("serial")
public class LoginView extends JFrame {
	
	/**
	 * Text fields for the view
	 */
	public JTextField userNameTextField;
	public JPasswordField passwordTextField;
	
	/**
	 * Panels for the view
	 */
	private JPanel panel1;
	private JPanel panel2;
	private JPanel buttonPanel;
	
	/**
	 * Buttons for the view
	 */
	private JButton login;
	private JButton cancel;
	
	/**
	 * Layout for the panels
	 */
	private GridLayout layout = new GridLayout(1, 2);
	
	/**
	 * Width of the window
	 */
	private final int WINDOW_WIDTH = 300;
	
	/**
	 * Height of the window
	 */
	private final int WINDOW_HEIGHT = 125;
	
	/**
	 * Constructor
	 */
	public LoginView() {
		super("Log In");
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new GridLayout(3, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setUpPanel1();
		add(panel1);
		
		setUpPanel2();
		add(panel2);
		
		setUpButtonPanel();
		add(buttonPanel);
		
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Sets up the first panel
	 */
	private void setUpPanel1() {
		JLabel messageUserName = new JLabel("User Name", SwingConstants.CENTER);
		userNameTextField = new JTextField(20);
		
		panel1 = new JPanel();
		panel1.setLayout(layout);
		panel1.add(messageUserName);
		panel1.add(userNameTextField);
	}
	
	/**
	 * Sets up the second panel
	 */
	private void setUpPanel2() {
		JLabel messgaePassword = new JLabel("Password", SwingConstants.CENTER);
		passwordTextField = new JPasswordField(20);
		
		panel2 = new JPanel();
		panel2.setLayout(layout);
		panel2.add(messgaePassword);
		panel2.add(passwordTextField);
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		login = new JButton("Log In");
		cancel = new JButton("Cancel");
		
		buttonPanel = new JPanel();
		buttonPanel.add(login);
		buttonPanel.add(cancel);
	}
	
	/**
	 * Registers the listeners for the button
	 * @param c : the login controller that the button is registered with
	 */
	public void registerListeners(LoginController c) {
		login.addActionListener(c);
		cancel.addActionListener(c);
	}
	
	/**
	 * Closes window
	 */
	public void closeWindow() {
		dispose();
	}
}
