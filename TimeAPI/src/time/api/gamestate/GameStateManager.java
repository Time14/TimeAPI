package time.api.gamestate;

import time.api.library.Library;

public class GameStateManager {
	
	private Library<GameState> libraries;
	
	public GameStateManager() {
		libraries = new Library<>("GameStateLibrary");
	}
}