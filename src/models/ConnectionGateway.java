package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Makes a connection to the database to handle adding parts and populating the parts table
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class ConnectionGateway {
	
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
	public ConnectionGateway() {
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
	 * gets parts from the database to populate the parts view
	 * @return parts : an array list of parts
	 */
	public ArrayList<Part> getParts() {
		createConnection();
		ArrayList<Part> parts = new ArrayList<Part>();
		ResultSet resultSet = null;
		Statement statement = null;
		String query = "Select * from ase_parts";
		try {
			statement = (Statement) connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				parts.add(new Part(resultSet.getInt("id"), resultSet.getString("number"), resultSet.getString("name"),
						resultSet.getString("vendor"), resultSet.getString("unit"), resultSet.getString("extNumber")));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return parts;
	}
	
	/**
	 * adds a part to the parts table of the database
	 * @param part : the part to add
	 */
	public void addPartToDatabase(Part part) {
		createConnection();
		preparedStatement = null;
		String query = "Insert into ase_parts(number, name, vendor, unit, extNumber) values(?, ?, ?, ?, ?)";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, part.getPartNumber());
			preparedStatement.setString(2, part.getPartName());
			preparedStatement.setString(3, part.getVendor());
			preparedStatement.setString(4, part.getUnitOfQuantity());
			preparedStatement.setString(5, part.getExternalPartNumber());
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * edits a part in the database
	 * @param part
	 */
	public void editPart(Part part) {
		createConnection();
		preparedStatement = null;
		String query = "Update ase_parts set name=?, vendor=?, unit=?, extNumber=? where id=?";
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, part.getPartName());
			preparedStatement.setString(2, part.getVendor());
			preparedStatement.setString(3, part.getUnitOfQuantity());
			preparedStatement.setString(4, part.getExternalPartNumber());
			preparedStatement.setInt(5, part.getIdNumber());
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * removes a part from the database
	 * @param part
	 */
	public void removePart(Part part) {
		createConnection();
		preparedStatement = null;
		String query = "Delete from ase_parts where id=?";
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, part.getIdNumber());
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
