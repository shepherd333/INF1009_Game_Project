package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.mygdx.game.Lifecycle.HighScoreManager;
import com.mygdx.game.Lifecycle.LifeManager;

import java.util.HashMap;
import java.util.Map;

public class GamePlay implements SceneInterface {
    private SpriteBatch batch;
    private Texture img;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SceneManager sceneManager;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;
    private EntityManager entityManager;
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();
    public LifeManager lifeManager = LifeManager.getInstance();
    private SceneManager sm;
    private SceneInterface currentScene;
    private Map<String, SceneInterface> scenes = new HashMap<>();
    private float scoreDisplayX;
    private float scoreDisplayY;
    private CollisionManager collisionManager;



    public void initialize() {
        lifeManager.getInstance().addLife();
        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("GamePlay.png"));
        camera = new OrthographicCamera();
        viewport = new StretchViewport(800, 600, camera);
        camera.position.set(400, 300, 0);
        font = new BitmapFont();
        batch = new SpriteBatch();
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
            //System.out.println("Raindrop created. Active: " + drop.isActive());
            entityManager.addEntity(drop);
        }
    }

    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    @Override
    public void update(float deltaTime) {
        // Update entities
        entityManager.updateEntities();
        // Handle collisions
        collisionManager.handleCollisions();
    }


    public void setHighScoreManager(HighScoreManager highScoreManager) {
        this.highScoreManager = highScoreManager;
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(img, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        font.draw(batch,"This is the GamePlay Scene.", 1, 450);
        entityManager.draw(batch, null);
        //highScoreManager.rendercurrent();
        String scoreDisplay = "Current Score: " + highScoreManager.getInstance().getCurrentScoreFormatted();
        Gdx.app.log("GamePlay", "Rendering score: " + scoreDisplay);
        GlyphLayout scoreLayout = new GlyphLayout(font, scoreDisplay);
        float scoreX = viewport.getWorldWidth() - scoreLayout.width - 20;
        float scoreY = viewport.getWorldHeight() - scoreLayout.height - 25;
        font.draw(batch, scoreDisplay, scoreX, scoreY);

        GlyphLayout layout = new GlyphLayout(); // Consider making this a field to avoid re-allocating each frame
        float width = layout.width; // Use this for calculating xPosition
        float xPosition = viewport.getWorldWidth() - width - 400;
        float yPosition = viewport.getWorldHeight() - layout.height - 10;

        String lifeDisplay = "LIVES: " + lifeManager.getInstance().getLives();
        layout.setText(font, lifeDisplay);
        font.draw(batch, lifeDisplay, xPosition, yPosition);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        entityManager.draw(null, shapeRenderer);
        shapeRenderer.end();

        entityManager.moveEntities();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);
        camera.update();

    }



    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Game_Engine.isMusicMuted = !Game_Engine.isMusicMuted;
        }
    }

    @Override
    public void dispose() {
        img.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}