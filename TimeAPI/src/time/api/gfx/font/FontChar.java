package time.api.gfx.font;

public class FontChar {
	
	public final float T_X, T_Y, T_WIDTH, T_HEIGHT, X_OFFSET, Y_OFFSET, X_ADVANCE;
	
	public FontChar(int tX, int tY, int tWidth, int tHeight,
			int xOffset, int yOffset, int xAdvance) {
		T_X = tX;
		T_Y = tY;
		T_WIDTH = tWidth;
		T_HEIGHT = tHeight;
		X_OFFSET = xOffset;
		Y_OFFSET = yOffset;
		X_ADVANCE = xAdvance;
	}
}