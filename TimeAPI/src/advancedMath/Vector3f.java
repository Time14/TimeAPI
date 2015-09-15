package advancedMath;

public class Vector3f extends VectorXf {
	public Vector3f(float x, float y, float z) {
		super(x, y, z);
	}
	
	public float getX() {
		return this.getN(0);
	}
	
	public float getY() {
		return this.getN(1);
	}
	
	public float getZ() {
		return this.getN(2);
	}
	
	public float setX(float x) {
		return this.setN(0, x);
	}
	
	public float setY(float y) {
		return this.setN(1, y);
	}
	
	public float setZ(float z) {
		return this.setN(2, z);
	}
}