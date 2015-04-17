package views;

import controllers.EditProductTemplatePartsController;
import models.DetailTemplateModel;
import models.ProductTemplate;
import models.ProductTemplatePartsModel;
import models.TableObserver;
import models.ProductTemplateParts;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Creates a view to edit a template
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class EditProductTemplatePartsView extends JFrame implements TableObserver {
   
   /**
    * Panels for this view
    */
   private JPanel panel1;
   private JPanel panel2;
   private JPanel panel3;
   private JPanel partsTablePanel;
   private JPanel tableButtonPanel;
   private JPanel buttonPanel;
   
   /**
    * Buttons for this view
    */
   private JButton editTemplate;
   private JButton addPart;
   private JButton removePart;
   private JButton cancel;
   
   /**
    * Text fields for this view
    */
   public JTextField productNumberTextField;
   public JTextField descriptionTextField;
   public JTextField quantityTextField;
   
   /**
    * Product Template Parts Model
    */
   private ProductTemplatePartsModel productTemplatePartsModel;
   
   /**
    * Layout for the panels of this view
    */
   GridLayout layout = new GridLayout(1, 2);
   
   /**
    * The row that is selected
    */
   private int selectedRow;
   
   /**
    * The last selected row
    */
   private int lastSelectedRow;
   
   /**
    * A table for the parts associated with this template
    */
   private JTable table;
   
   /**
    * The scroll pane for the list of parts
    */
   private JScrollPane scrollPane;
   
   /**
    * Width of this window
    */
   private final int WINDOW_WIDTH = 600;
   
   /**
    * Height of this window
    */
   private final int WINDOW_HEIGHT = 575;
   
   /**
    * Constructor
    */
   public EditProductTemplatePartsView(ProductTemplate template, ProductTemplateParts templatePart, 
         DetailTemplateModel detailTemplateModel, ProductTemplatePartsModel productTemplatePartsModel, MouseListener editTemplatePartsMouseListener) {
      
      super("Edit " + template.getProductNumber());
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      //setLayout(new GridLayout(5, 1));
      setLayout(new FlowLayout());
      this.productTemplatePartsModel = productTemplatePartsModel;
      
      setUpPanel1(template.getProductNumber());
      add(panel1);
      
      setUpPanel2(template.getDescription());
      add(panel2);
      
      
      setUpPartsTablePanel(detailTemplateModel, editTemplatePartsMouseListener);
      add(partsTablePanel);
      
      setUpTableButtons();
      add(panel3);
      
      JPanel panel4 = new JPanel();
      JLabel label4 = new JLabel("-----------------------------------------------------------------", SwingConstants.CENTER);
      panel4.add(label4);
      add(panel4);
      
      setUpButtonPanel();
      add(buttonPanel);
      
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
      Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
      int x = 5;
      int y = (int) (rect.getMaxY() - WINDOW_HEIGHT) / 2;
      setLocation(x, y);
      setVisible(true);
   }
   
   /**
    * Sets up the first panel
    */
   private void setUpPanel1(String productNumber) {
      JLabel messageProductNumber = new JLabel("Product Number", SwingConstants.CENTER);
      productNumberTextField = new JTextField(20);
      productNumberTextField.setText(productNumber);
      
      panel1 = new JPanel();
      panel1.setSize(550, 125);
      panel1.setLayout(layout);
      panel1.add(messageProductNumber);
      panel1.add(productNumberTextField);
   }
   
   /**
    * Sets up the second panel
    */
   private void setUpPanel2(String description) {
      JLabel messageDescription = new JLabel("Description", SwingConstants.CENTER);
      descriptionTextField = new JTextField(20);
      descriptionTextField.setText(description);
      
      panel2 = new JPanel();
      panel2.setSize(550, 125);
      panel2.setLayout(layout);
      panel2.add(messageDescription);
      panel2.add(descriptionTextField);
   }
   
   /**
    * Sets up the parts table panel
    */
   private void setUpPartsTablePanel(DetailTemplateModel detailTemplateModel, MouseListener editTemplatePartsMouseListener) {
      table = new JTable(detailTemplateModel);
      table.setPreferredScrollableViewportSize(new Dimension(550, 300));
      table.setFillsViewportHeight(true);
      table.addMouseListener(editTemplatePartsMouseListener);
      table.addMouseListener(tableMouseListener);
      
      scrollPane = new JScrollPane(table);
      productTemplatePartsModel.registerObserver(this);
      
      partsTablePanel = new JPanel();
      partsTablePanel.setSize(550, 300);
      partsTablePanel.add(scrollPane);
      detailTemplateModel.registerObserver(this);
   }
   
   /**
    * Sets up the table buttons
    */
   private void setUpTableButtons() {
	   JLabel messageButtonParts = new JLabel("Add or Remove Parts", SwingConstants.CENTER);
	   //messageButtonParts.setSize(275, 150);
	   addPart = new JButton("Add");
	   removePart = new JButton("Remove");
	   panel3 = new JPanel();
	   panel3.setLayout(layout);
	   //panel3.setSize(600, 150);
	   panel3.add(messageButtonParts);
	   tableButtonPanel = new JPanel();
	   tableButtonPanel.add(addPart);
	   tableButtonPanel.add(removePart);
	   tableButtonPanel.setSize(275, 150);
	   panel3.add(tableButtonPanel);
   }
   
   /**
    * Sets up the button panel
    */
   private void setUpButtonPanel() {
      editTemplate = new JButton("Edit");
      cancel = new JButton("Cancel");
      
      buttonPanel = new JPanel();
      buttonPanel.setSize(550, 150);
      buttonPanel.add(editTemplate);
      buttonPanel.add(cancel);
   }
   
   /**
    * Registers the listeners for this view
    * @param c : The controller the listeners are registered with
    */
   public void registerListeners(EditProductTemplatePartsController c) {
      editTemplate.addActionListener(c);
      cancel.addActionListener(c);
      addPart.addActionListener(c);
      removePart.addActionListener(c);
      productNumberTextField.addActionListener(c);
      descriptionTextField.addActionListener(c);
   }
   
   /**
    * Mouse listener used to detect mouse click made on the table
    */
   MouseListener tableMouseListener = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
         setLastSelectedRow(selectedRow);
         selectedRow = table.getSelectedRow();
      }
   };
   
   /**
    * Gets a selected row
    * @return selectedRow : int representing the selected row
    */
   public int getSelectedRow() {
      return selectedRow;
   }
   
   /**
    * Gets the template number
    * @return String : representing the template number
    */
   public String getTemplateNumber() {
      return productNumberTextField.getText();
   }
   
   /**
    * Gets the description
    */
   public String getDescription() {
      return descriptionTextField.getText();
   }
   
   /**
    * Gets the last selected row
    * @return lastSelectedRow
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
    * Update
    */
   @Override
   public void update() {
      table.updateUI();
   }
   
   /**
    * Closes the window
    */
   public void closeWindow() {
      dispose();
   }
}
