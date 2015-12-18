package time.api.debug;

import org.lwjgl.opengl.GL11;

import time.api.physics.Body;

public class Debug {
	
	/**
	 * 
	 * Draws a rectangle to the screen with give proportions.
	 * Should only be used for debugging.
	 * 
	 * @param x - the x coordinate of the center
	 * @param y - the y coordinate of the center
	 * @param w - the width of the rectangle
	 * @param h - the height of the rectangle
	 */
	public static void drawRect(float x, float y, float w, float h) {
		
//		Log("Drawing a rectangle sub-optimally", 2);
		
		//Set color
		GL11.glColor3f(1, 1, 1);
		
		//Begin
		GL11.glBegin(GL11.GL_QUADS);
		    GL11.glVertex2f(x - w/2,y - h/2);
		    GL11.glVertex2f(x + w/2,y - h/2);
		    GL11.glVertex2f(x + w/2,y + h/2);
		    GL11.glVertex2f(x - w/2,y + h/2);
	    GL11.glEnd();	
	}
	
	/**
	 * 
	 * Draw a rectangle with the give Body's dimensions.
	 * 
	 * @param b - the body to draw
	 */
	public static void drawRect(Body b) {
		drawRect(b.getPos().getX(), b.getPos().getY(), b.getDim().getX(), b.getDim().getY());
	}
	
	/**
	 * 
	 * Prints the message and where it was called.
	 * 
	 * @param message - the message you wish to print.
	 * @param i  - the depth of the caller function.
	 */
	public static void log(Object message, int i) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		System.out.println("[" + ste[i].getFileName() + " @ " + ste[i].getLineNumber() + "] " + message);
	}
	
	/**
	 * 
	 * Prints the message and where it was called.
	 * 
	 * @param message - the message you wish to print.
	 */
	public static void log(Object message) {
		int i = 2;
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		System.out.println("[" + ste[i].getFileName() + " @ " + ste[i].getLineNumber() + "] " + message);
	}
}
