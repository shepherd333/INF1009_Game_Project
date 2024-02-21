package com.mygdx.game.InputManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.EntityManagement.BucketEntity;

public class InputManager {
    public static void handlePlayerInput(BucketEntity bucket) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.setX((float) (bucket.getX() - bucket.getSpeed() * deltaTime));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.setX((float) (bucket.getX() + bucket.getSpeed() * deltaTime));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            bucket.setY((float) (bucket.getY() + bucket.getSpeed() * deltaTime));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            bucket.setY((float) (bucket.getY() - bucket.getSpeed() * deltaTime));
        }
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    public boolean mouseMoved(int screenX, int screenY) { return false; }
}
