package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;

public class LeaderboardButton extends ButtonEntity {

    public LeaderboardButton(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void activate() {
        // Implement start button activation logic
    }

    @Override
    public void deactivate() {
        // Implement start button deactivation logic
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void update() {

    }

    @Override
    public void movement() {

    }
}
