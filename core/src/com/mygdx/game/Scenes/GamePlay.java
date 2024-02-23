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
import com.mygdx.game.InputManagement.InputManager;
import com.mygdx.game.Lifecycle.HighScore.HighScoreManager;
import com.mygdx.game.Lifecycle.HighScore.ScoreFileHandler;
import com.mygdx.game.Lifecycle.HighScore.ScoreFormatter;
import com.mygdx.game.Lifecycle.HighScore.ScoreRenderer;
import com.mygdx.game.Lifecycle.LifeManager;

import java.util.HashMap;
import java.util.Map;

public class GamePlay extends Scene {
    private ShapeRenderer shapeRenderer;
    private EntityManager entityManager;
    private HighScoreManager highScoreManager;
    private ScoreFileHandler scoreFileHandler;
    private ScoreFormatter scoreFormatter;
    private ScoreRenderer scoreRenderer;
    private LifeManager lifeManager;
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
        lifeManager = LifeManager.getInstance();
        lifeManager.gamecheckStart();
        shapeRenderer = new ShapeRenderer();
        entityManager = new EntityManager();

        highScoreManager = HighScoreManager.getInstance();
        scoreFileHandler = new ScoreFileHandler();
        scoreFormatter = new ScoreFormatter();
        scoreRenderer = new ScoreRenderer();

        highScoreManager.create();
        highScoreManager.getHighScores().addAll(scoreFileHandler.loadScores());
        highScoreManager.resetCurrentScore();


        lifeManager.initializeSceneManager(sceneManager);
        collisionManager = new CollisionManager(entityManager.getEntities());

        Texture bucketTexture = new Texture(Gdx.files.internal("Fairy.png"));
        float bucketX = Gdx.graphics.getWidth() / 2f - bucketTexture.getWidth() / 20f; // Centered horizontally
        float bucketY = 0; // At the bottom of the screen
        BucketEntity bucket = new BucketEntity(bucketTexture, bucketX, bucketY, 200, batch,viewport);
        bucket.setWidth(bucketTexture.getWidth() / 10);
        bucket.setHeight(bucketTexture.getHeight() / 10);
        entityManager.addEntity(bucket);

        for (int i = 0; i < 2; i++) {
            float bucketWidth = bucket.getWidth();
            float minDropX = bucketX - bucketWidth;
            float maxDropX = bucketX + bucketWidth;

            float randomX;
            do {
                randomX = MathUtils.random(0, Gdx.graphics.getWidth());
            } while (randomX >= minDropX && randomX <= maxDropX);

            float randomY = MathUtils.random(Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 2);
            double dropSpeed = MathUtils.random(1, 5);
            Texture dropTexture = new Texture(Gdx.files.internal("dust.png"));
            RaindropEntity drop = new RaindropEntity(dropTexture, randomX, randomY, dropSpeed, batch, bucketX, bucket.getWidth());
            drop.setWidth(dropTexture.getWidth() / 5);
            drop.setHeight(dropTexture.getHeight() / 5);
            drop.setActive(true);
            entityManager.addEntity(drop);
        }
    }

    @Override
    public void update(float deltaTime) {
        entityManager.updateEntities();
        collisionManager.handleCollisions();
    }

    @Override
    public void render() {
        super.render();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        entityManager.draw(batch, null);

        String scoreDisplay = "Current Score: " + scoreFormatter.formatScore(highScoreManager.getCurrentScore());
        GlyphLayout scoreLayout = new GlyphLayout(font, scoreDisplay);
        float scoreX = viewport.getWorldWidth() - scoreLayout.width - 20;
        float scoreY = viewport.getWorldHeight() - scoreLayout.height - 20;
        font.draw(batch, scoreDisplay, scoreX, scoreY);

        String lifeDisplay = "LIVES: " + lifeManager.getLives();
        GlyphLayout lifeLayout = new GlyphLayout(font, lifeDisplay);
        float lifeX = viewport.getWorldWidth() - lifeLayout.width - 400;
        float lifeY = viewport.getWorldHeight() - lifeLayout.height - 10;
        font.draw(batch, lifeDisplay, lifeX, lifeY);

        String highScoreDisplay = "High Score: " + scoreFormatter.formatScore(highScoreManager.getHighestScore());
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
    }

    @Override
    public void onShow() {
        highScoreManager.resetCurrentScore();
    }

    @Override
    public void dispose() {
        if (!isDisposed) {
            super.dispose();
            Gdx.app.log("GamePlay", "Disposing ShapeRenderer in GamePlay");
            shapeRenderer.dispose();
            highScoreManager.addScore(highScoreManager.getCurrentScore());
            scoreFileHandler.saveScores(highScoreManager.getHighScores());
            isDisposed = true;
        } else {
            Gdx.app.log("GamePlay", "ShapeRenderer in GamePlay already disposed");
        }
    }
}