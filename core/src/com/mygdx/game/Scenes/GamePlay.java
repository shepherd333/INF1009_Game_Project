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
import com.mygdx.game.CollisionManagement.CollisionManager;
import com.mygdx.game.EntityManagement.*;
import com.mygdx.game.EntityManagement.Bins.*;
import com.mygdx.game.EntityManagement.Items.*;
import com.mygdx.game.EntityManagement.Static.ConveyorBeltActor;
import com.mygdx.game.InputManagement.InputManager;
import com.mygdx.game.Lifecycle.LevelConfig;
import com.mygdx.game.enums.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private Array<ItemActor> items = new Array<>();
    private List<CollidableActor> actors = new ArrayList<>();
    private LevelConfig levelConfig;
    private CollisionManager collisionManager;
    private ConveyorBeltActor conveyorBelt;

    public GamePlay(SceneManager sceneManager, LevelConfig levelConfig) {
        super(sceneManager);
        this.levelConfig = levelConfig;

        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        inputManager = new InputManager(stage);
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));

        bg = new Texture(Gdx.files.internal("FloorBG.jpg"));
        bgSprite = new Sprite(bg);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        BinActor glassBin = new BinActor(ItemType.GLASS, 0); // First position
        stage.addActor(glassBin);

        BinActor paperBin = new BinActor(ItemType.PAPER, 1); // Second position
        stage.addActor(paperBin);

        BinActor plasticBin = new BinActor(ItemType.PLASTIC, 2); // Third position
        stage.addActor(plasticBin);

        BinActor metalBin = new BinActor(ItemType.METAL, 3); // Fourth position
        stage.addActor(metalBin);

        BinActor trashBin = new BinActor(ItemType.TRASH, 4); // Fifth position
        stage.addActor(trashBin);


        conveyorBelt = new ConveyorBeltActor();
        stage.addActor(conveyorBelt);

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
        bucket = new BucketActor( 100, 100, 200,100,this);
//        bucket.setSize(75,75);
        actors.add(bucket); // Add the bucket to the actors list
        collisionManager = new CollisionManager(actors,stage);
        Gdx.app.log("GamePlay", "Bucket initialized at x=" + bucket.getX() + ", y=" + bucket.getY());
        stage.addActor(bucket);

        metalBin.setDebug(true);
        bucket.debug();
        stage.setDebugAll(true);

        collisionManager = new CollisionManager(actors, stage);
        shapeRenderer = new ShapeRenderer();



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
        ItemType[] itemTypes = ItemType.values();
        int itemToSpawnIndex = random.nextInt(itemTypes.length); // Randomly pick an item type
        ItemType itemType = itemTypes[itemToSpawnIndex];

        float baseSpeed = 100;
        ItemActor item = new ItemActor(itemType, baseSpeed * levelConfig.movementSpeedFactor, 0, 0, this);
        if (!checkCollision(item)) {
            items.add(item);
            actors.add(item); // Assuming 'actors' can include any CollidableActor
            stage.addActor(item);
            item.resetPosition(item.bucketX, item.bucketWidth);
        } else {
            item.remove(); // If there's a collision upon spawning, remove the item
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

    public void dropItemInBin(BucketActor bucket, ItemActor item) {
        // Remove the specific item from the stage and items array
        item.removeItem();
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
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);
        stage.act(Gdx.graphics.getDeltaTime());

        batch.begin();
        bgSprite.draw(batch);
        batch.end();

        collisionManager.handleCollisions();

        stage.draw();
        update(Gdx.graphics.getDeltaTime());

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());

        // It's more efficient to reuse the ShapeRenderer instance if possible
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        Rectangle bucketBounds = bucket.getBounds();
        shapeRenderer.rect(bucketBounds.x, bucketBounds.y, bucketBounds.width, bucketBounds.height);

        shapeRenderer.end();
        inputManager.handleInput(Gdx.graphics.getDeltaTime());
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
    }
}
