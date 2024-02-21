package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.EntityManagement.*;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.EntityManagement.StartButton;

import java.util.ArrayList;
import java.util.List;

public class Game_Engine extends ApplicationAdapter {
	private SpriteBatch batch;
	private List<Entity> entityList;
	private BucketEntity bucket;
	private RaindropEntity droplets;
	private EntityManager entityManager;
	private StartButton startButton;
	private PlayButton playButton;
	private MuteButton muteButton;
	private PauseButton pauseButton;
	private ContinueButton continueButton;
	private LeaderboardButton leaderboardButton;

	@Override
	public void create() {
		batch = new SpriteBatch();
		entityList = new ArrayList<>();
		entityManager = new EntityManager(entityList);

		// Initialize and add droplets
		droplets = new RaindropEntity(new Texture("droplet.png"), 100, 100, 0);
		entityList.add(droplets);

		// Initialize and add bucket
		bucket = new BucketEntity(new Texture("bucket.png"), 300, 100, 0);
		entityList.add(bucket);

		// Initialize and add start button
		startButton = new StartButton(new Texture("start-button.png"), 200, 0);
		entityList.add(startButton);

		playButton = new PlayButton(new Texture("play-button.png"), 300,200);
		entityList.add(playButton);

		muteButton = new MuteButton(new Texture("mute-button.png"), 400, 300);
		entityList.add(muteButton);

		pauseButton = new PauseButton(new Texture("pause-button.png"), 500, 100);
		entityList.add(pauseButton);

		continueButton = new ContinueButton(new Texture("continue-button.png"), 150, 250);
		entityList.add(continueButton);

		leaderboardButton = new LeaderboardButton(new Texture("leaderboard-button.png"), 200,200);
		entityList.add(leaderboardButton);


	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		for (Entity entity : entityList) {
			entity.update();
			batch.draw(entity.getTexture(), entity.getX(), entity.getY()); // Draw entity texture
		}
		batch.end();

		entityManager.updateEntities();
	}

	@Override
	public void dispose() {
		batch.dispose();
		batch.dispose();
		droplets.getTexture().dispose();
		bucket.getTexture().dispose();
		startButton.getTexture().dispose();
		playButton.getTexture().dispose();
		muteButton.getTexture().dispose();
		pauseButton.getTexture().dispose();
		continueButton.getTexture().dispose();
		leaderboardButton.getTexture().dispose();
	}
}