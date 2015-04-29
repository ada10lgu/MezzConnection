package connection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;

import connection.data.RandomByteSet;
import connection.packets.Packet;
import connection.packets.SuperPacket;
import connection.packets.operands.OperatorPacket;

public class Client {

	private static int CLIENTS;
	private final int n;
	private Connection conn;
	private RandomByteSet ids;
	private HashMap<Byte, OperatorPacket> inbox;
	private Model m;

	public Client(Socket s, Model m) {
		n = ++CLIENTS;
		this.m = m;
		conn = new Connection(s);
		ids = new RandomByteSet();
		inbox = new HashMap<>();

		new PacketHandler().start();
	}

	public Client(String addr, int port, Model m) throws UnknownHostException,
			IOException {
		this(new Socket(addr, port), m);
	}

	public synchronized byte send(OperatorPacket p) {
		byte id = ids.getRandomByte();
		SuperPacket sp = new SuperPacket(p, id);
		conn.send(sp.getData());
		return id;
	}

	public synchronized void sendNoResponse(OperatorPacket p) {
		byte id = send(p);
		new ResponseWaiter(id);
	}

	public synchronized OperatorPacket waitForReply(byte id) {
		OperatorPacket op = null;
		while ((op = inbox.get(id)) == null)
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace(System.err);
			}
		ids.returnByte(id);
		return op;
	}

	private synchronized void addResponse(byte id, OperatorPacket op) {
		inbox.put(id, op);
		notifyAll();
	}

	public void close() throws IOException {
		conn.close();
	}

	public boolean isAlive() {
		return conn.isAlive();
	}

	private class PacketHandler extends Thread {

		@Override
		public void run() {
			System.out.println("Lets do this!");
			while (true) {
				byte[] b = conn.recieve();
				System.out.println("Recieved package!");
				try {
					SuperPacket sp = (SuperPacket) Packet.createPacket(b);

					if (sp.isAck()) {
						System.out.println("ack:\t" + sp);
						byte id = sp.getId();
						OperatorPacket op = sp.getPacket();
						addResponse(id, op);
					} else {
						SuperPacket response = sp.getAck(m);
						System.out.println("incomming:\t" + sp);
						System.out.println("response:\t" + response);
						conn.send(response.getData());
					}
				} catch (ClassCastException e) {
					System.err.println("Error casting data to superpacket");
					System.err.println(Arrays.toString(b));
				}
			}

		}
	}

	private class ResponseWaiter extends Thread {
		private byte id;

		public ResponseWaiter(byte id) {
			this.id = id;
			start();
		}

		@Override
		public void run() {
			System.out.println(id + ":yay i got a job!");
			waitForReply(id);
			System.out.println(id + ": Done!");
		}
	}

	@Override
	public String toString() {
		String status = isAlive() ? "Online" : "Offline";
		return "Connection #" + n + ": " + status;
	}

}
