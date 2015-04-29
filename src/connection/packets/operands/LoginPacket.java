package connection.packets.operands;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import connection.Model;
import connection.packets.Packet;
import connection.packets.data.IntPacket;
import connection.packets.data.StringPacket;

public class LoginPacket extends OperatorPacket {

	private StringPacket username;
	private StringPacket password;

	public LoginPacket(String username, String password) {
		this.username = new StringPacket(username);
		
		
		String hash = "0";
		try {
			hash = md5(password);
		} catch (NoSuchAlgorithmException e) {
		}
		
		this.password = new StringPacket(hash);
		
	}
	
	public static String md5(String s) throws NoSuchAlgorithmException {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(s.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}

	public LoginPacket(byte[] data, Packet[] packets) {
		username = (StringPacket) packets[0];
		password = (StringPacket) packets[1];
	}

	@Override
	protected byte[] getLoad() {
		return new byte[0];
	}

	@Override
	protected Packet[] getPackages() {
		return new Packet[] {username,password};
	}

	@Override
	protected byte getType() {
		return LOGIN_PACKET;
	}
	
	@Override
	public String toString() {
		return "Login: user \"" + username + "\" pass \"" + password +  "\"";  
	}

	@Override
	public OperatorPacket perform(Model m) {
		int id = m.login();
		ResponsePacket rp = new ResponsePacket(new IntPacket(id));
		return rp;
	}

}
