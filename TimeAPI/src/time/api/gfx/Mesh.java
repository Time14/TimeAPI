package time.api.gfx;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import time.api.gfx.shader.OrthographicShaderProgram;
import time.api.gfx.shader.ShaderProgram;
import time.api.util.Util;

public class Mesh {
	
	private ShaderProgram program;
	
	private boolean created = false;
	
	private int vertexCount;
	private int indexCount;
	private int vao;
	private int ibo;
	private IntBuffer vbos;
	
	private int mode = GL11.GL_TRIANGLES;
	
	public Mesh() {}
	
	public Mesh(Vertex[] vertices, int... indicies) {
		createMesh(vertices);
	}
	
	public void createMesh(Vertex[] vertices, int... indices) {
		if(created)
			throw new IllegalStateException("Mesh (VAO: " + vao + ") is already created.");
		
		program = vertices[0].getShaderProgram();
		
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		vbos = program.initAttributes(vertices, GL15.GL_STATIC_DRAW);
		
		IntBuffer iboData = Util.createIntBuffer(indices.length);
		
		iboData.put(indices);
		
		iboData.flip();
		
		ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, iboData, GL15.GL_STATIC_DRAW);
		
		GL30.glBindVertexArray(0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		vertexCount = vertices.length;
		indexCount = indices.length;
		
		created = true;
	}
	
	public void draw() {
		GL30.glBindVertexArray(vao);
		
		if(isIndexed()) {
			GL11.glDrawElements(mode, indexCount, GL11.GL_UNSIGNED_INT, 0);
		} else {
			GL11.glDrawArrays(mode, 0, vertexCount);
		}
		
		GL30.glBindVertexArray(0);
	}
	
	public Mesh setMode(int mode) {
		this.mode = mode;
		return this;
	}
	
	public boolean isIndexed() {
		return ibo > 0;
	}
	
	public ShaderProgram getShaderProgram() {
		return program;
	}
	
}