package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input;
import com.mygdx.game.CollisionManagement.CollisionManager;
import com.mygdx.game.EntityManagement.*;
import com.mygdx.game.Game_Engine;
import com.mygdx.game.InputManagement.InputManager;
import com.mygdx.game.Lifecycle.HighScoreManager;
import com.mygdx.game.Lifecycle.LifeManager;

import java.util.HashMap;
import java.util.Map;

public class GamePlay extends Scene {
    private ShapeRenderer shapeRenderer;
    private EntityManager entityManager;
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();
    public LifeManager lifeManager = LifeManager.getInstance();
    private SceneManager sm;
    private SceneInterface currentScene;
    private Map<String, SceneInterface> scenes = new HashMap<>();
    private CollisionManager collisionManager;
    private boolean isDisposed = false;
    private InputManager inputManager;

    public GamePlay(SceneManager sceneManager) {
        super(sceneManager, "GamePlay.png", "This is the GamePlay Scene.");
        viewport = new StretchViewport(800, 600, camera);
        camera.position.set(400, 300, 0);
        initialize();
    }

    public void initialize() {
        lifeManager.getInstance().gamecheckStart();
        shapeRenderer = new ShapeRenderer();
        entityManager = new EntityManager();
        highScoreManager.create();
        highScoreManager.resetCurrentScore();
        lifeManager.initializeSceneManager(sceneManager);
        collisionManager = new CollisionManager(entityManager.getEntities());
        Texture bucketTexture = new Texture(Gdx.files.internal("bucket.png"));
        BucketEntity bucket = new BucketEntity(bucketTexture, 0, 0, 200, batch);
        entityManager.addEntity(bucket);

        for (int i = 0; i < 2; i++) {
            float randomX = MathUtils.random(0, Gdx.graphics.getWidth());
            float randomY = MathUtils.random(0, Gdx.graphics.getHeight());
            double dropSpeed = MathUtils.random(1,5);
            Texture dropTexture = new Texture(Gdx.files.internal("droplet.png"));
            float bucketX = bucket.getX();
            float bucketWidth = bucket.getWidth();
            RaindropEntity drop = new RaindropEntity(dropTexture, randomX, randomY, dropSpeed, batch, bucketX, bucketWidth);
            drop.setWidth(dropTexture.getWidth());
            drop.setHeight(dropTexture.getHeight());
            drop.setActive(true);
            entityManager.addEntity(drop);
        }
    }

    @Override
    public void update(float deltaTime) {
        // Update entities
        entityManager.updateEntities();
        // Handle collisions
        collisionManager.handleCollisions();
    }

    @Override
    public void render() {
        super.render();
        // Ensure camera updates are happening.
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        batch.begin();
        entityManager.draw(batch, null);

        String scoreDisplay = "Current Score: " + highScoreManager.getInstance().getCurrentScoreFormatted();
        GlyphLayout scoreLayout = new GlyphLayout(font, scoreDisplay);
        float scoreX = viewport.getWorldWidth() - scoreLayout.width - 20;
        float scoreY = viewport.getWorldHeight() - scoreLayout.height - 20;
        font.draw(batch, scoreDisplay, scoreX, scoreY);

        String lifeDisplay = "LIVES: " + lifeManager.getInstance().getLives();
        GlyphLayout lifeLayout = new GlyphLayout(font, lifeDisplay);
        float lifeX = viewport.getWorldWidth() - lifeLayout.width - 400;
        float lifeY = viewport.getWorldHeight() - lifeLayout.height - 10;
        font.draw(batch, lifeDisplay, lifeX, lifeY);

        String highScoreDisplay = "High Score: " + highScoreManager.getInstance().getHighestScoreFormatted();
        GlyphLayout highScoreLayout = new GlyphLayout(font, highScoreDisplay);
        float highScoreX = viewport.getWorldWidth() - highScoreLayout.width - 20;
        float highScoreY = scoreY - highScoreLayout.height - 10;
        font.draw(batch, highScoreDisplay, highScoreX, highScoreY);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        entityManager.draw(null, shapeRenderer);
        shapeRenderer.end();

        entityManager.moveEntities();
    }
    @Override
    public void handleInput() {
        // Add more input handling as needed
    }

    @Override
    public void dispose() {
        if (!isDisposed) {
            super.dispose();
            Gdx.app.log("GamePlay", "Disposing ShapeRenderer in GamePlay");
            shapeRenderer.dispose();
            isDisposed = true;
        } else {
            Gdx.app.log("GamePlay", "ShapeRenderer in GamePlay already disposed");
        }
    }
}