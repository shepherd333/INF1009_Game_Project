package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Game_Engine extends ApplicationAdapter {
    @Override
    public void create() {
        // This method will be called when your game is created.
        // Initialize your game here.
    }

    @Override
    public void render() {      
        // This method will be called every frame.
        // Update and render your game here.

        //clear the screen to red.
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose() {
        // This method will be called when your game is closed.
        // Dispose of your game's resources here.
    }
}
