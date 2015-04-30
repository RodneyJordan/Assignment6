package models;

import java.rmi.RemoteException;

import javax.rmi.PortableRemoteObject;

public class LogObserver implements LogObserverRemote {
	private core.InventoryManagementSoftware master = null;
	
	public LogObserver(core.InventoryManagementSoftware m) throws RemoteException {
		PortableRemoteObject.exportObject(this);
		master = m;
	}

	@Override
	public void callback(String data) throws RemoteException {
		master.updateTextBox(data);
	}

}
