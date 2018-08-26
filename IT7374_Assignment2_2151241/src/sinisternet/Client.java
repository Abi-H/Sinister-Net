package sinisternet;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Client implements Runnable {

	private ObjectInputStream in;
	private ObjectOutputStream out;

	private String crackedHash, hash, filename, algorithm;
	private HashMap<String, Integer> hosts;
	private HashMap<Socket, ObjectOutputStream> outputSockets;
	private HashMap<Socket, ObjectInputStream> inputSockets;
	private boolean matchFound;

	public Client(String hash, String algorithm) throws ClassNotFoundException, IOException {
		this.hash = hash;
		this.filename = "hosts.txt";
		this.algorithm = algorithm;
		this.matchFound = false;
		this.crackedHash = "";
		hosts = new LinkedHashMap<>();
		outputSockets = new LinkedHashMap<>();
		inputSockets = new LinkedHashMap<>();
	}
	
	public String getFilename() {
		return filename;
	}

	private void setupConnection(String host, int port) throws UnknownHostException, IOException {

		Socket socket;

		try {
			socket = new Socket(host, port);
			System.out.println("Connected to server at " + socket.getRemoteSocketAddress());
			setupStreams(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setupStreams(Socket socket) throws IOException {
		System.out.println("Setting up client streams for " + socket.getInetAddress());
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();

		in = new ObjectInputStream(socket.getInputStream());

		outputSockets.put(socket, out);
		inputSockets.put(socket, in);

		System.out.println("Client streams set up for " + socket.getInetAddress());
	}

	@Override
	public void run() {

		try {
			readFromFile(this.filename);
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}

		if (!hosts.isEmpty()) {
			try {

				for (Entry<String, Integer> entry : hosts.entrySet()) {
					setupConnection(entry.getKey(), entry.getValue());
				}

				try {
					process();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					closeStreams();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void process() throws IOException, ClassNotFoundException {
		File file = new File("passwords.txt");
		String line = "";

		for (Entry<Socket, ObjectOutputStream> entry : outputSockets.entrySet()) {
			entry.getValue().writeObject(this.hash);
			System.out.println("Sent hash");
			entry.getValue().writeObject(this.algorithm);
			System.out.println("Sent algorithm");
		}

		BufferedReader reader;

		reader = new BufferedReader(new FileReader(file));

		System.out.println("File exists: " + file.exists());
		System.out.println("Reader ready: " + reader.ready());

		while ((line = reader.readLine()) != null && !matchFound) {
			for (Entry<Socket, ObjectOutputStream> entry : outputSockets.entrySet()) {
				entry.getValue().writeObject(line);
			}

			for (Entry<Socket, ObjectInputStream> entry : inputSockets.entrySet()) {
				String response = (String) entry.getValue().readObject();

				if (!response.equals("\n")) {
					this.crackedHash = (String) in.readObject();
					matchFound = true;
					matchIsFound(line);
					break;
				}
			}
		}

		for (Entry<Socket, ObjectOutputStream> entry : outputSockets.entrySet()) {
			entry.getValue().writeObject("#ENDOFFILE");
		}

		reader.close();
	}

	private void sendData(String word) throws IOException {
		out.writeObject(word);
		// out.writeUTF(word);
		System.out.println("Client sent: " + word);
	}

	private String receiveData() throws IOException, ClassNotFoundException {
		String word = (String) in.readObject();
		return word;
	}

	private void readFromFile(String filename) throws ClassNotFoundException, IOException {
		File file = new File("hosts.txt");
		FileInputStream fis = new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		String line = "";

		while ((line = reader.readLine()) != null) {
			String[] split = line.split(":");
			hosts.put(split[0], Integer.parseInt(split[1]));
		}

		reader.close();
	}

	private void matchIsFound(String password) {
		System.out.println("Success! The password cracked hash is: " + password);
	}

	private void closeStreams() throws IOException {
		for (Entry<Socket, ObjectOutputStream> entry : outputSockets.entrySet()) {
			entry.getValue().close();
		}

		for (Entry<Socket, ObjectInputStream> entry : inputSockets.entrySet()) {
			entry.getValue().close();
		}
	}

	public String getCrackedHash() {
		return this.crackedHash;
	}
}
