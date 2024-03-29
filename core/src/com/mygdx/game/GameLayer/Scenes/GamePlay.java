package com.mygdx.game.GameLayer.Scenes;

import GameEngine.EntityManagement.EntityManager;
import GameEngine.InputControl.UIButtonManager;
import GameEngine.SceneManagement.BaseScene;
import GameEngine.SceneManagement.GameOverListener;
import GameEngine.SceneManagement.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import GameEngine.Collisions.CollisionManager;
import GameEngine.EntityManagement.CollidableActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ItemActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.Static.TrashMonsterActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.Static.BinActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.Static.ConveyorBeltActor;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import GameEngine.SimulationLifecycleManagement.LevelConfig;
import GameEngine.SimulationLifecycleManagement.ScoreManager;
import GameEngine.SimulationLifecycleManagement.TimerManager;
import GameEngine.Collisions.handlers.enums.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import GameEngine.AIControl.AIManager;
import GameEngine.Collisions.handlers.PlayerItemHandler;
import GameEngine.PlayerControl.PlayerController;
import com.mygdx.game.GameLayer.Scenes.Leaderboard;

public class GamePlay extends BaseScene implements GameOverListener {
    private ShapeRenderer shapeRenderer;
    private Random random = new Random();
    private Stage stage;
    private SpriteBatch batch;
    private Texture bg;
    private Sprite bgSprite;
    private PlayerActor bucket;
    private Texture bucketTexture;
    private float spawnTimer;
    private BitmapFont font;
    private Array<ItemActor> items = new Array<>();
    private List<CollidableActor> actors = new ArrayList<>();
    private LevelConfig levelConfig;
    private CollisionManager collisionManager;
    private ConveyorBeltActor conveyorBelt;
    private TrashMonsterActor trashMonsterActor;
    private AIManager aiManager;
    private ScoreManager scoreManager;
    private TimerManager timerManager;
    private PlayerItemHandler playerItemHandler;
    private PlayerController playerController;
    private final EntityManager entityManager;
    private UIButtonManager uiButtonManager;


    public GamePlay(SceneManager sceneManager, LevelConfig levelConfig) {  // Constructor initializes the game with the provided scene manager and level configuration
        super(sceneManager);
        this.levelConfig = levelConfig;
        ScoreManager.getInstance().setCurrentLevel(levelConfig.levelNumber);  // Setting up the game based on level selection
        entityManager = new EntityManager(this, levelConfig);

        initializeGraphics();  // Initialize components
        initializeGameComponents();
        initializeUIComponents();
        initializeGameManagers();
    }

    public void update(float deltaTime) {    // Main update loop for the game, called every frame
        Gdx.input.setInputProcessor(stage);    // All the components which have to be called and updated
        timerManager.update(deltaTime);
        handleItemSpawning(deltaTime);
        aiManager.updateMonsterFollowBehavior(deltaTime, bucket);
        trashMonsterActor.checkMonsterBucketCollision(bucket);
        handleCollisions();
        playerController.handleInput(deltaTime);
        playerItemHandler.handleItemPickupOrDrop();
    }

    @Override
    public void render() {  // Renders the game scene
        clearScreen();  // Clear screen, update state, render game elements
        update(Gdx.graphics.getDeltaTime());
        renderBatch();
        renderStage();
        renderScoreTimer();
    }

    //  Initializers for various components
    @Override
    public void initialize() {
        // Set initial spawnTimer value to a smaller value
        spawnTimer = MathUtils.random(1.0f, 2.0f); // Random initial delay between 1 and 3 seconds
    }
    private void initializeGameManagers() {
        timerManager = new TimerManager(60, AudioManager.getInstance(), this::goToLeaderboard, font, batch, shapeRenderer);
        playerController = new PlayerController(bucket, 200);
        playerItemHandler = new PlayerItemHandler(bucket);
    }

    private void initializeGraphics() {
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        bg = new Texture(Gdx.files.internal(levelConfig.backgroundImagePath));
        bgSprite = new Sprite(bg);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font = new BitmapFont();
    }

    private void initializeUIComponents(){
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));
        this.uiButtonManager = new UIButtonManager(skin, stage, sceneManager);
        uiButtonManager.setupGamePlay();
    }

    @Override
    protected String getBackgroundTexturePath() {
        return "FloorBG.png";
    }  // Sets the background for the game

    private void setupGameManagement() {  // Sets the core gameplay functionalities
        setupCollisionManager();
        initializeScoreManager();
        initializeAIManager();
    }
    private void initializeGameComponents() {  // Initializes core game components, like actors and the player
        spawnBins();
        entityManager.initializeGameEntities();
        initializeScoreManager();
        initializeBucket();
        setupCollisionManager();
        initializeAIManager();
        setupGameManagement();
    }
    private void initializeScoreManager() {  // Initializes the score tracking functionality
        scoreManager = ScoreManager.getInstance();
        scoreManager.resetCurrentScore();
    }


    private void initializeBucket() {  // Initializes the main player character
        bucketTexture = new Texture(Gdx.files.internal("Walle.png"));  // The player's sprite
        bucket = new PlayerActor(100, 100, 300, 100, this); // Setting the player's position, speed and health
        actors.add(bucket); // Assume actors is a list of actors for collision detection
        Gdx.app.log("GamePlay", "Bucket initialized at x=" + bucket.getX() + ", y=" + bucket.getY());
        stage.addActor(bucket);
    }

    private void setupCollisionManager() {  // Sets up the collision manager and the shape renderer
        collisionManager = new CollisionManager(actors, stage);
        shapeRenderer = new ShapeRenderer();
    }

    private void initializeAIManager() {  // Initialize the AI NPC which is the Trash Monster
        trashMonsterActor = new TrashMonsterActor();
        stage.addActor(trashMonsterActor);
        aiManager = new AIManager(stage, bucket,trashMonsterActor);
    }

    //Spawners
    private void spawnBins() {  // Spawning the different bin actors based on level configuration
        for (int i = 0; i < levelConfig.spawnTypes.length; i++) {
            BinActor bin = new BinActor(levelConfig.spawnTypes[i], i); // Position might need adjusting
            stage.addActor(bin);
        }
    }

    private void spawnItem() {  // Spawning of the items that player recycles
        if (levelConfig.spawnTypes.length > 0) {
            // Select a random item type from the level-configured bin types
            int itemToSpawnIndex = random.nextInt(levelConfig.spawnTypes.length);
            ItemType itemType = levelConfig.spawnTypes[itemToSpawnIndex];

            float baseSpeed = 75; // Consider making this part of LevelConfig if it varies by level
            // Setting the speed of the item movement based on level configured
            ItemActor item = new ItemActor(itemType, baseSpeed * levelConfig.movementSpeedFactor, 0, 0, this);
            if (!checkCollision(item)) {  // Ensuring the items check for collision before spawning
                items.add(item);
                actors.add(item); // Assuming 'actors' can include any CollidableActor
                stage.addActor(item);
                item.resetPosition(item.bucketX, item.bucketWidth);
            } else {
                item.remove(); // Remove the item if there's a collision upon spawning
            }
        }
    }

    private void handleItemSpawning(float deltaTime) {  // Setting the spawn intervals of the items
        spawnTimer += deltaTime;
        final float spawnInterval = 3 / levelConfig.spawnSpeedFactor;
        if (spawnTimer >= spawnInterval) {
            spawnItem();
            spawnTimer = 0; // Reset spawn timer
        }
    }

    //Collisions
    private boolean checkCollision(CollidableActor actor) {  // Checks collisions within the actors on the screen
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

    public void removeItemFromList(ItemActor item) {  // Removes an item from the game when necessary
        if (items.contains(item, true)) {
            items.removeValue(item, true);
        }

        // Remove the item from the actors list
        if (actors.contains(item)) {
            actors.remove(item);
        }
    }

    //GameOver
    public void onGameOver() {  // Calls the methods needed for when end of game is  detected
        AudioManager.getInstance().playSoundEffect("powerOff", 1.0f); // Sound effect for game end
        AudioManager.getInstance().stopCountdownSound();  // Stopping the countdown timer effect if game ends before final 10 seconds
        int currentScore = ScoreManager.getInstance().getCurrentScore();
        ScoreManager.getInstance().addScore(currentScore); // Add the current score to the high scores
        ScoreManager.getInstance().resetCurrentScore(); // Optionally reset the current score
        // Handle the transition to the leaderboard scene
        sceneManager.pushScene(new Leaderboard(sceneManager));
    }

    //GameRenderers
    private void clearScreen() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void renderBatch() {  // Begins a new batch for rendering, drawing the background and timer
        batch.begin();
        drawBackground();
        // Let TimerManager handle the drawing of the timer

        batch.end();
    }

    private void drawBackground() {
        bgSprite.draw(batch);
    }

    private void renderStage() {  // Renders the stage, which contains actors and UI elements
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void renderScoreTimer() {  // Renders the current score
        ScoreManager.getInstance().render(batch, stage.getViewport(), shapeRenderer);
        timerManager.drawTimer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    public Stage getStage() {
        return stage;
    }



    private void goToLeaderboard() {   // Transitions to the leaderboard scene when the game ends
        // logic to transition to the leaderboard scene
        sceneManager.pushScene(new Leaderboard(sceneManager));
        AudioManager.getInstance().stopCountdownSound();
    }


    @Override
    public void resize(int width, int height) {  // Handles window resizing, adjusting the viewport as necessary
        if (stage != null) {
            stage.getViewport().update(width, height, true);
        }
    }

    @Override
    public void dispose() {   // Disposes of resources when the game scene is closed
        super.dispose();

        //System.out.println("Disposing GamePlay. Is stage null? " + (stage == null));
        if (stage != null) {
            stage.dispose();
        }
        if (bucketTexture != null) bucketTexture.dispose();
        if (conveyorBelt != null) conveyorBelt.dispose();


        if (font != null) font.dispose();
    }
}