package time.api.math;

public class Transform {
	Vector3f position;
	Vector2f scale;
	float rotation;
	
	public Transform(float x,float y,float z,float sx,float sy,float r) {
		position = new Vector3f(x, y, z);
		scale = new Vector2f(sx, sy);
		rotation = r;
	}
	
	public Transform move(float x) {
		return move(x, 0);
	}
	
	public Transform move(float x, float y) {
		return move(x, y, 0);
	}
	
	public Transform move(float x, float y, float z) {
		return move(new VectorXf(x, y, z));
	}
	
	public Transform move(VectorXf vector) {
		position.add(vector);
		return this;
	}
	
	public Transform place(float x, float y, float z) {
		return place(new Vector3f(x, y, z));
	}
	
	public Transform place(Vector3f vector) {
		position = vector;
		return this;
	}
	
	public Transform scale(float x) {
		return scale(x, 1);
	}
	
	public Transform scale(float x, float y) {
		scale.setX(scale.getX() * x);
		scale.setY(scale.getY() * y);
		return this;
	}
	
	public Transform setScaleX(float x) {
		scale.setX(x);
		return this;
	}
	
	public Transform setScaleY(float y) {
		scale.setY(y);
		return this;
	}
	
	public Transform setScale(float x, float y) {
		scale.setX(x);
		scale.setY(y);
		return this;
	}
	
	public Transform rotate(float r) {
		rotation += r;
		return this;
	}
	
	public Transform setRotate(float r) {
		rotation = r;
		return this;
	}
	
	public Matrix4f getMatrix() {
		Matrix4f m = Matrix4f.IDENTITY();
		m.setValue(0, 0, scale.getX() * (float)Math.cos(rotation));
		m.setValue(1, 1, scale.getY() * (float)Math.cos(rotation));
		
		m.setValue(3, 0, position.getX());
		m.setValue(3, 1, position.getY());
		m.setValue(3, 2, position.getZ());
		
		m.setValue(1, 0, (float)Math.sin(rotation));
		m.setValue(0, 1, -(float)Math.sin(rotation));
		
		return m;
	}
}