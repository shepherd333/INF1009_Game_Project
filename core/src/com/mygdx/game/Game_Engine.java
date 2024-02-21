package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.Lifecycle.HighScoreManager;
import com.mygdx.game.Scenes.SceneManager;
import com.mygdx.game.Scenes.PauseMenu;
import com.badlogic.gdx.audio.Music;


public class Game_Engine extends ApplicationAdapter {
	SceneManager sm;
	private Music backgroundMusic;
	public static boolean isMusicMuted = false;
	private HighScoreManager highScoreManager;




	@Override
	public void create() { // Called when the Application is first created
		sm = new SceneManager();
		sm.initializeScenes();
		sm.setCurrentScene("MainMenu");
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgmusic2.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
		highScoreManager = new HighScoreManager();
		highScoreManager.create();

	}

	@Override
	public void render() {


		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			// The condition here assumes isCurrentSceneGamePlay() returns true if GamePlay scene is active
			// For unpausing, this check is not strictly necessary unless you want to restrict it further
			if (sm.getCurrentScene() instanceof PauseMenu || sm.isCurrentSceneGamePlay()) {
				sm.togglePause();
			}
		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
//			toggleMusic();
//		}
		// Only update and handle input if the game is not paused
		if (sm != null && !sm.isPaused()) {
			sm.getCurrentScene().handleInput();
			sm.update(Gdx.graphics.getDeltaTime()); // Update the scene manager
		}

		// Always render the current scene
		if (sm != null) {
			sm.render(); // Render the scene manager and thus the current scene
		}

		// Handle scene transitions based on key presses, but ensure they're not processed when paused
		if (!sm.isPaused()) {
			if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
				sm.transitionTo("GamePlay", 1);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.X)) {
				sm.transitionTo("Leaderboard", 1);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.C)) {
				sm.transitionTo("EndMenu", 1);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.V)) {
				sm.transitionTo("MainMenu", 1);
			}
		}
		if (isMusicMuted) {
			backgroundMusic.setVolume(0); // Mute the music
		} else {
			backgroundMusic.setVolume(1); // Set volume back to normal
		}

		highScoreManager.render();
	}
	public void toggleMusic() {
		isMusicMuted = !isMusicMuted;
		if (isMusicMuted) {
			backgroundMusic.pause();
		} else {
			backgroundMusic.play();
		}
	}

	public boolean isMusicMuted() {
		return isMusicMuted;
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (sm != null) {
			sm.resize(width, height); // Ensure SceneManager handles resize
		}
	}


	@Override
	public void dispose() { // Called when the Application is destroyed. get rid of textures created
		if (sm != null) {
			sm.dispose(); // Dispose resources managed by the scene manager
		}
		if (sm != null) {
			sm.dispose();
		}
		if (backgroundMusic != null) {
			backgroundMusic.dispose();
		}

		if (highScoreManager != null) {
			highScoreManager.dispose();
		}
	}
}


