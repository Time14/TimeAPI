package advancedMath;

import debug.Debug;

public class VectorXf {
	
	//The values stored in the vector 
	private float[] vals;
	
	
	/**
	 * A generic vector class
	 * 
	 * @param f - all the components you want in the vector;
	 */
	public VectorXf(float ... f) {
		vals = f;
	}
	
	/**
	 * Returns the vectors distance from origo
	 * @return
	 */
	public float getMagnitude() {
		float s = 0;
		for(float f : vals) {
			s += f * f;
		}
		return (float)Math.sqrt((double)s);
	}
	
	/**
	 * Returns the N-th vector element
	 * 
	 * @param n - the index of the value you want
	 * @return
	 */
	public float getN(int n) {
		if(n < 0 || vals.length < n) {
			Debug.Log("value not in range", 3);
		}
		return vals[n];
	}
	
	/**
	 * Tells you how many dimensions the vector has
	 * @return
	 */
	public int getDimension() {
		return vals.length;
	}
	
	/**
	 * Sets the N-th vector element
	 * 
	 * @param n - the index of the value you want to set
	 * @param f - the value you want to set it to
	 * @return
	 */
	public float setN(int n, float f) {
		if(n < 0 || vals.length < n) {
			Debug.Log("value not in range", 3);
		}
		return vals[n] = f;
	}
	
	/**
	 * Adds two vectors to eachother
	 * @param v - the vector you want to add to this vector
	 */
	public VectorXf add(VectorXf v) {
		float smallest = (v.vals.length < this.vals.length) ? v.vals.length : this.vals.length;
		
		for(int i = 0; i < smallest; i++) {
			this.vals[i] += v.vals[i];
		}
		return this;
	}
	
	/**
	 * Subtacts two vectors to eachother
	 * @param v - the vector you want to subtract from this vector
	 */
	public VectorXf sub(VectorXf v) {
		float smallest = (v.vals.length < this.vals.length) ? v.vals.length : this.vals.length;
		
		for(int i = 0; i < smallest; i++) {
			this.vals[i] -= v.vals[i];
		}
		return this;
	}
	
	/**
	 * returns the dot product of the two vectors
	 * @param v - the vector you want to dot with this vector
	 */
	public float dot(VectorXf v) {
		float smallest = (v.vals.length < this.vals.length) ? v.vals.length : this.vals.length;
		float s = 0;
		for(int i = 0; i < smallest; i++) {
			s += this.vals[i] * v.vals[i];
		}	
		return s;
	}
	
	public VectorXf scale(float f) {
		for(int i = 0; i < vals.length; i++) {
			this.vals[i] *= f;
		}
		return this;
	}
	
	public VectorXf clone() {
		
		switch (this.vals.length) {
			case 1:
				return new VectorXf(this.vals[0]);
			case 2:
				return new VectorXf(this.vals[0], this.vals[1]);
			case 3:
				return new VectorXf(this.vals[0], this.vals[1], this.vals[2]);
			case 4:
				return new VectorXf(this.vals[0], this.vals[1], this.vals[2], this.vals[3]);
			case 5:
				return new VectorXf(this.vals[0], this.vals[1], this.vals[2], this.vals[3], this.vals[4]);
			default:
				break;
		}
		
		return new VectorXf(this.vals);
	}
	
	public String toString() {
		String s = "";
		for(float f : this.vals) {
			s += f + "\t";
		}
		return s;
	}
}