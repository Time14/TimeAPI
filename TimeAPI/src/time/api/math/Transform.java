package time.api.math;

public class Transform {
	
	public Vector2f position;
	public float rotation;
	public Vector2f scale;
	
	/**
	 * 
	 * Constructs a new empty transform.
	 * 
	 */
	public Transform() {
		this(new Vector2f());
	}
	
	/**
	 * 
	 * Constructs a new transform with the specified position.
	 * 
	 * @param x - the x coordinate of this transform
	 * @param y - the y coordinate of this transform
	 */
	public Transform(float x, float y) {
		this(new Vector2f(x, y));	
	}
	
	/**
	 * 
	 * Constructs a new transform with the specified position.
	 * 
	 * @param position - the position of this transform
	 */
	public Transform(Vector2f position) {
		this(position, 0);
	}
	
	/**
	 * 
	 * Constructs a new transform with the specified position and rotation.
	 * 
	 * @param position - the position of this transform
	 * @param rotation - the z-axis rotation of this matrix
	 */
	public Transform(Vector2f position, float rotation) {
		this(position, rotation, new Vector2f(1, 1));
	}
	
	/**
	 * 
	 * Constructs a new transform with the specified position, rotation and scale.
	 * 
	 * @param position - the position of this transform
	 * @param rotation - the z-axis rotation of this matrix
	 * @param scale - the scale of this matrix
	 */
	public Transform(Vector2f position, float rotation, Vector2f scale) {
		this.position = position;
		this.scale = scale;
	}
	
	/**
	 * 
	 * Returns a composed matrix from this transform.
	 * 
	 * @return a composed matrix from this transform
	 */
	public Matrix4f getMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix = Matrix4f.IDENTITY();
		
		matrix.translate(position);
		matrix.rotate(rotation);
		matrix.scale(scale);
		
		return matrix;
	}
	
	/**
	 * 
	 * Sets the z-axis rotation of this transform.
	 * 
	 * @param rotation - the rotation to set
	 * @return this transform instance
	 */
	public Transform setRotation(float rotation) {
		this.rotation = rotation;
		rotation %= 360;
		return this;
	}
	
	/**
	 * 
	 * Rotates this transform around the z-axis.
	 * 
	 * @param rotation - the rotation amount
	 * @return this transform instance
	 */
	public Transform rotate(float rotation) {
		this.rotation += rotation;
		this.rotation %= 360;
		return this;
	}
	
	/**
	 * 
	 * Sets the scale of this transform uniformly.
	 * 
	 * @param scale - the scale to set
	 * @return this transform instance
	 */
	public Transform setScale(float scale) {
		setScaleX(scale);
		return setScaleY(scale);
	}
	
	/**
	 * 
	 * Sets the scale of this transform on the x-axis.
	 * 
	 * @param scale - the scale to set
	 * @return this transform instance
	 */
	public Transform setScaleX(float scale) {
		this.scale.setX(scale);
		return this;
	}
	
	/**
	 * 
	 * Sets the scale of this transform on the y-axis.
	 * 
	 * @param scale - the scale to set
	 * @return this transform instance
	 */
	public Transform setScaleY(float scale) {
		this.scale.setY(scale);
		return this;
	}
	
	/**
	 * 
	 * Sets the scale of this transform.
	 * 
	 * @param x - the x-axis scale
	 * @param y - the y-axis scale
	 * @return this transform instance
	 */
	public Transform setScale(float x, float y) {
		return setScale(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Sets the scale of this transform to the specified vector.
	 * 
	 * @param scale - the scale to set
	 * @return this transform instance
	 */
	public Transform setScale(Vector2f scale) {
		this.scale.setX(scale.getX());
		this.scale.setY(scale.getY());
		return this;
	}
	
	/**
	 * 
	 * Adds scale to this transform uniformly.
	 * 
	 * @param scale - the scale to add
	 * @return this transform instance
	 */
	public Transform addScale(float scale) {
		addScaleX(scale);
		return addScaleY(scale);
	}
	
	/**
	 * 
	 * Adds scale to this transform on the x-axis.
	 * 
	 * @param scale - the scale to add
	 * @return this transform instance
	 */
	public Transform addScaleX(float scale) {
		this.scale.setX(this.scale.getX() + scale);
		return this;
	}
	
	/**
	 * 
	 * Adds scale to this transform on the y-axis.
	 * 
	 * @param scale - the scale to add
	 * @return
	 */
	public Transform addScaleY(float scale) {
		this.scale.setY(this.scale.getY() + scale);
		return this;
	}
	
	/**
	 * 
	 * Adds scale to this transform.
	 * 
	 * @param x - the scale to add on the x-axis
	 * @param y - the scale to add on the y-axis
	 * @return this transform instance
	 */
	public Transform addScale(float x, float y) {
		return addScale(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Adds the specified vector to this transform scale.
	 * 
	 * @param scale - the scale to add
	 * @return this transform instance
	 */
	public Transform addScale(Vector2f scale) {
		this.scale.add(scale);
		return this;
	}
	
	/**
	 * 
	 * Scales this transform uniformly.
	 * 
	 * @param scalar - the scalar to scale with
	 * @return this transform instance
	 */
	public Transform scale(float scalar) {
		scale.scale(scalar);
		return this;
	}
	
	/**
	 * 
	 * Scales this transform on the x-axis.
	 * 
	 * @param scalar - the scalar to scale with
	 * @return this transform instance
	 */
	public Transform scaleX(float scalar) {
		this.scale.setX(this.scale.getX() * scalar);
		return this;
	}
	
	/**
	 * 
	 * Scales this transform on the y-axis.
	 * 
	 * @param scalar - the scalar to scale with
	 * @return this transform instance
	 */
	public Transform scaleY(float scalar) {
		this.scale.setY(this.scale.getY() * scalar);
		return this;
	}
	
	/**
	 * 
	 * Scales this transform.
	 * 
	 * @param x - the scalar to scale with on the x-axis
	 * @param y - the scalar to scale with on the y-axis
	 * @return this transform instance
	 */
	public Transform scale(float x, float y) {
		return scale(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Scales this transform with the specified vector.
	 * 
	 * @param scale - the vector to scale with
	 * @return this transform instance
	 */
	public Transform scale(Vector2f scale) {
		scaleX(scale.getX());
		scaleY(scale.getY());
		return this;
	}
	
	/**
	 * 
	 * Sets the x coordinate of this transform.
	 * 
	 * @param x - the x coordinate to set
	 * @return this transform instance
	 */
	public Transform setX(float x) {
		position.setX(x);
		return this;
	}
	
	/**
	 * 
	 * Sets the y coordinate of this transform.
	 * 
	 * @param y - the y coordinate to set
	 * @return this transform instance
	 */
	public Transform setY(float y) {
		position.setY(y);
		return this;
	}
	
	/**
	 * 
	 * Sets the position of this transform.
	 * 
	 * @param x - the x coordinate to set
	 * @param y - the y coordinate to set
	 * @return this transform instance
	 */
	public Transform setPosition(float x, float y) {
		return setPosition(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Sets the position of this transform to the specified vector.
	 * 
	 * @param position - the position to set
	 * @return this transform instance
	 */
	public Transform setPosition(Vector2f position) {
		setX(position.getX());
		setY(position.getY());
		return this;
	}
	
	/**
	 * 
	 * Translates this transform on the x-axis.
	 * 
	 * @param x - the translation on the x-axis
	 * @return this transform instance
	 */
	public Transform translateX(float x) {
		position.setX(position.getX() + x);
		return this;
	}
	
	/**
	 * 
	 * Translates this transform on the y-axis.
	 * 
	 * @param y - the translation on the y-axis
	 * @return this transform instance
	 */
	public Transform translateY(float y) {
		position.setY(position.getY() + y);
		return this;
	}
	
	/**
	 * 
	 * Translates this transform.
	 * 
	 * @param x - the translation on the x-axis
	 * @param y - the translation on the y-axis
	 * @return this transform instance
	 */
	public Transform translate(float x, float y) {
		return translate(new Vector2f(x, y));
	}
	
	/**
	 * 
	 * Translates this transform with the specified vector.
	 * 
	 * @param offset - the vector to translate with
	 * @return this transform instance
	 */
	public Transform translate(Vector2f offset) {
		position.add(offset);
		return this;
	}
}