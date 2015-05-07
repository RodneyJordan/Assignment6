package session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface LogObserverRemote extends Remote {
	public void callback(ArrayList<LogEntry> list) throws RemoteException;
}
