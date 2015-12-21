package time.api.gfx.gui;

import time.api.entity.Entity;

public abstract class GUIElement extends Entity {
	
	protected boolean mouseOver = false;
	
	protected GUI gui;
	
	//On click
	
	private GUIEvent clickEvent;
	
	public final void click(float x, float y) {
		onClick(x, y);
		if(clickEvent != null)
			clickEvent.fire();
	}
	
	protected abstract void onClick(float x, float y);
	
	public final GUIElement setClickEvent(GUIEvent e) {
		clickEvent = e;
		
		return this;
	}
	
	//On mouse in
	
	private GUIEvent mouseInEvent;
	
	public final void mouseIn() {
		onMouseIn();
		if(mouseInEvent != null)
			mouseInEvent.fire();
	}
	
	public abstract void onMouseIn();
	
	public final GUIElement setMouseInEvent(GUIEvent e) {
		mouseInEvent = e;
		
		return this;
	}
	
	//On mouse out
	
	private GUIEvent mouseOutEvent;
	
	public final void mouseOut() {
		onMouseOut();
		if(mouseOutEvent != null)
			mouseOutEvent.fire();
	}
	
	public abstract void onMouseOut();
	
	public final GUIElement setMouseOutEvent(GUIEvent e) {
		mouseOutEvent = e;
		
		return this;
	}
	
	//On update
	
	private GUIEvent updateEvent;
	
	public final void update(float dt) {
		onUpdate(dt);
		if(updateEvent != null)
			updateEvent.fire();
	}
	
	public abstract void onUpdate(float dt);
	
	public final GUIElement setUpdateEvent(GUIEvent e) {
		updateEvent = e;
		
		return this;
	}
	
	//On draw
	
	private GUIEvent drawEvent;
	
	public final void draw() {
		onDraw();
		if(drawEvent != null)
			drawEvent.fire();
	}
	
	public abstract void onDraw();
	
	public final GUIElement setDrawEvent(GUIEvent e) {
		drawEvent = e;
		
		return this;
	}
	
	//Is mouse over
	
	public boolean isMouseOver() {
		return mouseOver;
	}
	
	//set GUI
	
	public void setGUI(GUI gui) {
		this.gui = gui;
	}
	
	protected void destroy() {
		
	}
}