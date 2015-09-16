package sk.net;

public class SKClientPacket extends SKPacket {
	
	private boolean valid = false;
	
	public void validate() {
		valid = true;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public String getName() {
		return "Client Session Initialization";
	}
}