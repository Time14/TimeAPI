package time.api.math;

/**
 * 
 * This is a class representation of a 4x4 matrix specified in column-major order.
 * 
 * @author Alfred Sporre
 *
 */

public class Matrix4f {
	
	public static final int SIZE = Float.SIZE * 8 * 16;
	
	public float[] matrix = new float[16];
	
	
	/**
	 * 
	 * 
	 * 
	 * @return this Matrix4f instance
	 */
	public Matrix4f clear() {
		for(int i = 0; i < 16; i++) {
			matrix[i] = 0;
		}
		
		return this;
	}
	
	public Matrix4f loadIdentity() {
		clear();
		
		matrix[0 + 0 * 4] = 1;
		matrix[1 + 1 * 4] = 1;
		matrix[2 + 2 * 4] = 1;
		matrix[3 + 3 * 4] = 1;
		
		return this;
	}
	
	public Matrix4f multiply(Matrix4f right) {
		float[] result = new float[16];
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 4; k++) {
					result[i*4+j] += matrix[k+i*4] * right.toFloatArray()[j+k*4];
				}
			}
		}
		
		matrix = result;
		return this;
	}
	
	public Matrix4f translate(Vector2f vec2) {
		return translate(new Vector3f(vec2.getX(), vec2.getY(), 0));
	}
	
	public Matrix4f translate(Vector3f vec3) {
		matrix[0 + 3 * 4] += vec3.getX();
		matrix[1 + 3 * 4] += vec3.getY();
		matrix[2 + 3 * 4] += vec3.getZ();
		
		return this;
	}
	
	public float get(int column, int row) {
		return matrix[row + column * 4];
	}
	
	public Matrix4f rotate(float angle) {
		
		float r = (float)Math.toRadians(angle);
		float sin = (float)Math.sin(r);
		float cos = (float)Math.cos(r);
		
		matrix[0 + 0 * 4] = cos;
		matrix[0 + 1 * 4] = -sin;
		matrix[1 + 0 * 4] = sin;
		matrix[1 + 1 * 4] = cos;
		
		return this;
	}
	
	public Matrix4f scale(Vector2f scale) {
		matrix[0 + 0 * 4] *= scale.getX();
		matrix[1 + 1 * 4] *= scale.getY();
		return this;
	}
	
	public Matrix4f scale(Vector3f scale) {
		matrix[0 + 0 * 4] *= scale.getX();
		matrix[1 + 1 * 4] *= scale.getY();
		matrix[2 + 2 * 4] *= scale.getZ();
		return this;
	}
	
	public Matrix4f init(float[] values) {
		matrix = values;
		
		return this;
	}
	
	public Matrix4f set(int column, int row, float value) {
		matrix[column*4+row] = value;
		
		return this;
	}
	
	public float[] toFloatArray() {
		return matrix;
	}
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				str.append((matrix[i+j*4]));
				if(j < 3)
					str.append("\t");
			}
			str.append("\n");
		}
		
		return str.toString();
	}
	
	public static final Matrix4f IDENTITY() {
		return new Matrix4f().loadIdentity();
	}
}