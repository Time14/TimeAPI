package time.api.physics;

import java.util.HashSet;

import time.api.debug.Debug;
import time.api.math.Vector2f;


/**
 * 
 * A class to hold everything regarding physics.
 * 
 * @author Eddie-boi
 */

public class PhysicsEngine {
	
	public static final float FRAME_RATE = 120.0f;
	public static final float SIMULATION_STEP = 1.0f/FRAME_RATE;
	
	private HashSet<Body> bodies;
	private HashSet<Collision> collisions;
	
	private Vector2f gravity;
	
	private float timer;
	
	private boolean useStep = true;
	
	/**
	 * 
	 * Initialized the physics engine.
	 * 
	 */
	public PhysicsEngine() {
		bodies = new HashSet<Body>();
		collisions = new HashSet<Collision>();
		gravity = new Vector2f(0.0f, 0.0f);
	}
	
	/**
	 * 
	 * Updates the physics engine and progresses the physics world one step forward.
	 * 
	 * @param delta - the time passed since the previous frame
	 * @return - this PhysicsEngine instance
	 */
	public PhysicsEngine update(float delta) {
		
		//Increase the timer to make sure we run at a smooth frame rate.
		if (useStep) {
			timer += delta;
			delta = SIMULATION_STEP;
		}

		//Check if we're ready to step or running each frame, and if so, enter the loop. 
		while (SIMULATION_STEP < timer || !useStep) {
			//Decrease the timer so we step through the simulation for all the accumulated time
			if (useStep)
				timer -= SIMULATION_STEP;
			
			//These are to prevent double collisions
			int i = 0;
			int j = 0;
			for(Body a : bodies) {
				
				a._clearTags();
				a.addVel(gravity);
				
				i++;
				j = 0;
				for(Body b : bodies) {
					j++;
					if(j <= i) continue;
					a._checkCollision(b, this);
				}
			}
			
			for (Collision c : collisions) {
				c._solve();
			}
			
			for(Body a : bodies) {
				a.update(delta);
			}
			collisions.clear();
			
			//Break if we're running every frame
			if (!useStep)
				break;
		}
		return this;
	}
	
	/**
	 * 
	 * Sets the gravity constant to the supplied vector quantity.
	 * 
	 * @param x - The speed on the x-axis per second
	 * @param y - The speed on the y-axis per second
	 * @return this PhysicsEngine instance
	 */
	public PhysicsEngine setGravity(float x, float y) {
		gravity.setX(x);
		gravity.setY(y);
		gravity.scale(SIMULATION_STEP);
		return this;
	}
	
	/**
	 * 
	 * Draws all bodies within this PhysicsEngine. This is method is used for debugging only.
	 * 
	 * @return this PhysicsEngine instance
	 */
	public PhysicsEngine _debugDraw() {
		for(Body b : bodies)
			Debug.drawRect(b);
		return this;
	}
	 /**
	  * 
	  * Adds a body to the physics simulation.
	  * 
	  * @param body - the body to add
	  * @return this PhysicsEngine instance
	  */
	public PhysicsEngine addBody(Body body) {
		bodies.add(body);
		return this;
	}
	 /**
	  * 
	  * It adds a collision between two bodies for the physics engine to handle.
	  * Do not run call this method since it should only be called internally by the PhysicsEngine. 
	  * 
	  * @param col - the collision to add
	  * @return this PhysicsEngine instance
	  */
	public PhysicsEngine addCollision(Collision col) {
		collisions.add(col);
		return this;
	}
}
