import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import java.net.UnknownHostException;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class MyFactory extends SSLSocketFactory {
	private SSLSocketFactory factory;
	String eServer = "";
	int ePort = -1;
	
	public MyFactory(String eServer, int ePort) {
		factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		this.eServer = eServer;
		this.ePort = ePort;
	}
	
	public SSLSocket createSocket() throws UnknownHostException, IOException {
		return (SSLSocket)factory.createSocket(eServer, ePort);
	}

	@Override
	public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getDefaultCipherSuites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getSupportedCipherSuites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(InetAddress host, int port) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(String host, int port, InetAddress localHost, int localPort)
			throws IOException, UnknownHostException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
