package GameEngine.SceneManagement;

import GameEngine.SceneManagement.SceneInterface;
import GameEngine.SceneManagement.SceneManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


// BaseScene is an abstract class that implements the SceneInterface interface
// It provides a basic structure for the scenes classes, PauseMenu, MainMenu, LeaderBoard, HowToPlay and GamePlay.
// It provides a common structure for the scenes to initialize, update, render, and dispose.

// It also provides a method to add buttons to the scene.
// It also provides a method to get the path of the background texture for the scene.
public abstract class BaseScene implements SceneInterface {
    protected Stage stage;
    protected SceneManager sceneManager;
    protected Skin skin;
    protected SpriteBatch batch;
    protected Texture bg;
    protected Sprite bgSprite;

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

    protected void addButton(String buttonText, ClickListener listener, int x, int y, int buttonWidth, int buttonHeight) {
        TextButton button = new TextButton(buttonText, skin);
        button.setSize(buttonWidth, buttonHeight);
        button.setPosition(x, y);
        button.addListener(listener);
        stage.addActor(button);
    }

    @Override
    public void update(float deltaTime) {
        // Update game logic, deltaTime is the time between frames
    }

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
        return this.sceneManager;
    }
}
