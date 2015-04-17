package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class AuthenticationGateway {
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
	public AuthenticationGateway() {
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
	
	public String selectPassword(String email){
		createConnection();
		String password = "";
		ResultSet resultSet = null;
		preparedStatement = null;
		String query = "SELECT passtwerk_hash FROM ase_users WHERE email=?";
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				password = resultSet.getString("passtwerk_hash");
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return password;
	}
	
	public User getUser(String email) {
		createConnection();
		User user = null;
		ResultSet resultSet = null;
		preparedStatement = null;
		String query = "SELECT * FROM ase_users WHERE email=?";
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				user = new User(resultSet.getString("fullName"), resultSet.getString("email"), resultSet.getInt("role"));
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	/**
	 * returns array of role permission IDs.
	 * This could/maybe should be implemented using a couple more models to represent Policy and Roles, but
	 * this will suffice for now.
	 * @param roleId
	 * @return
	 */
	public ArrayList<Integer> getRolePolicy(int roleId){
		ArrayList<Integer> permissions = new ArrayList<Integer>();
		createConnection();
		User user = null;
		ResultSet resultSet = null;
		preparedStatement = null;
		String query = "SELECT permission_id FROM ase_policies WHERE role_id=?";
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1, roleId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				permissions.add(resultSet.getInt("permission_id"));
			}
			
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return permissions;	
	}
}
