package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.CollisionManagement.CollisionManager;
import com.mygdx.game.EntityManagement.Bins.*;
import com.mygdx.game.EntityManagement.Foundation.CollidableActor;
import com.mygdx.game.EntityManagement.Movers.BucketActor;
import com.mygdx.game.EntityManagement.Movers.ItemActor;
import com.mygdx.game.EntityManagement.Movers.TrashMonsterActor;
import com.mygdx.game.EntityManagement.Static.ConveyorBeltActor;
import com.mygdx.game.EntityManagement.Static.ToxicWasteActor;
import com.mygdx.game.InputManagement.InputManager;
import com.mygdx.game.Lifecycle.LevelConfig;
import com.mygdx.game.Lifecycle.ScoreSystem.ScoreManager;
import com.mygdx.game.enums.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.mygdx.game.AIManagement.AIManager;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GamePlay extends BaseScene {
    private ShapeRenderer shapeRenderer;
    private boolean isDisposed = false;
    private Random random = new Random();
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture bg;
    private Sprite bgSprite;
    private BucketActor bucket;
    private Texture bucketTexture;
    private float spawnTimer = 0;
    private InputManager inputManager;
    private BitmapFont font;
    private Array<ItemActor> items = new Array<>();
    private List<CollidableActor> actors = new ArrayList<>();
    private LevelConfig levelConfig;
    private CollisionManager collisionManager;
    private ConveyorBeltActor conveyorBelt;
    private long startTime;
    private TrashMonsterActor trashMonsterActor;
    private AIManager aiManager;
    private ToxicWasteActor toxicWaste;
    private float timer;
    public float getTimer() {
        return timer;
    }
    public void setTimer(int t) {
        timer = t;
    }
    public void increaseTimer(int t) {
        timer += t;
    }
    public void decreaseTimer(int t) {
        timer -= t;
    }

    private void goToLeaderboard() {
        // Assuming sceneManager is a member of GamePlay and is initialized in its constructor
        sceneManager.pushScene(new Leaderboard(sceneManager));
        // If you use pushScene for a stack-based navigation, replace setScene with pushScene as needed.
    }
    public void timerCountdown(float deltaTime) {
        if (timer > 0) {
            timer -= deltaTime;

            if (timer <= 0) {
                timer = 0; // Stop the timer at 0
                goToLeaderboard(); // Transition to the EndMenu scene
            }
        }
    }
    private Label timerLabel;

    public GamePlay(SceneManager sceneManager, LevelConfig levelConfig) {
        super(sceneManager);
        this.levelConfig = levelConfig;

        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        inputManager = new InputManager(stage);
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));
        spawnToxicWaste(levelConfig.spawnToxicWaste);

        bg = new Texture(Gdx.files.internal("FloorBG.jpg"));
        bgSprite = new Sprite(bg);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font = new BitmapFont();

        spawnBins();


        conveyorBelt = new ConveyorBeltActor();
        stage.addActor(conveyorBelt);

//        timerLabel = new Label(String.format("Time: %d", (int)Math.floor(timer)), skin);
//        timerLabel.setPosition(Gdx.graphics.getWidth() / 2 - timerLabel.getWidth() / 2, Gdx.graphics.getHeight() - timerLabel.getHeight() - 20); // Example position
//        stage.addActor(timerLabel);

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
                sceneManager.pushScene(new MainMenu(sceneManager));
            }
        });
        stage.addActor(homebtn);

        bucketTexture = new Texture(Gdx.files.internal("Walle.png"));
        bucket = new BucketActor( 100, 100, 300,100,this);
//        bucket.setSize(75,75);
        actors.add(bucket); // Add the bucket to the actors list
        collisionManager = new CollisionManager(actors,stage);
        Gdx.app.log("GamePlay", "Bucket initialized at x=" + bucket.getX() + ", y=" + bucket.getY());
        stage.addActor(bucket);

        bucket.debug();
        stage.setDebugAll(true);

        collisionManager = new CollisionManager(actors, stage);
        shapeRenderer = new ShapeRenderer();
        startTime = System.nanoTime();
        this.aiManager = new AIManager(stage, bucket);
        trashMonsterActor = new TrashMonsterActor();
        stage.addActor(trashMonsterActor);

        setTimer(90);
    }

    private void spawnBins() {
        for (int i = 0; i < levelConfig.spawnTypes.length; i++) {
            BinActor bin = new BinActor(levelConfig.spawnTypes[i], i); // Position might need adjusting
            stage.addActor(bin);
        }
    }


    @Override
    protected String getBackgroundTexturePath() {
        return "FloorBG.png";
    }

    @Override
    public void initialize() {
        // Set initial spawnTimer value to a smaller value
        spawnTimer = MathUtils.random(1.0f, 2.0f); // Random initial delay between 1 and 3 seconds
    }

    private void spawnItem() {
        if (levelConfig.spawnTypes.length > 0) {
            // Select a random item type from the level-configured bin types
            int itemToSpawnIndex = random.nextInt(levelConfig.spawnTypes.length);
            ItemType itemType = levelConfig.spawnTypes[itemToSpawnIndex];

            float baseSpeed = 75; // Consider making this part of LevelConfig if it varies by level
            ItemActor item = new ItemActor(itemType, baseSpeed * levelConfig.movementSpeedFactor, 0, 0, this);
            if (!checkCollision(item)) {
                items.add(item);
                actors.add(item); // Assuming 'actors' can include any CollidableActor
                stage.addActor(item);
                item.resetPosition(item.bucketX, item.bucketWidth);
            } else {
                item.remove(); // Remove the item if there's a collision upon spawning
            }
        }
    }


    private void spawnToxicWaste(int spawnToxicWaste) {
        for (int i = 0; i < spawnToxicWaste; i++) {
            ToxicWasteActor toxicwaste = new ToxicWasteActor();
            stage.addActor(toxicwaste);
        }
    }

    public void removeItemFromList(ItemActor item) {
        if (items.contains(item, true)) {
            items.removeValue(item, true);
        }

        // Remove the item from the actors list
        if (actors.contains(item)) {
            actors.remove(item);
        }
    }


    private boolean checkCollision(CollidableActor actor) {
        for (CollidableActor existingActor : actors) {
            if (actor.getBounds().overlaps(existingActor.getBounds())) {
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

    public void update(float deltaTime) {
        spawnTimer += deltaTime;
        if (spawnTimer >= 3/ levelConfig.spawnSpeedFactor) {
            spawnItem();
            spawnTimer = 0;
        }
        float followSpeed = 50; // Speed at which the monster follows the bucket, adjust as needed
        aiManager.updateFollower(trashMonsterActor, deltaTime, followSpeed);

        timerCountdown(deltaTime);
    }

//    @Override
    public void render() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        stage.act(Gdx.graphics.getDeltaTime());

        batch.begin();

        bgSprite.draw(batch);
        String timerText = String.format("Time: %d", (int)Math.floor(timer));
        GlyphLayout timerLayout = new GlyphLayout(); // Declare the GlyphLayout object
        timerLayout.setText(font, timerText); // Set the text for the layout
        float timerX = screenWidth - timerLayout.width - 10; // Right-align the timer
        float timerY = screenHeight - timerLayout.height - 100; // Adjust the y-coordinate as needed
        font.draw(batch, timerText, timerX, timerY);
        batch.end();

        collisionManager.handleCollisions();

        stage.draw();
        update(Gdx.graphics.getDeltaTime());

        ScoreManager.getInstance().render(batch, stage.getViewport());

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());

        // It's more efficient to reuse the ShapeRenderer instance if possible
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        Rectangle bucketBounds = bucket.getBounds();
        shapeRenderer.rect(bucketBounds.x, bucketBounds.y, bucketBounds.width, bucketBounds.height);

        shapeRenderer.end();
        inputManager.handleInput(Gdx.graphics.getDeltaTime());
//        logFPS();
    }
    private void logFPS() {
        if (System.nanoTime() - startTime >= 1000000000) { // Check if a second has passed
            Gdx.app.log("FPS", "Current FPS: " + Gdx.graphics.getFramesPerSecond());
            startTime = System.nanoTime();
        }
    }
    @Override
    public void resize(int width, int height) {
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (bucketTexture != null) bucketTexture.dispose();
        conveyorBelt.dispose();
        ScoreManager.getInstance().dispose();

        if (font != null) font.dispose();
    }
}