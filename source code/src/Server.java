
import java.io.*;
import java.security.*;
//import javax.net.ssl.
import java.net.*; 
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class Server implements Runnable {
	private ChatServerRunnable clients[] = new ChatServerRunnable[3];
	public int clientCount = 0;
	public int ePort = -1;
	static String eServer = "";
	
	public Server(int port) throws RemoteException {
		super();
		this.ePort = port;

		// server rmi 생성
		try {
			Emoticon em = new EmoticonImpl();
			Naming.rebind("rmi://localhost:1099/Emoticon", em); // 192.168.56.1
			System.out.println("야호!! RMI가 연결되었어요~~~♡");
		} catch (Exception e) {
			System.out.println("Trouble: "+e);
		}
	}

	public void run() {
		KeyStore ks;
		KeyManagerFactory kmf;
		SSLContext sc;
		
		String runRoot = "D:\\Haeri_Data\\2019 1학기\\NetworkProgramming\\JAVAeclipes\\CmdTalk\\bin\\"; // 본인의 루트로 바꿔야합니다. 

		SSLServerSocketFactory socketFactory = null;
		SSLServerSocket serverSocket = null;
		
		String ksName = runRoot+".keystore\\ServerKey";
		
		char keyStorePass[]="blackpink".toCharArray();
		char keyPass[]="foreveryoung".toCharArray();
		
		try {
			ks = KeyStore.getInstance("JKS");
			
			ks.load(new FileInputStream(ksName), keyStorePass);
			
			kmf=KeyManagerFactory.getInstance("SunX509");
			kmf.init(ks, keyPass);
			
			sc = SSLContext.getInstance("TLS");
			sc.init(kmf.getKeyManagers(), null, null);
			
			socketFactory = sc.getServerSocketFactory();
			serverSocket = (SSLServerSocket) socketFactory.createServerSocket(ePort);
			
			System.out.println ("Server가 "+InetAddress.getLocalHost().getHostAddress()+"의 "+ePort+"port에서 실행되었어요~~~♡\n당신은 저의 주인님이랍니다 (*'-⌒*)v ♡ ");		
			while (true) {
				addClient(serverSocket);
			}
		} catch (BindException b) {
			System.out.println("Can't bind on: "+ePort);
		} catch (IOException i) {
			System.out.println(i);
		} catch (Exception e) {
			System.out.println("What?? exit~");
		} finally {
			try {
				if (serverSocket != null) serverSocket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
	}
	
	public int whoClient(int clientID) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getClientID() == clientID)
				return i;
		return -1;
	}
	
	public void putClient(int clientID, String inputLine) throws IOException {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].getClientID() == clientID) {
				System.out.println("writer: "+clientID);
			} else {
				System.out.println("write: "+clients[i].getClientID());
				clients[i].out.println(inputLine);
			}
		}
	}
	
	public void addClient(SSLServerSocket serverSocket) {
		SSLSocket clientSocket = null;
		
		if (clientCount < clients.length) {
			try {
				clientSocket = (SSLSocket) serverSocket.accept();
				clientSocket.setSoTimeout(80000); // 2000/sec
			} catch (IOException i) {
				System.out.println ("Accept() fail: "+i);
			}
			clients[clientCount] = new ChatServerRunnable(this, clientSocket);
			new Thread(clients[clientCount]).start();
			clientCount++;
			System.out.println ("Client connected: " + clientSocket.getPort()
					+", CurrentClient: " + clientCount);
		} else {
			try {
				SSLSocket dummySocket = (SSLSocket) serverSocket.accept();
				ChatServerRunnable dummyRunnable = new ChatServerRunnable(this, dummySocket);
				new Thread(dummyRunnable);
				dummyRunnable.out.println(dummySocket.getPort()
						+ " < Sorry maximum user connected now");
				System.out.println("Client refused: maximum connection "
						+ clients.length + " reached.");
				dummyRunnable.close();
			} catch (IOException i) {
				System.out.println(i);
			}	
		}
	}
	
	public synchronized void delClient(int clientID) {
		int pos = whoClient(clientID);
		ChatServerRunnable endClient = null;
	      if (pos >= 0) {
	    	   endClient = clients[pos];
	    	  if (pos < clientCount-1)
	    		  for (int i = pos+1; i < clientCount; i++)
	    			  clients[i-1] = clients[i];
	    	  clientCount--;
	    	  System.out.println("Client removed: " + clientID
	    			  + " at clients[" + pos +"], CurrentClient: " + clientCount);
	    	  endClient.close();
	      }
	}
	
	public static void main(String[] args) throws RemoteException {
		if (args.length != 1) {
			System.out.println("Usage: Classname ServerPort");
			System.exit(1);
		}
		
		int ePort = Integer.parseInt(args[0]);
//		String eServName = args[1];
		// rmi port 항상 1099로 고정
		
		new Thread(new Server(ePort)).start();
	}
}

class ChatServerRunnable implements Runnable {
	protected Server chatServer=null;
	protected SSLSocket clientSocket=null;
	protected PrintWriter out=null;
	protected BufferedReader in=null;
	
	public int clientID=-1;
	
	public ChatServerRunnable (Server server, SSLSocket socket) {
		this.chatServer = server;
		this.clientSocket = socket;
		clientID = clientSocket.getPort();
		try {
			out=new PrintWriter(clientSocket.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException i) {
		}
	}
	public void run() {
		try {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				chatServer.putClient(getClientID(),getClientID()+":"+inputLine); // client로 부터 input을 받음
				if(inputLine.equalsIgnoreCase("<그럼이만>"))
					break;
			}
			chatServer.delClient(getClientID()); // client와 연결 끊기
		} catch (SocketTimeoutException ste) {
			System.out.println("Socket timeout Occurred, force close() : "+getClientID());
			chatServer.delClient(getClientID());
		} catch (IOException e) {
			chatServer.delClient(getClientID());
		}
	}
	public int getClientID() {
		return clientID;
	}
	
	public void close() {
		try {
			if (in != null) in.close();
			if(out != null) out.close();
			if(clientSocket!=null) clientSocket.close();
		} catch(IOException i) {
		}
	}
}