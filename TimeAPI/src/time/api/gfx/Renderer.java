package time.api.gfx;

import time.api.gfx.texture.Texture;

public class Renderer {
	
	private Texture texture = Texture.DEFAULT_TEXTURE;
	
	private Mesh mesh;
	
	public Renderer() {}
	
	public Renderer(Mesh mesh) {
		setMesh(mesh);
	}
	
	public Renderer(Mesh mesh, Texture texture) {
		setMesh(mesh);
		setTexture(texture);
	}
	
	public void draw() {
		mesh.getShaderProgram().bind();
		texture.bind(0);
		mesh.draw();
		Texture.unbind(0);
	}
	
	public Renderer setMesh(Mesh mesh) {
		this.mesh = mesh;
		return this;
	}
	
	public Renderer setTexture(Texture texture) {
		this.texture = texture;
		return this;
	}
	
}