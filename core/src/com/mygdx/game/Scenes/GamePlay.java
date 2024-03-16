package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
//import com.mygdx.game.CollisionManagement.CollisionManager;
import com.mygdx.game.CollisionManagement.CollisionManager;
import com.mygdx.game.EntityManagement.BucketEntity;
import com.mygdx.game.EntityManagement.EntityManager;
import com.mygdx.game.EntityManagement.RaindropEntity;
import com.mygdx.game.InputManagement.InputManager;
//import com.mygdx.game.Lifecycle.HighScore.HighScoreManager;
//import com.mygdx.game.Lifecycle.HighScore.ScoreFileHandler;
//import com.mygdx.game.Lifecycle.HighScore.ScoreFormatter;
//import com.mygdx.game.Lifecycle.HighScore.ScoreRenderer;
//import com.mygdx.game.Lifecycle.LifeManager;

public class GamePlay extends Scene {
    private ShapeRenderer shapeRenderer;
    private EntityManager entityManager;
//    private HighScoreManager highScoreManager;
//    private ScoreFileHandler scoreFileHandler;
//    private ScoreFormatter scoreFormatter;
//    private ScoreRenderer scoreRenderer;
//    private LifeManager lifeManager;
    private CollisionManager collisionManager;
    private boolean isDisposed = false;
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture bg;
    private Sprite bgSprite;
    private SceneManager sceneManager;
    private BucketEntity bucket;
    private Texture bucketTexture;
    private Texture raindropTexture; // Assume you have a texture for raindrops.
    private float spawnTimer = 0;
    private InputManager inputManager;
    private Array<RaindropEntity> raindrops = new Array<>();





    public GamePlay(SceneManager sceneManager) {
        super(sceneManager);
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        inputManager = new InputManager(stage);
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));

        bg = new Texture(Gdx.files.internal("GamePlay.png"));
        bgSprite = new Sprite(bg);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        int buttonWidth = 100;
        int buttonHeight = 25;
        int buttonSpacing = 10;
        int rightMargin = 10;
        int topMargin = 10;
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        int totalHeight = (buttonHeight + buttonSpacing) * 5;
        int totalWidth = (buttonWidth + buttonSpacing) * 5;

        int verticalOffset = (screenHeight - totalHeight) / 2;
        int horizontolOffset = (screenWidth - totalWidth) /2;
        TextButton pausebtn = new TextButton("Pause", skin);
        pausebtn.setSize(buttonWidth, buttonHeight);
        pausebtn.setPosition(screenWidth - buttonWidth - rightMargin, screenHeight - buttonHeight - topMargin);
        pausebtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the PlayScene
                sceneManager.pushScene(new PauseMenu(sceneManager));
            }
        });
        stage.addActor(pausebtn);

        TextButton homebtn = new TextButton("Home", skin);
        homebtn.setSize(buttonWidth, buttonHeight);
        homebtn.setPosition(screenWidth - buttonWidth - rightMargin, screenHeight - 2*buttonHeight - topMargin - buttonSpacing);
        homebtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Transition to the PlayScene
                sceneManager.pushScene(new MainMenu(sceneManager));
            }
        });
        stage.addActor(homebtn);


        Texture bucketTexture = new Texture(Gdx.files.internal("Fairy.png")); // Ensure the path is correct
        bucket = new BucketEntity(bucketTexture, 100, 100, 200); // Example parameters
        Gdx.app.log("GamePlay", "Bucket initialized at x=" + bucket.getX() + ", y=" + bucket.getY());
        stage.addActor(bucket);
        bucket.debug(); // Call this on your actor
        stage.setDebugAll(true); // Or call this on your stage to debug all actors


        // Load the raindrop texture.
        raindropTexture = new Texture(Gdx.files.internal("dust.png")); // Replace with your raindrop texture path.
        Array<RaindropEntity> raindrops = new Array<RaindropEntity>();
        collisionManager = new CollisionManager(bucket, raindrops);
        shapeRenderer = new ShapeRenderer();

    }


    @Override
    public void initialize() {
    }

    private void spawnRaindrop() {
        RaindropEntity raindrop = new RaindropEntity(raindropTexture, 100, 0, 0); // Example values
        raindrops.add(raindrop);
        stage.addActor(raindrop);
        raindrop.resetPosition(raindrop.bucketX, raindrop.bucketWidth); // Now you can call it directly
    }




    @Override
    public void update(float deltaTime) {

        // Handle raindrop spawning.
        spawnTimer += deltaTime;
        if (spawnTimer >= 3) { // Spawn a new raindrop every second as an example.
            spawnRaindrop();
            spawnTimer = 0;
        }

        // Update the stage, which automatically updates all actors added to it.
        stage.act(deltaTime);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // At the beginning of the render method in GamePlay
        Gdx.input.setInputProcessor(stage);
        // Update logic here
        stage.act(Gdx.graphics.getDeltaTime());

        // Clear the screen or draw the background first if needed.
        batch.begin();
        bgSprite.draw(batch);
        batch.end();
        collisionManager.checkCollisions(); // Check for collisions after updating positions




        // Draw the stage, which will draw the bucket and all raindrops.
        stage.draw();
        update(Gdx.graphics.getDeltaTime());

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

// Draw bounding box for the bucket
        Rectangle bucketBounds = bucket.getBounds();
        shapeRenderer.rect(bucketBounds.x, bucketBounds.y, bucketBounds.width, bucketBounds.height);

// Draw bounding boxes for raindrops
        for (RaindropEntity raindrop : raindrops) {
            Rectangle dropBounds = raindrop.getBounds();
            shapeRenderer.rect(dropBounds.x, dropBounds.y, dropBounds.width, dropBounds.height);
        }
        shapeRenderer.end();

        // Handle input
        inputManager.handleInput(Gdx.graphics.getDeltaTime());


    }
    @Override
    public void resize(int width, int height) {
        // Update the stage's viewport when the screen size changes
        stage.getViewport().update(width, height, true);
    }
    @Override
    public void dispose() {
        super.dispose();
        // Dispose resources.
        if (bucketTexture != null) bucketTexture.dispose();
        if (raindropTexture != null) raindropTexture.dispose();
        // Ensure you dispose of any other disposable resources you've used.
    }
}
