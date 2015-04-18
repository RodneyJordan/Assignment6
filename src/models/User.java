package models;


public class User {
	
	private String fullName;
	private String email;
	private int roleId;
	
	public User() {
		fullName = "";
		email = "";
		roleId = 0;
	}
	
	public User(String fullName, String email) {
		this.fullName = fullName;
		this.email = email;
	}
	
	public User(String fullName, String email, int roleId) {
		this.fullName = fullName;
		this.email = email;
		this.roleId = roleId;
	}
	
	/*
	 * ACCESSORS
	 */
	public String getFullName() {
		return this.fullName; 
	}
	
	public String getEmail() { 
		return this.email; 
	}
	
	public int getRoleId() { 
		return this.roleId; 
	}

	/*
	 * MUTATORS
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}
