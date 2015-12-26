package time.api.gfx.gui;

import time.api.gfx.QuadRenderer;
import time.api.gfx.texture.Texture;

public class Button extends GUIElement {
	
	private String label;
	private float labelSize = 48;
	
	public Button(float x, float y, float width, float height, Texture texture) {
		renderer = new QuadRenderer(x, y, width, height, Texture.DEFAULT_TEXTURE);
	}
	
	public void onClick(float x, float y) {}
	
	public void onMouseOver(float tick) {}
	
	public void onMouseIn() {}
	
	public void onMouseOut() {}
	
	public void onDraw() {
		renderer.draw();
	}
	
	public void onUpdate(float dt) {
		
	}
	
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