package views;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.*;

import controllers.AddProductTemplatePartsController;

/**
 * Generates an add product template parts view window
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class AddProductTemplatePartsView extends JFrame {

	/**
	 * Panels for the view
	 */
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel buttonPanel;
	
	/**
	 * Text fields for the view
	 */
	public JTextField templateIdTextField;
	public JTextField partIdTextField;
	public JTextField quantityTextField;
	
	/**
	 * Buttons for the view
	 */
	private JButton addTemplatePart;
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
	private final int WINDOW_HEIGHT = 200;
	
	/**
	 * Constructor
	 */
	public AddProductTemplatePartsView() {
		super("Add a Template Part");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new GridLayout(4,1));
		
		setUpPanel1();
		add(panel1);
		
		setUpPanel2();
		add(panel2);
		
		setUpPanel3();
		add(panel3);
		
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
		JLabel messageTemplateId = new JLabel("Template Id", SwingConstants.CENTER);
		templateIdTextField = new JTextField(20);
		
		panel1 = new JPanel();
		panel1.setLayout(layout);
		panel1.add(messageTemplateId);
		panel1.add(templateIdTextField);
	}
	
	/**
	 * Sets up the second panel
	 */
	private void setUpPanel2() {
		JLabel messagePartId = new JLabel("Part Id", SwingConstants.CENTER);
		partIdTextField = new JTextField(20);
		
		panel2 = new JPanel();
		panel2.setLayout(layout);
		panel2.add(messagePartId);
		panel2.add(partIdTextField);
	}
	
	/**
	 * Sets up the third panel
	 */
	private void setUpPanel3() {
		JLabel messageQuantity = new JLabel("Quantity", SwingConstants.CENTER);
		quantityTextField = new JTextField(20);
		
		panel3 = new JPanel();
		panel3.setLayout(layout);
		panel3.add(messageQuantity);
		panel3.add(quantityTextField);
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		addTemplatePart = new JButton("Add");
		cancel = new JButton("Cancel");
		
		buttonPanel = new JPanel();
		buttonPanel.add(addTemplatePart);
		buttonPanel.add(cancel);
	}
	
	/**
	 * Registers the listeners for the buttons
	 * @param c the add template controller that the button is registered with
	 */
	public void registerListeners(AddProductTemplatePartsController c) {
		addTemplatePart.addActionListener(c);
		cancel.addActionListener(c);
		
	}
	
	/**
	 * Closes the window
	 */
	public void closeWindow() {
		dispose();
	}
}
