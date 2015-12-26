package time.api.gfx.gui;

import java.util.ArrayList;

import time.api.Game;
import time.api.math.Vector2f;

public class GUI {
	
	private ArrayList<GUIElement> guiElements;
	
	private Game game;
	
	public GUI() {
		guiElements = new ArrayList<>();
	}
	
	public GUI addElements(GUIElement... elements) {
		for(GUIElement element : elements) {
			guiElements.add(element);
			element.setGUI(this);
		}
		
		return this;
	}
	
	public GUI click(Vector2f coords) {
		for(GUIElement e : guiElements) {
			if(e.contains(coords)) {
				e.click(coords.getX(), coords.getY());
			}
		}
		
		return this;
	}
	
	public GUI click(float x, float y) {
		return click(new Vector2f(x, y));
	}
	
	public GUI draw() {
		
		for(GUIElement e : guiElements)
			e.draw();
		
		return this;
	}
	
	public GUI update(float tick, Vector2f mousePos) {
		
		for(GUIElement e : guiElements) {
			if(e.contains(mousePos) && !e.mouseOver) {
				e.mouseIn();
				e.mouseOver = true;
			} else if(!e.contains(mousePos) && e.mouseOver) {
				e.mouseOut();
				e.mouseOver = false;
			}
				
			e.update(tick);
		}
		
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GUIElement> ArrayList<T> get(T type) {
		return (ArrayList<T>) get(type.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GUIElement> ArrayList<T> get(Class<T> type) {
		
		ArrayList<T> result = new ArrayList<>();
		
		for(GUIElement e : guiElements) {
//			Debug.log(e.getClass().getSuperclass().getName(), type);
			if(e.getClass().getSuperclass() == type)
				result.add(((T)e));
		}
		
		return result;
	}
	
}