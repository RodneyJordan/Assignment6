package views;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.AddPartToTemplatePartController;
import controllers.DetailTemplateController;

/**
 * Creates a view to add a part to an existing template part
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class AddPartToTemplatePartView extends JFrame {
	
	/**
	 * Panels for the view
	 */
	private JPanel panel1;
	private JPanel panel2;
	private JPanel buttonPanel;
	
	/**
	 * Buttons for the view
	 */
	private JButton add;
	private JButton cancel;
	
	/**
	 * The combo box for the view
	 */
	public JComboBox<String> partsComboBox;
	public JTextField quantityTextField;
	
	/**
	 * Layout for the panels
	 */
	GridLayout layout = new GridLayout(1, 2);
	
	/**
	 * Width of the window
	 */
	private final int WINDOW_WIDTH = 300;
	
	/**
	 * Height for the window
	 */
	private final int WINDOW_HEIGHT = 125;
	
	/**
	 * Constructor
	 */
	public AddPartToTemplatePartView(String[] parts) {
		super("Add Part");
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new GridLayout(3, 1));
		
		setUpPanel1(parts);
		add(panel1);
		
		setUpPanel2();
		add(panel2);
		
		setUpButtonPanel();
		add(buttonPanel);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Sets up the first panel
	 */
	public void setUpPanel1(String[] parts) {
		JLabel messageParts = new JLabel("Parts", SwingConstants.CENTER);
		partsComboBox = new JComboBox<String>(parts);
		
		panel1 = new JPanel();
		panel1.setLayout(layout);
		panel1.add(messageParts);
		panel1.add(partsComboBox);
	}
	
	/**
	 * Sets up panel 2
	 */
	public void setUpPanel2() {
		JLabel messageQuantity = new JLabel("Quantity", SwingConstants.CENTER);
		quantityTextField = new JTextField(20);

        panel2 = new JPanel();
        panel2.setLayout(layout);
        panel2.add(messageQuantity);
        panel2.add(quantityTextField);
	}
	
	/**
	 * Sets up button panel
	 */
	public void setUpButtonPanel() {
		add = new JButton("Add");
		cancel = new JButton("Cancel");
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(layout);
		buttonPanel.add(add);
		buttonPanel.add(cancel);
	}
	
	/**
	 * Registers the listeners for the buttons
	 * @param c : the controller the buttons are registered with
	 */
	public void registerListeners(AddPartToTemplatePartController c) {
		add.addActionListener(c);
		cancel.addActionListener(c);
	}
	
	/**
	 * Closes the window
	 */
	public void closeWindow() {
		dispose();
	}
}
