package sinisternet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkScanner {

	private String subnet;
	private File file;
	private BufferedWriter out;
	private String filename;

	public NetworkScanner() throws UnknownHostException, IOException {
		System.out.println("Network Scanner started ...");
		this.subnet = "192.168.1";
		this.filename = "hosts.txt";
		//createFile(filename);
		//findHosts(subnet);		
	}
	
	public String getFilename() {
		return filename;
	}

	private void findHosts(String subnet) throws UnknownHostException, IOException {
		int hostTimeout = 1000;
		int portTimeout = 500;
		int numHosts = 0;

		for (int i = 1; i < 30; i++) { // For testing purposes, viable host address on LAN from .1 to .30
			int port = 31332;
			String host = subnet + "." + i;
			
			if (InetAddress.getByName(host).isReachable(hostTimeout)) {
				System.out.print(host + " is reachable");
				
				while (port < 31336) { // Valid range for server ports 31332 - 31336
					if (testConnection(host, portTimeout, port)) {
						numHosts++;
						saveToFile(host, String.valueOf(port));
						System.out.println("Adding " + host + " and " + port + " to valid host list");
					}
					port++;
				}
			}
		}
		
		out.close();
		System.out.println("Closed file");
		System.out.println("Scanning complete.");
		System.out.println(numHosts + " valid hosts detected.");

	}

	private boolean testConnection(String host, int timeout, int port) {

		try {
			Socket socket = new Socket();

			socket.connect(new InetSocketAddress(host, port), timeout);
			System.out.println(" and is a valid host");
			socket.close();

			return true;
		} catch (Exception e) {
			System.out.print(" but is not a valid host on port " + port + "\n");
			return false;
		}
	}
	
	private void createFile(String filename) throws IOException {
		file = new File(filename);
		System.out.println("File created");
		out = new BufferedWriter(new FileWriter(file));
		System.out.println("Buffered Writer started");
	}
	

	private void saveToFile(String host, String port) throws IOException{
		out.write(host + ":" + port + "\n");
		System.out.println("Saved " + host + " at " + port + " to file");
	}
}
