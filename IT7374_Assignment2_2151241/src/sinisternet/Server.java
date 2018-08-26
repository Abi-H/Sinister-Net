package sinisternet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class Server {

	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private ServerSocket server;
	private int port, startPort;
	private String hash, algorithm;
	private HashConverter hashConverter;
	private boolean matchFound;

	public Server(int ID) throws ClassNotFoundException, IOException, NoSuchAlgorithmException {
		this.startPort = 31332;
		this.port = ID + startPort - 1; // Gives a different port to each server within the port range
		this.matchFound = false;
		this.hashConverter = new HashConverter();
		//testServer();
		//run();
	}

	private void startServer() throws ClassNotFoundException {
		try {
			server = null;
			System.out.println("Starting server...");
			server = new ServerSocket(port);
			System.out.println("Server started.");
		} catch (IOException e) {
			System.out.println("Could not start server.");
		}
	}

	private void setupStreams() throws IOException {
		System.out.println("Setting up server streams ...");
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
		System.out.println("Server streams set up.");
	}

	private void connectToClient() throws IOException {
		socket = server.accept();
		System.out.println("Connected to client at " + socket.getInetAddress());
	}

	public void run() throws NoSuchAlgorithmException {
		try {
			startServer();
			connectToClient();
			setupStreams();
			process();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Could not process server.");
		} finally {
			try {
				stopServer();
			} catch (IOException e) {
				System.out.println("Could not stop server");
			}
		}
	}
	
	public int getPort() {
		return port;
	}

	private void process() throws ClassNotFoundException, IOException, NoSuchAlgorithmException {
		String word = "";

		this.hash = receiveData();		
		System.out.println("Hash is: " + this.hash);

		this.algorithm = receiveData();
		System.out.println("Algorithm is " + this.algorithm);

		while (!word.equals("#ENDOFFILE") && !matchFound) {
			word = receiveData();
			System.out.println("Received password: " + word);

			if (hashConverter.getHash(word, algorithm).equals(this.hash)) {
				sendData("#YES");
				sendData(word);
				System.out.println("***************Match found********************");
				matchFound = true;
			} else if(word.equals("#ENDOFFILE")){
				sendData("Hash could not be cracked");
			} else {
				sendData("\n");
			}
		}
	}

	private void sendData(String password) throws IOException {
		out.writeObject(password);
	}

	private String receiveData() throws IOException, ClassNotFoundException {
		String word = (String) in.readObject();
		return word;
	}

	private void stopServer() throws IOException {
		if (server != null) {
			socket.close();
			server.close();
			System.out.println("Server stopped.");
		}
	}

	//Allows the server to be identified by Network Scanner
	private void testServer() throws ClassNotFoundException, IOException { 
		startServer();
		connectToClient();
		stopServer();
	}
}
