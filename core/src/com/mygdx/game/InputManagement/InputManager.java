package com.mygdx.game.InputManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.mygdx.game.EntityManagement.BucketEntity;

public class InputManager {

    private BucketEntity bucket;

    public InputManager(BucketEntity bucket) {
        this.bucket = bucket;
    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            float newX = bucket.getX() - bucket.getSpeed();
            bucket.setX(newX);
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            float newX = bucket.getX() + bucket.getSpeed();
            bucket.setX(newX);
        }
    }
}
