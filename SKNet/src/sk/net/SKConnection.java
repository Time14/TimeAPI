package sk.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SKConnection {
	
	private final int TYPE;
	
	private int id = -1;
	
	private Socket socket;
	
	private SKServer server;
	private SKClient client;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private SKCommunicationListener communicationListener;
	private Thread communicationListenerThread;
	
	/**
	 * 
	 * The constructor for this connection expecting a type (SK_SERVER or SK_CLIENT) and a socket.
	 * 
	 * @param type the type of this connection.
	 * @param socket the socket of this connection.
	 */
	public SKConnection(int type, Socket socket) {
		this.TYPE = type;
		this.socket = socket;
	}
	
	
	/**
	 * 
	 * Sends the session initialization packets between a client and server depending on the type specified in the constructor.
	 * 
	 * This method is only called internally of the SKNet API.
	 * 
	 * @param packet the packet to be sent to either the client or server. Must be of type {@link SKClientPacket} or {@link SKServerPacket}.
	 * @return this {@link SKServer} instance.
	 * @throws IllegalArgumentException if the argument was not of type {@link SKClientPacket} or {@link SKServerPacket}. Also thrown if the argument does not correspond to this connection type.
	 * @throws IOException if an I/O error occurs while writing stream header.
	 * @throws IllegalStateException if the end-point responded an invalid session initialization packet or if a client failed to validate a packet.
	 */
	protected SKConnection init(SKPacket packet) {
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("An I/O error occured while reading the stream header");
			e.printStackTrace();
		}
		
		
		
		if(packet instanceof SKServerPacket) {
			
			if(TYPE == SKNet.SK_CLIENT)
				throw new IllegalArgumentException("Cannot send \"" + packet.getName() + "\" Packet from connection of type SK_CLIENT");
			
			try {
				
				//Sends the server packet to the client
				sendPacket(packet);
				
				//Waits for response from the client
				SKPacket clientPacket = receivePacket();
				
				//Is the received packet the of the correct class?
				if(!(clientPacket instanceof SKClientPacket))
					throw new IllegalStateException("Invalid packet received, expected a client side session initialization packet");
				
				//Is the packet valid?
				if(!((SKClientPacket)clientPacket).isValid())
					throw new IllegalStateException("The client did not failed to validate the connection");
				
				//The procedure was successful
				
				this.id = ((SKServerPacket)packet).ID;
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		} else if(packet instanceof SKClientPacket) {
			if(TYPE == SKNet.SK_SERVER)
				throw new IllegalArgumentException("Cannot send \"" + packet.getName() + "\" Packet from connection of type SK_SERVER");
			
			try {
				
				//Waits for response from server
				SKPacket serverPacket = receivePacket();
				
				if(!(serverPacket instanceof SKServerPacket))
					throw new IllegalStateException("Invalid packet received, expected a server side session initialization packet");
				
				this.id = ((SKServerPacket)serverPacket).ID;
				
				((SKClientPacket)packet).validate();
				
				//Sends a validated client packet back to the server
				sendPacket(packet);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} else {
			throw new IllegalArgumentException("Packet must be of type SKClientPacket or SKServerPacket");
		}
		
		
		return this;
	}
	
	
	/**
	 * 
	 * Closes this connecting, including sockets and I/O streams.
	 * 
	 */
	protected void close() {
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void startListening() {
		communicationListener = new SKCommunicationListener(this);
		communicationListenerThread = new Thread(communicationListener);
		
		communicationListenerThread.start();
	}
	
	/**
	 * 
	 * Sends an {@link SKPacket} to the end-point of this connection's socket.
	 * 
	 * @param packet the packet to be sent.
	 * @return this {@link SKConnection} instance.
	 * @throws IOException if the OutputStream failed to operate.
	 */
	public SKConnection sendPacket(SKPacket packet) throws IOException {
		out.writeObject(packet);
		out.flush();
		return this;
	}
	
	
	/**
	 * 
	 * Receives an {@link SKPacket} from the input stream.
	 * 
	 * @return the packet read from the input stream.
	 * @throws IllegalStateException if the object received was not an {@link SKPacket}.
	 * @throws IOException if the InputStream failed to operate.
	 * @throws ClassNotFoundException if the received packet does not have a known corresponding class.
	 */
	protected SKPacket receivePacket() throws IOException, ClassNotFoundException {
		
		Object obj = in.readObject();
		
		if(obj instanceof SKPacket)
			return (SKPacket)obj;
		else if(obj == null)
			return null;
		else
			throw new IllegalStateException("The object received was not an SKPacket");
	}
	
	
	/**
	 * 
	 * Sets the {@link SKServer} instance associated with with this object. This will only work if the connection is of type SK_SERVER.
	 * 
	 * Otherwise, an IllegalStateException will be thrown.
	 * 
	 * @param server the {@link SKServer} instance to pass.
	 * @return this {@link SKConnection} instance.
	 * @throws IllegalStateException if this connection is not of type SK_SERVER.
	 */
	protected SKConnection setServer(SKServer server) {
		if(TYPE == SKNet.SK_CLIENT)
			throw new IllegalStateException("Cannot set server to a connection of type SK_CLIENT");
		this.server = server;
		return this;
	}
	
	/**
	 * 
	 * Returns the {@link SKServer} instance associated with with this object. This will only work if the connection is of type SK_SERVER.
	 * 
	 * Otherwise, an IllegalStateException will be thrown.
	 * 
	 * @return the {@link SKServer} instance of this connection.
	 * @throws IllegalStateException if this connection is not of type SK_SERVER.
	 */
	protected SKServer getServer() {
		if(TYPE == SKNet.SK_CLIENT)
			throw new IllegalStateException("Cannot get server from a connection of type SK_CLIENT");
		return server;
	}
	
	/**
	 * 
	 * Sets the {@link SKClient} instance associated with with this object. This will only work if the connection is of type SK_CLIENT.
	 * 
	 * Otherwise, an IllegalStateException will be thrown.
	 * 
	 * @param client the {@link SKClient} instance to pass.
	 * @return this {@link SKConnection} instance.
	 * @throws IllegalStateException if this connection is not of type SK_CLIENT.
	 */
	protected SKConnection setClient(SKClient client) {
		if(TYPE == SKNet.SK_SERVER)
			throw new IllegalStateException("Cannot set client to a connection of type SK_SERVER");
		this.client = client;
		return this;
	}
	
	/**
	 * 
	 * Returns the {@link SKClient} instance associated with with this object. This will only work if the connection is of type SK_CLIENT.
	 * 
	 * Otherwise, an IllegalStateException will be thrown.
	 * 
	 * @return the {@link SKClient} instance of this connection.
	 * @throws IllegalStateException if this connection is not of type SK_CLIENT.
	 */
	protected SKClient getClient() {
		if(TYPE == SKNet.SK_SERVER)
			throw new IllegalStateException("Cannot get client from a connection of type SK_SERVER");
		return client;
	}
	
	
	/**
	 * 
	 * Returns the type of this connection. It could be either SK_CLIENT or SK_SERVER.
	 * 
	 * @return the type of this connection.
	 */
	public int getType() {
		return TYPE;
	}
	
	
	/**
	 * 
	 * Returns the host address of this connection represented as a {@link String}.
	 * 
	 * @return the host address of this connection.
	 */
	public String getHostAddress() {
		return socket.getInetAddress().getHostAddress();
	}
	
	
	/**
	 * 
	 * Returns the id of this {@link SKConnection} instance.
	 * 
	 * If the id has not yet been assigned, or if the session initialization proccess failed, the id will is set to -1.
	 * 
	 * @return the id of this {@link SKConnection} instance.
	 */
	public int getID() {
		return id;
	}
	
	
	/**
	 * 
	 * Returns whether or not this connection has been closed.
	 * 
	 * @return if the connection is closed or not.
	 */
	public boolean isClosed() {
		return socket.isClosed();
	}
	
	/**
	 * 
	 * Returns the active port of this connection.
	 * 
	 * @return the port of this connection.
	 */
	public int getPort() {
		return socket.getPort();
	}
}