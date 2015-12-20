package time.api.gfx.gui;

import time.api.gfx.QuadRenderer;
import time.api.gfx.texture.DynamicTexture;
import time.api.physics.Body;

public class CheckBox extends Selectable {
	
	public CheckBox(float x, float y, float width, float height, DynamicTexture texture) {
		setRenderer(new QuadRenderer(x, y, width, height, texture));
		body = new Body(transform, width, height);
	}
	
	@Override
	protected void onClick(float x, float y) {
		select(!isSelected());
		((DynamicTexture)getRenderer().getTexture()).swap(isSelected() ? 1 : 0);
	}
	
	@Override
	public void onMouseIn() {}
	
	@Override
	public void onMouseOut() {}
	
	@Override
	public void onUpdate(float dt) {}
	
	@Override
	public void onDraw() {
		renderer.draw();
	}
}