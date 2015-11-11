package time.api.gfx;

import time.api.gfx.shader.ShaderProgram;
import time.api.gfx.texture.Texture;
import time.api.math.Transform;

public class Renderer {
	
	private Texture texture = Texture.DEFAULT_TEXTURE;
	
	private Mesh mesh;
	
	private ShaderProgram program;
	
	private Transform transform = new Transform();
	
	/**
	 * 
	 * Constructs an empty renderer. This renderer will not be able to process at least until a mesh has been specified.
	 * 
	 */
	public Renderer() {}
	
	/**
	 * 
	 * Constructs a renderer with the specified mesh.
	 * 
	 * @param mesh - the mesh of this renderer
	 */
	public Renderer(Mesh mesh) {
		setMesh(mesh);
	}
	
	/**
	 * 
	 * Constructs a renderer with the specified mesh and transform.
	 * 
	 * @param mesh - the mesh of this renderer
	 * @param transform - the transform of this renderer
	 */
	public Renderer(Mesh mesh, Transform transform) {
		setMesh(mesh);
		setTransform(transform);
	}
	
	/**
	 * 
	 * Constructs a renderer with the specified mesh and texture.
	 * 
	 * @param mesh - the mesh of this renderer
	 * @param texture - the texture of this renderer
	 */
	public Renderer(Mesh mesh, Texture texture) {
		setMesh(mesh);
		setTexture(texture);
	}
	
	/**
	 * 
	 * Constructs a renderer with the specified mesh, transform and texture.
	 * 
	 * @param mesh - the mesh of this renderer
	 * @param transform - the transform of this renderer
	 * @param texture - the texture of this renderer
	 */
	public Renderer(Mesh mesh, Transform transform, Texture texture) {
		setMesh(mesh);
		setTransform(transform);
		setTexture(texture);
	}
	
	/**
	 * 
	 * Draws with the specified mesh, texture and transform.
	 * 
	 */
	public void draw() {
		program.bind();
		program.sendMatrix("m_transform", transform.getMatrix());
		texture.bind();
		mesh.draw();
	}
	
	/**
	 * 
	 * Sets the mesh of this renderer.
	 * 
	 * @param mesh - the mesh of this renderer
	 * @return this renderer instance
	 */
	public Renderer setMesh(Mesh mesh) {
		this.mesh = mesh;
		program = mesh.getShaderProgram();
		return this;
	}
	
	/**
	 * 
	 * Sets the transform of this renderer.
	 * 
	 * @param transform - the transform of this renderer
	 * @return this renderer instance
	 */
	public Renderer setTransform(Transform transform) {
		this.transform = transform;
		return this;
	}
	
	/**
	 * 
	 * Sets the texture of this renderer.
	 * 
	 * @param texture - the texture of this renderer
	 * @return this renderer instance
	 */
	public Renderer setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
	
	/**
	 * 
	 * Returns the mesh of this renderer.
	 * 
	 * @return the mesh of this renderer
	 */
	public Mesh getMesh() {
		return mesh;
	}
	
	/**
	 * 
	 * Returns the transform of this mesh.
	 * 
	 * @return the transform of this mesh
	 */
	public Transform getTransform() {
		return transform;
	}
	
	/**
	 * 
	 * Returns the texture of this renderer.
	 * 
	 * @return the texture of this renderer
	 */
	public Texture getTexture() {
		return texture;
	}
}