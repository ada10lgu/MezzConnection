package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import connection.packets.Packet;
import connection.packets.data.BytePacket;
import connection.packets.data.IntPacket;
import connection.packets.data.StringPacket;
import connection.packets.operands.ChatPacket;
import connection.packets.operands.NullPacket;

public class PacketTest {

	private Packet backAndForth(Packet p) {
		return Packet.createPacket(p.getData());
	}

	private void testPacket(Packet p1) {
		Packet p2 = backAndForth(p1);
		
		byte[] a1 = p1.getData();
		byte[] a2 = p2.getData();
		
		if (a1.length == a2.length) {
			for (int i = 0; i < a1.length ; i++) {
				assertEquals(a1[i],a2[i]);
			}
		}  else 
			fail("" + p1 + " != " + p2);
	}
	

	@Test
	public void BytePacket() {
		byte[] bs = new byte[] {0,-1,73,Byte.MAX_VALUE,Byte.MIN_VALUE,54};

		for (byte b : bs) {
			BytePacket bp = new BytePacket(b);
			assertEquals(b,bp.getByte());
			
			testPacket(bp);
		}
	}

	@Test
	public void StringPacket() {
		String[] ss = new String[] {
				"",
				"lars",
				"banan",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" };

		for (String s : ss) {
			StringPacket sp = new StringPacket(s);
			assertEquals(s, sp.toString());

			testPacket(sp);
		}
	}
	
	@Test
	public void IntPacket() {
		int[] is = new int[] { 0, -1, Integer.MAX_VALUE, Integer.MIN_VALUE,54335,123145,76623};				

		for (int i : is) {
			IntPacket ip = new IntPacket(i);
			assertEquals(i, ip.toInt());

			testPacket(ip);
		}
	}
	
	@Test
	public void ChatPacket() {
		String[] ss = new String[] {
				"",
				"lars",
				"banan",
				"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
				"fjdkf klfjdskl jkl jfkldsljflksd едц едц едце дце fds_"};
		for (String s : ss) {
			ChatPacket cp = new ChatPacket(s);
			assertEquals(s, cp.getMessage());

			testPacket(cp);
		}
	}
	
	@Test
	public void NullPacket() {
		NullPacket np = new NullPacket();
		
		testPacket(np);
	}
	

}
