package time.api.util;

import org.lwjgl.glfw.GLFW;

public final class Time {
	
	private static float prevTime = getTime();
	
	private static float deltaTime;
	
	private static int fps;
	private static int fpsCount;
	private static float deltaStack;
	
	public static final void update() {
		float currentTime = getTime();
		deltaTime = currentTime - prevTime;
		prevTime = currentTime;
		
		deltaStack += deltaTime;
		fpsCount++;
		if(deltaStack > 1) {
			fps = --fpsCount;
			deltaStack = 0;
			fpsCount = 0;
		}
	}
	
	public static final float getTime() {
		return (float)GLFW.glfwGetTime();
	}
	
	public static final float getDelta() {
		return deltaTime;
	}
	
	public static final int getFPS() {
		return fps;
	}
}