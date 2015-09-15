package advancedMath;

public class Vector2f extends VectorXf {
	public Vector2f(float x, float y) {
		super(x, y);
	}
	
	public float getX() {
		return this.getN(0);
	}
	
	public float getY() {
		return this.getN(1);
	}
	
	public float setX(float x) {
		return this.setN(0, x);
	}
	
	public float setY(float y) {
		return this.setN(1, y);
	}
}