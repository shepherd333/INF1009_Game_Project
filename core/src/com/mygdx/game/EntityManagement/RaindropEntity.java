package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Texture;

public class RaindropEntity extends MovingEntities {
    public RaindropEntity(Texture texture, float x, float y, float speed) {
        super(texture.toString(), x, y, speed, true); // Droplets are AI-controlled
    }

    @Override
    public void update() {
    }
}