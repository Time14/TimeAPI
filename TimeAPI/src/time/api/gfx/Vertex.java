package time.api.gfx;

import java.util.ArrayList;

import time.api.gfx.shader.ShaderProgram;
import time.api.math.Vector2f;
import time.api.math.Vector3f;
import time.api.math.VectorXf;

public abstract class Vertex {
	protected VectorXf[] components;
	
	public Vertex(VectorXf... components) {
		this.components = components;
	}
	
	public VectorXf getComponent(int i) {
		return components[i];
	}
	
	public int getLength() {
		return components.length;
	}
	
	public abstract ShaderProgram getShaderProgram();
}