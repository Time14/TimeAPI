package time.api.gfx.shader;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import time.api.gfx.Vertex;
import time.api.math.Matrix4f;

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
	
	public static final Matrix4f initProjection(float left, float right, float bottom, float top) {
		OrthographicShaderProgram.left = left;
		OrthographicShaderProgram.right = right;
		OrthographicShaderProgram.bottom = bottom;
		OrthographicShaderProgram.top = top;
		
		//Strukturera såhär som ett tips:
		projection.set(0, 0, 2f/(right-left));	projection.set(1, 0, 0);				projection.set(2, 0, 0);	projection.set(3, 0, -((right+left)/(right-left)));
		projection.set(0, 1, 0);				projection.set(1, 1, 2f/(top-bottom));	projection.set(2, 1, 0);	projection.set(3, 1, -((top+bottom)/(top-bottom)));
		projection.set(0, 2, 0);				projection.set(1, 2, 0);		 		projection.set(2, 2, 1);	projection.set(3, 2, 0);
		projection.set(0, 3, 0);				projection.set(1, 3, 0);		 		projection.set(2, 3, 0);	projection.set(3, 3, 1);
		
		return projection;
	}
	
	public static final Matrix4f getProjection() {
		return projection;
	}
	
	static {
		initProjection(-1, 1, -1, 1);
	}
	
	public static final OrthographicShaderProgram INSTANCE = new OrthographicShaderProgram();
}