package time.api.physics;

import java.util.HashSet;

import time.api.debug.Debug;
import time.api.math.Transform;
import time.api.math.Vector2f;
import time.api.math.VectorXf;

/**
 * Something that can collide, an entity that effects other entities via simulated physical interaction.  
 * @author Eddie-boi
 *
 */

public class Body {

	boolean trigger;
	boolean absolute;
	
	HashSet<String> collisionTags;
	HashSet<String> myTags;

	float invMass;
	float epsilon;
	float mu;
	
	Vector2f dim;
	Transform transform;
	Vector2f vel;
	
	/**
	 * 
	 * Constructs a new body with the given dimensions and position.
	 * 
	 * @param transform - the transform of the body
	 * @param w - the width of the new body
	 * @param h - the height of the new body
	 */
	public Body(Transform transform, float w, float h) {
		
		trigger = false;
		absolute = false;
		
		invMass = 1;
		epsilon = 0.0f;
		mu = 0;
		
		dim = new Vector2f(w, h);
		this.transform = transform;
		vel = new Vector2f(0, 0);
		
		collisionTags = new HashSet<>();
		myTags = new HashSet<>();
		myTags.add("body");
	}
	
	/**
	 * 
	 * Constructs a new body with the given dimensions and position.
	 * 
	 * @param x - the center x coordinate of the new body
	 * @param y - the center y coordinate of the new body
	 * @param w - the width of the new body
	 * @param h - the height of the new body
	 */
	public Body(float x, float y, float w, float h) {
		
		trigger = false;
		absolute = false;
		
		invMass = 1;
		epsilon = 0.0f;
		mu = 1;
		
		dim = new Vector2f(w, h);
		this.transform = new Transform(x, y);
		vel = new Vector2f(0, 0);
		
		collisionTags = new HashSet<>();
		myTags = new HashSet<>();
		myTags.add("body");
	}
	
	/**
	 * 
	 * Checks whether or not this body is colliding with another body with the specified tag.
	 * 
	 * @param tag - the tag to check collision with
	 * @return whether or not there is a collision
	 */
	public boolean isCollidingWith(String tag) {
		return collisionTags.contains(tag);
	}
	
	/**
	 * 
	 * Adds a collision tag to this body.
	 * A body can be checked through the {@link #isCollidingWith(Tag)}
	 * to see the tags of bodies that are colliding.
	 * 
	 * @param tag - the tag to add to this body
	 */
	private void addCollidingTag(String tag) {
		collisionTags.add(tag);
	}
	
	/**
	 * 
	 * Returns the collision tags that belong to this body.
	 * 
	 * @return the collision tags of this body
	 */
	public HashSet<String> getTags() {
		return myTags;
	}
	
	/**
	 * 
	 * Checks whether or not this body contains the specified tag.
	 * 
	 * @param tag - the tag to check
	 * @return whether or not this body contains the specified tag.
	 */
	public boolean hasTag(String tag) {
		return myTags.contains(tag);
	}
	
	/**
	 * 
	 * Adds a new tag to this body.
	 * 
	 * @param tag - the tag to add to this body.
	 */
	public void addTag(String tag) {
		myTags.add(tag);
	}
	
	/**
	 * 
	 * Clears all the collision tags, should not be called. The physics engine manages this.
	 * 
	 */
	protected void _clearTags() {
		collisionTags.clear();
	}
	
	/**
	 * 
	 * Returns whether or not this body reacts to collisions
	 * 
	 * @return
	 */
	public boolean isTrigger() {
		return trigger;
	}
	/**
	 * 
	 * Sets if this body should react to collisions.
	 * 
	 * @param trigger - if the body should generate collision events on collision
	 * @return this Body instance
	 */
	public Body setTrigger(boolean trigger) {
		this.trigger = trigger;
		return this;
	}
	
	/**
	 * 
	 * If the body is locked in space or not.
	 * 
	 * @return whether or not the body is locked in space.
	 */
	public boolean isAbsolute() {
		return absolute;
	}

	/**
	 * 
	 * Sets if this body should be locked in space.
	 * 
	 * @param absolute - whether or not it is impossible to move in space
	 * @return this Body instance
	 */
	public Body setAbsolute(boolean absolute) {
		this.absolute = absolute;
		return this;
	}
	
	/**
	 * 
	 * Returns the inverse of this body's mass
	 * 
	 * @return the inverse of this body's mass
	 */
	public float getInvMass() {
		return invMass;
	}
	
	/**
	 * 
	 * Sets the mass of this body.
	 * 
	 * @param mass - the new mass of this body
	 * @return this Body instance
	 */
	public Body setMass(float mass) {
		this.invMass = 1 / mass;
		return this;
	}
	/**
	 * Gets the bounciness of this body.
	 * @return the bounciness of this body
	 */
	public float getEpsilon() {
		return epsilon;
	}
	
	/**
	 * Sets the bounciness of this body.
	 * @param epsilon - the bouncincess of this body (0-1)
	 * @return this Body instance
	 */
	public Body setEpsilon(float epsilon) {
		this.epsilon = epsilon;
		return this;
	}
	
	/**
	 * 
	 * Gets the friction of this body.
	 * 
	 * @return the friction of this body
	 */
	public float getFriction() {
		return mu;
	}
	
	/**
	 * 
	 * Sets the friction of this body.
	 * 
	 * @param f - the new friction for this body
	 * @return
	 */
	public Body setFriction(float f) {
		this.mu = f;
		return this;
	}
	
	/**
	 * 
	 * Returns the dimensions of this body.
	 * 
	 * @return the dimensions of this body
	 */
	public Vector2f getDim() {
		return dim;
	}
	
	/**
	 * 
	 * Sets the dimensions of this body.
	 * 
	 * @param dim - the new dimensions to be set
	 * @return this Body instance
	 */
	public Body setDim(Vector2f dim) {
		this.dim = dim;
		return this;
	}
	
	/**
	 * 
	 * Returns the position of this body.
	 * 
	 * @return the position of this body
	 */
	public Vector2f getPos() {
		return transform.pos;
	}
	
	/**
	 * 
	 * Sets the position of this body.
	 * 
	 * @param pos - the position of this body
	 * @return this Body instance
	 */
	public Body setPos(Vector2f pos) {
		transform.pos = pos;
		return this;
	}
	
	/**
	 * 
	 * Returns the current velocity of this body.
	 * 
	 * @return the current velocity of this body
	 */
	public Vector2f getVel() {
		if(absolute) 
			freezeVelocity();
		
		return vel;
	}
	
	/**
	 * 
	 * Sets the velocity of this body.
	 * 
	 * @param vel - the new velocity of this body
	 * @return this Body instance
	 */
	public Body setVel(Vector2f vel) {
		this.vel = vel;
		return this;
	}
	
	/**
	 * 
	 * Nullifies the velocity of this body.
	 * 
	 * @return this Body instance
	 */
	public Body freezeVelocity() {
		this.vel.setX(0);
		this.vel.setY(0);
		return this;
	}
	
	/**
	 * 
	 * Checks if two bodies are colliding, you should probably not call this. The physics engine will handle this perfectly well.
	 * 
	 * @param body - The other body you want to check for collision against
	 * @param pe - The physics engine where the check plays out
	 */
	protected void _checkCollision(Body body, PhysicsEngine pe) {
		
		//Collision Check
		float overlapX = 0.5f * Math.abs(this.dim.getX() + body.dim.getX()) - Math.abs(this.transform.pos.getX() - body.transform.pos.getX());
		if(overlapX < 0) return;		

		float overlapY = 0.5f * Math.abs(this.dim.getY() + body.dim.getY()) - Math.abs(this.transform.pos.getY() - body.transform.pos.getY());
		if(overlapY < 0) return;
		
		//Add in all my tags
		for(String t : myTags) {
			body.addCollidingTag(t);
		}
		
		//Add in all the other bodies tags
		for(String t : body.getTags()) {
			addCollidingTag(t);
		}
		
		//Make a collision event if necessary 
		if(trigger || body.isTrigger()) return;
		
		VectorXf distance = this.transform.pos.clone().sub(body.getPos());
		float depth = 0;
		
		if(Math.abs(distance.getN(0)) > Math.abs(distance.getN(1))) {
			distance.setN(1, 0);
			distance.scale(Math.abs(1/distance.getN(0)));
			depth = overlapX;
		}
		else {
			distance.setN(0, 0);
			distance.scale(Math.abs(1/distance.getN(1)));
			depth = overlapY;
		}

		new Collision(pe, new Vector2f(distance.getN(0), distance.getN(1)), depth, this, body);
		return;
	}
	
	/**
	 * 
	 * Pushes this body with the specified force.
	 * 
	 * @param vectorXf - the force to push this body with
	 */
	public void push(VectorXf vectorXf) {
		vel.add(vectorXf.scale(invMass));
		if(absolute) {
			this.vel.setX(0);
			this.vel.setY(0);
			return;
		}
	}
	
	/**
	 * 
	 * Adds velocity to this body.
	 * 
	 * @param vel - the velocity to extend the current with.
	 */
	public void addVel(VectorXf vel) {
		this.vel.add(vel);
	}
	
	/**
	 * 
	 * Updates this body and performs various calculations for the next frame.
	 * 
	 * @param delta - the time passed since the previous frame.
	 */
	public void update(float delta) {
		if(absolute) {
			freezeVelocity();
			return;
		}
		transform.pos.add(vel.clone().scale(delta));
	}
	
	/**
	 * 
	 * Checks whether or not a point is contained within this body.
	 * 
	 * @param x - the x coordinate of the point
	 * @param y - the y coordinate of the point
	 * @return true if the points is contained
	 */
	public boolean contains(float x, float y) {
		return contains(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Checks whether or not a point is contained within this body.
	 * 
	 * @param point - the point to check
	 * @return true if the point is contained
	 */
	public boolean contains(Vector2f point) {
		return point.getX() > transform.pos.getX() - dim.getX() / 2 && point.getX() < transform.pos.getX() + dim.getX() / 2
				&& point.getY() > transform.pos.getY() - dim.getY() / 2 && point.getY() < transform.pos.getY() + dim.getY() / 2;
	}
}
