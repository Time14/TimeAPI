package sk.net;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

class SKConnectionListener implements Runnable {
	
	private SKServer server;
	
	
	/**
	 * 
	 * Supplies the connection listener with the essential information required before the thread is run.
	 * 
	 * @param server the {@link SKServer} associated with this connection listener.
	 */
	protected SKConnectionListener(SKServer server) {
		this.server = server;
	}
	
	
	/**
	 * 
	 * Starts accepting client connections for this server. Called from the {@link SKServer#start()} method.
	 * 
	 */
	@Override
	public void run() {
		try {
			while(server.isRunning()) {
				
				if(!server.getServerSocket().isBound())
					continue;
				
				Socket socket;
				
				try {
					socket = server.getServerSocket().accept();
				} catch (SocketTimeoutException e) {
					continue;
				}
				
				new Thread(() -> {server.addConnection(socket);}).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}