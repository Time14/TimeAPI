package sk.net;

import java.io.Serializable;

public abstract class SKPacket implements Serializable {
	
	/**
	 * 
	 * Returns the name of this packet. Used as a more concrete identification.
	 * 
	 * @return the name of this packet
	 */
	public abstract String getName();
	
	public String toString() {
		return getName();
	}
}