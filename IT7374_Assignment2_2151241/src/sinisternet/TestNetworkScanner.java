package sinisternet;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestNetworkScanner {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testServerConstructor() throws ClassNotFoundException, NoSuchAlgorithmException, IOException {
		int id = 1;
		int port = 31332;
		Server instance = new Server(id);
		assertEquals(instance.getPort(), port);
	}

}
