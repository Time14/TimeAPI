package time.api.entity;

import time.api.gfx.Renderer;
import time.api.physics.Body;

public class Entity {
	
	private Renderer renderer;
	
	private Body body;
	
	/**
	 * 
	 * Constructs an entity without body and renderer.
	 * 
	 */
	public Entity() {}
	
	/**
	 * 
	 * Constructs an entity without a renderer.
	 * 
	 * @param body - the physical body of this entity
	 */
	public Entity(Body body) {
		setBody(body);
	}
	
	/**
	 * 
	 * Constructs an entity without a physical body.
	 * 
	 * @param renderer - the renderer of this entity
	 */
	public Entity(Renderer renderer) {
		setRenderer(renderer);
	}
	
	/**
	 * 
	 * Constructs an entity with both physical body and renderer.
	 * 
	 * @param renderer - the renderer of this entity
	 * @param body - the body of this entity
	 */
	public Entity(Renderer renderer, Body body) {
		setRenderer(renderer);
		setBody(body);
	}
	
	/**
	 * 
	 * Updates the entity and its body. The body will not be updated if none has been specified.
	 * 
	 * @param dt - the delta time since previous frame
	 */
	public void update(float dt) {
		if(body != null)
			body.update(dt);
	}
	
	/**
	 * 
	 * Causes this entity to draw using the specified renderer. If there is no renderer specified nothing will happen.
	 * 
	 */
	public void draw() {
		if(renderer != null)
			renderer.draw();
	}
	
	/**
	 * 
	 * Sets the renderer of this entity.
	 * 
	 * @param renderer - the renderer to be set
	 * @return this entity instance
	 */
	public Entity setRenderer(Renderer renderer) {
		this.renderer = renderer;
		return this;
	}
	
	/**
	 * 
	 * Returns the renderer of this entity.
	 * 
	 * @return the renderer of this entity
	 */
	public Renderer getRenderer() {
		return renderer;
	}
	
	/**
	 * 
	 * Sets the physical body of this entity.
	 * 
	 * @param body - the physical body to be set
	 * @return
	 */
	public Entity setBody(Body body) {
		this.body = body;
		return this;
	}
	
	/**
	 * 
	 * Returns the physical body of this entity.
	 * 
	 * @return the physical body of this entity
	 */
	public Body getBody() {
		return body;
	}
	
}