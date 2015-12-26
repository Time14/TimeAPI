package time.api.gfx.shader;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.DoubleBuffer;

import org.lwjgl.glfw.GLFW;

import time.api.debug.Debug;
import time.api.gfx.Vertex;
import time.api.math.Matrix4f;
import time.api.math.Vector2f;
import time.api.util.Util;

public class OrthographicShaderProgram extends ShaderProgram {
	
	private static float left, right, bottom, top;
	private static final Matrix4f projection = new Matrix4f();
	
	/**
	 * 
	 * Constructs a new OrthographicShaderProgram.
	 * 
	 */
	public OrthographicShaderProgram() {
		super("res/shader/ortho.vsh", "res/shader/ortho.fsh");
		
		sendMatrix("m_projection", getProjection());
		sendInt("t_sampler", 0);
	}
	
	protected void registerUniformLocations() {
		registerUniformLocation("m_projection");
		registerUniformLocation("m_transform");
		registerUniformLocation("m_view");
		registerUniformLocation("t_sampler");
	}
	
	public int getOutputFormat() {
		return GL_RGBA;
	}
	
	/**
	 * 
	 * Assigns a new orthographic projection matrix with the specified specifications.
	 * 
	 * @param left - the left most x coordinate of the screen
	 * @param right - the right most x coordinate of the screen
	 * @param bottom - the bottom most y coordinate of the screen
	 * @param top - the top most y coordinate of the screen
	 * @return the new projection matrix
	 */
	public static final Matrix4f initProjection(float left, float right, float bottom, float top) {
		OrthographicShaderProgram.left = left;
		OrthographicShaderProgram.right = right;
		OrthographicShaderProgram.bottom = bottom;
		OrthographicShaderProgram.top = top;
		
		projection.set(0, 0, 2f/(right-left));	projection.set(1, 0, 0);				projection.set(2, 0, 0);	projection.set(3, 0, -((right+left)/(right-left)));
		projection.set(0, 1, 0);				projection.set(1, 1, 2f/(top-bottom));	projection.set(2, 1, 0);	projection.set(3, 1, -((top+bottom)/(top-bottom)));
		projection.set(0, 2, 0);				projection.set(1, 2, 0);		 		projection.set(2, 2, 1);	projection.set(3, 2, 0);
		projection.set(0, 3, 0);				projection.set(1, 3, 0);		 		projection.set(2, 3, 0);	projection.set(3, 3, 1);
		
		return projection;
	}
	
	
	/**
	 * 
	 * Returns the current projection matrix.
	 * 
	 * @return the current projection matrix
	 */
	public static final Matrix4f getProjection() {
		return projection;
	}
	
	/**
	 * 
	 * Returns the current dimensions of this orthographic projection as a float array.
	 * <p>
	 * Index 0: left bound
	 * Index 1: right bound
	 * Index 2: bottom bound
	 * Index 3: top bound
	 * 
	 * @return
	 */
	public final float[] getDimensions() {
		return new float[]{left, right, bottom, top};
	}
	
	public final Vector2f getMouseClipspaceCoordinates(long window, int windowWidth, int windowHeight) {
		
		DoubleBuffer bufferX = Util.createDoubleBuffer(1);
		DoubleBuffer bufferY = Util.createDoubleBuffer(1);
		
		GLFW.glfwGetCursorPos(window, bufferX, bufferY);
		
		double x = bufferX.get();
		double y = bufferY.get();
		
		x /= windowWidth;
		y /= windowHeight;
		
		x *= right - left;
		y *= top - bottom;
		
		x += left;
		y += bottom;
		
		return new Vector2f((float)x, (float)-y);
	}
	
	static {
		//Default projection initialization
		initProjection(-1, 1, -1, 1);
	}
	
	//The instance of this object to use
	public static final OrthographicShaderProgram INSTANCE = new OrthographicShaderProgram();
}