package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;

public class PlayButton extends ButtonEntity {

    public PlayButton(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void activate() {
        // Implement play button activation logic
        System.out.println("Play Button Deactivated");
        setActivated(true);
    }

    @Override
    public void deactivate() {
        // Implement play button deactivation logic
        System.out.println("Play Button Deactivated");
        setActivated(false);
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
