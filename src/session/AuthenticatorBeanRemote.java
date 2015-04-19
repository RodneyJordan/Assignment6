package session;

import javax.ejb.Remote;

@Remote
public interface AuthenticatorBeanRemote {
	
	public String hashPass(String password) throws Exception;
	
	public Session isAuthenticated(String email, String password) throws Exception;
}
