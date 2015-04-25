package views;

import controllers.DetailController;
import models.InventoryItem;
import models.InventoryModel;
import models.ItemLogTableModel;

import javax.swing.*;

import session.LogEntry;

import java.awt.*;
import java.util.ArrayList;

/**
 * Creates the detail view window
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
@SuppressWarnings("serial")
public class DetailView extends JFrame
{
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
     * Buttons for the view
     */
    private JButton ok;
    private JButton addItem;
    private JButton edit;
    
    /**
     * The table for the view
     */
    private JTable table;
    
    /**
     * The scroll pane for the view
     */
    private JScrollPane scrollPane;

    /**
     * Layout for the panels
     */
    private GridLayout layout = new GridLayout(1,2);

    /**
     * Width of the window
     */
    private final int WINDOW_WIDTH = 400;

    /**
     * Height of the window
     */
    private final int WINDOW_HEIGHT = 250;

    /**
     * Constructor
     * @param item : The inventory item that details will be shown for.
     */
    public DetailView(InventoryItem item, InventoryModel model)
    {
        super("Inventory Item");

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new FlowLayout()); //GridLayout(6,1));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1));

        setUpPanel1(item.getIdNumber());
        infoPanel.add(panel1);

        setUpPanel2(item.getPart().getPartName());
        infoPanel.add(panel2);

        setUpPanel3(item.getLocation());
        infoPanel.add(panel3);

        setUpPanel4(item.getQuantityString());
        infoPanel.add(panel4);
        
        add(infoPanel);
        
        setUpPanel5(model.getLogList(item.getIdNumber()));
        add(panel5);

        setUpButtonPanel();
        add(buttonPanel);

        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * Sets up the first panel
     * @param partNumber
     */
    public void setUpPanel1(int idNumber)
    {
    	String idString = Integer.toString(idNumber);
        JLabel messageId = new JLabel("Id Number", SwingConstants.CENTER);
        JLabel infoId = new JLabel(idString, SwingConstants.CENTER);

        panel1 = new JPanel();
        panel1.setLayout(layout);
        panel1.add(messageId);
        panel1.add(infoId);
    }

    /**
     * Sets up the second panel
     * @param partName
     */
    public void setUpPanel2(String partName)
    {
        JLabel messagePartName = new JLabel("Part Name", SwingConstants.CENTER);
        JLabel infoPartName = new JLabel(partName, SwingConstants.CENTER);

        panel2 = new JPanel();
        panel2.setLayout(layout);
        panel2.add(messagePartName);
        panel2.add(infoPartName);
    }
    
    /**
     * Sets up the third panel
     * @param location
     */
    public void setUpPanel3(String location)
    {
    	JLabel messageLocation = new JLabel("Location", SwingConstants.CENTER);
    	JLabel infoLocation = new JLabel(location, SwingConstants.CENTER);
    	
    	panel3 = new JPanel();
    	panel3.setLayout(layout);
    	panel3.add(messageLocation);
    	panel3.add(infoLocation);
    }

    /**
     * Sets up the fourth panel
     * @param quantity
     */
    public void setUpPanel4(String quantity)
    {
        JLabel messageQuantity = new JLabel("Quantity", SwingConstants.CENTER);
        JLabel infoQuantity = new JLabel(quantity, SwingConstants.CENTER);

        panel4 = new JPanel();
        panel4.setLayout(layout);
        panel4.add(messageQuantity);
        panel4.add(infoQuantity);
    }
    
    /**
     * Sets up the fifth panel
     */
    public void setUpPanel5(ArrayList<LogEntry> list) {
    	ItemLogTableModel logModel = new ItemLogTableModel(list);
    	table = new JTable(logModel);
    	table.setPreferredScrollableViewportSize(new Dimension(350, 75));
    	table.setFillsViewportHeight(true);
    	scrollPane = new JScrollPane(table);
    	
    	panel5 = new JPanel();
    	panel5.add(scrollPane);
    	
    }
    
    /**
     * Sets up the button panel
     */
    public void setUpButtonPanel()
    {
        ok = new JButton("Ok");
        addItem = new JButton("Add");
        edit = new JButton("Edit");

        buttonPanel = new JPanel();
        buttonPanel.add(ok);
        buttonPanel.add(addItem);
        buttonPanel.add(edit);
    }

    /**
     * Registers the listeners for this view
     * @param detailController : The controller that the view is registered to.
     */
    public void registerListeners(DetailController detailController)
    {
        ok.addActionListener(detailController);
        addItem.addActionListener(detailController);
        edit.addActionListener(detailController);
    }

    /**
     * Closes the window
     */
    public void closeWindow()
    {
        dispose();
    }
}
