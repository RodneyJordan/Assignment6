package views;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import models.InventoryItem;
import models.PartsModel;
import models.Part;
import models.ProductTemplate;
import models.TemplateModel;
import controllers.EditItemController;

/**
 * Creates an edit item view
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
@SuppressWarnings("serial")
public class EditItemView extends JFrame
{
    /**
     * Panels for this view
     */
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panelButton;

    /**
     * Text fields for this view
     */
    public JTextField quantityTextField;
    public JComboBox<String> part;
    public JComboBox<String> product;
    public JComboBox<String> partLocations;

    /**
     * Buttons for this view
     */
    private JButton editInventory;
    
    /**
     * for iteration 
     */
    private int i;

    /**
     * Layout for the panels in this view
     */
    GridLayout layout = new GridLayout(1, 2);

    /**
     * Width of the window
     */
    private final int WINDOW_WIDTH = 500;

    /**
     * Height of the window
     */
    private final int WINDOW_HEIGHT = 150;

    /**
     * Constructor
     * @param inventoryItem : The inventory item to be edited
     */
    public EditItemView(InventoryItem inventoryItem, PartsModel partsModel)
    {
        super("Edit Part In Inventory");

        // set the size of the frame
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // set the layout of the window
        setLayout(new GridLayout(4, 1));

        setUpPanel1(inventoryItem, partsModel);
        add(panel1);
        
        setUpPanel2(inventoryItem.getLocation());
        add(panel2);

        setUpPanel3(inventoryItem.getQuantityString());
        add(panel3);

        setUpButtonPanel();
        add(panelButton);

        // Display the window
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = 5;
        int y = (int) (rect.getMaxY() - WINDOW_HEIGHT) / 2;
        setLocation(x, y);
        setVisible(true);
    }
    
    public EditItemView(InventoryItem inventoryItem, TemplateModel templateModel) {
    	super("Edit Product In Inventory");

        // set the size of the frame
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // set the layout of the window
        setLayout(new GridLayout(4, 1));

        setUpPanel4(inventoryItem, templateModel);
        add(panel4);
        
        setUpPanel2(inventoryItem.getLocation());
        add(panel2);

        setUpPanel3(inventoryItem.getQuantityString());
        add(panel3);

        setUpButtonPanel();
        add(panelButton);

        // Display the window
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = 5;
        int y = (int) (rect.getMaxY() - WINDOW_HEIGHT) / 2;
        setLocation(x, y);
        setVisible(true);
    }

    /**
     * Set up the first panel
     * @param partNumber
     */
    private void setUpPanel1(InventoryItem inventoryItem, PartsModel partsModel)
    {
    	JLabel messagePart = new JLabel("Part", SwingConstants.CENTER);
     	ArrayList<Part> parts = partsModel.getList();
     	String[] partsInList;
     	partsInList = new String[parts.size()];
     	for(i = 0; i < parts.size(); i++) {
     		partsInList[i] = parts.get(i).getPartName(); 
     	}
     	part = new JComboBox<String>(partsInList);
     	part.setSelectedItem(inventoryItem.getPart().getPartName()); //issue
     	
     	panel1 = new JPanel();
     	panel1.setLayout(layout);
     	panel1.add(messagePart);
     	panel1.add(part);
    }
    
    /**
     * Sets up the second panel
     * @param partLocation
     */
    private void setUpPanel2(String partLocation)
    {
    	JLabel messageLocation = new JLabel("Part Location", SwingConstants.CENTER);
    	String[] locations =  {"Unknown", "Facility 1 Warehouse 1", "Facility 1 Warehouse 2", "Facility 2"};
    	
    	partLocations = new JComboBox<String>(locations);
    	
    	partLocations.setSelectedItem(partLocation);
    	
    	panel2 = new JPanel();
    	panel2.setLayout(layout);
    	panel2.add(messageLocation);
    	panel2.add(partLocations);
    }

    /**
     * Sets up the third panel
     * @param partName
     */
    private void setUpPanel3(String quantity)
    {
        JLabel messageQuantity = new JLabel("Quantity", SwingConstants.CENTER);
        quantityTextField = new JTextField(20);
        quantityTextField.setText(quantity);

        panel3 = new JPanel();
        panel3.setLayout(layout);
        panel3.add(messageQuantity);
        panel3.add(quantityTextField);
    }
    
    private void setUpPanel4(InventoryItem inventoryItem, TemplateModel templateModel)
    {
    	JLabel messageProduct = new JLabel("Product", SwingConstants.CENTER);
     	ArrayList<ProductTemplate> products = templateModel.getList();
     	String[] productsInList;
     	productsInList = new String[products.size()];
     	for(i = 0; i < products.size(); i++) {
     		productsInList[i] = products.get(i).getDescription(); 
     	}
     	product = new JComboBox<String>(productsInList);
     	product.setSelectedItem(inventoryItem.getProduct().getDescription());
     	
     	panel4 = new JPanel();
     	panel4.setLayout(layout);
     	panel4.add(messageProduct);
     	panel4.add(product);
    }

    /**
     * Sets up the button panel
     */
    private void setUpButtonPanel()
    {
        editInventory = new JButton("Edit");
        panelButton = new JPanel();
        panelButton.add(editInventory);
    }

    /**
     * Registers the listeners for this view
     * @param c : The controller the listeners are registered to.
     */
    public void registerListeners(EditItemController c)
    {
        editInventory.addActionListener(c);
        quantityTextField.addActionListener(c);
    }

    /**
     * Closes the window
     */
    public void closeWindow()
    {
        dispose();
    }
}
