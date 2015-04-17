package views;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.*;

import controllers.AddTemplateController;

/**
 * Generates an add template view window
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class AddTemplateView extends JFrame {
	
	/**
	 * Panels for the view
	 */
	private JPanel panel1;
	private JPanel panel2;
	private JPanel buttonPanel;
	
	/**
	 * Text fields for the view
	 */
	public JTextField productNumberTextField;
	public JTextField descriptionTextField;
	
	/**
	 * Buttons for the view
	 */
	private JButton addTemplate;
	private JButton cancel;
	
	/**
	 * Layout for the panels
	 */
	private GridLayout layout = new GridLayout(1, 2);
	
	/**
	 * Width of the window
	 */
	private final int WINDOW_WIDTH = 400;
	
	/**
	 * Height of the window
	 */
	private final int WINDOW_HEIGHT = 125;
	
	/**
	 * Constructor
	 */
	public AddTemplateView() {
		super("Add a Template");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new GridLayout(3,1));
		
		setUpPanel1();
		add(panel1);
		
		setUpPanel2();
		add(panel2);
		
		setUpButtonPanel();
		add(buttonPanel);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - WINDOW_WIDTH;
        int y = (int) (rect.getMaxY() - WINDOW_HEIGHT) / 2;
        setLocation(x, y);
        setVisible(true);
	}
	
	/**
	 * Sets up the first panel
	 */
	private void setUpPanel1() {
		JLabel messageProductNumber = new JLabel("Product Number", SwingConstants.CENTER);
		productNumberTextField = new JTextField(20);
		
		panel1 = new JPanel();
		panel1.setLayout(layout);
		panel1.add(messageProductNumber);
		panel1.add(productNumberTextField);
	}
	
	/**
	 * Sets up the second panel
	 */
	private void setUpPanel2() {
		JLabel messageDescription = new JLabel("Description", SwingConstants.CENTER);
		descriptionTextField = new JTextField(255);
		
		panel2 = new JPanel();
		panel2.setLayout(layout);
		panel2.add(messageDescription);
		panel2.add(descriptionTextField);
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		addTemplate = new JButton("Add");
		cancel = new JButton("Cancel");
		
		buttonPanel = new JPanel();
		buttonPanel.add(addTemplate);
		buttonPanel.add(cancel);
	}
	
	/**
	 * Registers the listeners for the buttons
	 * @param c the add template controller that the button is registered with
	 */
	public void registerListeners(AddTemplateController c) {
		addTemplate.addActionListener(c);
		cancel.addActionListener(c);
		
	}
	
	/**
	 * Closes the window
	 */
	public void closeWindow() {
		dispose();
	}
}
