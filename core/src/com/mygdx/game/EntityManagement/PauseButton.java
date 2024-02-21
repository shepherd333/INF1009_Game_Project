package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;
public class PauseButton extends ButtonEntity {

    public PauseButton(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void activate() {
        // Implement pause button activation logic
        System.out.println("Pause Button Deactivated");
        setActivated(true);
    }

    @Override
    public void deactivate() {
        // Implement pause button deactivation logic
        System.out.println("Pause Button Deactivated");
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
