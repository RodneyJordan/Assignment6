package views;

import models.ProductTemplatePartsModel;
import models.TemplateModel;
import controllers.ProductTemplatePartsController;
import controllers.TemplateController;
import models.TableObserver;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Creates a product template parts view
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class ProductTemplatePartsView extends JFrame implements TableObserver {
	
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
	private int lastRowSelected;
	
	/**
	 * The scroll pane for this view
	 */
	private JScrollPane scrollPane;
	
	/**
	 * Width of the window
	 */
	private final int WINDOW_WIDTH = 300;
	
	/**
	 * Height of the window
	 */
	private final int WINDOW_HEIGHT = 500;
	
	/**
	 * Constructor
	 * @param templateModel
	 * @param templateMouseListener
	 */
	public ProductTemplatePartsView(ProductTemplatePartsModel model, MouseListener templatePartsMouseListener) {
		super("Template Parts");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new FlowLayout());
		
		setUpTable(model, templatePartsMouseListener);
		setUpTablePanel();
		setUpButtonPanel();
		
		add(tablePanel);
		add(buttonPanel);
		
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Sets up the table
	 * @param templateModel
	 * @param templateMouseListener
	 */
	private void setUpTable(ProductTemplatePartsModel model, MouseListener templatePartsMouseListener) {
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(250, 400));
		table.setFillsViewportHeight(true);
		table.addMouseListener(templatePartsMouseListener);
		table.addMouseListener(tableMouseListener);
		
		scrollPane = new JScrollPane(table);
		model.registerObserver(this);
	}
	
	/**
	 * Sets up the button panel
	 */
	private void setUpButtonPanel() {
		buttonPanel = new JPanel();
		add = new JButton("Add");
		ok = new JButton("Ok");
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
	 * Register the listeners for this view
	 * @param controller : The controller that this view is registered
	 */
	public void registerListeners(ProductTemplatePartsController controller) {
		ok.addActionListener(controller);
		add.addActionListener(controller);
		edit.addActionListener(controller);
		delete.addActionListener(controller);
	}
	
	/**
	 * Confirms deletion of a template
	 * @return true if selected template is to be deleted, false otherwise
	 */
	public boolean deleteTemplatePart() {
		if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this template?", "WARNING",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets a selected row
	 * @return selectedRow : int representation of the selected row
	 */
	public int getSelectedRow() {
		return selectedRow;
	}
	
	/**
	 * Gets the last selected row
	 * @return lastRowSelected : int representation of the last row selected
	 */
	public int getLastRowSelected() {
		return lastRowSelected;
	}
	
	/**
	 * Sets the last row selected
	 */
	public void setLastRowSelected(int row) {
		this.lastRowSelected = row;
	}
	
	/**
	 * Mouse Listener used to detect mouse click made on the table
	 */
	MouseListener tableMouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			setLastRowSelected(selectedRow);
			selectedRow = table.getSelectedRow();
		}
	};
	
	/**
	 * Updates the template view
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

