package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ToxicWasteActor extends CollidableActor {
    private Sprite sprite;

    public ToxicWasteActor() {
        sprite = new Sprite(new Texture(Gdx.files.internal("toxicWaste.png")));
        sprite.setSize(80, 80); // Set your desired size

        float minX = 0; // Minimum X-coordinate
        float maxX = Gdx.graphics.getWidth() - 120; // Maximum X-coordinate, assuming ToxicWasteActor has a width of 120
        float minY = 0; // Minimum Y-coordinate
        float maxY = Gdx.graphics.getHeight() - 120; // Maximum Y-coordinate, assuming ToxicWasteActor has a height of 120

        float randomX = MathUtils.random(minX, maxX);
        float randomY = MathUtils.random(minY, maxY);
        setPosition(randomX,randomY);







    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setPosition(getX(), getY());
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}

