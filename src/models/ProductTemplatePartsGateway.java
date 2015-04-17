package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class ProductTemplatePartsGateway {
	
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
	public ProductTemplatePartsGateway() {
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
	 * Add a part to the database
	 * @param templateId
	 * @param partId
	 * @param quantity
	 */
	public void addPart(int templateId, int partId, int quantity) {
		System.out.println(templateId + " " + partId + " " + quantity);
		createConnection();
		preparedStatement = null;
		String query = "INSERT into ase_templateParts (templateId, partId, partQuantity) values (?, ?, ?)";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, templateId);
			preparedStatement.setInt(2, partId);
			preparedStatement.setInt(3, quantity);
			preparedStatement.execute();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Edits a part in the database
	 */
	public void editPart(int templateId, int partId, int quantity) {
		createConnection();
		preparedStatement = null;
		String query = "UPDATE ase_templateParts set partQuantity=? where templateId=? and partId=?";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, templateId);
			preparedStatement.setInt(3, partId);
			preparedStatement.execute();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Delete a part in the database
	 */
	public void deletePart(int templateId, int partId) {
		System.out.println(templateId + " " +partId);
		createConnection();
		preparedStatement = null;
		String query = "DELETE from ase_templateParts where templateId=? and partId=?";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, templateId);
			preparedStatement.setInt(2, partId);
			preparedStatement.execute();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the template parts list
	 * @return parts
	 */
	public ArrayList<ProductTemplateParts> getTemplateParts() {
		createConnection();
		ArrayList<ProductTemplateParts> parts = new ArrayList<ProductTemplateParts>();
		ResultSet resultSet = null;
		preparedStatement = null;
		String query = "SELECT * from ase_templateParts";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				ProductTemplateParts temp;
				temp = new ProductTemplateParts(resultSet.getInt("templateId"), resultSet.getInt("partId"),
						resultSet.getInt("partQuantity"));
				parts.add(temp);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parts;
	}
	
	//public ArrayList<ProductTemplateParts> getTemplatePartsForId()
}
