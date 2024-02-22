package com.mygdx.game.InputManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.EntityManagement.BucketEntity;
import com.mygdx.game.Scenes.SceneManager;

public class InputManager {

    private SceneManager sm;

    public InputManager(SceneManager sm) {
        this.sm = sm;
    }
    public static void handlePlayerInput(BucketEntity bucket) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.setX((float) (bucket.getX() - bucket.getSpeed() * deltaTime - 2));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.setX((float) (bucket.getX() + bucket.getSpeed() * deltaTime + 2));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            bucket.setY((float) (bucket.getY() + bucket.getSpeed() * deltaTime + 2));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            bucket.setY((float) (bucket.getY() - bucket.getSpeed() * deltaTime - 2));
        }
    }

    public void handleOpeningInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            sm.transitionTo("GamePlay", 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            sm.transitionTo("Leaderboard", 1);
        }
        //if (Gdx.input.isKeyPressed(Input.Keys.C)) {
        //    sm.transitionTo("EndMenu", 1);
        //}
        //if (Gdx.input.isKeyPressed(Input.Keys.V)) {
        //    sm.transitionTo("MainMenu", 1);
        //}
    }
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    public boolean mouseMoved(int screenX, int screenY) { return false; }
}
