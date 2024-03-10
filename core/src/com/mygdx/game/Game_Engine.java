package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.Scenes.AudioManager;
import com.mygdx.game.Scenes.MainMenu;
import com.mygdx.game.Scenes.SceneManager;
import com.mygdx.game.Lifecycle.HighScore.HighScoreManager;
import com.mygdx.game.Lifecycle.HighScore.ScoreFileHandler;
import com.mygdx.game.Lifecycle.LifeManager;

public class Game_Engine extends ApplicationAdapter {
	SceneManager sm;
	private Music backgroundMusic;
	public static boolean isMusicMuted = false;
	private HighScoreManager highScoreManager;
	private LifeManager lifeManager;
	private ScoreFileHandler scoreFileHandler;
	private AudioManager Audiomanager;

	@Override
	public void create() {
		sm = new SceneManager();
		sm.pushScene(new MainMenu(sm)); // Initialize the game with the main menu scene.
		AudioManager.getInstance().playBackgroundMusic("bgmusic2.mp3", true, 0.1f);

		lifeManager = new LifeManager(-1);

		scoreFileHandler = new ScoreFileHandler();
		highScoreManager = HighScoreManager.getInstance();
		highScoreManager.create();
		highScoreManager.getHighScores().addAll(scoreFileHandler.loadScores());
	}

	@Override
	public void render() {
		sm.render();

//		if (lifeManager.getLives() == 0) {
//			highScoreManager.addScore(highScoreManager.getCurrentScore());
//			scoreFileHandler.saveScores(highScoreManager.getHighScores());
//			lifeManager.endLife();
//			sm.pushScene(new com.mygdx.game.Scenes.EndMenu(sm)); // Assuming you have an EndMenu scene for game over
//		}
	}

	public void toggleMusic() {
		isMusicMuted = !isMusicMuted;
		if (isMusicMuted) {
			backgroundMusic.pause();
		} else {
			backgroundMusic.play();
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		sm.resize(width, height);
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
