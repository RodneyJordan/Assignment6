package session;

import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.rmi.PortableRemoteObject;

import models.InventoryModel;
import models.ItemLogTableModel;

public class LogObserver implements LogObserverRemote {
	private ItemLogTableModel itemLogTableModel = null;
	
	public LogObserver(ItemLogTableModel m) throws RemoteException {
		PortableRemoteObject.exportObject(this);
		itemLogTableModel = m;
	}

	@Override
	public void callback(ArrayList<LogEntry> list) throws RemoteException {
		if(list != null) {
			itemLogTableModel.updateLogList(list);
		}
	}

}
