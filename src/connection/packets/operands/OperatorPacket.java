package connection.packets.operands;

import connection.Model;
import connection.packets.Packet;


public abstract class OperatorPacket extends Packet {

	public abstract OperatorPacket perform(Model m);

}
