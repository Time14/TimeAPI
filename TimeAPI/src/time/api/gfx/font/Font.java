package time.api.gfx.font;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import time.api.gfx.texture.Texture;

public class Font {
	
	private HashMap<Integer, FontChar> characters;
	
	private Texture texture;
	
	
	public Font(String path) {
		String[] paths = path.split("/");
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 1; i < paths.length; i++)
			sb.append(paths[i - 1]).append("/");
		
		loadFont(sb.toString(), paths[paths.length - 1]);
	}
	
	private void loadFont(String folderPath, String fileName) {
		
		characters = new HashMap<>();
		
		try (Scanner s = new Scanner(new File(folderPath + fileName))) {
			while(s.hasNextLine()) {
				String[] line = s.nextLine().split(" +");
				
				switch(line[0]) {
				case "page":
					texture = new Texture(folderPath + getStringProperty(line[2]));
					break;
				case "char":
					characters.put(getIntProperty(line[1]), new FontChar(getIntProperty(line[2]),
							getIntProperty(line[3]), getIntProperty(line[4]), getIntProperty(line[5]),
							getIntProperty(line[6]), getIntProperty(line[7]), getIntProperty(line[8])));
					break;
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String getStringProperty(String property) {
		return property.split("\\=")[1].replace("\"", "");
	}
	
	private int getIntProperty(String property) {
		return Integer.parseInt(property.split("\\=")[1]);
	}
	
	public final FontChar getChar(char c) {
		return characters.get((int)c);
	}
	
	public final Texture getTexture() {
		return texture;
	}
	
	public static final Font FNT_ARIAL = new Font("res/font/arial.fnt");
	
}