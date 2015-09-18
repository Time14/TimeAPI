import java.io.IOException;
import java.util.Scanner;

import sk.net.SKClient;
import sk.net.SKServer;

public class Client {
	
	private volatile boolean running = false;
	
	public Client() {
		SKClient client = new SKClient();
		
		try {
			client.addPacketListener(new ClientListener());
			client.connect("localhost", 6969);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scanner scanner = new Scanner(System.in);
		
		running = true;
		while(running) {
			String str = scanner.nextLine();
			
			switch(str) {
			case "dc":
				client.disconnect();
				break;
			case "closed":
				System.out.println(client.getConnection().isClosed());
				break;
			}
		}
	}
	
	public static final void main(String[] args) {
		new Client();
	}
	
}