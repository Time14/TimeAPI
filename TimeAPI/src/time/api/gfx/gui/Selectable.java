package time.api.gfx.gui;

public abstract class Selectable extends GUIElement {
	
	protected boolean selected = false;
	
	private GUIEvent selectEvent;
	private GUIEvent unselectEvent;
	
	public Selectable select(boolean selected) {
		this.selected = selected;
		
		if(selected && selectEvent != null)
			selectEvent.fire();
		else if(!selected && unselectEvent != null)
			unselectEvent.fire();
		
		return this;
	}
	
	public final Selectable setSelectEvent(GUIEvent e) {
		selectEvent = e;
		
		return this;
	}
	
	public final Selectable setUnselectEvent(GUIEvent e) {
		unselectEvent = e;
		
		return this;
	}
	
	public boolean isSelected() {
		return selected;
	}
}