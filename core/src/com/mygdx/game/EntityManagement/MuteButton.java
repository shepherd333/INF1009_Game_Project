package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;

public class MuteButton extends ButtonEntity {

    public MuteButton(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void activate() {
        // Implement mute button activation logic
        System.out.println("Mute Button Deactivated");
        setActivated(true);
    }

    @Override
    public void deactivate() {
        // Implement mute button deactivation logic
        System.out.println("Mute Button Deactivated");
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
