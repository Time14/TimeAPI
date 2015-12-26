package time.api.gfx;

import org.lwjgl.opengl.GL15;

import time.api.gfx.font.Font;
import time.api.gfx.font.FontChar;
import time.api.gfx.texture.Texture;
import time.api.math.Vector2f;
import time.api.math.Vector3f;

public class FontRenderer {
	
//	private static Mesh mesh;
	
	private static Font font = Font.FNT_ARIAL;
	
	private static float size = 0.01f;
	
	public static final void draw(float x, float y, String text) {
//		if(mesh == null)
//			mesh = new Mesh().createEmpty();
		
		float cursor = 0;
		for(int i = 0; i < text.length(); i++) {
			FontChar c = font.getChar(text.charAt(i));
			
			QuadRenderer r = new QuadRenderer(
				x + cursor, y,
				c.T_WIDTH * size, c.T_HEIGHT * size,
				c.T_X / font.getTexture().getWidth(), (c.T_Y + c.T_HEIGHT) / font.getTexture().getHeight(),
				(c.T_X + c.T_WIDTH) / font.getTexture().getWidth(), c.T_Y / font.getTexture().getHeight(),
				font.getTexture()
			);
			cursor += c.X_ADVANCE * size;
			
			r.draw();
			
			r.getMesh().destroy();
		}
	}
	
	public static final void setFont(Font font) {
		FontRenderer.font = font;
	}
	
}