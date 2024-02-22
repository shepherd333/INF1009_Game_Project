package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Game_Engine;

public abstract class Scene implements SceneInterface {
    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected SceneManager sceneManager;
    protected SpriteBatch batch;
    protected Texture img;
    protected BitmapFont font;
    protected String sceneText;

    protected Scene(SceneManager sceneManager, String texturePath, String sceneText) {
        this.sceneManager = sceneManager;
        this.camera = new OrthographicCamera();
        this.batch = new SpriteBatch();
        this.img = new Texture(Gdx.files.internal(texturePath));
        this.font = new BitmapFont();
        this.sceneText = sceneText;
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
    }

    public abstract void initialize();

    public abstract void update(float deltaTime);

    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(img, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        font.draw(batch, sceneText, 1, 450);
        batch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        batch.dispose();
        img.dispose();
        font.dispose();
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public abstract void handleInput();
}