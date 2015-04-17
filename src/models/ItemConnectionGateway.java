package models;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;




public class ItemConnectionGateway {
	
	/**
	 * The connection
	 */
	private Connection connection;
	
	/**
	 * prepared statement
	 */
	private PreparedStatement preparedStatement;
	
	/**
	 * The data source
	 */
	private MysqlDataSource dataSource;
	
	/**
	 * The url for the database
	 */
	private String dbUrl;
	
	/**
	 * The user name for the database
	 */
	private String userName;
	
	/**
	 * The password for the database
	 */
	private String password;
	
	/**
	 * Constructor
	 */
	public ItemConnectionGateway() {
		this.dbUrl = "jdbc:mysql://devcloud.fulgentcorp.com:3306/whi385";
		this.userName = "whi385";
		this.password = "ZIU5ZfWQkvCx5PKhWQ05";
	}
	
	/**
	 * Creates a connection to the database
	 */
	private void createConnection() {
		dataSource = new MysqlDataSource();
		dataSource.setUrl(dbUrl);
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		try {
			connection = (Connection) dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * gets the items from the database
	 * @return items : an array list of items
	 */
	public ArrayList<InventoryItem> getItem() {
		createConnection();
		ArrayList<InventoryItem> items = new ArrayList<InventoryItem>();
		ResultSet resultSet = null;
		preparedStatement = null;
		String queryParts = "SELECT i.id, i.location, i.quantity, p.id, p.number, p.name, p.vendor, p.unit, p.extNumber "
				+ "FROM ase_inventoryParts as ip INNER JOIN ase_inventoryItems as i ON i.id = ip.inventory_id INNER JOIN "
				+ "ase_parts as p ON p.id = ip.part_id"; // WHERE ip.inventory_id = ?";
		
		String queryProducts = "SELECT i.id as inventoryId, i.quantity, i.location, tp.product_id, tp.template_id, t.number, t.description " 
				+ "FROM ase_inventoryItems as i INNER JOIN ase_inventoryProds as ip ON i.id = ip.inventory_id " 
				+ "INNER JOIN ase_templateProds as tp ON ip.product_id = tp.product_id INNER JOIN ase_templates as t ON tp.template_id = t.id";
		try {
			//parts gathering
			preparedStatement = (PreparedStatement) connection.prepareStatement(queryParts);
			resultSet = preparedStatement.executeQuery();
			Part temp = null;
			while(resultSet.next()) {
				temp = new Part(resultSet.getInt("id"), resultSet.getString("number"), resultSet.getString("name"),
						resultSet.getString("vendor"), resultSet.getString("unit"), resultSet.getString("extNumber"));
				items.add(new InventoryItem(resultSet.getInt("id"), temp, resultSet.getString("location"), resultSet.getInt("quantity")));
			}
			
			//products gathering
			preparedStatement = (PreparedStatement) connection.prepareStatement(queryProducts);
			resultSet = preparedStatement.executeQuery();
			ProductTemplate temp2 = null;
			while(resultSet.next()) {
				temp2 = new ProductTemplate(resultSet.getInt("product_id"), resultSet.getString("number"), resultSet.getString("description"));
				items.add(new InventoryItem(resultSet.getInt("inventoryId"), temp2, resultSet.getString("location"), resultSet.getInt("quantity")));
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	/**
	 * adds items to the database
	 * @param item
	 * @param typeFlag : 0 = part, 1 = new product at location, 2 = product already exists at location, increase quantity by 1
	 */
	public void addItemToDatabase(InventoryItem item, int typeFlag) {
		
		createConnection();
		preparedStatement = null;
		String query = "Insert into ase_inventoryItems(location, quantity) values(?, ?)";
			
			if(typeFlag == 0){
				try {
					preparedStatement = (PreparedStatement) connection.prepareStatement(query);
					preparedStatement.setString(1, item.getLocation());
					preparedStatement.setInt(2, item.getQuantity());
					preparedStatement.execute();
					addToInventoryPart((int) preparedStatement.getLastInsertID(), item);
					
					preparedStatement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else if(typeFlag == 1){
				try {
					connection.setAutoCommit(false);
					preparedStatement = (PreparedStatement) connection.prepareStatement(query);
					preparedStatement.setString(1, item.getLocation());
					preparedStatement.setInt(2, item.getQuantity());
					preparedStatement.executeUpdate();
					connection.commit();
					addToInventoryProduct((int) preparedStatement.getLastInsertID(), item);
					
					
					preparedStatement.close();
					connection.close();
				} catch(Exception e) {
					e.printStackTrace();
					try {
						connection.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			} else if(typeFlag == 2) {
				try {
					query = "Update ase_inventoryItems set quantity=? where id=?";
					preparedStatement = (PreparedStatement) connection.prepareStatement(query);
					preparedStatement.setInt(1, item.getQuantity());
					preparedStatement.setInt(2, item.getIdNumber());
					preparedStatement.executeUpdate();	
					
					preparedStatement.close();
					connection.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			
	}
	
	/**
	 * adds a relationship between an inventory item and a part
	 * @param inventoryId 
	 * @param item
	 */
	private void addToInventoryPart(int inventoryId, InventoryItem item) {
		preparedStatement = null;
		String query = "Insert into ase_inventoryParts(inventory_id, part_id) values(?, ?)";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, inventoryId);
			preparedStatement.setInt(2, item.getPart().getIdNumber());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * adds a relationship between an inventory item and a product
	 * @param inventoryId
	 * @param item
	 */
	private void addToInventoryProduct(int inventoryId, InventoryItem item) {
		preparedStatement = null;
		String query = "Insert into ase_inventoryProds(inventory_id, product_id) values(?, ?)";
		String query2 = "Insert into ase_templateProds(template_id, product_id) values(?, ?)";
		try {
			connection.setAutoCommit(false);
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, inventoryId);
			preparedStatement.setInt(2, item.getProduct().getId());
			preparedStatement.execute();

			preparedStatement = (PreparedStatement) connection.prepareStatement(query2);
			preparedStatement.setInt(1, item.getIdNumber());
			preparedStatement.setInt(2, item.getProduct().getId());
			
			connection.commit();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}		
	}
	
	/**
	 * edits an item in the database
	 * @param item
	 */
	public void editItem(InventoryItem item) {
		InventoryItem oldItem = selectItem(item.getIdNumber());
		createConnection();
		preparedStatement = null;
		String query = "Update ase_inventoryItems set location=?, quantity=? where id=? AND timestamp=?";
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, item.getLocation());
			preparedStatement.setInt(2, item.getQuantity());
			preparedStatement.setInt(3, item.getIdNumber());
			preparedStatement.setString(4, oldItem.getTimeStamp());
			if(preparedStatement.executeUpdate() >= 1) {
				preparedStatement.close();
				changeRelationship(item.getIdNumber(), item.getPart().getIdNumber());
				connection.close();
			}
			else {
				JOptionPane.showMessageDialog(null, "Unable to update, please try again", "Error", JOptionPane.ERROR_MESSAGE);
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * edits a product item in the database
	 * @param item
	 */
	public void editItemProduct(InventoryItem item) {
		InventoryItem oldItem = selectItemProduct(item.getIdNumber());
		createConnection();
		preparedStatement = null;
		String query = "Update ase_inventoryItems set location=?, quantity=? where id=? AND timestamp=?";
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, item.getLocation());
			preparedStatement.setInt(2, item.getQuantity());
			preparedStatement.setInt(3, item.getIdNumber());
			preparedStatement.setString(4, oldItem.getTimeStamp());
			if(preparedStatement.executeUpdate() >= 1) {
				preparedStatement.close();
				changeProductRelationship(item.getIdNumber(), item.getProduct().getId());
				connection.close();
			}
			else {
				JOptionPane.showMessageDialog(null, "Unable to update product, please try again", "Error", JOptionPane.ERROR_MESSAGE);
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * removes an item from the database
	 * @param item
	 */
	public void removeItem(InventoryItem item) {
		createConnection();
		preparedStatement = null;
		String query = "Delete from ase_inventoryItems where id=?";
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, item.getIdNumber());
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * changes a relationship between an item and a part
	 * @param itemId
	 * @param partId
	 */
	private void changeRelationship(int itemId, int partId) {
		preparedStatement = null;
		String query = "Update ase_inventoryParts set part_id=? where inventory_id =?";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, partId);
			preparedStatement.setInt(2, itemId);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * changes a relationship between an item and a product
	 * @param itemId
	 * @param productId
	 */
	private void changeProductRelationship(int itemId, int productId) {
		preparedStatement = null;
		String query = "Update ase_inventoryProds set product_id=? where inventory_id =?";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);
			preparedStatement.setInt(2, itemId);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private InventoryItem selectItem(int id) {
		createConnection();
		InventoryItem item = new InventoryItem();
		ResultSet resultSet = null;
		preparedStatement = null;
		String query = "SELECT i.id, i.location, i.quantity, p.id, p.number, p.name, p.vendor, p.unit, p.extNumber, i.timestamp "
				+ "FROM ase_inventoryParts as ip INNER JOIN ase_inventoryItems as i ON i.id = ip.inventory_id INNER JOIN "
				+ "ase_parts as p ON p.id = ip.part_id WHERE ip.inventory_id = ?";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			Part temp = null;
			while(resultSet.next()) {
				temp = new Part(resultSet.getInt("id"), resultSet.getString("number"), resultSet.getString("name"),
						resultSet.getString("vendor"), resultSet.getString("unit"), resultSet.getString("extNumber"));
				item = new InventoryItem(resultSet.getInt("id"), temp, resultSet.getString("location"), resultSet.getInt("quantity"));
				item.setTimeStamp(resultSet.getString("timestamp").trim());
				
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	private InventoryItem selectItemProduct(int id) {
		createConnection();
		InventoryItem item = new InventoryItem();
		ResultSet resultSet = null;
		preparedStatement = null;
		String query = "SELECT i.id as inventory_id, i.location, i.quantity, i.timestamp, t.description, ip.product_id, t.number "
				+ "FROM ase_inventoryProds as ip "
				+ "INNER JOIN ase_inventoryItems as i ON i.id = ip.inventory_id "
				+ "INNER JOIN ase_templates as t ON t.id = ip.product_id "
				+ "WHERE ip.inventory_id = ?";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			ProductTemplate temp = null;
			while(resultSet.next()) {
				temp = new ProductTemplate(resultSet.getInt("product_id"), resultSet.getString("number"), resultSet.getString("description"));
				item = new InventoryItem(resultSet.getInt("inventory_id"), temp, resultSet.getString("location"), resultSet.getInt("quantity"));
				item.setTimeStamp(resultSet.getString("timestamp").trim());
				
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	private int checkLocationProductQty(int id){
		int qty = 0;
		
		return qty;
	}
}
