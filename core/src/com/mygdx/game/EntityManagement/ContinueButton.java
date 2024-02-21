package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;

public class ContinueButton extends ButtonEntity {

    public ContinueButton(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void activate() {
        // Implement continue button activation logic
        System.out.println("Continue Button Deactivated");
        setActivated(true);
    }

    @Override
    public void deactivate() {
        // Implement continue button deactivation logic
        System.out.println("Continue Button Deactivated");
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
