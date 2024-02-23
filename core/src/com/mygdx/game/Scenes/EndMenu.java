package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EndMenu extends Scene {

    public EndMenu(SceneManager sceneManager) {
        super(sceneManager, "EndMenu.png", "This is the EndMenu Scene.");
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

        batch.draw(img, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        font.draw(batch,"This is the EndMenu Scene.", 1, 450);
        font.draw(batch,"Press X to transit to LeaderBoard Scene.", 1, 400);
        font.draw(batch,"Press SPACEBAR to transit to MainMenu Scene.", 1, 300);
        batch.end();
    }

    @Override
    public void handleInput() {
        // Handle other inputs specific to EndMenu...
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}