package sk.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * This class is used for hosting a server with SKNet.
 * 
 * @author Alfred Sporre
 *
 */
public class SKServer {
	
	private HashMap<Integer, SKConnection> connections;
	
	private ServerSocket serverSocket;
	
	private SKConnectionListener connectionListener;
	private Thread connectionListenerThread;
	
	private ArrayList<SKPacketListener> packetListeners;
	
	private volatile boolean running = false;
	
	private int timeout = 10000;
	private int maxConnections = 1000;
	
	public SKServer() {
		packetListeners = new ArrayList<>();
		connections = new HashMap<>();
	}
	
	/**
	 * 
	 * Starts all the underlying server threads.
	 * 
	 * @return this {@link SKServer} instance.
	 * @throws IOException if the server socket could not be opened.
	 * @throws IllegalStateException if there are no packet listeners associated with this server.
	 */
	public SKServer start() throws IOException {
		
		if(packetListeners.size() < 1)
			throw new IllegalStateException("There are no packet listeners associated with this server");
		
		serverSocket = new ServerSocket();
		
		serverSocket.setSoTimeout(timeout);
		
		running = true;
		
		connectionListener = new SKConnectionListener(this);
		connectionListenerThread = new Thread(connectionListener);
		connectionListenerThread.start();
		
		return this;
	}
	
	/**
	 * 
	 * Adds a client connection to this server and performs the session initialization procedure.
	 * 
	 * @param socket the socket connection to the client.
	 * @return this {@link SKServer} instance.
	 */
	public synchronized SKServer addConnection(Socket socket) {
		
		SKConnection connection = new SKConnection(SKNet.SK_SERVER, socket).setServer(this);
		
		int id = 0;
		while(id < maxConnections || maxConnections == 0) {
			
			if(connections.get(id) == null) {
				connection.init(new SKServerPacket(id));
				break;
			}
			
			id++;
		}
		
		connections.put(id, connection);
		
		for(SKPacketListener packetListener : packetListeners) {
			packetListener.connected(connection);
		}
		
		connection.startListening();
		
		return this;
	}
	
	public void disconnect(int clientID) {
		try {
			connections.get(clientID).sendPacket(new SKDisconnectPacket());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected SKServer close(int clientID) {
		
		SKConnection connection = connections.get(clientID);
		
		try {
			
			for(SKPacketListener packetListener : packetListeners) {
				packetListener.disconnected(connection);
			}
			
			connection.sendPacket(new SKDisconnectPacket());
			
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	/**
	 * 
	 * Adds an {@link SKPacketListener} to this server.
	 * 
	 * @param packetListener the {@link SKPacketListener} to be added.
	 * @return this {@link SKServer} instance.
	 */
	public SKServer addPacketListener(SKPacketListener packetListener) {
		packetListeners.add(packetListener);
		return this;
	}
	
	/**
	 * 
	 * Binds the server socket to the specified hostname and port.
	 * 
	 * @param hostname the hostname of the server.
	 * @param port the port of the server.
	 * @return this {@link SKServer} instance.
	 * @throws IOException if the specified hostname and port could not be bound.
	 */
	public SKServer bind(String hostname, int port) throws IOException {
		return bind(new InetSocketAddress(hostname, port));
	}
	
	/**
	 * 
	 * Binds the server socket to the specified {@link InetSocketAddress}.
	 * 
	 * @param address the {@link InetSocketAddress} of the server.
	 * @return this {@link SKServer} instance.
	 * @throws IOException if the specified {@link InetSocketAddress} could not be bound.
	 */
	public SKServer bind(InetSocketAddress address) throws IOException {
		serverSocket.bind(address);
		return this;
	}
	
	
	/**
	 * 
	 * Returns the connection with the corresponding id. If there is no connection with the specified id this method will return null.
	 * 
	 * @param id the id of the connection.
	 * @return the connection with the specified id.
	 */
	public SKConnection getConnection(int id) {
		return connections.get(id);
	}
	
	/**
	 * 
	 * Returns the {@link ServerSocket} of this server.
	 * 
	 * @return the {@link ServerSocket}.
	 */
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	/**
	 * 
	 * Returns the host name of this server.
	 * 
	 * @return the host name.
	 */
	public String getHostName() {
		return serverSocket.getInetAddress().getHostName();
	}
	
	
	/**
	 * 
	 * Returns the host address of this server.
	 * 
	 * @return the host address.
	 */
	public String getHostAddress() {
		return serverSocket.getInetAddress().getHostAddress();
	}
	
	
	/**
	 * 
	 * Returns the server's {@link InetAddress}.
	 * 
	 * @return the {@link InetAddress}.
	 */
	public InetAddress getInetAddress() {
		return serverSocket.getInetAddress();
	}
	
	
	/**
	 * 
	 * Sets the number of maximum client connections allowed.
	 * 
	 * @param maxConnections the number of client connections to accept. Set the value to 0 for unlimited connections.
	 * @return this {@link SKServer} instance.
	 */
	public SKServer setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
		return this;
	}
	
	
	/**
	 * 
	 * Returns the number of maximum client connections allowed.
	 * 
	 * @return the number of client connections to accept.
	 */
	public int getMaxConnections() {
		return maxConnections;
	}
	
	
	/**
	 * 
	 * Returns the local port of this server.
	 * 
	 * @return the local port of this server.
	 */
	public int getLocalPort() {
		return serverSocket.getLocalPort();
	}
	
	
	/**
	 * 
	 * Returns the SO-timeout for this server's {@link ServerSocket}.
	 * 
	 * @return the SO-timeout for this server's {@link ServerSocket}.
	 */
	public int getTimeout() {
		return timeout;
	}
	
	
	/**
	 * 
	 * Sets the SO-timeout for this server's {@link ServerSocket}.
	 * 
	 * @param ms the timeout specified in milliseconds.
	 * @return this {@link SKServer} instance.
	 */
	public SKServer setTimeout(int ms) {
		timeout = ms;
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
	
	
	/**
	 * 
	 * Returns whether or not this server is running.
	 * 
	 * @return true if the server is running.
	 */
	public boolean isRunning() {
		return running;
	}
}