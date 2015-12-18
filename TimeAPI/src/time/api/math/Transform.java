package time.api.math;

public class Transform {
	
	public Vector2f position;
	public float rotation;
	public Vector2f scale;
	
	public Transform() {
		this(new Vector2f());
	}
	
	public Transform(float x, float y) {
		this(new Vector2f(x, y));	
	}
	
	public Transform(Vector2f position) {
		this(position, 0);
	}
	
	public Transform(Vector2f position, float rotation) {
		this(position, rotation, new Vector2f(1, 1));
	}
	
	public Transform(Vector2f position, float rotation, Vector2f scale) {
		this.position = position;
		this.scale = scale;
	}
	
	public Matrix4f getMatrix() {
		Matrix4f matrix = new Matrix4f();
		matrix = Matrix4f.IDENTITY();
		
		matrix.translate(position);
		matrix.rotate(rotation);
		matrix.scale(scale);
		
		return matrix;
	}
	
	public Transform setRotation(float rotation) {
		this.rotation = rotation;
		rotation %= 360;
		return this;
	}
	
	public Transform rotate(float rotation) {
		this.rotation += rotation;
		this.rotation %= 360;
		return this;
	}
	
	public Transform setScale(float scale) {
		setScaleX(scale);
		return setScaleY(scale);
	}
	
	public Transform setScaleX(float scale) {
		this.scale.setX(scale);
		return this;
	}
	
	public Transform setScaleY(float scale) {
		this.scale.setY(scale);
		return this;
	}
	
	public Transform setScale(float x, float y) {
		return setScale(new Vector2f(x, y));
	}
	
	public Transform setScale(Vector2f scale) {
		this.scale.setX(scale.getX());
		this.scale.setY(scale.getY());
		return this;
	}
	
	public Transform addScale(float scalar) {
		addScaleX(scalar);
		return addScaleY(scalar);
	}
	
	public Transform addScaleX(float scale) {
		this.scale.setX(this.scale.getX() + scale);
		return this;
	}
	
	public Transform addScaleY(float scale) {
		this.scale.setY(this.scale.getY() + scale);
		return this;
	}
	
	public Transform addScale(float x, float y) {
		return addScale(new Vector2f(x, y));
	}
	
	public Transform addScale(Vector2f scale) {
		this.scale.add(scale);
		return this;
	}
	
	public Transform scale(float scalar) {
		scale.scale(scalar);
		return this;
	}
	
	public Transform scaleX(float scale) {
		this.scale.setX(this.scale.getX() * scale);
		return this;
	}
	
	public Transform scaleY(float scale) {
		this.scale.setY(this.scale.getY() * scale);
		return this;
	}
	
	public Transform scale(float x, float y) {
		return scale(new Vector2f(x, y));
	}
	
	public Transform scale(Vector2f scale) {
		scaleX(scale.getX());
		scaleY(scale.getY());
		return this;
	}
	
	public Transform setX(float x) {
		position.setX(x);
		return this;
	}
	
	public Transform setY(float y) {
		position.setY(y);
		return this;
	}
	
	public Transform setPosition(float x, float y) {
		return setPosition(new Vector2f(x, y));
	}
	
	public Transform setPosition(Vector2f position) {
		setX(position.getX());
		setY(position.getY());
		return this;
	}
	
	public Transform translateX(float x) {
		position.setX(position.getX() + x);
		return this;
	}
	
	public Transform translateY(float y) {
		position.setY(position.getY() + y);
		return this;
	}
	
	public Transform translate(float x, float y) {
		return translate(new Vector2f(x, y));
	}
	
	public Transform translate(Vector2f offset) {
		position.add(offset);
		return this;
	}
}