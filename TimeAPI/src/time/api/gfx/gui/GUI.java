package time.api.gfx.gui;

import java.util.ArrayList;

import time.api.Game;

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
	
	public GUI click(float x, float y) {
		
		for(GUIElement e : guiElements) {
			if(e.contains(x, y)) {
				e.click(x, y);
			}
		}
		
		return this;
	}
	
	public GUI draw() {
		
		for(GUIElement e : guiElements)
			e.draw();
		
		return this;
	}
	
	public GUI update(float tick) {
		
		for(GUIElement e : guiElements)
			e.update(tick);
		
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