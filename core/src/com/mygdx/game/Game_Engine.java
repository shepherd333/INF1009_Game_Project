package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.mygdx.game.InputManagement.InputManager;
import com.mygdx.game.Lifecycle.LifeManager;
import com.mygdx.game.Scenes.Scene;
import com.mygdx.game.Scenes.SceneInterface;
import com.mygdx.game.Scenes.SceneManager;
import com.mygdx.game.Scenes.PauseMenu;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.Lifecycle.HighScoreManager;


public class Game_Engine extends ApplicationAdapter {
	SceneManager sm; // SceneManager to manage different scenes (like menus, game levels).
	private Music backgroundMusic; // Background music for the game.
	public static boolean isMusicMuted = false;  // Global flag to mute/unmute the game music.
	private HighScoreManager highScoreManager; // High score manager for the game.
	private InputManager inputManager; // Input manager for handling user input across different scenes.
	public LifeManager lifeManager; // Life manager for tracking player lives.

	// Called when the Application is first created.
	@Override
	public void create() { // Called when the Application is first created
		// Initialize the scene manager and set the initial scene to MainMenu.
		sm = new SceneManager();
		sm.initializeScenes();
		sm.setCurrentScene("MainMenu");
		// Setup background music to loop and start playing.
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgmusic2.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
		// Initialize input, life, and high score managers.
		inputManager = new InputManager(sm);
		this.lifeManager = new LifeManager(-1);
		highScoreManager = new HighScoreManager();
		highScoreManager.create();

	}

	// Main game loop method, called continuously to render the game.
	@Override
	public void render() {
		// Handle toggling pause state with the P key.
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			// The condition here assumes isCurrentSceneGamePlay() returns true if GamePlay scene is active
			// For unpausing, this check is not strictly necessary unless you want to restrict it further
			if (sm.getCurrentScene() instanceof PauseMenu || sm.isCurrentSceneGamePlay()) {
				sm.togglePause();
			}
		}


		// Only update and handle input if the game is not paused
		if (sm != null && !sm.isPaused()) {
			sm.getCurrentScene().handleInput();
			sm.update(Gdx.graphics.getDeltaTime()); // Update the scene manager and entities

			if (sm.getCurrentSceneName() == "MainMenu"){
				inputManager.handleMMInput(); // Update the scene manager
			}
			if (sm.getCurrentSceneName() == "Leaderboard"){
				inputManager.handleLBInput();
			}
			if (sm.getCurrentSceneName() == "EndMenu"){
				inputManager.handleEndInput();
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
			isMusicMuted = !isMusicMuted;
		}

		// Always render the current scene
		if (sm != null) {
			sm.render(); // Render the scene manager and thus the current scene
		}

		if (lifeManager.getInstance().getLives() == 0){
			highScoreManager.saveScores();
			lifeManager.getInstance().endLife();
			sm.transitionTo("EndMenu", 1);
		}

		if (isMusicMuted) {
			backgroundMusic.setVolume(0); // Mute the music
		} else {
			backgroundMusic.setVolume(0.05F); // Set volume back to normal
		}

	}
	// Toggle the mute state of the game music.
	public void toggleMusic() {
		isMusicMuted = !isMusicMuted;
		if (isMusicMuted) {
			backgroundMusic.pause();
		} else {
			backgroundMusic.play();
		}
	}

	// Check if the music is currently muted.
	public boolean isMusicMuted() {
		return isMusicMuted;
	}

	// Handle screen resize events, ensuring the current scene adjusts to the new size.
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (sm != null) {
			sm.resize(width, height); // Ensure SceneManager handles resize
		}
	}

	// Dispose of game resources when closing.
	@Override
	public void dispose() { // Called when the Application is destroyed. get rid of textures created
		if (sm != null) {
			sm.dispose(); // Dispose resources managed by the scene manager
		}
		if (backgroundMusic != null) {
			backgroundMusic.dispose();
		}

		if (highScoreManager != null) {
			highScoreManager.dispose();
		}
	}
}




