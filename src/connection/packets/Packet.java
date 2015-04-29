package connection.packets;

import connection.data.ByteArrayReader;
import connection.packets.data.BytePacket;
import connection.packets.data.IntPacket;
import connection.packets.data.StringPacket;
import connection.packets.operands.ChatPacket;
import connection.packets.operands.LoginPacket;
import connection.packets.operands.NullPacket;
import connection.packets.operands.ResponsePacket;


public abstract class Packet {

	protected static final byte SUPER_PACKET = 1;

	protected static final byte RESPONSE_PACKET = 10;
	protected static final byte LOGIN_PACKET = 11;
	protected static final byte CHAT_PACKET = 12;
	protected static final byte NULL_PACKET = 13;

	protected static final byte STRING_PACKET = 101;
	protected static final byte INT_PACKET = 102;
	protected static final byte BYTE_PACKET = 103;

	public static Packet createPacket(byte[] data) {
		return createPacket(new ByteArrayReader(data));
	}

	private static Packet createPacket(ByteArrayReader bar) {
		byte type = bar.next();

		byte dataSize = bar.next();
		byte[] data = new byte[dataSize];
		bar.fillArray(data);

		byte packetSize = bar.next();
		Packet[] packets = new Packet[packetSize];
		for (int i = 0; i < packetSize; i++) {
			packets[i] = createPacket(bar);
		}

		Packet p = null;

		switch (type) {
		case SUPER_PACKET:
			p = new SuperPacket(data, packets);
			break;
		case RESPONSE_PACKET:
			p = new ResponsePacket(data,packets);
			break;
		case LOGIN_PACKET:
			p = new LoginPacket(data, packets);
			break;
		case STRING_PACKET:
			p = new StringPacket(data, packets);
			break;
		case INT_PACKET:
			p = new IntPacket(data, packets);
			break;
		case BYTE_PACKET: 
			p = new BytePacket(data,packets);
			break;
		case CHAT_PACKET:
			p = new ChatPacket(data,packets);
			break;
		case NULL_PACKET:
			p = new NullPacket(data,packets);
			break;
		default:
			throw new IllegalArgumentException(bar.toString());
		}

		return p;
	}

	public final byte[] getData() {
		int size = 3;
		byte[] load = getLoad();
		size += load.length;
		for (Packet p : getPackages())
			size += p.getData().length;

		byte[] data = new byte[size];
		int i = 0;
		data[i++] = getType();
		data[i++] = (byte) load.length;
		for (byte b : load)
			data[i++] = b;
		data[i++] = (byte) getPackages().length;
		for (Packet p : getPackages()) {
			byte[] temp = p.getData();
			for (byte b : temp)
				data[i++] = b;
		}

		return data;
	}

	protected abstract byte[] getLoad();

	protected abstract Packet[] getPackages();

	protected abstract byte getType();

}
