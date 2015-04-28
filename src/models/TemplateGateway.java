package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Makes a connection to the database to handle adding templates 
 * and populating the template table.
 * @author Rodney Jordan
 * @author Jacob Pagano
 */
public class TemplateGateway {
	
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
	public TemplateGateway() {
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
	 * gets templates from the database to populate the template view
	 * @return templates : an array list of templates
	 */
	public ArrayList<ProductTemplate> getTemplates() {
		createConnection();
		ArrayList<ProductTemplate> templates = new ArrayList<ProductTemplate>();
		ResultSet resultSet = null;
		Statement statement = null;
		String query = "Select * from ase_templates";
		try {
			statement = (Statement) connection.createStatement();
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				templates.add(new ProductTemplate(resultSet.getInt("id"), resultSet.getString("number"),
						resultSet.getString("description")));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return templates;
	}
	
	/**
	 * adds a template to the template table of the database
	 * @param template : the template to add
	 */
	public void addTemplateToDatabase(ProductTemplate template) {
		createConnection();
		preparedStatement = null;
		String query = "Insert into ase_templates(number, description) values(?, ?)";
		String relQuery = "Insert into ase_templateProds(template_id, product_id) values(?,?)";
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, template.getProductNumber());
			preparedStatement.setString(2, template.getDescription());
			preparedStatement.execute();
			
			template.setId((int)preparedStatement.getLastInsertID());

			preparedStatement = (PreparedStatement) connection.prepareStatement(relQuery);
			preparedStatement.setInt(1, template.getId());
			preparedStatement.setInt(2, template.getId());
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * edits a template in the database
	 * @param template
	 */
	public void editTemplate(ProductTemplate template) {
		createConnection();
		preparedStatement = null;
		String query = "Update ase_templates set number=?, description=? where id=?";
		
		try{
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setString(1, template.getProductNumber());
			preparedStatement.setString(2, template.getDescription());
			preparedStatement.setInt(3, template.getId());
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes a template from the database
	 * @param template
	 */
	public void removeTemplate(ProductTemplate template) {
		createConnection();
		preparedStatement = null;
		String query = "Delete from ase_templates where id=?";
		
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			preparedStatement.setInt(1,  template.getId());
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}