package time.api.gfx;

import time.api.gfx.shader.OrthographicShaderProgram;
import time.api.gfx.shader.ShaderProgram;
import time.api.math.Vector2f;
import time.api.math.Vector3f;

public class VertexTex extends Vertex {
	
	public VertexTex(Vector3f pos, Vector2f texCoords) {
		super(pos, texCoords);
	}
	
	public ShaderProgram getShaderProgram() {
		return OrthographicShaderProgram.INSTANCE;
	}
}