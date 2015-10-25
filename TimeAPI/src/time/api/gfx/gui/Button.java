package sk.client.gfx.gui;

import sk.client.gfx.texture.DynamicTexture;
import sk.client.gfx.texture.Texture;
import sk.client.renderer.QuadRenderer;
import sk.client.renderer.TextRenderer;
import sk.client.util.Util;

public class Button extends GUIElement {
	
	private String label;
	private float labelSize = 48;
	
	public Button(float x, float y, float width, float height, Texture texture) {
		renderer = new QuadRenderer(x, y, width, height, false, texture);
	}
	
	public void onClick(float x, float y) {
	}
	
	public void onMouseOver(float tick) {
		
	}
	
	public void onMouseIn() {
		
	}
	
	public void onMouseOut() {
		
	}
	
	public void update(float tick) {
		if (active && updating) {
			if (isMouseOver) {
				onMouseOver(tick);
				if (!contains(Util.getRelativeMX(), Util.getRelativeMY())) {
					onMouseOut();
					isMouseOver = false;
				}
			} else {
				if (contains(Util.getRelativeMX(), Util.getRelativeMY())) {
					onMouseIn();
					isMouseOver = true;
				}
			}
			
			onUpdate(tick);
		}
	}
	
	public void draw() {
		if(active && visible) {
			if(renderer.getTexture() instanceof DynamicTexture) {
				if(isMouseOver) {
					((DynamicTexture)renderer.getTexture()).swap(1);
					renderer.draw();
				} else {
					((DynamicTexture)renderer.getTexture()).swap(0);
					renderer.draw();
				}
			}
			
			if (label != null) {
				if (label.length() > 0) {
					TextRenderer.setSize(labelSize);
					TextRenderer.draw(label, (getX() + getWidth() / 2)
							- (labelSize * TextRenderer.getDistance()
									* (((float) label.length()) + 1f) / 2),
							(getY() + getHeight() / 2) - (labelSize / 2));
				}
			}
		}
	}
	
	protected void onUpdate(float tick) {
		
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