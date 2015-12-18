package time.api.gfx.texture;

public class DynamicTexture extends Texture {
	
	private SpriteSheet spritesheet;
	
	private int currentSpriteX;
	private int currentSpriteY;
	
	public DynamicTexture() {}
	
	public DynamicTexture(Texture texture) {
		this(new SpriteSheet(1, 1, texture.getWidth(), texture.getHeight()).setTexture(texture, 0, 0));
	}
	
	public DynamicTexture(SpriteSheet spritesheet) {
		this.spritesheet = spritesheet;
		currentSpriteX = 0;
		currentSpriteY = 0;
	}
	
	public DynamicTexture setSpriteSheet(SpriteSheet spritesheet) {
		this.spritesheet = spritesheet;
		
		return this;
	}
	
	public void swap(int x, int y) {
		swapX(x);
		swapY(y);
	}
	
	public void swap(int i) {
		swapX(i%spritesheet.getSpritesX());
		swapY(Math.floorDiv(i, spritesheet.getSpritesX()));
	}
	
	public void swapX(int x) {
		currentSpriteX = x;
	}
	
	public void swapY(int y) {
		currentSpriteY = y;
	}
	
	public boolean incrementX() {
		currentSpriteX++;
		if(currentSpriteX >= spritesheet.getSpritesX()) {
			currentSpriteX--;
			return false;
		}
		return true;
	}
	
	public boolean incrementY() {
		currentSpriteY++;
		if(currentSpriteY >= spritesheet.getSpritesY()) {
			currentSpriteY--;
			return false;
		}
		return true;
	}
	
	public Texture bind() {
		spritesheet.bind(currentSpriteX, currentSpriteY, 0);
		return this;
	}
	
	public Texture getTexture() {
		return spritesheet.getTexture(currentSpriteX, currentSpriteY);
	}
	
	public SpriteSheet getSpriteSheet() {
		return spritesheet;
	}
	
	public int getSpriteX() {
		return currentSpriteX;
	}
	
	public int getSpriteY() {
		return currentSpriteY;
	}
}