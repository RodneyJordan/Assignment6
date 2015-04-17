package views;

import models.PartsModel;
import models.Part;
import models.ProductTemplate;
import models.TemplateModel;

import java.util.ArrayList;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controllers.AddItemController;

/**
 * Generates an add item view window
 * @author Rodney Jordan
 * @author Jacob Pagano
 */


@SuppressWarnings("serial")
public class AddItemView extends JFrame
{
    /**
     * Panels for the view
     */
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panelButton;

    /**
     * Text fields for the view
     */
    public JComboBox<String> part;
    public JComboBox<String> product;
    public JComboBox<String> option;
    public JTextField quantityTextField;
    public JTextField nameTextField;
    public JComboBox<String> partLocations;

    /**
     * Buttons for the view
     */
    private JButton addToInventory;
    
    /**
     * int for iteration through for loop
     */
    private int i;

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
    private final int WINDOW_HEIGHT = 200;

    /**
     * Constructor
     */
    public AddItemView(PartsModel partsModel, TemplateModel productModel)
    {    	
        super("Add to Inventory");

        // set the size of the frame
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // set the layout of the window
        setLayout(new GridLayout(6, 1));

        setUpPanel1(); // part 
        add(panel1);
        
        setUpPanel2(partsModel); 
        add(panel2);

        setUpPanel3(productModel);
        add(panel3);
        
        setUpPanel4();
        add(panel4);
        
        setUpPanel5();
        add(panel5);

        setUpButtonPanel();
        add(panelButton);

        // Display the window

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Sets up the first panel
     */
    private void setUpPanel1() {
    	JLabel messageOption = new JLabel("Part or Product", SwingConstants.CENTER);
    	String[] optionList = {"Part", "Product"};
    	
    	option = new JComboBox<String>(optionList);
    	
    	panel1 = new JPanel();
    	panel1.setLayout(layout);
    	panel1.add(messageOption);
    	panel1.add(option);
    	
    	option.addActionListener(new ActionListener(){
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			if(option.getSelectedIndex() == 0) {
    				panel2.setVisible(true);
    				panel3.setVisible(false);
    				panel5.setVisible(true);
    				repaint();
    			}
    			else {
    				panel3.setVisible(true);
    				panel2.setVisible(false);
    				panel5.setVisible(false);
    				repaint();
    			}
    		}
    	});
    }

    /**
     * Sets up the second panel
     */
    private void setUpPanel2(PartsModel partsModel)
    {
    	JLabel messagePart = new JLabel("Part", SwingConstants.CENTER);
    	ArrayList<Part> parts = partsModel.getList();
    	String[] partsInList;
    	partsInList = new String[parts.size()];
    	for(i = 0; i < parts.size(); i++) {
    		partsInList[i] = parts.get(i).getPartName(); 
    	}
    	part = new JComboBox<String>(partsInList);
     	
    	panel2 = new JPanel();
    	panel2.setLayout(layout);
    	panel2.add(messagePart);
    	panel2.add(part);
    	panel2.setVisible(false);
    	if(option.getSelectedIndex() == 1) {
    		panel2.setVisible(false);
    	}
    	else {
    		panel2.setVisible(true);
    		this.repaint();
    	} 
    }
    
    /**
     * Sets up the third panel
     * @param productModel
     */
    private void setUpPanel3(TemplateModel productModel) {
    	JLabel messageProduct = new JLabel("Product", SwingConstants.CENTER);
    	ArrayList<ProductTemplate> products = productModel.getList();
    	String[] productsInList;
    	productsInList = new String[products.size()];
    	for(i = 0; i < products.size(); i++) {
    		productsInList[i] = products.get(i).getProductNumber();
    	}
    	product = new JComboBox<String>(productsInList);
    	
    	panel3 = new JPanel();
    	panel3.setLayout(layout);
    	panel3.add(messageProduct);
    	panel3.add(product);
    	panel3.setVisible(false);
    	if(option.getSelectedIndex() == 0) {
    		panel3.setVisible(false);
    	}
    	else {
    		panel3.setVisible(true);
    		this.repaint();
    	}
    }
    
    /**
     * Sets up the fourth panel
     */
    private void setUpPanel4()
    {
    	 JLabel messageLocation = new JLabel("Part Location", SwingConstants.CENTER);
     	String[] locations =  {"Unknown", "Facility 1 Warehouse 1", "Facility 1 Warehouse 2", "Facility 2"};
     	
     	partLocations = new JComboBox<String>(locations);
     	
     	panel4 = new JPanel();
     	panel4.setLayout(layout);
     	panel4.add(messageLocation);
     	panel4.add(partLocations);
    }
    
    /**
     * Sets up the fifth panel
     */
    private void setUpPanel5()
    {
        JLabel messageQuantity = new JLabel("Quantity", SwingConstants.CENTER);
        quantityTextField = new JTextField(20);

        panel5 = new JPanel();
        panel5.setLayout(layout);
        panel5.add(messageQuantity);
        panel5.add(quantityTextField);
    }
    
    /**
     * Sets up the button panel
     */
    private void setUpButtonPanel()
    {
        addToInventory = new JButton("Add");

        panelButton = new JPanel();
        panelButton.add(addToInventory);
    }

    /**
     * Registers the listeners for the text fields and buttons
     * @param c the add item controller that the text fields and buttons are registered with
     */
    public void registerListeners(AddItemController c)
    {
        addToInventory.addActionListener(c);
        quantityTextField.addActionListener(c);
    }

    /**
     * Closes window
     */
    public void closeWindow()
    {
        dispose();
    }

}
