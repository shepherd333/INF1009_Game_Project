package GameEngine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.audio.Music;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import com.mygdx.game.GameLayer.Scenes.MainMenu;
import GameEngine.SceneManagement.SceneManager;

// Main class for the game, extending ApplicationAdapter for lifecycle management.
public class Game_Engine extends ApplicationAdapter {
	private SceneManager sm; // Manages scenes within the game, such as menus and gameplay.
	private Music backgroundMusic; // Background music for the game.
	public static boolean isMusicMuted = false; // Flag to check if the music is currently muted.
	private AudioManager Audiomanager; // Handles all audio within the game, including music and sound effects.

	// Called when the game is created. Initial setup should be done here.
	@Override
	public void create() {
		sm = new SceneManager();
		sm.pushScene(new MainMenu(sm)); // Start the game with the Main Menu scene.
		AudioManager.getInstance().playBackgroundMusic("bgmusic2.mp3", true, 0.1f); // Play background music on loop.
	}

	// Called every frame, responsible for rendering the game.
	@Override
	public void render() {
		sm.render(); // Delegate rendering to the SceneManager, which handles the current scene.
	}

	// Toggles the state of the music between play and pause.
	public void toggleMusic() {
		isMusicMuted = !isMusicMuted; // Toggle the muted state.
		if (isMusicMuted) {
			backgroundMusic.pause(); // Pause the music if muted.
		} else {
			backgroundMusic.play(); // Play the music if not muted.
		}
	}

	// Called when the application is resized. This can happen at any point during a non-paused state but will never happen before a call to create().
	@Override
	public void resize(int width, int height) {
		super.resize(width, height); // Call the superclass method which does nothing by default.
		sm.resize(width, height); // Notify the SceneManager of the resize event.
	}

	// Called when the application is destroyed. Use this method to dispose of resources and clean up.
	@Override
	public void dispose() {
		if (sm != null) {
			sm.dispose(); // Dispose of the SceneManager and its resources.
		}
		if (backgroundMusic != null) {
			backgroundMusic.dispose(); // Dispose of the background music.
		}
	}
}
