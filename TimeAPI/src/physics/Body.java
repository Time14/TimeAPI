package physics;

import java.util.HashSet;

import advancedMath.Vector2f;
import advancedMath.VectorXf;
import debug.Debug;

/**
 * Something that can collide, an entiry that interacts without via physical behavior.  
 * @author Eddie-boi
 *
 */

public class Body {

	boolean trigger;
	boolean absolute;
	
	HashSet<Tag> collisionTags;
	HashSet<Tag> myTags;

	float invMass;
	float epsilon;
	float mu;
	
	Vector2f dim;
	Vector2f pos;
	Vector2f vel;
	
	public Body(float x, float y, float w, float h) {
		
		trigger = false;
		absolute = false;
		
		invMass = 1;
		epsilon = 1;
		mu = 1;
		
		dim = new Vector2f(w, h);
		pos = new Vector2f(x, y);
		vel = new Vector2f(0, 0);
		
		collisionTags = new HashSet<Tag>();
		myTags = new HashSet<Tag>();
		myTags.add(Tag.BODY);
	}
	
	public boolean isCollidingWith(Tag tag) {
		return collisionTags.contains(tag);
	}
	
	private void addCollidingTag(Tag tag) {
		collisionTags.add(tag);
	}
	
	public HashSet<Tag> getTags() {
		return myTags;
	}
	
	public boolean hasTag(Tag tag) {
		return myTags.contains(tag);
	}
	
	public void addTag(Tag tag) {
		myTags.add(tag);
	}
	
	/**
	 * Clears all the collision tags, should not be called. The physics engine manages this.
	 */
	public void _clearTags() {
		collisionTags.clear();
	}
	
	/**
	 * If the body reacts to collisions
	 * @return
	 */
	public boolean isTrigger() {
		return trigger;
	}
	/**
	 * Decide if the body reacts to collisions
	 * @param trigger - if the body should generate collision events on collision
	 */
	public Body setTrigger(boolean trigger) {
		this.trigger = trigger;
		return this;
	}
	
	/**
	 * If the body is able to move by velocity
	 * @return
	 */
	public boolean isAbsolute() {
		return absolute;
	}

	/**
	 * Decide if the body is locked in the world
	 * @param absolute - if it is impossible to move in the world. 
	 * @return
	 */
	public Body setAbsolute(boolean absolute) {
		this.absolute = absolute;
		return this;
	}

	public float getInvMass() {
		return invMass;
	}

	public Body setMass(float mass) {
		this.invMass = 1 / mass;
		return this;
	}
	/**
	 * Get the bouncy-ness
	 * @return
	 */
	public float getEpsilon() {
		return epsilon;
	}
	
	/**
	 * Set how bouncy this body is
	 * @param epsilon - how much bounce this body should have. Should be between 0 and 1
	 * @return
	 */
	public Body setEpsilon(float epsilon) {
		this.epsilon = epsilon;
		return this;
	}

	public float getFriction() {
		return mu;
	}

	public Body setFriction(float f) {
		this.mu = f;
		return this;
	}

	public Vector2f getDim() {
		return dim;
	}

	public Body setDim(Vector2f dim) {
		this.dim = dim;
		return this;
	}

	public Vector2f getPos() {
		return pos;
	}

	public Body setPos(Vector2f pos) {
		this.pos = pos;
		return this;
	}

	public Vector2f getVel() {
		return vel;
	}

	public Body setVel(Vector2f vel) {
		this.vel = vel;
		return this;
	}
	
	public Body freezeVelocity() {
		this.vel.setX(0);
		this.vel.setY(0);
		return this;
	}
	
	/**
	 * Checks if two bodies are colliding, you should probably not call this. The physics engine should handle this perfectly well.
	 * @param body - The other body you want to check collision against.
	 * @param pe - The physics engine where all of this is happeneing. 
	 */
	public void _checkCollision(Body body, PhysicsEngine pe) {
		
		//Collision Check
		float overlapX = 0.5f * Math.abs(this.dim.getX() + body.dim.getX()) - Math.abs(this.pos.getX() - body.pos.getX());
		if(overlapX < 0) return;		

		float overlapY = 0.5f * Math.abs(this.dim.getY() + body.dim.getY()) - Math.abs(this.pos.getY() - body.pos.getY());
		if(overlapY < 0) return;
		
		//Add in all tags
		for(Tag t : myTags) {
			body.addCollidingTag(t);
		}
		
		for(Tag t : body.getTags()) {
			addCollidingTag(t);
		}
		
		//Make a collision event if necessary 
		if(trigger || body.isTrigger()) return;
		
		VectorXf distance = this.pos.clone().sub(body.getPos());
		float depth = 0;
		
		if(Math.abs(distance.getN(0)) > Math.abs(distance.getN(1))) {
			distance.setN(1, 0);
			distance.scale(Math.abs(1/distance.getN(0)));
			depth = overlapY;
		}
		else {
			distance.setN(0, 0);
			distance.scale(Math.abs(1/distance.getN(1)));
			depth = overlapX;
		}
		
		
		Vector2f normal = new Vector2f(distance.getN(0), distance.getN(1));
		new Collision(pe, normal, depth, this, body);
		return;
	}
	
	public void push(VectorXf vectorXf) {
		vel.add(vectorXf.scale(invMass));
	}
	
	public void addVel(VectorXf vel) {
		this.vel.add(vel);
	}
	
	public void update(float delta) {
		//TODO
		if(absolute) {
			this.vel.setX(0);
			this.vel.setY(0);
		}
		pos.add(vel.clone().scale(delta));
	}	
}
