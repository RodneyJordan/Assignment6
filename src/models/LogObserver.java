package models;

import java.rmi.RemoteException;

import javax.rmi.PortableRemoteObject;

import session.LogObserverRemote;

public class LogObserver implements LogObserverRemote {
	private main.Main master = null;
	
	public LogObserver(main.Main m) throws RemoteException {
		PortableRemoteObject.exportObject(this);
		master = m;
	}

	@Override
	public void callback(String data) throws RemoteException {
		master.updateTextBox(data);
	}

}
