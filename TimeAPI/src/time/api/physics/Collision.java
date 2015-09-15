package time.api.physics;

import time.api.debug.Debug;
import time.api.math.Vector2f;
import time.api.math.VectorXf;

public class Collision {
	
	private Body[] bodies;
	
	private Vector2f normal;
	
	private float depth;
	
	/**
	 * 
	 * Creates a collision object that will soon be solved.
	 * 
	 * @param pe - the physics engine where the collision will take place
	 * @param normal - the normal vector of the collision
	 * @param depth - how deep the collision is
	 * @param b - the bodies that are colliding, supposedly 2
	 */
	public Collision(PhysicsEngine pe, Vector2f normal, float depth, Body ... b) {
		this.normal = normal;
		this.depth = depth;
		
		bodies = b;
		
		pe.addCollision(this);
	}
	
	/**
	 * 
	 * Solves a collision, you should not call this.
	 * 
	 */
	protected void _solve() {
		
		VectorXf v = bodies[0].getVel().clone().add(bodies[1].getVel());
		
		Vector2f tangent = new Vector2f(normal.getY(), -normal.getX());
		float dir = normal.dot(v);
		
		if(0 > dir) return;
		
		//Bounce Calculations
		float p = (1/bodies[0].getInvMass()) + (1/bodies[1].getInvMass());
		float e = (float) (((bodies[0].getEpsilon() + bodies[1].getEpsilon()) * 0.5 + 1) * 0.5);

		p *= v.getMagnitude() * e;
		
		//Friction calculations
		dir = tangent.dot(v);
		float mu = (bodies[0].getFriction() + bodies[1].getFriction()) * 0.5f;
				
		mu = normal.dot(v) * mu;
		
		//Applying Force for bounce
		bodies[0].push(normal.scale(p));
		bodies[1].push(normal.scale(-1));
		
		//Movement correction
		_move();
		
		//Applying Friction
		if(mu == 0) return;
		
		if(Math.abs(tangent.dot(bodies[0].getVel())*(1/bodies[0].getInvMass())) < Math.abs(mu)) {
			bodies[0].addVel(tangent.clone().scale(-tangent.dot(bodies[0].getVel())));
		} else {
			bodies[0].push(tangent.clone().scale(-mu));		
		}
		
		if(Math.abs(tangent.dot(bodies[1].getVel())*(1/bodies[1].getInvMass())) < Math.abs(mu)) {
			bodies[1].addVel(tangent.clone().scale(tangent.dot(bodies[1].getVel())));
		} else {
			bodies[1].push(tangent.clone().scale(mu));		
		}
	}
	 
	/**
	 * Cleans up the collision from floating point errors, you should not call this.
	 */
	protected void _move() {
		float d = 0.02f;
		for(Body b : bodies) {
			if(!b.isAbsolute()){
				b.getPos().setX(b.getPos().getX() + normal.getX() * depth * d);
				b.getPos().setY(b.getPos().getY() + normal.getY() * depth * d);
			}
		}
	}
}
