package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import connection.Server;

public class ServerTest {

	@Test
	public void connect() {

		try {
			Server s = 	new Server(12345, null);
			s.close();
		} catch (IOException e) {
			fail(e.toString());
		}

	}

}
