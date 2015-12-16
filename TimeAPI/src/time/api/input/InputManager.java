package time.api.input;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.glfw.GLFW;
import org.omg.Messaging.SyncScopeHelper;

import time.api.debug.Debug;

public final class InputManager {

	
	public static final String SAVE_DIRECTORY = "res/keybindings.keys";
	
	private static final String KEY_LINE_REGEX = "[\\d]+[#][\\d]{1}[-]{1}[\\w]+";
	private static final String SCAN_LINE_REGEX = "[\\d]+[#][\\d]{1}[-]{1}[\\w]+";
	
	static private String rebind = "";
	static private HashMap<String, String> keyMap = new HashMap<String, String>();
	static private HashMap<String, String> scanMap = new HashMap<String, String>();
	static private HashMap<String, KeyState> states = new HashMap<String, KeyState>();
	
	/**
	 * Updates the inputs, changes all Pressed and Released states to Down and Up.
	 * This method should be called at the end of the main loop
	 * 
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
	
	public static void rebind(String name) {
		if (states.containsKey(name)) {
			rebind = name;
		} else {
			System.err.println("Could not find virtual key with the name: " + name);
		}
	}
	
	/**
	 * Loads all the key bindings from the keybindings.keys file 
	 * and generates virtual keys for them. 
	 */
	public static void loadInputs() {
		try {
			File file = new File(SAVE_DIRECTORY);
			if (!file.exists()) {
				file.createNewFile();
				return;
			}
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			while (reader.ready()) {
				String line = reader.readLine();
				
				//Make sure the input is correct.
				if (line.matches(KEY_LINE_REGEX)) {
					String[] split = line.split("-");
					addKey(split[0], split[1]);
				} else if (line.matches(SCAN_LINE_REGEX)){
					String[] split = line.split("-");
					addScan(split[0], split[1]);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes all the current virtual keys to a file for storage
	 */
	public static void saveInputs() {
		BufferedWriter output = null;
		try {
			File file = new  File(SAVE_DIRECTORY);
			if (file.exists())
				file.delete();
			
			file.createNewFile();
			
			output = new BufferedWriter(new FileWriter(file));
			
			Object[] keys = keyMap.keySet().toArray();
			for (Object objectKey : keys) {
				String key = objectKey.toString();
				String name = keyMap.get(key);
				
				Debug.log(key + "-" + name);
				
				output.write(key);
				output.write("-");
				output.write(name);
				output.newLine();
				output.flush();
			}
			
			keys = scanMap.keySet().toArray();
			for (Object objectKey : keys) {
				String key = objectKey.toString();
				String name = scanMap.get(key);
				
				Debug.log(key + "-" + name);
				
				output.write(key);
				output.write("-");
				output.write(name);
				output.newLine();
				output.flush();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Makes sure all the pressed buttons this frame affect the virtual buttons.
	 * @param key - the key code of the affected keyboard key
	 * @param scan - the scan code of the affected keyboard key 
	 * @param mods - the modifiers that are held too. E.g. shift or alt
	 * @param action - the id of the action that happened, a constant passed in by GLFW
	 */
	public static void updateInput(int key, int scan, int mods, int action) {

		if (action == GLFW.GLFW_REPEAT) return;
		
		String refKey = key + "#" + mods;
		String refScan = scan + "$" + mods;
		
		//Check if anything needs rebinding
		if (action == GLFW.GLFW_RELEASE && rebind != "") {
			addScan(refScan, rebind);
			Debug.log("Key: " + key + "\t" + (char)(key));
			Debug.log("Scan: " + scan + "\t" + (char)(scan));
			rebind = "";
			return;
		}
		
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
	
	/**
	 * Removes the key from the input check, freeing up allocated resources.
	 * 
	 * @param name - the name of the virtual key.
	 */
	public static void removeKey(String name) {
		
		states.remove(name);
	}
	
	/**
	 * Fetches the @see  KeyState of virtual key at this moment in time.
	 * 
	 * @param name - the name of the virtual key.
	 * @return - the current KeyState of the key.
	 */
	public static KeyState getKey(String name) {
		return states.get(name);
	}

	/**
	 * Adds a scan input to the virtual key hash map.
	 * @param scan - the scan code of the virtual key.
	 * @param mods - the mods for the virtual key, e.g shift or alt.
	 * @param name - the name of the virtual key.
	 */
	public static void addScan(int scan, int mods, String name) {
		addScan(scan + "$" + mods, name);
	}
	
	/**
	 * Adds a scan input to the virtual key hash map.
	 * @param key - the completed string for a virtual key using scan codes.
	 * @param name - the name of the virtual key.
	 */
	public static void addScan(String key, String name) {
		
		scanMap.put(key, name);
		states.put(name, KeyState.Up);
		
	}
	
	 /**
	  * Adds a key input to the virtual key hash map.
	  * @param key - the key code of the virtual key.
	  * @param mods - the mods for the virtual key, e.g shift or alt.
	  * @param name - the name of the virtual key.
	  */
	public static void addKey(int key, int mods, String name) {
		addKey(key + "#" + mods, name);
	}
	
	/**
	 * Adds a key input to the virtual key hash map.
	 * @param key - the completed string for a virtual key using key codes.
	 * @param name - the name of the virtual key.
	 */
	public static void addKey(String key, String name) {
		
		keyMap.put(key, name);
		states.put(name, KeyState.Up);
		
	}
}
