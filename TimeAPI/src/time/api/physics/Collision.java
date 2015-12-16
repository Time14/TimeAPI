package time.api.physics;

import time.api.debug.Debug;
import time.api.math.Vector2f;
import time.api.math.VectorXf;
import time.api.util.Time;

public class Collision {
	
	// The constant that decides how much the bodies should move after collision.
	private static float MOVE_CONSTANT = 3.5f;

	private Body[] bodies;
	private Vector2f normal;
	private float depth;
	
	/**
	 * 
	 * Creates a collision object that will soon be solved.
	 * 
	 * @param pe - the physics engine where the collision is takeing place
	 * @param normal - the normal vector of the collision
	 * @param depth - how deep the collision is
	 * @param b - the two bodies that are colliding
	 */
	public Collision(PhysicsEngine pe, Vector2f normal, float depth, Body ... b) {
		this.normal = normal;
		this.depth = depth;
		
		bodies = b;
		
		pe.addCollision(this);
	}
	
	/**
	 * Solves this collision, you should not call this.
	 */
	protected void _solve() {
		
		VectorXf v = bodies[0].getVel().clone().sub(bodies[1].getVel());
		
		Vector2f tangent = new Vector2f(normal.getY(), -normal.getX());
		
		float dir = normal.dot(v);
		
		if(dir > 0) return;
		
		// Bounce Calculations
		float p = -(dir * (1 + Math.min(bodies[0].getEpsilon(), bodies[1].getEpsilon())) ) / (bodies[0].getInvMass() + bodies[1].getInvMass());
		
		// Mu Calculations
		float mu = Math.min(bodies[0].getFriction(), bodies[1].getFriction());		
		
		// Applying Force for bounce
		bodies[0].push(normal.scale(p));
		bodies[1].push(normal.scale(-1));
		
		// Movement correction
		_move();

		// Applying Friction
		if (mu == 0 || tangent.dot(v) == 0) return;

		for(int i = 0; i < 2; i++) {
			float friction = -Math.signum(tangent.dot(bodies[i].getVel())) * mu * p; 
			// Make sure it doesn't change the direction of the body
			if (Math.abs(tangent.dot(bodies[i].vel)) < Math.abs(friction)) {
				bodies[i].push(tangent.clone().scale(-tangent.dot(bodies[i].vel)*(1/bodies[i].invMass)));
			} else {
				bodies[i].push(tangent.clone().scale(friction));
			}
		}
	}
	 
	/**
	 * Cleans up the collision from floating point errors, you should not call this.
	 */
	protected void _move() {
		float move = MOVE_CONSTANT;
		
		if(bodies[0].isAbsolute() != bodies[1].isAbsolute())
			move *= 2;
		
		if(!bodies[0].isAbsolute()){
			bodies[0].getPos().setX(bodies[0].getPos().getX() + normal.getX() * depth * -move);
			bodies[0].getPos().setY(bodies[0].getPos().getY() + normal.getY() * depth * -move);
		}
		if(!bodies[1].isAbsolute()){
			bodies[1].getPos().setX(bodies[1].getPos().getX() + normal.getX() * depth * move);
			bodies[1].getPos().setY(bodies[1].getPos().getY() + normal.getY() * depth * move);
		}
	}
}
