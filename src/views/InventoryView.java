package views;

import controllers.InventoryController;
import models.InventoryModel;
import models.TableObserver;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Creates a inventory view.
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class InventoryView extends JFrame implements TableObserver
{
    /**
     * Panels for this view
     */
    private JPanel tablePanel;
    private JPanel buttonPanel;

    /**
     * Buttons for this view
     */
    private JButton addToTable;
    private JButton editItem;
    private JButton deleteItem;
    private JButton parts;
    private JButton templates;

    /**
     * The table for this view
     */
    private JTable table;

    /**
     * The row that is selected
     */
    private int selectedRow;

    /**
     * The last row selected
     */
    private int lastSelectedRow;

    /**
     * The scroll pane for this view
     */
    private JScrollPane scrollPane;

    /**
     * Width of the window
     */
    private final int WINDOW_WIDTH = 1000;

    /**
     * Height of the window
     */
    private final int WINDOW_HEIGHT = 600;

    /**
     * Constructor
     * @param inventoryModel
     * @param inventoryMouseListener
     */
    public InventoryView(InventoryModel inventoryModel, MouseListener inventoryMouseListener)
    {
        // Set up frame
        super("Inventory");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUpTable(inventoryModel, inventoryMouseListener);
        setUpTablePanel();
        setUpButtonPanel();

        add(tablePanel);
        add(buttonPanel);

        setVisible(true);
        setLocationRelativeTo(null);

    }

    /**
     * Sets up the table
     * @param inventoryModel
     * @param inventoryMouseListener
     */
    private void setUpTable(InventoryModel inventoryModel, MouseListener inventoryMouseListener)
    {
        table = new JTable(inventoryModel);
        table.setPreferredScrollableViewportSize(new Dimension(950, 500));
        table.setFillsViewportHeight(true);
        table.addMouseListener(inventoryMouseListener);
        table.addMouseListener(tableMouseListener);

        scrollPane = new JScrollPane(table);
        inventoryModel.registerObserver(this);
    }

    /**
     * Sets up the button panel
     */
    private void setUpButtonPanel()
    {
        buttonPanel = new JPanel(new GridLayout(1,3));

        editItem = new JButton("Edit");
        addToTable = new JButton("Add");
        deleteItem = new JButton("Delete");
        parts = new JButton("Parts");
        templates = new JButton("Templates");

        buttonPanel.add(editItem);
        buttonPanel.add(addToTable);
        buttonPanel.add(deleteItem);
        buttonPanel.add(parts);
        buttonPanel.add(templates);
    }

    /**
     * Sets up the table panel
     */
    private void setUpTablePanel()
    {
        tablePanel = new JPanel();
        tablePanel.add(scrollPane);
    }

    /**
     * Registers the listeners for this view
     * @param c : The inventory controller this view is registered with
     */
    public void registerListeners(InventoryController c)
    {
        addToTable.addActionListener(c);
        editItem.addActionListener(c);
        deleteItem.addActionListener(c);
        parts.addActionListener(c);
        templates.addActionListener(c);
    }

    /**
     * Confirms deletion of an item
     * @return true if selected item is to be deleted, false otherwise
     */
    public boolean deleteItem()
    {
        if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?", "WARNING",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            return true;
        }
        return false;
    }

    /**
     * Gets a selected row
     * @return integer representation of the selected row
     */
    public int getSelectedRow()
    {
        return selectedRow;
    }

    /**
     * Gets the last selected row
     * @return integer representation of the last selected row
     */
    public int getLastSelectedRow()
    {
        return lastSelectedRow;
    }

    /**
     * Sets the last selected row
     * @param row
     */
    public void setLastSelectedRow(int row)
    {
        this.lastSelectedRow = row;
    }

    /**
     * Mouse listener used to detect mouse click made on the table
     */
    MouseListener tableMouseListener = new MouseAdapter()
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            setLastSelectedRow(selectedRow);
            selectedRow = table.getSelectedRow();
        }
    };

    /**
     * Updates the inventory view
     */
    @Override
    public void update() {
        table.updateUI();
    }
}
