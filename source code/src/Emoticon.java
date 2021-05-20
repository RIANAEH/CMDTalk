import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Emoticon extends Remote {
	public String smile() throws RemoteException;
	public String cry() throws RemoteException;
	public String soju() throws RemoteException;
	public String ok() throws RemoteException;
}
