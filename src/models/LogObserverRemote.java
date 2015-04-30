package models;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LogObserverRemote extends Remote {
	public void callback(String data) throws RemoteException;
}
