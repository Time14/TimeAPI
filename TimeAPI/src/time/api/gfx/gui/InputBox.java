package time.api.gfx.gui;

import time.api.gfx.texture.Texture;

public class InputBox extends Selectable {
	
	protected String text = new String();
	protected float textCap = 0;
	
	public InputBox(float x, float y, float width, float height, Texture texture) {}
	
	@Override
	public void onUpdate(float tick) {}
	
	@Override
	public void onClick(float x, float y) {}
	
	@Override
	public void onDraw() {}

	@Override
	public void onMouseIn() {}

	@Override
	public void onMouseOut() {}
}