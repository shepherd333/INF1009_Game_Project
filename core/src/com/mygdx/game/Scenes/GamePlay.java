// Package declaration.
package com.mygdx.game.Scenes;

// Import statements to include external classes and libraries.
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

// Definition of the GamePlay class that extends Scene.
public class GamePlay extends Scene {
    // Declaration of class fields.
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

    // Constructor for the GamePlay class.
    public GamePlay(SceneManager sceneManager) {
        super(sceneManager, "GamePlay.png", "This is the GamePlay Scene.");
        // Initialize the viewport to a specific size and the camera's position.
        viewport = new StretchViewport(800, 600, camera);
        camera.position.set(400, 300, 0);
        initialize(); // Call to the initialize method.
    }

    // Method to initialize various components of the game.
    public void initialize() {
        // Initialization of game life, score, entities, and collision management.
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
        highScoreManager.minusToCurrentScore(20);

        lifeManager.initializeSceneManager(sceneManager);
        collisionManager = new CollisionManager(entityManager.getEntities());

        // Creating and positioning the bucket entity.
        Texture bucketTexture = new Texture(Gdx.files.internal("fairy.png"));
        float bucketX = Gdx.graphics.getWidth() / 2f - bucketTexture.getWidth() / 20f; // Centered horizontally
        float bucketY = 0; // At the bottom of the screen
        BucketEntity bucket = new BucketEntity(bucketTexture, bucketX, bucketY, 200, batch,viewport);
        bucket.setWidth(bucketTexture.getWidth());
        bucket.setHeight(bucketTexture.getHeight());
        entityManager.addEntity(bucket);

        // Creating and positioning raindrop entities.
        for (int i = 0; i < 2; i++) {
            float bucketWidth = bucket.getWidth();
            float minDropX = bucketX - bucketWidth;
            float maxDropX = bucketX + bucketWidth;

            float randomX;
            do {
                randomX = MathUtils.random(0, Gdx.graphics.getWidth());
            } while (randomX >= minDropX && randomX <= maxDropX);

            float randomY = Gdx.graphics.getHeight() * 2;
            double dropSpeed = MathUtils.random(1, 6);
            Texture dropTexture = new Texture(Gdx.files.internal("dust.png"));
            RaindropEntity drop = new RaindropEntity(dropTexture, randomX, randomY, dropSpeed, batch, bucketX, bucket.getWidth());
            drop.setWidth(dropTexture.getWidth());
            drop.setHeight(dropTexture.getHeight());
            drop.setActive(true);
            entityManager.addEntity(drop);
        }
    }

    // Method to update game logic.
    @Override
    public void update(float deltaTime) {
        entityManager.updateEntities();
        collisionManager.handleCollisions();
    }

    // Method for rendering the game's graphics.
    @Override
    public void render() {
        super.render(); // Call to the render method of the superclass.
        camera.update(); // Update the camera.
        batch.setProjectionMatrix(camera.combined); // Apply the camera's projection matrix.

        batch.begin(); // Begin sprite batch.
        // Drawing entities and displaying score and lives.
        entityManager.draw(batch, null);

        // Display current score.
        String scoreDisplay = "Current Score: " + scoreFormatter.formatScore(highScoreManager.getCurrentScore());
        GlyphLayout scoreLayout = new GlyphLayout(font, scoreDisplay);
        float scoreX = viewport.getWorldWidth() - scoreLayout.width - 20;
        float scoreY = viewport.getWorldHeight() - scoreLayout.height - 20;
        font.draw(batch, scoreDisplay, scoreX, scoreY);

        // Display remaining lives.
        String lifeDisplay = "LIVES: " + lifeManager.getLives();
        GlyphLayout lifeLayout = new GlyphLayout(font, lifeDisplay);
        float lifeX = viewport.getWorldWidth() - lifeLayout.width - 400;
        float lifeY = viewport.getWorldHeight() - lifeLayout.height - 10;
        font.draw(batch, lifeDisplay, lifeX, lifeY);

        // Display high score.
        String highScoreDisplay = "High Score: " + scoreFormatter.formatScore(highScoreManager.getHighestScore());
        GlyphLayout highScoreLayout = new GlyphLayout(font, highScoreDisplay);
        float highScoreX = viewport.getWorldWidth() - highScoreLayout.width - 20;
        float highScoreY = scoreY - highScoreLayout.height - 10;
        font.draw(batch, highScoreDisplay, highScoreX, highScoreY);

        batch.end(); // End sprite batch.

        // Begin shape rendering for additional graphics (if any).
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        entityManager.draw(null, shapeRenderer);
        shapeRenderer.end();

        entityManager.moveEntities(); // Update entity positions.
    }

    @Override
    public void handleInput() {
        // Method stub for handling input. Implementation can be added as needed.
    }

    @Override
    public void onShow() {
        // Reset the current score when the game scene is shown.
        highScoreManager.resetCurrentScore();
    }

    // Method to dispose of resources when they are no longer needed.
    @Override
    public void dispose() {
        if (!isDisposed) {
            super.dispose(); // Call dispose on the superclass.
            Gdx.app.log("GamePlay", "Disposing ShapeRenderer in GamePlay");
            shapeRenderer.dispose(); // Dispose of the ShapeRenderer.
            isDisposed = true; // Set the disposed flag to prevent double-disposing.
        }
    }
}
