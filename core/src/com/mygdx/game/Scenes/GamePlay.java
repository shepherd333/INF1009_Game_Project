package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input;
import com.mygdx.game.CollisionManagement.CollisionManager;
import com.mygdx.game.EntityManagement.*;
import com.mygdx.game.InputManagement.InputManager;
import com.mygdx.game.Lifecycle.HighScoreManager;
import com.mygdx.game.Lifecycle.LifeManager;

import java.util.HashMap;
import java.util.Map;

// The GamePlay class extends Scene to represent the main gameplay scene of the game.
public class GamePlay extends Scene {
    private ShapeRenderer shapeRenderer; // Used for drawing shapes, such as debug outlines.
    private EntityManager entityManager; // Manages entities (like players or enemies) within the game.
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();  // Singleton for managing high scores.
    public LifeManager lifeManager = LifeManager.getInstance(); // Singleton for managing player lives.
    private SceneManager sm; // Manages switching between different scenes
    private SceneInterface currentScene; // The current active scene.
    private Map<String, SceneInterface> scenes = new HashMap<>(); // Manages switching between different scenes
    private CollisionManager collisionManager; // Handles collision detection and response.
    private boolean isDisposed = false; // Tracks whether resources have been disposed of
    private InputManager inputManager; // Manages input from the player.
    private Sprite BackgroundSprite;
    private Stage stage;
    // Constructor initializes the gameplay scene with a specific viewport and camera setup.
    public GamePlay(SceneManager sceneManager) {
        super(sceneManager, "GamePlay.png", "This is the GamePlay Scene.");
        viewport = new StretchViewport(800, 600, camera);
        camera.position.set(400, 300, 0);
        initialize();
    }

    // Initialize method sets up necessary game components and entities.
    public void initialize() {
        lifeManager.getInstance().gamecheckStart();
        shapeRenderer = new ShapeRenderer();
        entityManager = new EntityManager();
        highScoreManager.create();
        highScoreManager.resetCurrentScore();
        lifeManager.initializeSceneManager(sceneManager);
        collisionManager = new CollisionManager(entityManager.getEntities());
        Texture bucketTexture = new Texture(Gdx.files.internal("Fairy.png"));
        BucketEntity bucket = new BucketEntity(bucketTexture, 0, 0, 200, batch, viewport);
        bucket.setWidth(bucketTexture.getWidth()/10);
        bucket.setHeight(bucketTexture.getHeight()/10);
        entityManager.addEntity(bucket);

        // Creates initial raindrop entities with random positions and adds them to the entityManager.
        for (int i = 0; i < 2; i++) {
            float randomX = MathUtils.random(0, Gdx.graphics.getWidth());
            float randomY = MathUtils.random(0, Gdx.graphics.getHeight());
            double dropSpeed = MathUtils.random(1,5);
            Texture dropTexture = new Texture(Gdx.files.internal("dust.png"));
            float bucketX = bucket.getX();
            float bucketWidth = bucket.getWidth();
            RaindropEntity drop = new RaindropEntity(dropTexture, randomX, randomY, dropSpeed, batch, bucketX, bucketWidth);
            drop.setWidth(dropTexture.getWidth()/5);
            drop.setHeight(dropTexture.getHeight()/5);
            drop.setActive(true);
            entityManager.addEntity(drop);
        }
    }

    // Update method called every frame to update game logic.
    @Override
    public void update(float deltaTime) {
        // Update entities
        entityManager.updateEntities();
        // Handle collisions
        collisionManager.handleCollisions();
    }

    // Render method called every frame to draw the scene.
    @Override
    public void render() {
        super.render(); // Calls render method from the Scene superclass.
        camera.update(); // Ensure camera updates are happening.
        batch.setProjectionMatrix(camera.combined); // Sets the SpriteBatch's projection matrix to the camera's combined matrix.


        batch.begin();
        entityManager.draw(batch, null); // Draws all entities managed by the entityManager.

        // Drawing UI elements like the current score and player lives.
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
        entityManager.draw(null, shapeRenderer); // Optionally draws entities with the shapeRenderer for debug.
        shapeRenderer.end();

        entityManager.moveEntities(); // Moves entities based on their velocity and game logic.
    }
    @Override
    public void handleInput() {
        // Add more input handling as needed
    }

    @Override
    public void dispose() {
        // Disposes of resources when they are no longer needed or when the game is closing.
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