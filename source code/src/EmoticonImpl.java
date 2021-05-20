import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class EmoticonImpl extends UnicastRemoteObject implements Emoticon {
	private static final long serialVersionUID = 1L;
	
	public EmoticonImpl() throws RemoteException {
		super();
	}
	
	public String smile() throws RemoteException {
		String s = "(*������)";
		return s;
	}
	
	public String cry() throws RemoteException {
		String s = 
				"�ܦ���������������������\r\n" + 
				"��   ��  ��   ��\r\n" + 
				"�� * ���䦭 * ��\r\n" + 
				"������������������������\r\n";
		return s;
	}
	
	public String soju() throws RemoteException {
		String s = 
				"  �Ȣ�  \r\n" + 
				"�� ���� �����赵\r\n" + 
				"�����ئ����ƴµ�\r\n" + 
				"���̽������ְ�?^^\r\n" + 
				"��====��\r\n";
		return s;
	}
	
	public String ok() throws RemoteException {
		String s = 
				"  ��������\r\n" + 
				"������  ��\r\n" + 
				"����   ����!!!!!\r\n" + 
				"����   ��\r\n" + 
				"������������\r\n";
		return s;
	}
}