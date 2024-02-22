package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Game_Engine;

public class PauseMenu extends Scene {

    public PauseMenu(SceneManager sceneManager) {
        super(sceneManager, "PauseMenu.png", "This is the PauseMenu Scene.");
    }

    @Override
    public void initialize() {
        // Initialize any additional resources if needed
    }

    @Override
    public void update(float deltaTime) {
        // Handle any animations or transitions in the menu.
    }

    @Override
    public void render() {
        super.render();
        batch.begin();
        // Draw any additional elements specific to PauseMenu
        batch.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            Game_Engine.isMusicMuted = !Game_Engine.isMusicMuted;
        }
        // Handle other inputs specific to PauseMenu...
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}