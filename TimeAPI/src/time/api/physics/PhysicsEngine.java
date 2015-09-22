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
	
	private HashSet<Body> bodies;
	private HashSet<Collision> collisions;
	//private HeshSet<Collision> collisions;
	
	/**
	 * 
	 * Initialized the physics engine.
	 * 
	 */
	public PhysicsEngine() {
		bodies = new HashSet<Body>();
		collisions = new HashSet<Collision>();
	}
	
	/**
	 * 
	 * Updates the physics engine and progresses the physics world one step forward.
	 * 
	 * @param delta - the time passed since the previous frame
	 * @return - this PhysicsEngine instance
	 */
	public PhysicsEngine update(float delta) {
		
		Vector2f gravity = new Vector2f(0.0f, -0.5f);
		gravity.scale(delta);
	
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
