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
import com.mygdx.game.AIManagement.AIManager;
import com.mygdx.game.CollisionManagement.CollisionManager;
import com.mygdx.game.EntityManagement.*;
import com.mygdx.game.InputManagement.InputManager;

import java.util.*;

public class GamePlay extends BaseScene {
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
    private Texture trashitemsTexture;
    private Texture metalitemsTexture;
    private Texture glassitemsTexture;
    private Texture plasticitemsTexture;
    private float spawnTimer = 0;
    private InputManager inputManager;
    private Array<PaperItemsActor> paperitems = new Array<>();
    private Array<MetalItemsActor> metalitems = new Array<>();
    private Array<GlassItemsActor> glassitems = new Array<>();
    private Array<PlasticItemsActor> plasticitems = new Array<>();
    private Array<TrashItemsActor> trashitems = new Array<>();
    private List<CollidableActor> actors = new ArrayList<>();
    private GlassBinActor glassBin;
    private PaperBinActor paperBin;
    private PlasticBinActor plasticBin;
    private MetalBinActor metalBin;
    private TrashBinActor trashBin;
    private ToxicWasteActor toxicWaste;
    private LevelConfig levelConfig;
    private CollisionManager collisionManager;
    private ConveyorBeltActor conveyorBelt;
    private TrashMonsterActor trashMonsterActor;
    private AIManager aiManager;

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
        glassBin = new GlassBinActor();
        stage.addActor(glassBin);
        paperBin = new PaperBinActor();
        stage.addActor(paperBin);
        plasticBin = new PlasticBinActor();
        stage.addActor(plasticBin);
        metalBin = new MetalBinActor();
        stage.addActor(metalBin);
        trashBin = new TrashBinActor();
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
        bucket = new BucketActor( 100, 100, 200);
//        bucket.setSize(75,75);
        actors.add(bucket); // Add the bucket to the actors list
        collisionManager = new CollisionManager(actors, paperitems, metalitems, glassitems, plasticitems, trashitems ,stage);
        Gdx.app.log("GamePlay", "Bucket initialized at x=" + bucket.getX() + ", y=" + bucket.getY());
        stage.addActor(bucket);
        bucket.debug();
        this.aiManager = new AIManager(stage, bucket);
        trashMonsterActor = new TrashMonsterActor();
        stage.addActor(trashMonsterActor);
        stage.setDebugAll(true);

        paperitemsTexture = new Texture(Gdx.files.internal("paperitems.png"));
        metalitemsTexture = new Texture(Gdx.files.internal("metalitems.png"));
        glassitemsTexture = new Texture(Gdx.files.internal("glassitems.png"));
        plasticitemsTexture = new Texture(Gdx.files.internal("plasticitems.png"));
        trashitemsTexture = new Texture(Gdx.files.internal("trashitems.png"));
        collisionManager = new CollisionManager(actors, paperitems, metalitems, glassitems, plasticitems, trashitems ,stage);
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
        int itemToSpawn = random.nextInt(5); // Generates 0, 1, 2, 3 or 4 randomly

        if (itemToSpawn == 0) {
            spawnPaperItem();
        } else if (itemToSpawn == 1) {
            spawnMetalItem();
        } else if (itemToSpawn == 2) {
            spawnGlassItem();
        } else if (itemToSpawn == 3){
            spawnPlasticItem();
        } else {
            spawnTrashItem();
        }
    }

    private void spawnPaperItem() {
        float baseSpeed = 100;
        PaperItemsActor paperitem = new PaperItemsActor(baseSpeed * levelConfig.movementSpeedFactor, 0, 0, this);
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
        float baseSpeed = 100;
        MetalItemsActor metalitem = new MetalItemsActor(baseSpeed * levelConfig.movementSpeedFactor, 0, 0, this);
        if (!checkCollision(metalitem)) { // Check for collision
            metalitems.add(metalitem);
            actors.add(metalitem);
            stage.addActor(metalitem);
            metalitem.resetPosition(metalitem.bucketX, metalitem.bucketWidth);
        } else {
            metalitem.remove(); // Remove the item if it overlaps
        }
    }
    private void spawnGlassItem() {
        float baseSpeed = 100;
        GlassItemsActor glassitem = new GlassItemsActor(baseSpeed * levelConfig.movementSpeedFactor, 0, 0, this);
        if (!checkCollision(glassitem)) { // Check for collision
            glassitems.add(glassitem);
            actors.add(glassitem);
            stage.addActor(glassitem);
            glassitem.resetPosition(glassitem.bucketX, glassitem.bucketWidth);
        } else {
            glassitem.remove(); // Remove the item if it overlaps
        }
    }
    private void spawnPlasticItem() {
        float baseSpeed = 100;
        PlasticItemsActor plasticitem = new PlasticItemsActor(baseSpeed * levelConfig.movementSpeedFactor, 0, 0, this);
        if (!checkCollision(plasticitem)) { // Check for collision
            plasticitems.add(plasticitem);
            actors.add(plasticitem);
            stage.addActor(plasticitem);
            plasticitem.resetPosition(plasticitem.bucketX, plasticitem.bucketWidth);
        } else {
            plasticitem.remove(); // Remove the item if it overlaps
        }
    }

    private void spawnTrashItem() {
        float baseSpeed = 100;
        TrashItemsActor trashitem = new TrashItemsActor(baseSpeed * levelConfig.movementSpeedFactor, 0, 0, this);
        if (!checkCollision(trashitem)) { // Check for collision
            trashitems.add(trashitem);
            actors.add(trashitem);
            stage.addActor(trashitem);
            trashitem.resetPosition(trashitem.bucketX, trashitem.bucketWidth);
        } else {
            trashitem.remove(); // Remove the item if it overlaps
        }
    }
    private void spawnToxicWaste(int spawnToxicWaste) {
        for (int i = 0; i < spawnToxicWaste; i++) {
            ToxicWasteActor toxicwaste = new ToxicWasteActor();
            stage.addActor(toxicwaste);
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
        if (spawnTimer >= 3/ levelConfig.spawnSpeedFactor) {
            spawnItem();
            spawnTimer = 0;

        }
        float followSpeed = 50; // Speed at which the monster follows the bucket, adjust as needed
        aiManager.updateFollower(trashMonsterActor, deltaTime, followSpeed);

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
        for (GlassItemsActor glassitem : glassitems) {
            Rectangle dropBounds = glassitem.getBounds();
            shapeRenderer.rect(dropBounds.x, dropBounds.y, dropBounds.width, dropBounds.height);
        }
        for (PlasticItemsActor plasticitem : plasticitems) {
            Rectangle dropBounds = plasticitem.getBounds();
            shapeRenderer.rect(dropBounds.x, dropBounds.y, dropBounds.width, dropBounds.height);
        }
        for (TrashItemsActor trashitem : trashitems) {
            Rectangle dropBounds = trashitem.getBounds();
            shapeRenderer.rect(dropBounds.x, dropBounds.y, dropBounds.width, dropBounds.height);
        }
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
        if (paperitemsTexture != null) paperitemsTexture.dispose();
        if (metalitemsTexture != null) metalitemsTexture.dispose();
        if (glassitemsTexture != null) glassitemsTexture.dispose();
        if (plasticitemsTexture != null) plasticitemsTexture.dispose();
        if (trashitemsTexture != null) trashitemsTexture.dispose();
        glassBin.dispose();
        paperBin.dispose();
        plasticBin.dispose();
        metalBin.dispose();
        trashBin.dispose();
        toxicWaste.dispose();
        conveyorBelt.dispose();
    }
}
