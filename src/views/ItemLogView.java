package views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import session.ItemLogGatewayBeanRemote;
import controllers.InventoryController;
import models.InventoryModel;
import models.ItemLogTableModel;
import models.LogViewObserver;

@SuppressWarnings("serial")
public class ItemLogView extends JFrame implements LogViewObserver {
	
	/**
	 * Panels for the view
	 */
	private JPanel tablePanel;
	private JPanel buttonPanel;
	
	/**
	 * Button for the view
	 */
	private JButton close;
	
	/**
	 * The table for the view
	 */
	private JTable table;
	
	/**
	 * The scroll pane for the view
	 */
	private JScrollPane scrollPane;
	
	ItemLogTableModel itemLogTableModel;
	
	InventoryModel inventoryModel;
		
	/**
	 * The width of the window
	 */
	private final int WINDOW_WIDTH = 600;
	
	/**
	 * The height of the window
	 */
	private final int WINDOW_HEIGHT = 500;
	
	/**
	 * Constructor
	 */
	public ItemLogView(ItemLogTableModel model, InventoryModel inventoryModel, int id) {
		super("Log File");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new FlowLayout());
		
		setUpTable(model);
		setUpTablePanel();
		setUpButtonPanel();
		
		add(tablePanel);
		add(buttonPanel);
		this.itemLogTableModel = model;
			
		setVisible(true);
		setLocationRelativeTo(null);
		this.inventoryModel = inventoryModel;
		this.itemLogTableModel.registerLogViewObserver(this);
	}
	
	/**
	 * Set up the table
	 */
	private void setUpTable(ItemLogTableModel model) {
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(550, 400));
		table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(table);
	}
	
	/**
	 * Sets up the table panel
	 */
	private void setUpTablePanel() {
		tablePanel = new JPanel();
		tablePanel.add(scrollPane);
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		close = new JButton("Close");
		buttonPanel = new JPanel();
		
		buttonPanel.add(close);
	}
	
	/**
	 * Register the listener for this view
	 * @param c : Inventory Controller that listens for button presses
	 */
	public void registerListeners(InventoryController c) {
		close.addActionListener(c);
	}
	
	/**
	 * Closes the window
	 */
	public void closeWindow() {
		this.dispose();
	}

	@Override
	public void update() {
		table.updateUI();
		table.repaint();
		table.revalidate();
	}
}
