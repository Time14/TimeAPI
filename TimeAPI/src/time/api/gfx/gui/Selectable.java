package time.api.gfx.gui;

public abstract class Selectable extends GUIElement {
	
	protected boolean selected = false;
	
	private GUIEvent selectEvent;
	private GUIEvent unselectEvent;
	
	/**
	 * 
	 * Performs a select event on this selectable GUI element.
	 * 
	 * @param selected - when or not to select this GUI element
	 * @return this selectable GUI element instance
	 */
	public Selectable select(boolean selected) {
		this.selected = selected;
		
		if(selected && selectEvent != null)
			selectEvent.fire();
		else if(!selected && unselectEvent != null)
			unselectEvent.fire();
		
		return this;
	}
	
	/**
	 * 
	 * Sets an action to perform when a select event is performed on this selectable GUI element.
	 * 
	 * @param e - the action to perform
	 * @return this selectable GUI element instance
	 */
	public final Selectable setSelectEvent(GUIEvent e) {
		selectEvent = e;
		
		return this;
	}
	
	/**
	 * 
	 * Sets an action to perform when an unselect event is performed on this selectable GUI element.
	 * 
	 * @param e - the action to perform
	 * @return this slectable GUI element instance
	 */
	public final Selectable setUnselectEvent(GUIEvent e) {
		unselectEvent = e;
		
		return this;
	}
	
	/**
	 * 
	 * Returns whether or not this selectable GUI element is selected.
	 * 
	 * @return this selectable GUI element instance
	 */
	public boolean isSelected() {
		return selected;
	}
}