package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Scene {

    public Scene(SceneManager sceneManager) {
    }

    public abstract void initialize();
    public abstract void update(float delta);
    public void render() {

    }
    public void resize(int width, int height) {

    }
    public void dispose() {
    }

    public void onShow() {
        // This method will be called when the scene is shown
    }
}
