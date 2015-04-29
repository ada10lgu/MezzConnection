package connection.data;

import java.util.Arrays;

public class ByteArrayReader {

	private byte[] data;
	private int i = 0;

	public ByteArrayReader(byte[] data) {
		this.data = data;
	}
	
	public boolean hasNext() {
		return !(i == data.length);
	}
	
	public byte next() {
		if (!hasNext())
			throw new RuntimeException("End of data");
		return data[i++];
	}
	
	public void fillArray(byte[] array) {
		for (int j = 0; j < array.length; j++) {
			array[j] = next();
		}
	}
	
	@Override
	public String toString() {
		return Arrays.toString(data);
	}
	
}