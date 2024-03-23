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
import com.mygdx.game.InputManagement.InputManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePlay extends Scene {
    private ShapeRenderer shapeRenderer;
    private boolean isDisposed = false;
    private Random random = new Random();
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture bg;
    private Sprite bgSprite;
    private Sprite bgSprite2; // Additional background sprite for scrolling effect

    private SceneManager sceneManager;
    private BucketActor bucket;
    private Texture bucketTexture;
    private Texture paperitemsTexture;
    private Texture trashTexture;
    private Texture metalitemsTexture;
    private float spawnTimer = 0;
    private InputManager inputManager;
    private Array<PaperItemsActor> paperitems = new Array<>();
    private Array<MetalItemsActor> metalitems = new Array<>();
    private List<CollidableActor> actors = new ArrayList<>();
    private boolean spawnTrashNext = false;
    private GlassBinActor glassBin;
    private PaperBinActor paperBin;
    private PlasticBinActor plasticBin;
    private MetalBinActor metalBin;


    private CollisionManager collisionManager;

    private ConveyorBeltActor conveyorBelt;

    public GamePlay(SceneManager sceneManager) {
        super(sceneManager);
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        inputManager = new InputManager(stage);
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));

        bg = new Texture(Gdx.files.internal("FloorBG.jpg"));
        bgSprite = new Sprite(bg);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        glassBin = new GlassBinActor();
        stage.addActor(glassBin);
        paperBin = new PaperBinActor();
        stage.addActor(paperBin);
        plasticBin = new PlasticBinActor();
        stage.addActor(plasticBin);
        metalBin = new MetalBinActor();
        stage.addActor(metalBin);
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
        bucket = new BucketActor( 100, 100, 200);
        actors.add(bucket); // Add the bucket to the actors list
        collisionManager = new CollisionManager(actors, paperitems, metalitems, stage);
        Gdx.app.log("GamePlay", "Bucket initialized at x=" + bucket.getX() + ", y=" + bucket.getY());
        stage.addActor(bucket);
        bucket.debug();
        stage.setDebugAll(true);

        paperitemsTexture = new Texture(Gdx.files.internal("paperitems.png"));
        metalitemsTexture = new Texture(Gdx.files.internal("metalitems.png"));

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void initialize() {
        // Set initial spawnTimer value to a smaller value
        spawnTimer = MathUtils.random(1.0f, 3.0f); // Random initial delay between 1 and 3 seconds
    }

    private void spawnItem() {
        if (random.nextBoolean()) {
            spawnPaperItem();
        } else {
            spawnMetalItem();
        }
    }

    private void spawnPaperItem() {
        PaperItemsActor paperitem = new PaperItemsActor(100, 0, 0, this);
        if (!checkCollision(paperitem)) { // Check for collision
            paperitems.add(paperitem);
            actors.add(paperitem);
            stage.addActor(paperitem);
            paperitem.resetPosition(paperitem.bucketX, paperitem.bucketWidth);
        } else {
            paperitem.remove(); // Remove the item if it overlaps
        }
    }

    private void spawnMetalItem() {
        MetalItemsActor metalitem = new MetalItemsActor(100, 0, 0, this);
        if (!checkCollision(metalitem)) { // Check for collision
            metalitems.add(metalitem);
            actors.add(metalitem);
            stage.addActor(metalitem);
            metalitem.resetPosition(metalitem.bucketX, metalitem.bucketWidth);
        } else {
            metalitem.remove(); // Remove the item if it overlaps
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

    public void removeItem(PaperItemsActor paperitem) {
        paperitems.removeValue(paperitem, true);
        paperitem.remove();
    }

    public void update(float deltaTime) {
        spawnTimer += deltaTime;
        if (spawnTimer >= 3) {
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

        for (PaperItemsActor paperitem : paperitems) {
            Rectangle dropBounds = paperitem.getBounds();
            shapeRenderer.rect(dropBounds.x, dropBounds.y, dropBounds.width, dropBounds.height);
        }
        for (MetalItemsActor metalitem : metalitems) {
            Rectangle dropBounds = metalitem.getBounds();
            shapeRenderer.rect(dropBounds.x, dropBounds.y, dropBounds.width, dropBounds.height);
        }

        shapeRenderer.end();

        inputManager.handleInput(Gdx.graphics.getDeltaTime());

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (bucketTexture != null) bucketTexture.dispose();
        if (paperitemsTexture != null) paperitemsTexture.dispose();
        if (metalitemsTexture != null) metalitemsTexture.dispose();
        glassBin.dispose();
        paperBin.dispose();
        plasticBin.dispose();
        metalBin.dispose();
        conveyorBelt.dispose();
    }
}

