package connection.packets.operands;

import connection.Model;
import connection.packets.Packet;
import connection.packets.data.StringPacket;

public class ChatPacket extends OperatorPacket {

	private StringPacket sp;

	public ChatPacket(String s) {
		sp = new StringPacket(s);
	}

	public ChatPacket(byte[] data, Packet[] packets) {
		sp = (StringPacket) packets[0];
	}

	@Override
	public OperatorPacket perform(Model m) {
		m.incommingMessage(sp.toString());
		return new NullPacket();
	}

	@Override
	protected byte[] getLoad() {
		return new byte[0];
	}

	@Override
	protected Packet[] getPackages() {
		return new Packet[] { sp };
	}

	@Override
	protected byte getType() {
		return CHAT_PACKET;
	}

	@Override
	public String toString() {
		return "Chat: " + sp.toString();
	}

	public Object getMessage() {
		return sp.toString();
	}

}
