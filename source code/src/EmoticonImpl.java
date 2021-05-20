import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EmoticonImpl extends UnicastRemoteObject implements Emoticon {
	private static final long serialVersionUID = 1L;
	
	public EmoticonImpl() throws RemoteException {
		super();
	}
	
	public String smile() throws RemoteException {
		String s = "(*╞╯��)";
		return s;
	}
	
	public String cry() throws RemoteException {
		String s = 
				"≒收收收收收收收收收收≒\r\n" + 
				"早   有  有   早\r\n" + 
				"早 * 早∩早 * 早\r\n" + 
				"曲收收收收收收收收收收旭\r\n";
		return s;
	}
	
	public String soju() throws RemoteException {
		String s = 
				"  〦〦  \r\n" + 
				"旨 旭曲 旬衛я紫\r\n" + 
				"早霤藯早蜂蟻朝等\r\n" + 
				"早檜蝸早模輿陝?^^\r\n" + 
				"曲====旭\r\n";
		return s;
	}
	
	public String ok() throws RemoteException {
		String s = 
				"  旨收收旬\r\n" + 
				"旨收旭  早\r\n" + 
				"曳收   早屬!!!!!\r\n" + 
				"曳收   早\r\n" + 
				"曲收收收收旭\r\n";
		return s;
	}
}