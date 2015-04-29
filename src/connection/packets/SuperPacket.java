package connection.packets;

import connection.Model;
import connection.packets.data.BytePacket;
import connection.packets.operands.OperatorPacket;

public class SuperPacket extends Packet {

	private final static byte ACK = 0;
	private final static byte SEND = -1;

	private OperatorPacket load;
	private BytePacket way;
	private BytePacket id;

	public SuperPacket(OperatorPacket p, byte id) {
		this(p, id, SEND);
	}

	private SuperPacket(OperatorPacket p, byte id, byte way) {
		this.load = p;
		this.id = new BytePacket(id);
		this.way = new BytePacket(way);
	}

	public SuperPacket getAck(Model m) {
		OperatorPacket response = load.perform(m);
		SuperPacket sp = new SuperPacket(response, id.getByte(), ACK);
		return sp;
	}

	public boolean isAck() {
		return way.getByte() == ACK;
	}

	public SuperPacket(byte[] data, Packet[] packets) {
		way = (BytePacket) packets[0];
		id = (BytePacket) packets[1];
		load = (OperatorPacket) packets[2];
	}

	public OperatorPacket getPacket() {
		return load;
	}

	public byte getId() {
		return id.getByte();
	}

	@Override
	protected byte[] getLoad() {
		return new byte[0];
	}

	@Override
	protected Packet[] getPackages() {
		return new Packet[] { way, id, load };
	}

	@Override
	protected byte getType() {
		return SUPER_PACKET;
	}

	@Override
	public String toString() {
		String w = way.getByte() == SEND ? "SEND" : "ACK";
		byte i = id.getByte();
		return "Super! (" + w + " id" + i + "): " + load;
	}

}
