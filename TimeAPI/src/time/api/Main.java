package time.api;

import org.lwjgl.glfw.GLFW;

import time.api.gamestate.GameState;
import time.api.gamestate.GameStateManager;
import time.api.util.Time;

public class Main {
	
	public static final void main(String[] args) {
		
		Game game = new Game();
		
		GameStateManager.registerState(new GameState("Main") {
			@Override
			public void init() {
				System.out.println("Initiated " + NAME);
			}

			@Override
			public void onKeyboard(long window, int key, int scancode, int action, int mods) {
				if(key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
					game.stop();
				}
			}
			
			@Override
			public void onMouse(long window, int button, int action, int mods) {
				
			}
			
			@Override
			public void update(float dt) {
				GLFW.glfwSetWindowTitle(game.getWindow(), Integer.toString(Time.getFPS()));
			}
			
			@Override
			public void draw() {
				
			}

			@Override
			public void exit() {
				
			}
		});
		
		 game.run("TimeWars", 1280, 720);
	}
}