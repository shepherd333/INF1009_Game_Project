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
import com.mygdx.game.EntityManagement.*;
import com.mygdx.game.Game_Engine;
import com.mygdx.game.Lifecycle.HighScoreManager;

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
    private float scoreDisplayX;
    private float scoreDisplayY;

    public void initialize() {
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




        Texture bucketTexture = new Texture(Gdx.files.internal("bucket.png"));
        BucketEntity bucket = new BucketEntity(bucketTexture, 0, 0, 200, batch);
        entityManager.addEntity(bucket);

        for (int i = 0; i < 5; i++) {
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
        font.draw(batch,"Press P to toggle the Pause Menu on/off.", 1, 400);
        font.draw(batch,"Press X to transit to LeaderBoard Scene.", 1, 350);
        font.draw(batch,"Press C to transit to EndMenu Scene.", 1, 300);
        font.draw(batch,"Press V to transit to MainMenu Scene.", 1, 250);
        font.draw(batch,"Press M to mute/unmute the audio.", 1, 200);
        entityManager.draw(batch, null);
        //highScoreManager.rendercurrent();
        String scoreDisplay = "Current Score: " + highScoreManager.getInstance().getCurrentScoreFormatted();
        Gdx.app.log("GamePlay", "Rendering score: " + scoreDisplay);
        GlyphLayout scoreLayout = new GlyphLayout(font, scoreDisplay);
        float scoreX = viewport.getWorldWidth() - scoreLayout.width - 20;
        float scoreY = viewport.getWorldHeight() - scoreLayout.height - 25;
        font.draw(batch, scoreDisplay, scoreX, scoreY);
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