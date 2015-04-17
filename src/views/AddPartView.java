package views;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.AddPartController;

/**
 * Generates an add part view window
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
@SuppressWarnings("serial")
public class AddPartView extends JFrame {
   
	/**
     * Panels for the view
     */
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel buttonPanel;
    
    /**
     * Text fields for the view
     */
    public JTextField partNumberTextField;
    public JTextField partNameTextField;
    public JTextField vendorTextField;
    public JTextField externalPartNumberTextField;
    
    /**
     * Combo boxes for the view
     */
    public JComboBox<String> unitOfQuantityBox;
    
    /**
     * Buttons for the view
     */
    private JButton addPart;
    
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
    public AddPartView() {
    	super("Add a Part");
    	
    	setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    	
    	setLayout(new GridLayout(6, 1));
    	
    	setUpPanel1();
    	add(panel1);
    	
    	setUpPanel2();
    	add(panel2);
    	
    	setUpPanel3();
    	add(panel3);
    	
    	setUpPanel4();
    	add(panel4);
    	
    	setUpPanel5();
    	add(panel5);
    	
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
    	JLabel messagePartNumber = new JLabel("Part Number", SwingConstants.CENTER);
    	partNumberTextField = new JTextField(20);
    	
    	panel1 = new JPanel();
    	panel1.setLayout(layout);
    	panel1.add(messagePartNumber);
    	panel1.add(partNumberTextField);
    }
    
    /**
     * Sets up the second panel
     */
    private void setUpPanel2() {
    	JLabel messagePartName = new JLabel("Part Name", SwingConstants.CENTER);
        partNameTextField = new JTextField(20);

        panel2 = new JPanel();
        panel2.setLayout(layout);
        panel2.add(messagePartName);
        panel2.add(partNameTextField);
    }
    
    /**
     * Sets up the third panel
     */
    private void setUpPanel3() {
    	JLabel messageVendor = new JLabel("Vendor", SwingConstants.CENTER);
        vendorTextField = new JTextField(20);

        panel3 = new JPanel();
        panel3.setLayout(layout);
        panel3.add(messageVendor);
        panel3.add(vendorTextField);
    }
    
    /**
     * Sets up the fourth panel
     */
    private void setUpPanel4() {
    	JLabel messageUnitType = new JLabel("Unit of Quantity", SwingConstants.CENTER);
    	String[] unitTypes = {"Unknown", "Linear Feet", "Pieces"};
    	
    	unitOfQuantityBox = new JComboBox<String>(unitTypes);
    	
    	panel4 = new JPanel();
    	panel4.setLayout(layout);
    	panel4.add(messageUnitType);
    	panel4.add(unitOfQuantityBox);
    }
    
    /**
     * Sets up the fifth panel
     */
    private void setUpPanel5() {
    	JLabel messageExternalPartNumber = new JLabel("External Part Number", SwingConstants.CENTER);
    	externalPartNumberTextField = new JTextField(50);
    	
    	panel5 = new JPanel();
    	panel5.setLayout(layout);
    	panel5.add(messageExternalPartNumber);
    	panel5.add(externalPartNumberTextField);
    }
    
    /**
     * Set up the button panel
     */
    private void setUpButtonPanel() {
    	addPart = new JButton("Add");
    	
    	buttonPanel = new JPanel();
    	buttonPanel.add(addPart);
    }
    
    /**
     * Registers the listeners for the text fields and buttons
     * @param c the add part controller that the button is registered with
     */
    public void registerListeners(AddPartController c) {
    	addPart.addActionListener(c);
    }
    
    /**
     * Closes window
     */
    public void closeWindow() {
    	dispose();
    }

}
