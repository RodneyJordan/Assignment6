package views;

import controllers.DetailPartController;
import models.Part;

import javax.swing.*;

import java.awt.*;

/**
 * Creates a detail part view
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

@SuppressWarnings("serial")
public class DetailPartView extends JFrame {
	
	/**
	 * Panels for this view
	 */
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	private JPanel panel6;
	private JPanel buttonPanel;
	
	/**
	 * Buttons for this view
	 */
	private JButton ok;
	private JButton addPart;
	private JButton editPart;
	
	/**
	 * Layout for the panels
	 */
	private GridLayout layout = new GridLayout(1,2);
	
	/**
	 * Width of the window
	 */
	private final int WINDOW_WIDTH = 500;
	
	/**
	 * Height of the window
	 */
	private final int WINDOW_HEIGHT = 250;
	
	/**
	 * Constructor
	 * @param part : The part that details will be shown.
	 */
	public DetailPartView(Part part) {
		super(part.getPartName());
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLayout(new GridLayout(7,1));
		
		setUpPanel1(part.getIdNumber());
		add(panel1);
		
		setUpPanel2(part.getPartNumber());
		add(panel2);
		
		setUpPanel3(part.getPartName());
		add(panel3);
		
		setUpPanel4(part.getVendor());
		add(panel4);
		
		setUpPanel5(part.getUnitOfQuantity());
		add(panel5);
		
		setUpPanel6(part.getExternalPartNumber());
		add(panel6);
		
		setUpButtonPanel();
		add(buttonPanel);
		
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Sets up the first panel
	 * @param idNumber
	 */
	public void setUpPanel1(int idNumber) {
		String idString = Integer.toString(idNumber);
		JLabel messageId = new JLabel("ID Number", SwingConstants.CENTER);
		JLabel infoId = new JLabel(idString, SwingConstants.CENTER);
		
		panel1 = new JPanel();
		panel1.setLayout(layout);
		panel1.add(messageId);
		panel1.add(infoId);
	}
	
	/**
	 * Sets up the second panel
	 * @param partNumber
	 */
	public void setUpPanel2(String partNumber) {
		JLabel messagePartNumber = new JLabel("Part Number", SwingConstants.CENTER);
		JLabel infoPartNumber = new JLabel(partNumber, SwingConstants.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(layout);
		panel2.add(messagePartNumber);
		panel2.add(infoPartNumber);
	}
	
	/**
	 * Sets up the third panel
	 * @param partName
	 */
	public void setUpPanel3(String partName) {
		JLabel messagePartName = new JLabel("Part Name", SwingConstants.CENTER);
		JLabel infoPartName = new JLabel(partName, SwingConstants.CENTER);
		
		panel3 = new JPanel();
		panel3.setLayout(layout);
		panel3.add(messagePartName);
		panel3.add(infoPartName);
	}
	
	/**
	 * Sets up the fourth panel
	 * @param vendor
	 */
	public void setUpPanel4(String vendor) {
		JLabel messageVendor = new JLabel("Vendor", SwingConstants.CENTER);
		JLabel infoVendor = new JLabel(vendor, SwingConstants.CENTER);
		
		panel4 = new JPanel();
		panel4.setLayout(layout);
		panel4.add(messageVendor);
		panel4.add(infoVendor);
	}
	
	/**
	 * Sets up the fifth panel
	 * @param unitOfQuantity 
	 */
	public void setUpPanel5(String unitOfQuantity) {
		JLabel messageUnitOfQuantity = new JLabel("Unit of Quantity", SwingConstants.CENTER);
		JLabel infoUnitOfQuantity = new JLabel(unitOfQuantity, SwingConstants.CENTER);
		
		panel5 = new JPanel();
		panel5.setLayout(layout);
		panel5.add(messageUnitOfQuantity);
		panel5.add(infoUnitOfQuantity);
	}
	
	/**
	 * Sets up the sixth panel
	 * @param externalPartNumber
	 */
	public void setUpPanel6(String externalPartNumber) {
		JLabel messageExternalPartNumber = new JLabel("External Part Number", SwingConstants.CENTER);
		JLabel infoExternalPartNumber = new JLabel(externalPartNumber, SwingConstants.CENTER);
		
		panel6 = new JPanel();
		panel6.setLayout(layout);
		panel6.add(messageExternalPartNumber);
		panel6.add(infoExternalPartNumber);
	}
	
	/**
	 * Sets up the button panel
	 */
	public void setUpButtonPanel() {
		ok = new JButton("Ok");
		addPart = new JButton("Add");
		editPart = new JButton("Edit");
		
		buttonPanel = new JPanel();
		buttonPanel.add(ok);
		buttonPanel.add(addPart);
		buttonPanel.add(editPart);
	}
	
	/**
	 * Registers the listeners for this view
	 * @param detailController : The controller that the view is registered to.
	 */
	public void registerListeners(DetailPartController detailPartController) {
		ok.addActionListener(detailPartController);
		addPart.addActionListener(detailPartController);
		editPart.addActionListener(detailPartController);
	}
	
	/**
	 * Close the window
	 */
	public void closeWindow() {
		dispose();
	}
}
