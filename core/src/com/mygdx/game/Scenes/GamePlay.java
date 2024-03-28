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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.CollisionManagement.CollisionManager;
import com.mygdx.game.EntityManagement.Foundation.CollidableActor;
import com.mygdx.game.EntityManagement.Movers.BucketActor;
import com.mygdx.game.EntityManagement.Movers.ItemActor;
import com.mygdx.game.EntityManagement.Movers.TrashMonsterActor;
import com.mygdx.game.EntityManagement.Static.BinActor;
import com.mygdx.game.EntityManagement.Static.ConveyorBeltActor;
import com.mygdx.game.EntityManagement.Static.ToxicWasteActor;
import com.mygdx.game.InputManagement.InputManager;
import com.mygdx.game.Lifecycle.AudioManager;
import com.mygdx.game.Lifecycle.LevelConfig;
import com.mygdx.game.Lifecycle.ScoreSystem.ScoreManager;
import com.mygdx.game.Lifecycle.TimerManager;
import com.mygdx.game.enums.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.mygdx.game.AIManagement.AIManager;

public class GamePlay extends BaseScene implements GameOverListener {
    private ShapeRenderer shapeRenderer;
    private Random random = new Random();
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture bg;
    private Sprite bgSprite;
    private BucketActor bucket;
    private Texture bucketTexture;
    private float spawnTimer;
    private BitmapFont font;
    private Array<ItemActor> items = new Array<>();
    private List<CollidableActor> actors = new ArrayList<>();
    private LevelConfig levelConfig;
    private CollisionManager collisionManager;
    private ConveyorBeltActor conveyorBelt;
    private long startTime;
    private TrashMonsterActor trashMonsterActor;
    private AIManager aiManager;
    private ScoreManager scoreManager;
    private TimerManager timerManager;

    public GamePlay(SceneManager sceneManager, LevelConfig levelConfig) {
        super(sceneManager);
        this.levelConfig = levelConfig;
        ScoreManager.getInstance().setCurrentLevel(levelConfig.levelNumber);

        // First, initialize all graphical components and input processors.
        // This step ensures that 'font' and 'batch' are initialized before being used.
        initializeGraphics();

        // Initialize UI components next. This method likely relies on graphical elements
        // like 'font' but not on gameplay logic, making it a good second step.
        initializeUIComponents();

        // Initialize gameplay components. This might include setting up the game world,
        // entities, and any managers that don't depend on 'font' or 'batch'.
        initializeGameComponents();

        // Now that all dependencies are assured to be initialized, set up the TimerManager.
        // This step is done last to ensure 'font', 'batch', and 'AudioManager' are ready.
        timerManager = new TimerManager(2, AudioManager.getInstance(), this::goToLeaderboard, font, batch);
    }

    public void update(float deltaTime) {
        handleItemSpawning(deltaTime);
        updateMonsterFollowBehavior(deltaTime);
        checkMonsterBucketCollision();
        // Update timer
        timerManager.update(deltaTime);
        handleCollisions();
    }

    @Override
    public void render() {
        clearScreen();
        processInput();

        update(Gdx.graphics.getDeltaTime());

        renderBatch();
        renderStage();
        renderScore();
        //renderDebugShapes();
    }

    //Initalizers
    @Override
    protected String getBackgroundTexturePath() {
        return "FloorBG.png";
    }

    @Override
    public void initialize() {
        // Set initial spawnTimer value to a smaller value
        spawnTimer = MathUtils.random(1.0f, 2.0f); // Random initial delay between 1 and 3 seconds
    }

    private void initializeGraphics() {
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        bg = new Texture(Gdx.files.internal("FloorBG.jpg"));
        bgSprite = new Sprite(bg);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font = new BitmapFont();
    }

    private void initializeUIComponents() {
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));
        createPauseButton();
        createHomeButton();
    }

    private void createPauseButton() {
        TextButton pauseBtn = new TextButton("Pause", skin);
        setupButton(pauseBtn, 1);
        pauseBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.pushScene(new PauseMenu(sceneManager));
            }
        });
    }

    private void createHomeButton() {
        TextButton homeBtn = new TextButton("Home", skin);
        setupButton(homeBtn, 2);
        homeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sceneManager.pushScene(new MainMenu(sceneManager));
            }
        });
    }

    private void setupButton(TextButton button, int order) {
        int buttonWidth = 100;
        int buttonHeight = 25;
        int buttonSpacing = 10;
        int rightMargin = 10;
        int topMargin = 10;
        button.setSize(buttonWidth, buttonHeight);
        button.setPosition(Gdx.graphics.getWidth() - buttonWidth - rightMargin,
                Gdx.graphics.getHeight() - order * (buttonHeight + buttonSpacing) - topMargin);
        stage.addActor(button);
    }

    private void initializeGameComponents() {
        spawnToxicWaste(levelConfig.spawnToxicWaste);
        spawnBins();
        initializeScoreManager();
        setupConveyorBelt();
        initializeBucket();
        setupCollisionManager();
        initializeAIManager();
    }
    private void initializeScoreManager() {
        scoreManager = ScoreManager.getInstance();
        scoreManager.resetCurrentScore();
    }

    private void setupConveyorBelt() {
        conveyorBelt = new ConveyorBeltActor();
        stage.addActor(conveyorBelt);
    }

    private void initializeBucket() {
        bucketTexture = new Texture(Gdx.files.internal("Walle.png"));
        bucket = new BucketActor(100, 100, 300, 100, this);
        actors.add(bucket); // Assume actors is a list of actors for collision detection
        Gdx.app.log("GamePlay", "Bucket initialized at x=" + bucket.getX() + ", y=" + bucket.getY());
        stage.addActor(bucket);
    }

    private void setupCollisionManager() {
        collisionManager = new CollisionManager(actors, stage);
        shapeRenderer = new ShapeRenderer();
    }

    private void initializeAIManager() {
        aiManager = new AIManager(stage, bucket);
        trashMonsterActor = new TrashMonsterActor();
        stage.addActor(trashMonsterActor);
    }

    //Spawners
    private void spawnBins() {
        for (int i = 0; i < levelConfig.spawnTypes.length; i++) {
            BinActor bin = new BinActor(levelConfig.spawnTypes[i], i); // Position might need adjusting
            stage.addActor(bin);
        }
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

    private void handleItemSpawning(float deltaTime) {
        spawnTimer += deltaTime;
        final float spawnInterval = 3 / levelConfig.spawnSpeedFactor;
        if (spawnTimer >= spawnInterval) {
            spawnItem();
            spawnTimer = 0; // Reset spawn timer
        }
    }

    //Collisions
    private boolean checkCollision(CollidableActor actor) {
        for (CollidableActor existingActor : actors) {
            if (actor.getBounds().overlaps(existingActor.getBounds())) {
                return true; // Collision detected
            }
        }
        return false; // No collision detected
    }

    private void handleCollisions() {
        collisionManager.handleCollisions(); // Handles all game collisions
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

    //MonsterBehavior
    private void updateMonsterFollowBehavior(float deltaTime) {
        float followSpeed = 50; // Speed at which the monster follows the bucket
        aiManager.updateFollower(trashMonsterActor, deltaTime, followSpeed);
    }

    private void checkMonsterBucketCollision() {
        if (trashMonsterActor.overlaps(bucket)) {
            bucket.decreaseLife(10); // This method needs to be defined in the BucketActor class
            AudioManager.collisionSound.play(); // Play sound on collision
            trashMonsterActor.respawnAtRandomEdge(); // Respawns the monster
        }
    }

    //GameOver
    public void onGameOver() {
        AudioManager.powerOffSound.play();
        int currentScore = ScoreManager.getInstance().getCurrentScore();
        ScoreManager.getInstance().addScore(currentScore); // Add the current score to the high scores
        ScoreManager.getInstance().resetCurrentScore(); // Optionally reset the current score
        // Handle the transition to the leaderboard scene
        sceneManager.pushScene(new Leaderboard(sceneManager));
    }
    private void goToLeaderboard() {
        // Assuming sceneManager is a member of GamePlay and is initialized in its constructor
        sceneManager.pushScene(new Leaderboard(sceneManager));
        // If you use pushScene for a stack-based navigation, replace setScene with pushScene as needed.
    }

    //GameRenderers
    private void clearScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void processInput() {
        Gdx.input.setInputProcessor(stage);
    }

    private void renderBatch() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        batch.begin();
        drawBackground();
        // Let TimerManager handle the drawing of the timer
        timerManager.drawTimer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    private void drawBackground() {
        bgSprite.draw(batch);
    }

    private void renderStage() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void renderScore() {
        ScoreManager.getInstance().render(batch, stage.getViewport());
    }

    private void renderDebugShapes() {
        setupShapeRenderer();
        drawDebugShapes();
    }

    private void setupShapeRenderer() {
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
    }

    private void drawDebugShapes() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        Rectangle bucketBounds = bucket.getBounds();
        shapeRenderer.rect(bucketBounds.x, bucketBounds.y, bucketBounds.width, bucketBounds.height);
        shapeRenderer.end();
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