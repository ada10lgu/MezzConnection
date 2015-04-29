package connection.packets.data;

import connection.packets.Packet;


public class StringPacket extends DataPacket {

	private String s;
	
	public StringPacket(String s) {
		this.s = s;
	}
	
	public StringPacket(byte[] data, Packet[] packets) {
		s = new String(data);
	}

	@Override
	protected byte[] getLoad() {
		return s.getBytes();
	}

	@Override
	protected byte getType() {
		return STRING_PACKET;
	}
	
	@Override
	public String toString() {
		return s;
	}
	
}
