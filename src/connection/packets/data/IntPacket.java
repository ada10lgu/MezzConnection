package connection.packets.data;

import java.nio.ByteBuffer;

import connection.packets.Packet;

public class IntPacket extends DataPacket {

	private int i;

	public IntPacket(int i) {
		this.i = i;
	}

	public IntPacket(byte[] data, Packet[] packets) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		i = bb.getInt();
	}

	@Override
	protected byte[] getLoad() {
		return ByteBuffer.allocate(4).putInt(i).array();
	}

	@Override
	protected byte getType() {
		return INT_PACKET;
	}
	
	@Override
	public String toString() {
		return "" + i;
	}

	public int toInt() {
		return i;
	}

}
