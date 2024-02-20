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

import java.util.ArrayList;
import java.util.List;

public class Game_Engine extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private List<Entity> entityList;
	private BucketEntity bucket;
	private RaindropEntity droplets[];
	private StartButton startButton;
	private EntityManager entityManager;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		entityList = new ArrayList<>();
		entityManager = new EntityManager(entityList);

		droplets = new RaindropEntity[1];
		for (int i = 0; i < droplets.length; i++) {
			float dropletWidth = 50;
			float randomX = MathUtils.random(dropletWidth / 2, Gdx.graphics.getWidth() - dropletWidth / 2);
			float randomY = MathUtils.random(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 2);
			droplets[i] = new RaindropEntity(new Texture("droplet.png"), randomX, randomY, 2f);
			entityList.add(droplets[i]);
		}

		bucket = new BucketEntity(new Texture("bucket.png"), 200, 100, 0);
		entityList.add(bucket);

//		startButton = new StartButton(new Texture("START.png"), 200, 200);
//		entityList.add(startButton);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		for (Entity entity : entityList) {
			entity.update();
			batch.draw(entity.getTexture(), entity.getX(), entity.getY());
		}
		batch.end();

		entityManager.updateEntities();

//		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
//			bucket.setX(bucket.getX() - 200 * Gdx.graphics.getDeltaTime());
//		}
//		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
//			bucket.setX(bucket.getX() + 200 * Gdx.graphics.getDeltaTime());
//		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		shapeRenderer.dispose();
		for (RaindropEntity droplet : droplets) {
			droplet.getTexture().dispose();
		}
		bucket.getTexture().dispose();
		startButton.getTexture().dispose();
	}
}