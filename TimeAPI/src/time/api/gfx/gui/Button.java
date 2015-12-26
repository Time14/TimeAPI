package time.api.gfx.gui;

import time.api.gfx.QuadRenderer;
import time.api.gfx.font.FontRenderer;
import time.api.gfx.font.FontType;
import time.api.gfx.texture.DynamicTexture;
import time.api.gfx.texture.Texture;
import time.api.physics.Body;

public class Button extends GUIElement {
	
	private FontRenderer fontRenderer;
	
	public Button(float x, float y, float width, float height, Texture texture) {
		setRenderer(new QuadRenderer(x, y, width, height, texture));
		body = new Body(transform, width, height);
	}
	
	@Override
	public void onClick(float x, float y) {}
	
	@Override
	public void onMouseIn() {
		if(getRenderer().getTexture() instanceof DynamicTexture) {
			((DynamicTexture)getRenderer().getTexture()).swap(1);
		}
	}
	
	@Override
	public void onMouseOut() {
		if(getRenderer().getTexture() instanceof DynamicTexture) {
			((DynamicTexture)getRenderer().getTexture()).swap(0);
		}
	}
	
	@Override
	public void onDraw() {
		renderer.draw();
		if(fontRenderer != null)
			fontRenderer.draw();
	}
	
	@Override
	public void onUpdate(float dt) {}
	
	public Button setFont(String text, FontType font, float size) {
		return setFont(new FontRenderer(renderer.getX(), renderer.getY(), text, font, size));
	}
	
	public Button setFont(FontRenderer fontRenderer) {
		this.fontRenderer = fontRenderer;
		
		fontRenderer.setPosition(
				renderer.getX() - fontRenderer.getWidth() / 2,
				renderer.getY() + fontRenderer.getHeight() / 2
		);
		
		return this;
	}
	
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
}