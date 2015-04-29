package connection.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class RandomByteSet {
	private HashSet<Byte> set;
	private ArrayList<Byte> list;
	private Random r;
	
	public RandomByteSet() {
		set = new HashSet<>();
		list = new ArrayList<>();
		r = new Random();
	}
	
	public synchronized byte getRandomByte() {
		if (list.isEmpty())
			fill();
		int i = r.nextInt(list.size());
		return list.remove(i);
		
	}
	
	public synchronized void returnByte(byte b) {
		list.add(b);
	}
	
	private void fill() {
		byte size = (byte) set.size();
		for (byte i = size; i < size+10; i++) {
			set.add(i);
			list.add(i);
		}
	}
	
	
	@Override
	public String toString() {
		return set.toString() + "\n" + list.toString();
	}
}
