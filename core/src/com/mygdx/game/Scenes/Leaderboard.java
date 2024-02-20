package com.mygdx.game.Scenes;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Game_Engine;

public class Leaderboard implements SceneInterface{

    private Texture img;
    private SpriteBatch batch;
    public OrthographicCamera camera;
    public Viewport viewport;
    public Vector3 tmp = new Vector3();
    private SceneManager sceneManager;
    private BitmapFont font;

    @Override
    public void initialize() {
        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("Leaderboard.png"));
        camera = new OrthographicCamera();
        viewport = new StretchViewport(800, 600, camera); // Use your desired world size
        camera.position.set(400, 300, 0); // Adjust according to your viewport's world width/height
        font = new BitmapFont();
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
        font.draw(batch,"This is the LeaderBoard Scene.", 1, 450);
        font.draw(batch,"Press Z to transit to GamePlay Scene.", 1, 400);
        font.draw(batch,"Press C to transit to EndMenu Scene.", 1, 350);
        font.draw(batch,"Press V to transit to MainMenu Scene.", 1, 300);
        font.draw(batch,"Press M to mute/unmute the audio.", 1, 250);
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
        if (Gdx.input.justTouched()) {
            camera.unproject(tmp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Game_Engine.isMusicMuted = !Game_Engine.isMusicMuted;
        }
    }
    @Override
    public void dispose() {
        img.dispose();
        batch.dispose();
    }



}

