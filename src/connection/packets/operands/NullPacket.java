package connection.packets.operands;

import connection.Model;
import connection.packets.Packet;

public class NullPacket  extends OperatorPacket{

	public NullPacket() {
		
	}
	
	public NullPacket(byte[] data, Packet[] packets) {
	}

	@Override
	public OperatorPacket perform(Model m) {
		return new NullPacket();
	}

	@Override
	protected byte[] getLoad() {
		return new byte[0];
	}

	@Override
	protected Packet[] getPackages() {
		return new Packet[0];
	}

	@Override
	protected byte getType() {
		return NULL_PACKET;
	}

	@Override
	public String toString() {
		return "NULL";
	}
	
}
