package sk.net;


public class SKClientPacket extends SKPacket {
	
	private boolean valid = false;
	
	/**
	 * 
	 * Called by the server to verify a successful initialization.
	 * 
	 */
	public void validate() {
		valid = true;
	}
	
	/**
	 * 
	 * Returns whether or not this packet has been verified by the server.
	 * 
	 * @return whether or not this packet has been verified by the server 
	 */
	public boolean isValid() {
		return valid;
	}
	
	public String getName() {
		return "Client Session Initialization";
	}
}