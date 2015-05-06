package session;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.rmi.PortableRemoteObject;

import models.InventoryModel;

public class LogObserver implements LogObserverRemote {
	private InventoryModel inventoryModel = null;
	
	public LogObserver(InventoryModel m) throws RemoteException {
		PortableRemoteObject.exportObject(this);
		inventoryModel = m;
	}

	@Override
	public void callback(ArrayList<LogEntry> list) throws RemoteException {
		if(list != null) {
			inventoryModel.updateLogList(list);
		}
	}

}
