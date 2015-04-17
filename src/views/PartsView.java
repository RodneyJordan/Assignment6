package views;

import controllers.PartsController;
import models.PartsModel;
import models.TableObserver;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Creates a parts view
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class PartsView extends JFrame implements TableObserver {
	
	/**
	 * Panels for this view
	 */
	private JPanel tablePanel;
	private JPanel buttonPanel;
	
	/**
	 * Buttons for this view
	 */
	private JButton ok;
	private JButton add;
	private JButton edit;
	private JButton delete;
	
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
	private final int WINDOW_WIDTH = 800;
	
	/**
	 * Height of the window
	 */
	private final int WINDOW_HEIGHT = 500;
	
	/**
	 * Constructor
	 * @param inventoryModel
	 * @param partsMouseListener
	 */
	public PartsView(PartsModel partsModel, MouseListener partsMouseListener) {
		super("Parts");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new FlowLayout());
		
		setUpTable(partsModel, partsMouseListener);
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
	 * @param partsMouseListener
	 */
	private void setUpTable(PartsModel partsModel, MouseListener partsMouseListener) {
		table = new JTable(partsModel);
		table.setPreferredScrollableViewportSize(new Dimension(750, 400));
		table.setFillsViewportHeight(true);
		table.addMouseListener(partsMouseListener);
		table.addMouseListener(tableMouseListener);
		
		scrollPane = new JScrollPane(table);
		partsModel.registerObserver(this);
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		buttonPanel = new JPanel();
		add = new JButton("Add");		
		ok = new JButton("OK");
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		
		buttonPanel.add(add);
		buttonPanel.add(ok);
		buttonPanel.add(edit);
		buttonPanel.add(delete);
	}
	
	/**
	 * Sets up the table panel
	 */
	private void setUpTablePanel() {
		tablePanel = new JPanel();
		tablePanel.add(scrollPane);
	}
	
	/**
	 * Registers the listeners for this view
	 * @param controller : The controller that this view is registered with
	 */
	public void registerListeners(PartsController controller) {
		ok.addActionListener(controller);
		add.addActionListener(controller);
		edit.addActionListener(controller);
		delete.addActionListener(controller);
	}
	
	/**
	 * Confirms deletion of a part
	 * @return true if selected part is to be deleted, false otherwise
	 */
	public boolean deletePart() {
		if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this part?", "WARNING",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;		
	}
	
	/**
	 * Gets a selected Row
	 * @return selectedRow : int representation of the selected row
	 */
	public int getSelectedRow() {
		return selectedRow;
	}
	
	/**
	 * Gets a last selected row
	 * @return lastSelectedRow : int representation of the last selected row
	 */
	public int getLastSelectedRow() {
		return lastSelectedRow;
	}
	
	/**
	 * Sets the last selected row
	 */
	public void setLastSelectedRow(int row) {
		this.lastSelectedRow = row;
	}
	
	/**
	 * Mouse Listener used to detect mouse click made on the table
	 */
	MouseListener tableMouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			setLastSelectedRow(selectedRow);
			selectedRow = table.getSelectedRow();
		}
	};
	
	/**
	 * Updates the parts view
	 */
	@Override
	public void update() {
		table.updateUI();
	}
	
	/**
	 * Closes Window
	 */
	public void closeWindow() {
		dispose();
	}
}
