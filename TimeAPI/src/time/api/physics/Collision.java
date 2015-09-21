package time.api.physics;

import time.api.math.Vector2f;
import time.api.math.VectorXf;
import time.api.util.Time;

public class Collision {
	
	// The constant that decides how much the bodies should move after collision.
	private static float MOVE_CONSTANT = 3.55f;
	// The frame rate the physics engine was tweaked for. 
	// Changing this will result in numbers needing to be changed. 
	private static int FRAME_RATE = 60;

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
	 * Solves this collision, you should not call this.
	 */
	protected void _solve() {
		
		VectorXf v = bodies[0].getVel().clone().add(bodies[1].getVel());
		
		Vector2f tangent = new Vector2f(normal.getY(), -normal.getX());
		float dir = normal.dot(v);
		
		if(dir < 0) return;
		
		// Bounce Calculations
		float p = (dir * (1 + Math.min(bodies[0].getEpsilon(), bodies[1].getEpsilon()))) / (bodies[0].getInvMass() + bodies[1].getInvMass());
		
		//Friction calculations
		float mu = tangent.dot(v) * -Math.min(bodies[0].getFriction(), bodies[1].getFriction());		
		
		// Make sure the collision acts the same no matter the frame rate.
		p *= Time.getDelta() * FRAME_RATE;
		mu *= Time.getDelta() * FRAME_RATE;
		
		// Applying Force for bounce
		bodies[0].push(normal.scale(p));
		bodies[1].push(normal.scale(-1));
		
		// Movement correction
		_move();

		// Applying Friction
		if(mu == 0) return;
		
		if(Math.abs(tangent.dot(bodies[0].getVel())*(1/bodies[0].getInvMass())) < Math.abs(mu)) {
			bodies[0].push(tangent.clone().scale(-tangent.dot(bodies[0].getVel())));
		} else {
			bodies[0].addVel(tangent.clone().scale(-mu));
		}
		
		if(Math.abs(tangent.dot(bodies[1].getVel())*(1/bodies[1].getInvMass())) < Math.abs(mu)) {
			bodies[1].push(tangent.clone().scale(-tangent.dot(bodies[1].getVel())));
		} else {
			bodies[1].addVel(tangent.clone().scale(mu));
		}
		
		
	}
	 
	/**
	 * Cleans up the collision from floating point errors, you should not call this.
	 */
	protected void _move() {
		if(!bodies[0].isAbsolute()){
			bodies[0].getPos().setX(bodies[0].getPos().getX() + normal.getX() * depth * -MOVE_CONSTANT);
			bodies[0].getPos().setY(bodies[0].getPos().getY() + normal.getY() * depth * -MOVE_CONSTANT);
		}
		if(!bodies[1].isAbsolute()){
			bodies[1].getPos().setX(bodies[1].getPos().getX() + normal.getX() * depth * MOVE_CONSTANT);
			bodies[1].getPos().setY(bodies[1].getPos().getY() + normal.getY() * depth * MOVE_CONSTANT);
		}
	}
}
