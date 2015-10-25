package sk.client.gfx.gui;

import sk.client.renderer.QuadRenderer;
import sk.client.world.entity.Entity;
import sk.client.world.entity.EntityQuad;

public abstract class GUIElement extends EntityQuad {
	
	protected boolean isMouseOver = false;
	
	protected GUI gui;
	
	protected void init() {
		
	}
	
	public final void click(float x, float y) {
		onClick(x, y);
	}
	
	public abstract void onClick(float x, float y);
	
	public final void mouseOver(float tick) {
		onMouseOver(tick);
	}
	
	public abstract void onMouseOver(float tick);
	
	public final void mouseIn() {
		onMouseIn();
	}
	
	public abstract void onMouseIn();
	
	public final void mouseOut() {
		onMouseOut();
	}
	
	public abstract void onMouseOut();
	
	public boolean isMouseOver() {
		return isMouseOver;
	}
	
	public void setGUI(GUI gui) {
		this.gui = gui;
	}
	
	protected void destroy() {
		
	}
}