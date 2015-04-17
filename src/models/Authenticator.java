package models;

import java.security.MessageDigest;

public class Authenticator {
	
	private Session session;
	private AuthenticationGateway authGate;
	
	public Authenticator(){
//		this.user = new User();
//		this.session = new Session();
		authGate = new AuthenticationGateway();
	}
	
	public Authenticator(Session session){
		this.session = session;
		authGate = new AuthenticationGateway();
	}
	
	public Authenticator(String email, String password) {
		this.session = new Session();
		authGate = new AuthenticationGateway();
		
	}
	
	public String hashPass(String password) throws Exception {
	      MessageDigest md = MessageDigest.getInstance("SHA-256");
	      md.update(password.getBytes());
	      byte[] digest = md.digest();
	      StringBuffer sb = new StringBuffer();
	      for (byte b : digest) {
	         sb.append(String.format("%02x", b & 0xff));
	      }
	      /* 
	       * debugging
	       */
	      System.out.println("original:" + password);
	      System.out.println("digested(hex):" + sb.toString());

	      return sb.toString();
	}
	
	public Session isAuthenticated(String email, String password) throws Exception {
		if(hashPass(password).equals(authGate.selectPassword(email))) {
			this.session = new Session(email);
			
			return this.session;
		}
		throw new Exception("Failed login");
	}
}
