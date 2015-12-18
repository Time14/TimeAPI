package sk.client.gfx.gui;

import sk.client.util.Util;

public class Selectable extends GUIElement {
	
	protected boolean selected = false;
	
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
	
	public Selectable setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
}