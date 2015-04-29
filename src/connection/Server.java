package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	private ServerSocket ss;
	private ConnectionHandler ch;
	
	public Server(int port, ConnectionHandler ch) throws IOException {
		ss = new ServerSocket(port);
		this.ch = ch;
	}

	@Override
	public void run() {
		System.out.println("Server started");
		while (!isInterrupted()) {
			try {
				Socket s = ss.accept();
				System.out.println(s);
				ch.addConnection(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public synchronized void interrupt() {
		super.interrupt();
		try {
			ss.close();
		} catch (IOException e) {
			System.err.println("Could not close socket");
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		ss.close();
	}

}
