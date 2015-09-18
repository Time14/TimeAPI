import java.io.IOException;

import sk.net.SKConnection;
import sk.net.SKPacket;
import sk.net.SKPacketListener;


public class ClientListener implements SKPacketListener {

	@Override
	public void received(SKConnection connection, SKPacket packet) {
		if(packet instanceof SKPacketMSG) {
			System.out.println("Server: " + ((SKPacketMSG)packet).MSG);
			try {
				Thread.sleep(1000);
				connection.sendPacket(new SKPacketMSG("ping"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void connected(SKConnection connection) {
		System.out.println("Connected to " + connection.getHostAddress() + ":" + connection.getPort() + " (id: " + connection.getID() + ")");
	}

	@Override
	public void disconnected(SKConnection connection) {
		System.out.println("Disconnected from server");
	}
	
}
