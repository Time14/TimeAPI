package time.api.gfx.gui;

import time.api.gfx.QuadRenderer;
import time.api.gfx.texture.DynamicTexture;
import time.api.gfx.texture.Texture;
import time.api.physics.Body;

public class Button extends GUIElement {
	
	private String label;
	private float labelSize = 48;
	
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
	}
	
	@Override
	public void onUpdate(float dt) {}
	
	public Button setLabel(String label) {
		return setLabel(label, labelSize);
	}
	
	public Button setLabelSize(float labelSize) {
		this.labelSize = labelSize;
		return this;
	}
	
	public Button setLabel(String label, float labelSize) {
		this.label = label;
		this.labelSize = labelSize;
		return this;
	}
}