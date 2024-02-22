package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Leaderboard extends Scene {
    public Vector3 tmp = new Vector3();

    public Leaderboard(SceneManager sceneManager) {
        super(sceneManager, "Leaderboard.png", "This is the LeaderBoard Scene.");
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
        font.draw(batch,"Press Z to transit to GamePlay Scene.", 1, 400);
        font.draw(batch,"Press C to transit to EndMenu Scene.", 1, 350);
        font.draw(batch,"Press V to transit to MainMenu Scene.", 1, 300);
        font.draw(batch,"Press M to mute/unmute the audio.", 1, 250);
        batch.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            camera.unproject(tmp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            // Add any additional touch input handling here
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}