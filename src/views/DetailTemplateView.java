package views;

import controllers.DetailTemplateController;
import models.ProductTemplate;

import javax.swing.*;

import java.awt.*;
import java.text.*;
import java.util.Date;

/**
 * Creates the detail template view window
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class DetailTemplateView extends JFrame {
	
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
	private JButton edit;
	private JButton addPart;
	
	/**
	 * Layout for the panels
	 */
	private GridLayout layout = new GridLayout(1, 2);
	
	/**
	 * Width of the window
	 */
	private final int WINDOW_WIDTH = 600;
	
	/**
	 * Height of the window
	 */
	private final int WINDOW_HEIGHT = 250;
	
	/**
	 * Date formatter
	 */
	SimpleDateFormat df = new SimpleDateFormat("MM/DD/YYYY");
	
	/**
	 * Constructor
	 * @param template : The template that details will be shown
	 */
	public DetailTemplateView(ProductTemplate template) {
		super("Product Template");
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new GridLayout(6, 1));
		
		setUpPanel1(template.getId());
		add(panel1);
		
		setUpPanel2(template.getProductNumber());
		add(panel2);
		
		setUpPanel3(template.getDescription());
		add(panel3);
		
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
	 * Sets up the button panel
	 */
	public void setUpButtonPanel() {
		ok = new JButton("Ok");
		edit = new JButton("Edit");
		addPart = new JButton("Add Part");
		
		buttonPanel = new JPanel();
		buttonPanel.add(ok);
		buttonPanel.add(edit);
		buttonPanel.add(addPart);
	}
	
	/**
	 * Registers the listeners for this view
	 * @param c : the controller that the view is registered to
	 */
	public void registerListeners(DetailTemplateController c) {
		ok.addActionListener(c);
		edit.addActionListener(c);
		addPart.addActionListener(c);
	}
	
	/**
	 * Closes the window
	 */
	public void closeWindow() {
		dispose();
	}
}