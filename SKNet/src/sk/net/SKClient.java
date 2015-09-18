package sk.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SKClient {
	
	private int id;
	
	private SKConnection connection;
	
	private ArrayList<SKPacketListener> packetListeners;
	
	public SKClient() {
		packetListeners = new ArrayList<>();
	}
	
	/**
	 * 
	 * Connects to the a server with the specified address and port.
	 * 
	 * The CommunicationListener thread is also started.
	 * 
	 * @param address the host address to connect to.
	 * @param port the active server port of the host.
	 * @return this {@link SKClient} instance.
	 * @throws UnknownHostException if the IP address of the host could not be determined.
	 * @throws IOException if an I/O error occurs when creating the socket.
	 * @throws IllegalArgumentException if the port parameter is outside the specified range of valid port values, which is between 0 and 65535, inclusive.
	 * @throws IllegalStateException if there are no packet listeners associated with this client.
	 */
	public SKClient connect(String address, int port) throws UnknownHostException, IOException {
		
		if(packetListeners.size() < 1)
			throw new IllegalStateException("There are no packet listeners associated with this client");
		
		Socket socket = new Socket(address, port);
		
		connection = new SKConnection(SKNet.SK_CLIENT, socket).setClient(this);
		
		connection.init(new SKClientPacket());
		
		for(SKPacketListener packetListener : packetListeners) {
			packetListener.connected(connection);
		}
		
		connection.startListening();
		
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public SKClient disconnect() {
		
		try {
			connection.sendPacket(new SKDisconnectPacket());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * 
	 * Disconnects this client from the server.
	 * 
	 * @return this {@link SKClient} instance.
	 */
	protected SKClient close() {
		
		for(SKPacketListener packetListener : packetListeners) {
			packetListener.disconnected(connection);
		}
		
		try {
			connection.sendPacket(new SKDisconnectPacket());
			
			SKPacket packet = connection.receivePacket();
			
			if(!(packet instanceof SKDisconnectPacket))
				throw new IllegalStateException("Invalid packet received, expected SKDisconnectPacket");
			
			connection.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	
	/**
	 * 
	 * Adds an {@link SKPacketListener} to this client.
	 * 
	 * @param packetListener the {@link SKPacketListener} to be added.
	 * @return this {@link SKClient} instance.
	 */
	public SKClient addPacketListener(SKPacketListener packetListener) {
		packetListeners.add(packetListener);
		return this;
	}
	
	/**
	 * 
	 * Returns a list of all packet listeners associated with this server.
	 * 
	 * @return a list of all packet listeners.
	 */
	public ArrayList<SKPacketListener> getPacketListeners() {
		return packetListeners;
	}
	
	
	public SKConnection getConnection() {
		return connection;
	}
}