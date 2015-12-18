package time.api.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import time.api.math.Matrix4f;

public class Util {
	
	public static final FloatBuffer toFloatBuffer(Matrix4f data) {
		return toFloatBuffer(data.matrix);
	}
	
	public static final IntBuffer toIntBuffer(int... data) {
		IntBuffer buffer = createIntBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static final FloatBuffer toFloatBuffer(float... data) {
		FloatBuffer buffer = createFloatBuffer(data.length);
		
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	public static final FloatBuffer createFloatBuffer(int size) {
		return createByteBuffer(size << 2).asFloatBuffer();
	}
	
	public static final IntBuffer createIntBuffer(int size) {
		return createByteBuffer(size << 2).asIntBuffer();
	}
	
	public static final ByteBuffer createByteBuffer(int size) {
		return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
	}
}