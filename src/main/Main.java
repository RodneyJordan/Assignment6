package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import models.LogObserver;
import session.ItemLogGatewayBeanRemote;
import session.LogObserverRemote;
import session.Session;
import controllers.LoginController;



/**
 * The purpose of this software is to generate an inventory that can be added to
 * and deleted from, as well as giving the user the ability to edit an item in the inventory.
 * @author Rodney Jordan
 * @author Jacob Pagano
 */

public class Main extends JFrame
{

	
		private static final long serialVersionUID = 1L;
		private LogObserver stateObserver;
		private JTextField textField;
		private ItemLogGatewayBeanRemote sessionState = null;
		
		public void initSession() {
			try {
				Properties props = new Properties();
				props.put("org.omg.CORBA.ORBInitialHost", "localhost");
	            props.put("org.omg.CORBA.ORBInitialPort", "3700");
	            
	            //connect to the remote EJB
				InitialContext itx = new InitialContext(props);
		        //sessionState = (StateBeanRemote) itx.lookup("java:global/cs4743_session_bean/StateBean!session.StateBeanRemote");
				sessionState = (ItemLogGatewayBeanRemote) itx.lookup("java:global/cs4743_session_bean/ItemLogGatewayBean!session.ItemLogGatewayBeanRemote");
				
				//create an observer for this frame and register it with the remote EJB
				stateObserver = new LogObserver(this);
				sessionState.registerObserver(stateObserver);
				
			} catch (NamingException e1) {
				e1.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		public Main() {
			super();
			
			initSession();
			
			this.setLayout(new GridLayout(3, 1));
			textField = new JTextField("");
			textField.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					Main.this.sessionState.setEditText(Main.this.getEditText());
				}
				
			});
			textField.addActionListener(new ActionListener() {

			    @Override
			    public void actionPerformed(ActionEvent e) {
					Main.this.sessionState.setEditText(Main.this.getEditText());
			    }
			});
			add(textField);
			
			add(new JLabel("Create a few instances of this program. Then type something and press \"Enter\""));
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					System.out.println("Unregistering observer...");
					Main.this.sessionState.unregisterObserver(stateObserver);
				}
			});

		}
		
		public String getEditText() {
			return textField.getText();
		}
		
		public void updateTextBox(String str) {
			textField.setText(str);
		}
		
		//creates and displays the JFrame
		public static void createAndShowGUI() {
			Main frame = new Main();
			frame.setSize(520, 150);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
			frame.setVisible(true);
		}
		
	public static Session session = null;

    public static void main(String[] args)
    {
    	LoginController loginController = new LoginController(session);
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    	
    }

	/*public void updateTextBox(String data) {
		
	}*/
}
