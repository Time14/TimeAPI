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
	
	private static boolean useSaveFile = false;
	
	/**
	 * Updates the inputs, changes all Pressed and Released states to Down and Up.
	 * This method should be called at the end of the main loop
	 * 
	 */
	public static final void update() {
		Object[] keys = states.keySet().toArray();
		for(Object key : keys) {
			if (states.get(key.toString()) == KeyState.PRESSED)
				states.put(key.toString(), KeyState.DOWN);
			if (states.get(key.toString()) == KeyState.RELEASED)
				states.put(key.toString(),  KeyState.UP);
		}
	}
	
	public static final void rebind(String name) {
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
	public static final void loadInputs() {
		
		if(!useSaveFile)
			return;
		
		File file = new File(SAVE_DIRECTORY);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			
			while (reader.ready()) {
				String line = reader.readLine();
				
				//Make sure the input is correct.
				if (line.matches(KEY_LINE_REGEX)) {
					String[] split = line.split("-");
					registerKey(split[0], split[1]);
				} else if (line.matches(SCAN_LINE_REGEX)){
					String[] split = line.split("-");
					registerScan(split[0], split[1]);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes all the current virtual keys to a file for storage
	 */
	public static final void saveInputs() {
		
		if(!useSaveFile)
			return;
		
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
	public static final void updateInput(int key, int scan, int mods, int action) {

		if (action == GLFW.GLFW_REPEAT) return;
		
		String refKey = key + "#" + mods;
		String refScan = scan + "$" + mods;
		
		//Check if anything needs rebinding
		if (action == GLFW.GLFW_RELEASE && rebind != "") {
			registerScan(refScan, rebind);
			Debug.log("Key: " + key + "\t" + (char)(key));
			Debug.log("Scan: " + scan + "\t" + (char)(scan));
			rebind = "";
			return;
		}
		
		if (keyMap.containsKey(refKey)) {
			if (states.containsKey(keyMap.get(refKey))) {
				if (action == GLFW.GLFW_PRESS)
					states.put(keyMap.get(refKey), KeyState.PRESSED);
				else if (action == GLFW.GLFW_RELEASE)
					states.put(keyMap.get(refKey), KeyState.RELEASED);
			} else {
				keyMap.remove(refScan);
			}
		}
		
		if (scanMap.containsKey(refScan)) {
			if (states.containsKey(scanMap.get(refScan))) {
				if (action == GLFW.GLFW_PRESS)
					states.put(scanMap.get(refScan), KeyState.PRESSED);
				else if (action == GLFW.GLFW_RELEASE)
					states.put(scanMap.get(refScan), KeyState.RELEASED);
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
	public static final void removeKey(String name) {
		
		states.remove(name);
	}
	
	/**
	 * Fetches the @see  KeyState of virtual key at this moment in time.
	 * 
	 * @param name - the name of the virtual key.
	 * @return - the current KeyState of the key.
	 */
	public static final KeyState getKeyState(String name) {
		return states.get(name);
	}
	
	/**
	 * 
	 * Compares a key with a key state.
	 * 
	 * @param key - the name of the key
	 * @param keyState - the key state
	 */
	public static final boolean checkState(String key, KeyState keyState) {
		return getKeyState(key) == keyState;
	}
	
	/**
	 * 
	 * Returns whether or not the specified key was pressed this frame.
	 * 
	 * @param key - the name of the key
	 * @return true if the key was pressed
	 */
	public static final boolean wasPressed(String key) {
		return checkState(key, KeyState.PRESSED);
	}
	
	/**
	 * 
	 * Returns whether or not the specified key was released this frame.
	 * 
	 * @param key - the name of the key
	 * @return true if the key was released
	 */
	public static final boolean wasReleased(String key) {
		return checkState(key, KeyState.RELEASED);
	}
	
	/**
	 * 
	 * Checks if the specified key is being held down.
	 * 
	 * @param key - the name of the key
	 * @return true if the key is currently being held down
	 */
	public static final boolean isDown(String key) {
		return checkState(key, KeyState.DOWN);
	}
	
	/**
	 * 
	 * Checks if the specified key is not being held down.
	 * 
	 * @param key - the name of the key
	 * @return true if the key is currently not being held down
	 */
	public static final boolean isUp(String key) {
		return checkState(key, KeyState.UP);
	}

	/**
	 * Adds a scan input to the virtual key hash map.
	 * @param scan - the scan code of the virtual key.
	 * @param mods - the mods for the virtual key, e.g shift or alt.
	 * @param name - the name of the virtual key.
	 */
	public static final void registerScan(int scan, int mods, String name) {
		registerScan(scan + "$" + mods, name);
	}
	
	/**
	 * Adds a scan input to the virtual key hash map.
	 * @param key - the completed string for a virtual key using scan codes.
	 * @param name - the name of the virtual key.
	 */
	public static final void registerScan(String key, String name) {
		
		scanMap.put(key, name);
		states.put(name, KeyState.UP);
		
	}
	
	 /**
	  * Adds a key input to the virtual key hash map.
	  * @param key - the key code of the virtual key.
	  * @param mods - the mods for the virtual key, e.g shift or alt.
	  * @param name - the name of the virtual key.
	  */
	public static final void registerKey(int key, int mods, String name) {
		registerKey(key + "#" + mods, name);
	}
	
	/**
	 * Adds a key input to the virtual key hash map.
	 * @param key - the completed string for a virtual key using key codes.
	 * @param name - the name of the virtual key.
	 */
	public static final void registerKey(String key, String name) {
		
		keyMap.put(key, name);
		states.put(name, KeyState.UP);
		
	}
	
	/**
	 * 
	 * Sets whether or not to use a save file.
	 * 
	 * @param useSaveFile - true if you wish to use a save file
	 */
	public static final void useSaveFile(boolean useSaveFile) {
		InputManager.useSaveFile = useSaveFile;
	}
}
