package time.api;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import time.api.gamestate.GameState;
import time.api.gamestate.GameStateManager;
import time.api.gfx.Mesh;
import time.api.gfx.Renderer;
import time.api.gfx.VertexTex;
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
			}

			@Override
			public void onKeyboard(long window, int key, int scancode, int action, int mods) {
				
				boolean w = key == GLFW.GLFW_KEY_W;
				boolean a = key == GLFW.GLFW_KEY_A;
				boolean s = key == GLFW.GLFW_KEY_S;
				boolean d = key == GLFW.GLFW_KEY_D;
				boolean i = key == GLFW.GLFW_KEY_I;
				boolean o = key == GLFW.GLFW_KEY_O;
				
				
				if(action == GLFW.GLFW_REPEAT) {
					float amt = .1f;
					if(w)
						b.push(new Vector2f(0, amt));
					if(a)
						b.push(new Vector2f(-amt, 0));
					if(s)
						b.push(new Vector2f(0, -amt));
					if(d)
						b.push(new Vector2f(amt, 0));
					if(i)
						animation.setSpeed(animation.getSpeed() - 0.5f);
					if(o)
						animation.setSpeed(animation.getSpeed() + 0.5f);
				}
			}
			
			@Override
			public void onMouse(long window, int button, int action, int mods) {
				
			}
			
			@Override
			public void update(float dt) {
				GLFW.glfwSetWindowTitle(game.getWindow(), Integer.toString(Time.getFPS()));
				pe.update(dt);
				animation.update(dt);
			}
			
			@Override
			public void draw() {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				renderer.draw();
			}

			@Override
			public void exit() {
				
			}
		});
		
		 game.run("TimeWars", 1280, 720);
	}
}