package time.api.gfx.texture;

import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import time.api.library.Library;
import time.api.util.Loader;
import time.api.util.Util;

public class Texture {
	
	public final Library<Texture> LIB = new Library<>("TextureLibrary");
	
	protected int id;
	protected int width;
	protected int height;
	
	public Texture() {}
	
	public Texture(String path) {
		this(path, false);
	}
	
	public Texture(String path, boolean repeat) {
		loadTexture(path, repeat);
	}
	
	public Texture loadTexture(String path, boolean repeat) {
		try {
			BufferedImage image = Loader.loadImage(path);
			
			int width = image.getWidth();
			int height = image.getHeight();
			
			int[] pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		
			genTexture(pixels, repeat, width, height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public Texture genTexture(int[] pixels, boolean repeat, int width, int height) {
		this.width = width;
		this.height = height;
		
		id = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		
		if(repeat) {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		} else {
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		}
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, Util.toIntBuffer(pixels));
		
		System.out.println("Generated new texture with ID: " + id);
		
		return this;
	}
	
	public Texture bind(int texTarget) {
		
		if(texTarget > 31 || texTarget < 0) {
			throw new InvalidParameterException("Texture target must be in range 0 - 31");
		}
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + texTarget);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		return this;
	}
	
	public Texture bind() {
		return bind(0);
	}
	
	public Texture setParameters(int param, int... pnames) {
		bind();
		for(int i : pnames)
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, i, param);
		return this;
	}
	
	public static final void unbindAll() {
		for(int i = 0; i < 32; i++)
			unbind(i);
	}
	
	public static final void unbind() {
		unbind(0);
	}
	
	public static final void unbind(int target) {

		if(target > 31 || target < 0) {
			throw new InvalidParameterException("Target must be in range 0 - 31");
		}
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + target);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getID() {
		return id;
	}
	
	public void destroy() {
		GL11.glDeleteTextures(id);
		System.out.println("Destroyed texture with ID: " + id);
	}
	
	public static final Texture DEFAULT_TEXTURE = new Texture("res/texture/default_texture.png");
}