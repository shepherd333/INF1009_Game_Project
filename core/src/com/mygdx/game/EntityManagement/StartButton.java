package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;

public class StartButton extends ButtonEntity {
    public StartButton(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    @Override
    public void activate() {
        // Implement start button activation logic
        System.out.println("Start Button Activated");
        setActivated(true);
    }

    @Override
    public void deactivate() {
        // Implement start button deactivation logic
        System.out.println("Start Button Deactivated");
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