package sk.net;

import java.io.IOException;

public class SKCommunicationListener implements Runnable {
	
	private final int TYPE;
	
	private SKServer server;
	private SKClient client;
	
	private SKConnection connection;
	
	public SKCommunicationListener(SKConnection connection) {
		this.connection = connection;
		this.TYPE = connection.getType();
		
		if(TYPE == SKNet.SK_SERVER) {
			this.server = connection.getServer();
		} else if(TYPE == SKNet.SK_CLIENT) {
			this.client = connection.getClient();
		}
	}
	
	@Override
	public void run() {
		
		while(!connection.isClosed()) {
			
			try {
				SKPacket packet = connection.receivePacket();
				
				//Disconnect packet?
				if(packet instanceof SKDisconnectPacket) {
					if(TYPE == SKNet.SK_CLIENT) {
						client.close();
						break;
					} else if(TYPE == SKNet.SK_SERVER) {
						server.close(connection.getID());
						break;
					}
				}
				
				
				//Handle received packet
				if(TYPE == SKNet.SK_SERVER) {
					for(SKPacketListener packetListener : server.getPacketListeners()) {
						new Thread(() -> packetListener.received(connection, packet)).start();
					}
				} else if(TYPE == SKNet.SK_CLIENT) {
					for(SKPacketListener packetListener : client.getPacketListeners()) {
						new Thread(() -> packetListener.received(connection, packet)).start();
					}
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}