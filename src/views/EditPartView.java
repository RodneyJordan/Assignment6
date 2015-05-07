package views;

import controllers.EditPartController;
import models.Part;

import javax.swing.*;

import java.awt.*;

/**
 * Creates a view to edit a part
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class EditPartView extends JFrame {
	
	/**
	 * Panels for this view
	 */
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel buttonPanel;
	
	/**
	 * Buttons for this view
	 */
	private JButton editPart;
	
	/**
	 * Text fields for this view
	 */
	public JTextField partNumberTextField;
	public JTextField partNameTextField;
	public JTextField vendorTextField;
	public JTextField externalPartNumberTextField;
	public JComboBox<String> unitOfQuantityCombo;
	
	/**
	 * Layout for the panels of this view
	 */
	GridLayout layout = new GridLayout(1, 2);
	
	/**
	 * Width of the window
	 */
	private final int WINDOW_WIDTH = 510;
	
	/**
	 * Height of the window
	 */
	private final int WINDOW_HEIGHT = 175;
	
	/**
	 * Constructor
	 * @param part : The part to be edited
	 */
	public EditPartView(Part part) {
		super("Edit " + part.getPartName());
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		setLayout(new GridLayout(5, 1));
		
		setUpPanel1(part.getPartName());
		add(panel1);
		
		setUpPanel2(part.getVendor());
		add(panel2);
		
		setUpPanel3(part.getUnitOfQuantity());
		add(panel3);
		
		setUpPanel4(part.getExternalPartNumber());
		add(panel4);
		
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
	 * Sets up panel 1
	 * @param partName
	 */
	private void setUpPanel1(String partName) {
		JLabel messagePartName = new JLabel("Part Name", SwingConstants.CENTER);
		partNameTextField = new JTextField(20);
		partNameTextField.setText(partName);
		
		panel1 = new JPanel();
		panel1.setLayout(layout);
		panel1.add(messagePartName);
		panel1.add(partNameTextField);
	}
	
	/**
	 * Sets up panel 2
	 * @param vendor
	 */
	private void setUpPanel2(String vendor) {
		JLabel messageVendor = new JLabel("Vendor", SwingConstants.CENTER);
		vendorTextField = new JTextField(20);
		vendorTextField.setText(vendor);
		
		panel2 = new JPanel();
		panel2.setLayout(layout);
		panel2.add(messageVendor);
		panel2.add(vendorTextField);
	}
	
	/**
	 * Sets up panel 3
	 * @param unityOfQuantity
	 */
	private void setUpPanel3(String unitOfQuantity) {
		JLabel messageUnitType = new JLabel("Unit of Quantity", SwingConstants.CENTER);
    	String[] unitTypes = {"Unknown", "Linear Feet", "Pieces"};
    	
    	unitOfQuantityCombo = new JComboBox<String>(unitTypes);
    	
    	unitOfQuantityCombo.setSelectedItem(unitOfQuantity);
    	
    	
    		
    	panel3 = new JPanel();
    	panel3.setLayout(layout);
    	panel3.add(messageUnitType);
    	panel3.add(unitOfQuantityCombo);
	}
	
	/**
	 * Sets up panel 4
	 * @param externalPartNumber
	 */
	private void setUpPanel4(String externalPartNumber) {
		JLabel messageExternalPartNumber = new JLabel(" External Part Number", SwingConstants.CENTER);
		externalPartNumberTextField = new JTextField(20);
		externalPartNumberTextField.setText(externalPartNumber);
		
		panel4 = new JPanel();
		panel4.setLayout(layout);
		panel4.add(messageExternalPartNumber);
		panel4.add(externalPartNumberTextField);
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		editPart = new JButton("Edit Part");
		buttonPanel = new JPanel();
		buttonPanel.add(editPart);
	}
	
	/**
	 * Registers the listeners for this view
	 * @param c : The controller the listeners are registered to.
	 */
	public void registerListeners(EditPartController c) {
		editPart.addActionListener(c);
		partNameTextField.addActionListener(c);
		vendorTextField.addActionListener(c);
		externalPartNumberTextField.addActionListener(c);
	}
	
	/**
	 * Closes the window
	 */
	public void closeWindow() {
		dispose();
	}
}
