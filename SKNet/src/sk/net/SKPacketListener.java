package sk.net;

public interface SKPacketListener {
	
	
	/**
	 * 
	 * Called when an {@link SKPacket} is received from either a client or server.
	 * 
	 * @param connection the {@link SKConnection} from which the packet was received.
	 * @param packet the received packet.
	 */
	public void received(SKConnection connection, SKPacket packet);
	
	
	/**
	 * 
	 * Called when a new connection to either a client or server is established.
	 * 
	 * @param connection the new connection established.
	 */
	public void connected(SKConnection connection);
	
	
	/**
	 * 
	 * Called when a client is disconnected from the server.
	 * 
	 * @param connection the closed connection.
	 */
	public void disconnected(SKConnection connection);
	
}