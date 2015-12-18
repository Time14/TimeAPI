package time.api.gfx.texture;

public class Animation  {
	
	private DynamicTexture dynTex;
	
	private int mark;
	private int[] indices;
	
	private float speed = 1f;
	
	private float deltaStack;
	
	public Animation() {}
	
	public Animation(DynamicTexture dt, int... indices) {
		setDynamicTexture(dt);
		setIndices(indices);
	}
	
	public void update(float dt) {
		deltaStack += dt;
		if(deltaStack > 1f / speed) {
			deltaStack = 0;
			dynTex.swap(indices[mark]);
			mark++;
			mark %= indices.length;
		}
	}
	
	public Texture getTexture() {
		return dynTex.getTexture();
	}
	
	public Animation setSpeed(float speed) {
		this.speed = speed;
		return this;
	}
	
	public Animation setDynamicTexture(DynamicTexture dt) {
		this.dynTex = dt;
		return this;
	}
	
	public Animation setIndices(int... indices) {
		this.indices = indices;
		return this;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public DynamicTexture getDynamicTexture() {
		return dynTex;
	}
	
	public int[] getIndices() {
		return indices;
	}
}