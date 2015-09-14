package debug;

import org.lwjgl.opengl.GL11;

import physics.Body;

public class Debug {
	
	/**
	 * Draws a rectangle, should only be used for debugging.
	 * 
	 * @param x - the x position
	 * @param y - the y position
	 * @param w - width
	 * @param h - height
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
	
	public static void drawRect(Body b) {
		drawRect(b.getPos().getX(), b.getPos().getY(), b.getDim().getX(), b.getDim().getY());
	}
	
	/**
	 * Prints the message and where it was called.
	 * 
	 * @param message - The message you wish to print.
	 * @param i  - The depth of the caller function.
	 */
	public static void Log(String message, int i) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		System.out.println("[" + ste[i].getFileName() + " @ " + ste[i].getLineNumber() + "] " + message);
	}
	
	/**
	 * Prints the message and where it was called.
	 * 
	 * @param message - The message you wish to print.
	 */
	public static void Log(String message) {
		int i = 2;
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		System.out.println("[" + ste[i].getFileName() + " @ " + ste[i].getLineNumber() + "] " + message);
	}
}
