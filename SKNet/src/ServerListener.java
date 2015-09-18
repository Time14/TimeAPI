import java.io.IOException;

import sk.net.SKConnection;
import sk.net.SKPacket;
import sk.net.SKPacketListener;


public class ServerListener implements SKPacketListener {

	@Override
	public void received(SKConnection connection, SKPacket packet) {
		if(packet instanceof SKPacketMSG) {
			System.out.println("Client " + connection.getID() + ": " + ((SKPacketMSG)packet).MSG);
			try {
				Thread.sleep(1000);
				connection.sendPacket(new SKPacketMSG("pong"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void connected(SKConnection connection) {
		System.out.println("Client " + connection.getHostAddress() + " connected with id " + connection.getID());
	}

	@Override
	public void disconnected(SKConnection connection) {
		System.out.println("Client " + connection.getID() + " disconnected");
	}
	
}
