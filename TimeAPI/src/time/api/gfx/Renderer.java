package time.api.gfx;

import time.api.gfx.shader.ShaderProgram;
import time.api.gfx.texture.Texture;
import time.api.math.Transform;

public class Renderer {
	
	private Texture texture = Texture.DEFAULT_TEXTURE;
	
	private Mesh mesh;
	
	private ShaderProgram program;
	
	private Transform transform = new Transform();
	
	public Renderer() {}
	
	public Renderer(Mesh mesh) {
		setMesh(mesh);
	}
	
	public Renderer(Mesh mesh, Transform transform) {
		setMesh(mesh);
		setTransform(transform);
	}
	
	public Renderer(Mesh mesh, Texture texture) {
		setMesh(mesh);
		setTexture(texture);
	}
	
	public Renderer(Mesh mesh, Transform transform, Texture texture) {
		setMesh(mesh);
		setTransform(transform);
		setTexture(texture);
	}
	
	public void draw() {
		program.bind();
		program.sendMatrix("m_transform", transform.getMatrix());
		texture.bind();
		mesh.draw();
	}
	
	public Renderer setMesh(Mesh mesh) {
		this.mesh = mesh;
		program = mesh.getShaderProgram();
		return this;
	}
	
	public Renderer setTransform(Transform transform) {
		this.transform = transform;
		return this;
	}
	
	public Renderer setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public Transform getTransform() {
		return transform;
	}
	
	public Texture getTexture() {
		return texture;
	}
}