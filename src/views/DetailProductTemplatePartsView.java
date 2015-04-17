package views;

import controllers.DetailProductTemplatePartsController;
import models.ProductTemplate;
import models.DetailTemplateModel;
import models.TableObserver;
import models.ProductTemplatePartsModel;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.text.*;
import java.util.Date;

/**
 * Creates the detail template view window
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class DetailProductTemplatePartsView extends JFrame implements TableObserver {
   
   /**
    * Panels for the view
    */
   private JPanel panel1;
   private JPanel panel2;
   private JPanel panel3;
   private JPanel panel4;
   private JPanel panel5;
   private JPanel partsTablePanel;
   private JPanel buttonPanel;
   
   /**
    * Buttons for the view
    */
   private JButton cancel;
   private JButton edit;
   private JButton addPart;

   
   /**
    * Layout for the panels
    */
   private GridLayout layout = new GridLayout(1, 2);
   
   /**
    * The row that is selected
    */
   private int selectedRow;
   
   /**
    * The last selected row
    */
   private int lastSelectedRow;
   
   /**
    * Width of the window
    */
   private final int WINDOW_WIDTH = 600;
   
   /**
    * Height of the window
    */
   private final int WINDOW_HEIGHT = 300;
   
   /**
    * Date formatter
    */
   SimpleDateFormat df = new SimpleDateFormat("MM/DD/YYYY");
   
   /**
    * A Table for the parts associated with this template
    */
   private JTable table;
   
   /**
    * The scroll pane for the list of parts
    */
   private JScrollPane scrollPane;
   
   /**
    * The product template parts model
    */
   private ProductTemplatePartsModel productTemplatePartsModel;
   
   /**
    * Constructor
    * @param template : The template that details will be shown
    */
   public DetailProductTemplatePartsView(ProductTemplate template, DetailTemplateModel detailTemplateModel, 
         ProductTemplatePartsModel productTemplatePartsModel, MouseListener templatePartsMouseListener) {
      super("Product Template");
      
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
      //setLayout(new GridLayout(5, 1));
      setLayout(new FlowLayout());
      this.productTemplatePartsModel = productTemplatePartsModel;
      
      JPanel infoPanel = new JPanel();
      infoPanel.setLayout(new GridLayout(3, 1));
      setUpPanel1(template.getId());
      infoPanel.add(panel1);
      
      setUpPanel2(template.getProductNumber());
      infoPanel.add(panel2);
      
      setUpPanel3(template.getDescription());
      infoPanel.add(panel3);
      
      add(infoPanel);
      
      setUpPanelPartsTable(template, detailTemplateModel, templatePartsMouseListener);
      add(partsTablePanel);
      /*
      setUpPanel4(template.getDateAdded());
      add(panel4);
      
      setUpPanel5(template.getLastModified());
      add(panel5);
      */
      setUpButtonPanel();
      add(buttonPanel);
      
      setVisible(true);
      setLocationRelativeTo(null);
   }
   
   /**
    * Sets up the first panel
    * @param id
    */
   public void setUpPanel1(int id) {
      String idString = Integer.toString(id);
      JLabel messageId = new JLabel("Id Number", SwingConstants.CENTER);
      JLabel infoId = new JLabel(idString, SwingConstants.CENTER);
      
      panel1 = new JPanel();
      panel1.setLayout(layout);
      panel1.add(messageId);
      panel1.add(infoId);
   }
   
   /**
    * Sets up the second panel
    * @param productNumber
    */
   public void setUpPanel2(String productNumber) {
      JLabel messageProductNumber = new JLabel("Product Number", SwingConstants.CENTER);
      JLabel infoProductNumber = new JLabel(productNumber, SwingConstants.CENTER);
      
      panel2 = new JPanel();
      panel2.setLayout(layout);
      panel2.add(messageProductNumber);
      panel2.add(infoProductNumber);
   }
   
   /**
    * Sets up the third panel
    * @param description
    */
   public void setUpPanel3(String description) {
      JLabel messageDescription = new JLabel("Description", SwingConstants.CENTER);
      JLabel infoDescription = new JLabel(description, SwingConstants.CENTER);
      
      panel3 = new JPanel();
      panel3.setLayout(layout);
      panel3.add(messageDescription);
      panel3.add(infoDescription);
   }
   
   /**
    * Sets up the fourth panel
    * @param dateAdded
    */
   public void setUpPanel4(Date dateAdded) {
      String date = df.format(dateAdded);
      JLabel messageDateAdded = new JLabel("Date Added", SwingConstants.CENTER);
      JLabel infoDateAdded = new JLabel(date, SwingConstants.CENTER);
      
      panel4 = new JPanel();
      panel4.setLayout(layout);
      panel4.add(messageDateAdded);
      panel4.add(infoDateAdded);
   }
   
   /**
    * Sets up the fifth panel
    * @param dateModified
    */
   public void setUpPanel5(Date dateModified) {
      String dateMod = df.format(dateModified);
      JLabel messageDateModified = new JLabel("Last Modified", SwingConstants.CENTER);
      JLabel infoDateModified = new JLabel(dateMod, SwingConstants.CENTER);
      
      panel5 = new JPanel();
      panel5.setLayout(layout);
      panel5.add(messageDateModified);
      panel5.add(infoDateModified);
   }
   /**
    * Sets up the parts table
    */
   public void setUpPanelPartsTable(ProductTemplate template, DetailTemplateModel detailTemplateModel, MouseListener templatePartsMouseListener) {
      //String templateId = template.getProductNumber();
      table = new JTable(detailTemplateModel);
      table.setPreferredScrollableViewportSize(new Dimension(550, 125));
      table.setFillsViewportHeight(true);
      table.addMouseListener(templatePartsMouseListener);
      table.addMouseListener(tableMouseListener);
      
      scrollPane = new JScrollPane(table);
      productTemplatePartsModel.registerObserver(this);
      
      partsTablePanel = new JPanel();
      partsTablePanel.add(scrollPane);
   }
   
   /**
    * Sets up the button panel
    */
   public void setUpButtonPanel() {
      addPart = new JButton("Add Part");
      edit = new JButton("Edit");
      cancel = new JButton("Cancel");
      
      buttonPanel = new JPanel();
      buttonPanel.add(addPart);
      buttonPanel.add(edit);
      buttonPanel.add(cancel);
   }
   
   /**
    * Registers the listeners for this view
    * @param c : the controller that the view is registered to
    */
   public void registerListeners(DetailProductTemplatePartsController c) {
      addPart.addActionListener(c);
      edit.addActionListener(c);
      cancel.addActionListener(c);
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
