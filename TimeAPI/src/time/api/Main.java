package time.api;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import time.api.debug.Debug;
import time.api.gamestate.GameState;
import time.api.gamestate.GameStateManager;
import time.api.gfx.Mesh;
import time.api.gfx.Renderer;
import time.api.gfx.VertexTex;
import time.api.gfx.font.FontType;
import time.api.gfx.font.FontRenderer;
import time.api.gfx.gui.Button;
import time.api.gfx.gui.CheckBox;
import time.api.gfx.gui.GUI;
import time.api.gfx.gui.InputBox;
import time.api.gfx.shader.OrthographicShaderProgram;
import time.api.gfx.texture.Animation;
import time.api.gfx.texture.DynamicTexture;
import time.api.gfx.texture.SpriteSheet;
import time.api.gfx.texture.Texture;
import time.api.input.InputManager;
import time.api.input.KeyState;
import time.api.math.Vector2f;
import time.api.math.Vector3f;
import time.api.physics.Body;
import time.api.physics.PhysicsEngine;
import time.api.util.Time;

public class Main {
	
	public static final void main(String[] args) {
		
		Game game = new Game();
		
		GameStateManager.registerState(new GameState("Main") {
			
			FontRenderer fnt;
			
			GUI gui;
			
			PhysicsEngine pe;
			
			@Override
			public void init() {
				//Projection
				OrthographicShaderProgram.initProjection(0, 1280, 0, 720);
				OrthographicShaderProgram.INSTANCE.sendMatrix("m_projection", OrthographicShaderProgram.getProjection());
				
				//Font
				fnt = new FontRenderer(100, 500, "Hello", FontType.FNT_VERDANA, 1f);
				
				InputManager.registerKey(GLFW.GLFW_KEY_1, 0, "fnt1");
				InputManager.registerKey(GLFW.GLFW_KEY_2, 0, "fnt2");
				
				//Register textures
				SpriteSheet.register("box", new SpriteSheet(2, 1, 16, 16).loadTexture("res/texture/box.png"));
				Texture.register("box", new DynamicTexture(SpriteSheet.get("box")));
				
				Button b = new Button(640, 100, 300, 100, Texture.getDT("box", true)).setFont("Click Me!", FontType.FNT_ARIAL, 1);
				
				InputBox in = new InputBox(640, 360, 300, 100, Texture.getDT("box", true), FontType.FNT_CHILLER, 1);
				
				b.getFontRenderer().setColor(1, 0, 0, 1);
				
				fnt.setColor(0, 1, 0, .5f);
				
				//GUI
				gui = new GUI().addElements(
					b,
					in
				);
			}
			
			@Override
			public void onMouse(long window, int button, int action, int mods) {
				if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_RELEASE) {
					gui.click(OrthographicShaderProgram.INSTANCE.getMouseClipspaceCoordinates(game.getWindow(), 1280, 720));
				}
			}
			
			@Override
			public void onKeyboard(long window, int key, int scancode, int action, int mods) {
				gui.triggerKey(key, mods, action);
			}
			
			@Override
			public void update(float dt) {
				GLFW.glfwSetWindowTitle(game.getWindow(), Integer.toString(Time.getFPS()));
				if(InputManager.wasPressed("fnt1")) {
					fnt.setSize(fnt.getSize() * 0.9f);
				}
				if(InputManager.wasPressed("fnt2")) {
					fnt.setSize(fnt.getSize() * 1.1f);
				}
				
//				pe.update(dt);
				
//				Debug.log(InputManager.getMetaState("fnt1"));
				
				gui.update(dt, OrthographicShaderProgram.INSTANCE.getMouseClipspaceCoordinates(game.getWindow(), 1280, 720));
			}
			
			@Override
			public void draw() {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				gui.draw();
				fnt.draw();
			}

			@Override
			public void exit() {
				
			}
		});
		
		 game.run("TimeWars", 1280, 720);
	}
}