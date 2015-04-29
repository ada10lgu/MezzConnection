package connection.data;

import java.util.ArrayList;

public class ConnectionQueue {

	
	private ArrayList<byte[]> data;
	
	public ConnectionQueue() {
		data = new ArrayList<>();
	}
	
	public synchronized boolean isEmpty() {
		return data.isEmpty();
	}
	
	public synchronized void offer(byte[] b) {
		data.add(b);
	}
	
	public synchronized byte[] poll() {
		if (isEmpty())
			return null;
		return data.remove(0);
	}
	
}
