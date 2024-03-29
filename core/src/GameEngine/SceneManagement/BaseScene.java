package GameEngine.SceneManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;


/**
 * BaseScene is an abstract class that implements the SceneInterface interface.
 * It provides a basic structure for the scenes classes, such as PauseMenu, MainMenu, LeaderBoard, HowToPlay, and GamePlay.
 * This includes:
 * - Providing a common structure for the scenes to initialize, update, render, and dispose.
 * - Providing a method to add buttons to the scene.
 * - Providing a method to get the path of the background texture for the scene.
 */
public abstract class BaseScene implements SceneInterface {
    protected Stage stage;
    protected SceneManager sceneManager;
    protected Skin skin;
    protected SpriteBatch batch;
    protected Texture bg;
    protected Sprite bgSprite;

//    Constructs a BaseScene with a reference to the SceneManager.
    public BaseScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        initialize();
    }

    @Override
    public void initialize() {
        // Initialize scene resources and setups
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("cloud-form-ui.json"));
        bg = new Texture(Gdx.files.internal(getBackgroundTexturePath()));
        bgSprite = new Sprite(bg);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    // Abstract method to get the path of the background texture for subclasses to implement
    protected abstract String getBackgroundTexturePath();

    @Override
    public void update(float deltaTime) {
        // Update game logic
    }

    //Render resources
    @Override
    public void render() {
        batch.begin();
        bgSprite.draw(batch);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        // Dispose of any resources used by the scene
        System.out.println("Disposing GamePlay. Is stage null? " + (stage == null));
        if (stage != null) {
            stage.dispose();
        }
        skin.dispose();
        if (batch != null) {
            batch.dispose();
        }
    }

    @Override
    public SceneManager getSceneManager() {
        return this.sceneManager; // Return the SceneManager associated with this scene
    }
}
