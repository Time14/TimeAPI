package time.api;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import time.api.gamestate.GameState;
import time.api.gamestate.GameStateManager;
import time.api.math.Vector2f;
import time.api.physics.Body;
import time.api.physics.PhysicsEngine;
import time.api.util.Time;

public class Main {
	
	public static final void main(String[] args) {
		
		Game game = new Game();
		
		GameStateManager.registerState(new GameState("Main") {
			
			PhysicsEngine pe = new PhysicsEngine();
			
			Body b = new Body(-.75f, -.75f, .1f, .1f);
			
			@Override
			public void init() {
				System.out.println("Initiated " + NAME);
				pe.addBody(new Body(0, 0, .5f, .5f));
				pe.addBody(b);
				
			}
			
			/*
			@Override
			public void onKeyboard(long window, int key, int scancode, int action, int mods) {
				
				boolean w = key == GLFW.GLFW_KEY_W;
				boolean a = key == GLFW.GLFW_KEY_A;
				boolean s = key == GLFW.GLFW_KEY_S;
				boolean d = key == GLFW.GLFW_KEY_D;
				
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
				}
				
				if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
					game.stop();
				}
				
			}*/
			
			@Override
			public void onMouse(long window, int button, int action, int mods) {
				
			}
			
			@Override
			public void update(float dt) {
				GLFW.glfwSetWindowTitle(game.getWindow(), Integer.toString(Time.getFPS()));
				pe.update(dt);
			}
			
			@Override
			public void draw() {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				pe._debugDraw();
			}

			@Override
			public void exit() {
				
			}
		});
		
		 game.run("TimeWars", 1280, 720);
	}
}