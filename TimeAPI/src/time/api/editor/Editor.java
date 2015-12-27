package time.api.editor;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import time.api.Game;
import time.api.Main;
import time.api.debug.Debug;
import time.api.gamestate.GameState;
import time.api.gamestate.GameStateManager;
import time.api.gfx.font.FontRenderer;
import time.api.gfx.font.FontType;
import time.api.gfx.gui.Button;
import time.api.gfx.gui.GUI;
import time.api.gfx.shader.OrthographicShaderProgram;
import time.api.gfx.texture.DynamicTexture;
import time.api.gfx.texture.SpriteSheet;
import time.api.gfx.texture.Texture;
import time.api.input.InputManager;

public class Editor {
	
	public static final int PROJECTION_WIDTH = 1280;
	public static final int PROJECTION_HEIGHT = 720;
	
	
	public static void init() {
		GameStateManager.registerState(new GameState("Editor") {
			
			FontType font; 

			GUI gui;
			
			@Override
			public void init() {
				//Projection
				OrthographicShaderProgram.initProjection(0, PROJECTION_WIDTH, 0, PROJECTION_HEIGHT);
				OrthographicShaderProgram.INSTANCE.sendMatrix("m_projection", OrthographicShaderProgram.getProjection());
				
				//Fonts
				font = FontType.FNT_ARIAL;
				
				//Register textures
				SpriteSheet.register("box", new SpriteSheet(2, 1, 16, 16).loadTexture("res/texture/box.png"));
				Texture.register("box", new DynamicTexture(SpriteSheet.get("box")));
				
				int width = 75;
				int height = 25;
				
				float fontSize = 0.15f;
				
				Button[] tabs  = {
						new Button(PROJECTION_WIDTH - width * 4.5f, PROJECTION_HEIGHT - height * 0.5f, width, height, Texture.getDT("box", true)).setFont("SCENE", font,fontSize),
						new Button(PROJECTION_WIDTH - width * 3.5f, PROJECTION_HEIGHT - height * 0.5f, width, height, Texture.getDT("box", true)).setFont("ENTITY", font,fontSize),
						new Button(PROJECTION_WIDTH - width * 2.5f, PROJECTION_HEIGHT - height * 0.5f, width, height, Texture.getDT("box", true)).setFont("ASSETS", font,fontSize),
						new Button(PROJECTION_WIDTH - width * 1.5f, PROJECTION_HEIGHT - height * 0.5f, width, height, Texture.getDT("box", true)).setFont("GLOBAL", font,fontSize),
						new Button(PROJECTION_WIDTH - width * 0.5f, PROJECTION_HEIGHT - height * 0.5f, width, height, Texture.getDT("box", true)).setFont("HIDE", font,fontSize)
				};
				
				//GUI
				gui = new GUI().addElements(
					tabs[0], tabs[1], tabs[2], tabs[3], tabs[4]
				);
			}
			
			@Override
			public void update(float dt) {
				gui.update(dt, OrthographicShaderProgram.INSTANCE.getMouseClipspaceCoordinates(Main.game.getWindow(), 1280, 720));
			}
			
			@Override
			public void onMouse(long window, int button, int action, int mods) {
				// TODO Auto-generated method stub
				
			}
			
			
			
			@Override
			public void exit() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void draw() {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				gui.draw();
			}
		});
	}
}
