package input;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.lwjgl.glfw.GLFW;

import time.api.debug.Debug;

public final class InputManager {

	static private HashMap<String, String> keyMap = new HashMap<String, String>();
	static private HashMap<String, String> scanMap = new HashMap<String, String>();
	static private HashMap<String, KeyState> states = new HashMap<String, KeyState>();
	
	/**
	 * Updates the inputs, changes all Pressed and Released states to Down and Up.
	 * This method should be called at the end of the main loop
	 * @return
	 */
	public static void update() {
		Object[] keys = states.keySet().toArray();
		for(Object key : keys) {
			if (states.get(key.toString()) == KeyState.Pressed)
				states.put(key.toString(), KeyState.Down);
			if (states.get(key.toString()) == KeyState.Released)
				states.put(key.toString(),  KeyState.Up);
		}
	}
	
	public static void updateInput(int key, int scan, int mods, int action) {
		
		if (action == GLFW.GLFW_REPEAT) return;
		
		
		String refKey = key + "#" + mods;
		String refScan = scan + "$" + mods;
		
		if (keyMap.containsKey(refKey)) {
			if (states.containsKey(keyMap.get(refKey))) {
				if (action == GLFW.GLFW_PRESS)
					states.put(keyMap.get(refKey), KeyState.Pressed);
				else if (action == GLFW.GLFW_RELEASE)
					states.put(keyMap.get(refKey), KeyState.Released);
			} else {
				keyMap.remove(refScan);
			}
		}
		
		if (scanMap.containsKey(refScan)) {
			if (states.containsKey(scanMap.get(refScan))) {
				if (action == GLFW.GLFW_PRESS)
					states.put(scanMap.get(refScan), KeyState.Pressed);
				else if (action == GLFW.GLFW_RELEASE)
					states.put(scanMap.get(refScan), KeyState.Released);
			} else {
				scanMap.remove(refScan);
			}
		}
	}
	
	public static void removeKey(String name) {
		
		states.remove(name);
		
	}
	
	public static KeyState getKey(String name) {
		return states.get(name);
	}
	
	
//	Scancode keys:
//		123$4
//	Keycode keys:
//		123#4

	
	public static void addScan(int scan, int mods, String name) {
		addScan(scan + "$" + mods, name);
	}
	
	public static void addScan(String key, String name) {
		
		scanMap.put(key, name);
		states.put(name, KeyState.Up);
		
	}
	
	public static void addKey(int key, int mods, String name) {
		addKey(key + "#" + mods, name);
	}
	
	public static void addKey(String key, String name) {
		
		keyMap.put(key, name);
		states.put(name, KeyState.Up);
		
	}
}
