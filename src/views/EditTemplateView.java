package views;

import controllers.EditTemplateController;
import models.ProductTemplate;

import javax.swing.*;

import java.awt.*;

/**
 * Creates a view to edit a template
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class EditTemplateView extends JFrame {
	
	/**
	 * Panels for this view
	 */
	private JPanel panel1;
	private JPanel panel2;
	private JPanel buttonPanel;
	
	/**
	 * Buttons for this view
	 */
	private JButton editTemplate;
	private JButton cancel;
	
	/**
	 * Text fields for this view
	 */
	public JTextField productNumberTextField;
	public JTextField descriptionTextField;
	
	/**
	 * Layout for the panels of this view
	 */
	GridLayout layout = new GridLayout(1, 2);
	
	/**
	 * Width of this window
	 */
	private final int WINDOW_WIDTH = 510;
	
	/**
	 * Height of this window
	 */
	private final int WINDOW_HEIGHT = 125;
	
	/**
	 * Constructor
	 */
	public EditTemplateView(ProductTemplate template) {
		super("Edit " + template.getProductNumber());
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setLayout(new GridLayout(3, 1));
		
		setUpPanel1(template.getProductNumber());
		add(panel1);
		
		setUpPanel2(template.getDescription());
		add(panel2);
		
		setUpButtonPanel();
		add(buttonPanel);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
		int x = 5;
		int y = (int) (rect.getMaxY() - WINDOW_HEIGHT) / 2;
		setLocation(x, y);
		setVisible(true);
	}
	
	/**
	 * Sets up the first panel
	 */
	private void setUpPanel1(String productNumber) {
		JLabel messageProductNumber = new JLabel("Product Number", SwingConstants.CENTER);
		productNumberTextField = new JTextField(20);
		productNumberTextField.setText(productNumber);
		
		panel1 = new JPanel();
		panel1.setLayout(layout);
		panel1.add(messageProductNumber);
		panel1.add(productNumberTextField);
	}
	
	/**
	 * Sets up the second panel
	 */
	private void setUpPanel2(String description) {
		JLabel messageDescription = new JLabel("Description", SwingConstants.CENTER);
		descriptionTextField = new JTextField(255);
		descriptionTextField.setText(description);
		
		panel2 = new JPanel();
		panel2.setLayout(layout);
		panel2.add(messageDescription);
		panel2.add(descriptionTextField);
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		editTemplate = new JButton("Edit");
		cancel = new JButton("Cancel");
		
		buttonPanel = new JPanel();
		buttonPanel.add(editTemplate);
		buttonPanel.add(cancel);
	}
	
	/**
	 * Registers the listeners for this view
	 * @param c : The controller the listeners are registered with
	 */
	public void registerListeners(EditTemplateController c) {
		editTemplate.addActionListener(c);
		cancel.addActionListener(c);
		productNumberTextField.addActionListener(c);
		descriptionTextField.addActionListener(c);
	}
	
	/**
	 * Closes the window
	 */
	public void closeWindow() {
		dispose();
	}
}