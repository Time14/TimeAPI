package time.api.gfx;

import time.api.gfx.texture.Texture;
import time.api.math.Vector2f;
import time.api.math.Vector3f;

public class QuadRenderer extends Renderer {
	
	public QuadRenderer(float x, float y, float width, float height, Texture texture) {
		setMesh(new Mesh(new VertexTex[]{
				new VertexTex(new Vector3f(-width/2, -height/2, 0), new Vector2f(0, 0)),
				new VertexTex(new Vector3f(-width/2, height/2, 0), new Vector2f(0, 1)),
				new VertexTex(new Vector3f(width/2, height/2, 0), new Vector2f(1, 1)),
				new VertexTex(new Vector3f(width/2, -height/2, 0), new Vector2f(1, 0))
		}, 0, 1, 2, 0, 2, 3));
		getTransform().translate(x, y);
		setTexture(texture);
	}
	
}