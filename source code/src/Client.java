import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Client {
	
	static String eServer = "";
	static int ePort = 0000;
	static SSLSocket socket = null;
//	static SSLSocketFactory socketFactory = null; -> MyFactory를 사용하여 코드 간소화
	static String ePassword = "";
	
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage: Classname ServerName ServerPort Password");
			System.exit(1);
		}
		
		eServer = args[0];
		ePort = Integer.parseInt(args[1]);
		ePassword = args[2];
		
		try {
			System.setProperty("javax.net.ssl.trustStore", "trustedcerts");
			System.setProperty("javax.net.ssl.trustStorePassword", ePassword);
			
//			socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault(); -> MyFactory를 사용하여 코드 간소화
//			socket = (SSLSocket) socketFactoory.create(eServer, ePort); -> MyFactory를 사용하여 코드 간소화
			socket = new MyFactory(eServer, ePort).createSocket();
			
			String[] supported = socket.getSupportedCipherSuites();
			socket.setEnabledCipherSuites(supported);
			
			socket.startHandshake();
			
		} catch (BindException b) {
			System.out.println("Can't bind on: "+ePort);
			System.exit(1);
		} catch (IOException i) {
			System.out.println(i);
			System.exit(1);
		}
		
		new Thread(new ClientReceiver(socket)).start();
		new Thread(new ClientSender(socket)).start();
	}
}

class ClientSender implements Runnable {
	private SSLSocket chatSocket=null;
	
	ClientSender(SSLSocket socket) {
		this.chatSocket = socket;
	}
	public void run() {
		Scanner KeyIn=null;
		PrintWriter out=null;
		try {
			KeyIn=new Scanner(System.in);
			out=new PrintWriter(chatSocket.getOutputStream(),true);
			
			String userInput="";
			System.out.println("( っ '～')づ 방가봐용~~^^* HaeyKey의 CmdTalk에 참여하신걸 환영합니다이에요~~~♡\n당신의 번호는 "+chatSocket.getLocalPort()+"이에요~~~♡");
			System.out.println("이모티콘은 <웃음>, <ㅠㅠ>, <소주각>, <콜>을 사용할 수 있어요 y(^o^)yeah 퀄리티 장난아니죠?^^");
			System.out.println("퇴장하고 싶으면 <그럼이만>을 입력해주세요~~~♡");
			while((userInput=KeyIn.nextLine())!=null) {
				out.println(userInput);
				out.flush();
				if(userInput.equalsIgnoreCase("<그럼이만>")) 
					break;
				if(userInput.equalsIgnoreCase("<웃음>")) {
					try {
						Emoticon em = (Emoticon)Naming.lookup("rmi://"+Client.eServer+":1099/Emoticon");
						System.out.println(em.smile());
						out.println(em.smile());
						out.flush();
					}
					catch (MalformedURLException mue) {
						System.out.println("MalformedURLException: "+mue);
					}
					catch (RemoteException re) {
						System.out.println("RemoteException: "+re);
					}
					catch (NotBoundException nbe) {
						System.out.println("NotBoundException: "+nbe);
					}
					catch (java.lang.ArithmeticException ae) {
						System.out.println("java.lang.ArithmeticException "+ae);
					}
				}
				if(userInput.equalsIgnoreCase("<ㅠㅠ>")) {
					try {
						Emoticon em = (Emoticon)Naming.lookup("rmi://"+Client.eServer+":1099/Emoticon");
						System.out.println(em.cry());
						out.println(em.cry());
						out.flush();
					}
					catch (MalformedURLException mue) {
						System.out.println("MalformedURLException: "+mue);
					}
					catch (RemoteException re) {
						System.out.println("RemoteException: "+re);
					}
					catch (NotBoundException nbe) {
						System.out.println("NotBoundException: "+nbe);
					}
					catch (java.lang.ArithmeticException ae) {
						System.out.println("java.lang.ArithmeticException "+ae);
					}
				}
				if(userInput.equalsIgnoreCase("<소주각>")) {
					try {
						Emoticon em = (Emoticon)Naming.lookup("rmi://"+Client.eServer+":1099/Emoticon");
						System.out.println(em.soju());
						out.println(em.soju());
						out.flush();
					}
					catch (MalformedURLException mue) {
						System.out.println("MalformedURLException: "+mue);
					}
					catch (RemoteException re) {
						System.out.println("RemoteException: "+re);
					}
					catch (NotBoundException nbe) {
						System.out.println("NotBoundException: "+nbe);
					}
					catch (java.lang.ArithmeticException ae) {
						System.out.println("java.lang.ArithmeticException "+ae);
					}
				}
				if(userInput.equalsIgnoreCase("<콜>")) {
					try {
						Emoticon em = (Emoticon)Naming.lookup("rmi://"+Client.eServer+":1099/Emoticon");
						System.out.println(em.ok());
						out.println(em.ok());
						out.flush();
					}
					catch (MalformedURLException mue) {
						System.out.println("MalformedURLException: "+mue);
					}
					catch (RemoteException re) {
						System.out.println("RemoteException: "+re);
					}
					catch (NotBoundException nbe) {
						System.out.println("NotBoundException: "+nbe);
					}
					catch (java.lang.ArithmeticException ae) {
						System.out.println("java.lang.ArithmeticException "+ae);
					}
				}
			}
			KeyIn.close();
			out.close();
			chatSocket.close();
		} catch (IOException i) {
			try {
				if (out!=null) out.close();
				if (KeyIn!=null) KeyIn.close();
				if (chatSocket!=null) chatSocket.close();
			} catch (IOException e) {
			}
			System.exit(1);
		}
	}
}

class ClientReceiver implements Runnable {
	private SSLSocket chatSocket=null;
	
	ClientReceiver(SSLSocket socket) {
		this.chatSocket=socket;
	}
	
	public void run() {
		while (chatSocket.isConnected()) {
			BufferedReader in=null;
			try {
				in=new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
				String readSome=null;
				while ((readSome=in.readLine())!=null) {
					System.out.println(readSome);
				}
				in.close();
				chatSocket.close();
			} catch (IOException i) {
				try {
					if (in!=null) in.close();
					if (chatSocket!=null) chatSocket.close();
				} catch (IOException e) {
				}
				
				System.out.println("성공적으로 퇴장하셨어요~~~♡\n다음에 또 만나요 Q(^.^Q)");
				System.exit(1);
			}
		}
	}
}