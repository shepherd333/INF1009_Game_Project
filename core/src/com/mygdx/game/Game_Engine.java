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
import com.mygdx.game.Lifecycle.HighScore.HighScoreManager;
import com.mygdx.game.Lifecycle.HighScore.ScoreFileHandler;

public class Game_Engine extends ApplicationAdapter {
	SceneManager sm;
	private Music backgroundMusic;
	public static boolean isMusicMuted = false;
	private HighScoreManager highScoreManager;
	private InputManager inputManager;
	public LifeManager lifeManager;
	private ScoreFileHandler scoreFileHandler;

	@Override
	public void create() {
		sm = new SceneManager();
		sm.initializeScenes();
		sm.setCurrentScene("MainMenu");

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bgmusic2.mp3"));
		backgroundMusic.setLooping(true);
		backgroundMusic.play();

		inputManager = new InputManager(sm);
		this.lifeManager = new LifeManager(-1);

		scoreFileHandler = new ScoreFileHandler();
		highScoreManager = HighScoreManager.getInstance();
		highScoreManager.create();
		highScoreManager.getHighScores().addAll(scoreFileHandler.loadScores());
	}

	@Override
	public void render() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			if (sm.getCurrentScene() instanceof PauseMenu || sm.isCurrentSceneGamePlay()) {
				sm.togglePause();
			}
		}

		if (sm != null && !sm.isPaused()) {
			sm.getCurrentScene().handleInput();
			sm.update(Gdx.graphics.getDeltaTime());

			if (sm.getCurrentSceneName() == "MainMenu"){
				inputManager.handleMMInput();
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

		if (sm != null) {
			sm.render();
		}

		if (lifeManager.getInstance().getLives() == 0){
			highScoreManager.addScore(highScoreManager.getCurrentScore());
			scoreFileHandler.saveScores(highScoreManager.getHighScores());
			lifeManager.getInstance().endLife();
			sm.transitionTo("EndMenu", 1);
		}

		if (isMusicMuted) {
			backgroundMusic.setVolume(0);
		} else {
			backgroundMusic.setVolume(0.05F);
		}
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
			sm.resize(width, height);
		}
	}

	@Override
	public void dispose() {
		if (sm != null) {
			sm.dispose();
		}
		if (backgroundMusic != null) {
			backgroundMusic.dispose();
		}
		if (highScoreManager != null) {
			highScoreManager.addScore(highScoreManager.getCurrentScore());
			scoreFileHandler.saveScores(highScoreManager.getHighScores());
			highScoreManager.dispose();
		}
	}
}