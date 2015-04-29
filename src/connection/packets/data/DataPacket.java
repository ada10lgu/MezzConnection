package connection.packets.data;

import connection.packets.Packet;

public abstract class DataPacket extends Packet  {

	@Override
	protected Packet[] getPackages() {
		return new Packet[0];
	}

}
