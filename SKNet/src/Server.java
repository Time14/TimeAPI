import java.io.IOException;
import java.util.Scanner;

import sk.net.SKServer;

public class Server {
	
	private volatile boolean running = false;
	
	public Server() {
		SKServer server = new SKServer();
		
		try {
			server.addPacketListener(new ServerListener());
			server.start();
			server.bind("localhost", 6969);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scanner scanner = new Scanner(System.in);
		
		running = true;
		while(running) {
			String str = scanner.nextLine();
			
			switch(str) {
			case "go":
				try {
					server.getConnection(0).sendPacket(new SKPacketMSG("go!"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case "dc":
				server.disconnect(0);
				break;
			case "closed":
				System.out.println(server.getConnection(0).isClosed());
				break;
			}
		}
	}
	
	public static final void main(String[] args) {
		new Server();
	}
}