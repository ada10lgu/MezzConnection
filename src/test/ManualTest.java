package test;

import java.io.IOException;
import java.net.Socket;

import connection.Client;
import connection.ConnectionHandler;
import connection.Server;
import connection.packets.data.StringPacket;
import connection.packets.operands.ResponsePacket;

public class ManualTest implements ConnectionHandler {
	public static void main(String[] args) {

		new ManualTest();

	}

	public ManualTest() {
		try {
			new Server(12345,this).start();;
			new Client("localhost", 12345,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addConnection(Socket s) {
		System.out.println(s);
		Client c = new Client(s,null);
		System.out.println(c);
		c.send(new ResponsePacket(new StringPacket("Lars")));
	}

}
