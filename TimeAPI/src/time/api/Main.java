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
import time.api.level.Level;
import time.api.math.Vector2f;
import time.api.math.Vector3f;
import time.api.physics.Body;
import time.api.physics.PhysicsEngine;
import time.api.util.Time;

public class Main {
	
	public static final void main(String[] args) {
		
		Game game = new Game();
		
		GameStateManager.registerState(new GameState("Main") {
			
			Level level;
			
			@Override
			public void init() {
				//Projection
				OrthographicShaderProgram.initProjection(0, 1280, 0, 720);
				OrthographicShaderProgram.INSTANCE.sendMatrix("m_projection", OrthographicShaderProgram.getProjection());
				
				level = new Level("res/level/test.level");
				
				Debug.log(level.getName());
			}
			
			@Override
			public void onMouse(long window, int button, int action, int mods) {
				
			}
			
			@Override
			public void onKeyboard(long window, int key, int scancode, int action, int mods) {
				
			}
			
			@Override
			public void update(float dt) {
				GLFW.glfwSetWindowTitle(game.getWindow(), Integer.toString(Time.getFPS()));
				level.update(dt);
			}
			
			@Override
			public void draw() {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				level.draw();
			}

			@Override
			public void exit() {
				
			}
		});
		
		 game.run("TimeWars", 1280, 720);
	}
}