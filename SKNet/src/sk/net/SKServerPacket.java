package sk.net;

public class SKServerPacket extends SKPacket {
	
	public final int ID;
	
	public SKServerPacket(int id) {
		this.ID = id;
	}
	
	public String getName() {
		return "Server Session Initialization";
	}
}