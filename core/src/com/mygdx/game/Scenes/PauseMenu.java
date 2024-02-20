package com.mygdx.game.Scenes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Game_Engine;

public class PauseMenu implements SceneInterface {
    private SceneManager sceneManager;
    private SpriteBatch batch;
    private Texture img;
    public OrthographicCamera camera;
    public Viewport viewport;

    // Implement necessary methods from SceneInterface
    @Override
    public void initialize() {
        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("PauseMenu.png"));
        camera = new OrthographicCamera();
        viewport = new StretchViewport(800, 600, camera); // Use your desired world size
        camera.position.set(400, 300, 0); // Adjust according to your viewport's world width/height
    }
    @Override
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
    @Override
    public void update(float deltaTime) {
        // Handle any animations or transitions in the menu.
    }
    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(img, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Game_Engine.isMusicMuted = !Game_Engine.isMusicMuted;
        }
    }

    @Override
    public void dispose() {
        img.dispose();
        batch.dispose();
    }


    // Implement other required methods...
}
