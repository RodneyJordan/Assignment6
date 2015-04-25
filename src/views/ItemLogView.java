package views;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import models.ItemLogTableModel;

public class ItemLogView extends JFrame {
	
	/**
	 * Panels for the view
	 */
	private JPanel tablePanel;
	private JPanel buttonPanel;
	
	/**
	 * Button for the view
	 */
	private JButton ok;
	
	/**
	 * The table for the view
	 */
	private JTable table;
	
	/**
	 * The scroll pane for the view
	 */
	private JScrollPane scrollPane;
	
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
	public ItemLogView(ItemLogTableModel model) {
		super("Log File");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new FlowLayout());
		
		setUpTable(model);
		setUpTablePanel();
		setUpButtonPanel();
		
		add(tablePanel);
		add(buttonPanel);
		
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Set up the table
	 */
	private void setUpTable(ItemLogTableModel model) {
		
	}
	
	/**
	 * Sets up the table panel
	 */
	private void setUpTablePanel() {
		
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		
	}
}
