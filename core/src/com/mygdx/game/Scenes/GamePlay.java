package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.CollisionManagement.CollisionManager;
import com.mygdx.game.EntityManagement.EntityManager;
import com.mygdx.game.Lifecycle.HighScore.HighScoreManager;
import com.mygdx.game.Lifecycle.HighScore.ScoreFileHandler;
import com.mygdx.game.Lifecycle.HighScore.ScoreFormatter;
import com.mygdx.game.Lifecycle.HighScore.ScoreRenderer;
import com.mygdx.game.Lifecycle.LifeManager;

public class GamePlay extends Scene {
    private ShapeRenderer shapeRenderer;
    private EntityManager entityManager;
    private HighScoreManager highScoreManager;
    private ScoreFileHandler scoreFileHandler;
    private ScoreFormatter scoreFormatter;
    private ScoreRenderer scoreRenderer;
    private LifeManager lifeManager;
    private CollisionManager collisionManager;
    private boolean isDisposed = false;
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture bg;
    private Sprite bgSprite;
    private SceneManager sceneManager;



    public GamePlay(SceneManager sceneManager) {
        super(sceneManager);
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
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
    }

    @Override
    public void initialize() {
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render() {
        Gdx.input.setInputProcessor(stage);

        super.render();
        batch.begin();
        bgSprite.draw(batch);
        batch.end();
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        // Update the stage's viewport when the screen size changes
        stage.getViewport().update(width, height, true);
    }
    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
        skin.dispose();
        batch.dispose();
    }
}
