package time.api;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import time.api.debug.Debug;
import time.api.gamestate.GameState;
import time.api.gamestate.GameStateManager;
import time.api.gfx.FontRenderer;
import time.api.gfx.Mesh;
import time.api.gfx.Renderer;
import time.api.gfx.VertexTex;
import time.api.gfx.gui.Button;
import time.api.gfx.gui.CheckBox;
import time.api.gfx.gui.GUI;
import time.api.gfx.shader.OrthographicShaderProgram;
import time.api.gfx.texture.Animation;
import time.api.gfx.texture.DynamicTexture;
import time.api.gfx.texture.SpriteSheet;
import time.api.gfx.texture.Texture;
import time.api.math.Vector2f;
import time.api.math.Vector3f;
import time.api.physics.Body;
import time.api.physics.PhysicsEngine;
import time.api.util.Time;

public class Main {
	
	public static final void main(String[] args) {
		
		Game game = new Game();
		
		GameStateManager.registerState(new GameState("Main") {
			
			PhysicsEngine pe = new PhysicsEngine();
			
			Body b = new Body(-.75f, -.75f, .1f, .1f);
			
			Renderer renderer;
			
			Mesh mesh;
			
			DynamicTexture dt;
			
			Animation animation;
			
			GUI gui = new GUI();
			
			@Override
			public void init() {
				System.out.println("Initiated " + NAME);
				pe.addBody(new Body(0, 0, .5f, .5f));
				pe.addBody(b);
				mesh = new Mesh(new VertexTex[]{
						new VertexTex(new Vector3f(-0.5f, 0.5f * (16f/9f), 0), new Vector2f(0, 0)),
						new VertexTex(new Vector3f(0.5f, 0.5f * (16f/9f), 0), new Vector2f(1, 0)),
						new VertexTex(new Vector3f(0.5f, -0.5f * (16f/9f), 0), new Vector2f(1, 1)),
						new VertexTex(new Vector3f(-0.5f, -0.5f * (16f/9f), 0), new Vector2f(0, 1))
				}, 0, 1, 2, 0, 2, 3);
				
				dt = new DynamicTexture(new SpriteSheet(9, 1, 32, 32).loadTexture("res/texture/coin.png"));
				
				renderer = new Renderer(mesh, dt);
				animation = new Animation(dt, 0, 1, 2, 3, 4, 5, 6, 7, 8).setSpeed(10);
				
				SpriteSheet.register("box", new SpriteSheet(2, 1, 16, 16).loadTexture("res/texture/box.png"));
				Texture.register("box", new DynamicTexture(SpriteSheet.get("box")));
				
				gui.addElements(
						new Button(-0.5f, 0, .2f, .2f, Texture.getDT("box", true)).setMouseOutEvent(() -> Debug.log("out")),
						new CheckBox(0, 0, .2f, .2f, Texture.getDT("box", true))
						.setMouseInEvent(() -> Debug.log("in")).setMouseOutEvent(() -> Debug.log("out"))
				);
			}
			
			@Override
			public void onMouse(long window, int button, int action, int mods) {
				if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_RELEASE) {
					gui.click(OrthographicShaderProgram.INSTANCE.getMouseClipspaceCoordinates(window, 1280, 720));
				}
			}
			
			@Override
			public void update(float dt) {
				GLFW.glfwSetWindowTitle(game.getWindow(), Integer.toString(Time.getFPS()));
				pe.update(dt);
				animation.update(dt);
				gui.update(dt, OrthographicShaderProgram.INSTANCE.getMouseClipspaceCoordinates(game.getWindow(), 1280, 720));
			}
			
			@Override
			public void draw() {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				renderer.draw();
				gui.draw();
				FontRenderer.draw(0, 0, "Hej");
			}

			@Override
			public void exit() {
				
			}
		});
		
		 game.run("TimeWars", 1280, 720);
	}
}