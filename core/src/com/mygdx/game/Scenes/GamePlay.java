package com.mygdx.game.Scenes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Input;
import com.mygdx.game.AIManagement.AIManager;
import com.mygdx.game.EntityManagement.BucketEntity;
import com.mygdx.game.EntityManagement.Entity;
import com.mygdx.game.EntityManagement.EntityManager;
import com.mygdx.game.Game_Engine;
import com.mygdx.game.InputManagement.InputManager;
import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleShader.Setters.screenWidth;

public class GamePlay implements SceneInterface {
    private SpriteBatch batch;
    private Texture img;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SceneManager sceneManager;
    private BitmapFont font;
    // Example instantiation within a game scene
    Texture raindropTexture = new Texture("assets/droplet.png");
    float raindropSpeed = 200;
    private EntityManager entityManager;
    AIManager aiManager = new AIManager(entityManager, 800);

    private float spawnTimer = 0;
    private final float spawnInterval = 1.0f;
    private InputManager inputManager;

    public void initialize() {
        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("GamePlay.png"));
        camera = new OrthographicCamera();
        viewport = new StretchViewport(800, 600, camera); // Use your desired world size
        camera.position.set(400, 300, 0); // Adjust according to your viewport's world width/height
        font = new BitmapFont();
        // Initialize the list of entities
        List<Entity> entities = new ArrayList<>();

        // Create a BucketEntity
        BucketEntity bucket = new BucketEntity(new Texture("bucket.png"), 100, 100, 5);

        // Add the BucketEntity to the list
        entities.add(bucket);

        // Initialize the EntityManager with the list of entities
        entityManager = new EntityManager(entities);

        // Initialize the InputManager with the bucket
        inputManager = new InputManager(bucket);

        // Now, the AIManager can be initialized with the properly initialized EntityManager
        aiManager = new AIManager(entityManager, 800);

    }
    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    @Override
    public void update(float deltaTime) {
        inputManager.handleInput();
        spawnTimer += deltaTime;
        if (spawnTimer >= spawnInterval) {
            aiManager.spawnRaindrop(raindropTexture);
            spawnTimer = 0;
        }
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


        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
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
    }
}
