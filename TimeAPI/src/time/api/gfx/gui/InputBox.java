package sk.client.gfx.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import sk.client.debug.Debug;
import sk.client.gfx.texture.DynamicTexture;
import sk.client.gfx.texture.Texture;
import sk.client.renderer.QuadRenderer;
import sk.client.renderer.TextRenderer;
import sk.client.util.Util;
import sk.stb.STB;

public class InputBox extends Selectable {
	
	protected String text = new String();
	protected float textCap = 0;
	
	public InputBox(float x, float y, float width, float height, Texture texture) {
		renderer = new QuadRenderer(x, y, width, height, false, texture);
		STB.start("InputBox Mark", 2f);
	}
	
	public void onKeyboard(int key, boolean pressed) {
		if(selected && pressed) {
			if(pressed) {
				if(Util.isPrintable(Keyboard.getEventCharacter())) {
					if(TextRenderer.getWidth(text + Keyboard.getEventCharacter()) <= getWidth())
						text += Keyboard.getEventCharacter();
				}
				
				switch(key) {
				case Keyboard.KEY_BACK:
					if(text.length() > 0)
						text = text.substring(0, text.length() - 1);
					break;
				case Keyboard.KEY_RETURN:
					if(selected)
						selected = false;
					break;
				}
			}
		}
		
		super.onKeyboard(key, pressed);
	}
	
	public void update(float tick) {
		super.update(tick);
		STB.update(tick / 1000f, "InputBox Mark");
		if(STB.done("InputBox Mark"))
			STB.reset("InputBox Mark");
	}
	
	public void onClick(float x, float y) {
		selected = !selected;
		for(InputBox e : gui.get(InputBox.class)) {
			if(e != this)
				e.setSelected(false);
		}
	}
	
	public void draw() {
		if(renderer.getTexture() instanceof DynamicTexture) {
			if(selected) {
				((DynamicTexture)renderer.getTexture()).swap(1);
				renderer.draw();
				((DynamicTexture)renderer.getTexture()).swap(0);
				Debug.log(STB.getDuration("InputBox Mark"));
				if(STB.getCount("InputBox Mark") >= 1f) {
					GL11.glPushMatrix();
					{
						GL11.glTranslatef(getX() + TextRenderer.getWidth(text), getY(), 0);
						GL11.glBegin(GL11.GL_LINES);
						{
							GL11.glVertex2f(0, getHeight() * 0.15f);
							GL11.glVertex2f(0, getHeight() - getHeight() * 0.15f);
						}
						GL11.glEnd();
					}
					GL11.glPopMatrix();
				}
			} else {
				renderer.draw();
			}
			TextRenderer.setSize(getHeight());
			TextRenderer.draw(text, renderer.getX(), renderer.getY());
		}
	}
}