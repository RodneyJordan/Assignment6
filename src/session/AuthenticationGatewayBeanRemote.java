package session;

import java.util.ArrayList;

import javax.ejb.Remote;

@Remote
public interface AuthenticationGatewayBeanRemote {
	
	public void createConnection();
	
	public String selectPassword(String email);
	
	public User getUser(String email);
	
	public ArrayList<Integer> getRolePolicy(int roleId);
	
	
}
