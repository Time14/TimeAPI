package time.api.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Loader {
	
	public static final String loadSource(String path) {
		Scanner scanner;
		try {
			scanner = new Scanner(new File(path));
			StringBuilder source = new StringBuilder();
			
			while(scanner.hasNextLine()) {
				source.append(scanner.nextLine());
				if(scanner.hasNextLine())
					source.append("\n");
			}
			
			scanner.close();
			
			return source.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static final BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}
}