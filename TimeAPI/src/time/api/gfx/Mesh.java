package time.api.gfx;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

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
	
	/**
	 * 
	 * Creates an empty Mesh. Call {@link #createMesh(Vertex[], int...) createMesh()} to initialize it.
	 * 
	 */
	public Mesh() {}
	
	
	/**
	 * 
	 * Creates a new Mesh with the provided vertices and indices. If no indices are specified, this mesh will not be indexed.
	 * 
	 * @param vertices - the vertices of this mesh
	 * @param indices - the indices of this mesh
	 */
	public Mesh(Vertex[] vertices, int... indices) {
		createMesh(vertices, indices);
	}
	
	/**
	 * 
	 * Generates a new mesh if not yet created. If no indices are specified, this mesh will not be indexed.
	 * 
	 * @param vertices - the vertices of this mesh
	 * @param indices - the indices of this mesh
	 * @throws IllegalStateException if a mesh has already been generated for this instance
	 */
	public void createMesh(Vertex[] vertices, int... indices) {
		if(created)
			throw new IllegalStateException("Mesh (VAO: " + vao + ") is already created.");
		
		program = vertices[0].getShaderProgram();
		
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		vbos = program.initAttributes(vertices, GL15.GL_STATIC_DRAW);
		
		if(indices.length > 0) {
			IntBuffer iboData = Util.createIntBuffer(indices.length);
			
			iboData.put(indices);
			
			iboData.flip();
			
			ibo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, iboData, GL15.GL_STATIC_DRAW);
		}
		
		GL30.glBindVertexArray(0);
		
		vertexCount = vertices.length;
		indexCount = indices.length;
		
		created = true;
	}
	
	/**
	 * 
	 * Draws the specified vertices with the corresponding indices if there are any.
	 * 
	 */
	public void draw() {
		GL30.glBindVertexArray(vao);
		
		if(isIndexed()) {
			GL11.glDrawElements(mode, indexCount, GL11.GL_UNSIGNED_INT, 0);
		} else {
			GL11.glDrawArrays(mode, 0, vertexCount);
		}
		
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * 
	 * Sets what kind of primitives for this mesh to render.
	 * 
	 * @param mode - specifies what kind of primitives to render. Symbolic constants GL_POINTS, GL_LINE_STRIP, GL_LINE_LOOP, GL_LINES, GL_LINE_STRIP_ADJACENCY, GL_LINES_ADJACENCY, GL_TRIANGLE_STRIP, GL_TRIANGLE_FAN, GL_TRIANGLES, GL_TRIANGLE_STRIP_ADJACENCY, GL_TRIANGLES_ADJACENCY and GL_PATCHES are accepted
	 * @return this mesh instance
	 */
	public Mesh setMode(int mode) {
		this.mode = mode;
		return this;
	}
	
	/**
	 * 
	 * Returns whether or not this mesh is indexed ({@code true} if an Index Buffer Object has successfully been generated).
	 * 
	 * @return true if this mesh is indexed
	 */
	public boolean isIndexed() {
		return ibo > 0;
	}
	
	/**
	 * 
	 * Returns the shader program associated with this mesh.
	 * 
	 * @return the shader program of this mesh
	 */
	public ShaderProgram getShaderProgram() {
		return program;
	}
	
}