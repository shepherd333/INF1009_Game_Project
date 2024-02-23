package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

// The EndMenu class represents the end menu scene of the game.
//// This is where players are taken after the game concludes, offering options to navigate to other parts of the game.
//public class EndMenu extends Scene
public class EndMenu extends Scene {

    // Constructor that calls the superclass constructor to initialize the scene with a specific background and description.
    public EndMenu(SceneManager sceneManager) {
        super(sceneManager, "EndMenu.png", "This is the EndMenu Scene.");
    }

    @Override
    public void initialize() {
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void render() {
        //Calls super to handle common render functions
        super.render();
        batch.begin();
        //Draw the Scene
        batch.draw(img, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        font.draw(batch,"This is the EndMenu Scene.", 1, 450);
        font.draw(batch,"Press X to transit to LeaderBoard Scene.", 1, 400);
        font.draw(batch,"Press SPACEBAR to transit to MainMenu Scene.", 1, 300);
        batch.end();
    }

    @Override
    public void handleInput() {
    }

    //Called when the scene is no longer required, and for the resources to be disposed.
    @Override
    public void dispose() {
        super.dispose();
    }
}