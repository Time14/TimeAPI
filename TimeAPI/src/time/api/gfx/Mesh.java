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
	private int usage;
	
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
		createMesh(GL15.GL_STATIC_DRAW, vertices, indices);
	}
	
	/**
	 * 
	 * Creates a new Mesh with the provided vertices and indices. If no indices are specified, this mesh will not be indexed.
	 * 
	 * @param usage - the expected usage pattern of the data store. One of: GL_STREAM_DRAW, GL_STREAM_READ, GL_STREAM_COPY, GL_STATIC_DRAW, GL_STATIC_READ, GL_STATIC_COPY, GL_DYNAMIC_DRAW, GL_DYNAMIC_READ, or GL_DYNAMIC_COPY
	 * @param vertices - the vertices of this mesh
	 * @param indices - the indices of this mesh
	 */
	public Mesh(int usage, Vertex[] vertices, int... indices) {
		createMesh(usage, vertices, indices);
	}
	
	/**
	 * 
	 * Creates a new empty mesh. This mesh will not contain any vertices or indices. This will only generate a VAO.
	 * 
	 * @return this mesh instance
	 */
	public Mesh createEmpty() {
		vao = GL30.glGenVertexArrays();
		created = true;
		
		return this;
	}
	
	/**
	 * 
	 * Generates a new mesh if not yet created. If no indices are specified, this mesh will not be indexed.
	 * 
	 * @param usage - the expected usage pattern of the data store. One of: GL_STREAM_DRAW, GL_STREAM_READ, GL_STREAM_COPY, GL_STATIC_DRAW, GL_STATIC_READ, GL_STATIC_COPY, GL_DYNAMIC_DRAW, GL_DYNAMIC_READ, or GL_DYNAMIC_COPY
	 * @param vertices - the vertices of this mesh
	 * @param indices - the indices of this mesh
	 * @throws IllegalStateException if a mesh has already been generated for this instance
	 */
	public void createMesh(int usage, Vertex[] vertices, int... indices) {
		if(created)
			throw new IllegalStateException("Mesh (VAO: " + vao + ") is already created.");
		
		this.usage = usage;
		
		program = vertices[0].getShaderProgram();
		
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		vbos = program.initAttributes(vertices, usage);
		
		if(indices.length > 0) {
			IntBuffer iboData = Util.createIntBuffer(indices.length);
			
			iboData.put(indices);
			
			iboData.flip();
			
			ibo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, iboData, usage);
		}
		
		GL30.glBindVertexArray(0);
		
		vertexCount = vertices.length;
		indexCount = indices.length;
		
		created = true;
	}
	
	/**
	 * 
	 * Reallocates this mesh. If no indices are specified, this mesh will not be indexed.
	 * 
	 * @param usage - the expected usage pattern of the data store. One of: GL_STREAM_DRAW, GL_STREAM_READ, GL_STREAM_COPY, GL_STATIC_DRAW, GL_STATIC_READ, GL_STATIC_COPY, GL_DYNAMIC_DRAW, GL_DYNAMIC_READ, or GL_DYNAMIC_COPY
	 * @param vertices - the vertices of this mesh
	 * @param indices - the indices of this mesh
	 * @throws IllegalStateException if this mesh has not previously been allocated
	 */
	public void reallocateData(int usage, Vertex[] vertices, int... indices) {
		if(!created)
			throw new IllegalStateException("Cannot reallocate unallocated data");
		
		if(vbos != null)
			GL15.glDeleteBuffers(vbos);
		
		GL30.glBindVertexArray(vao);
		
		program = vertices[0].getShaderProgram();
		
		vbos = program.initAttributes(vertices, usage);
		
		ibo = 0;
		if(indices.length > 0) {
			IntBuffer iboData = Util.createIntBuffer(indices.length);
			
			iboData.put(indices);
			
			iboData.flip();
			
			ibo = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, iboData, usage);
		}
		
		GL30.glBindVertexArray(0);
		
		vertexCount = vertices.length;
		indexCount = indices.length;
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
	
	/**
	 * 
	 * Destroys all buffers associated with this mesh.
	 * 
	 */
	public void destroy() {
		GL15.glDeleteBuffers(vbos);
		GL15.glDeleteBuffers(vao);
	}
}