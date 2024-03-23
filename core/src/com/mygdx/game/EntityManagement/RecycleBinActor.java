package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RecycleBinActor extends CollidableActor {
    private Sprite glassBin, paperBin, plasticBin, metalBin;

    public RecycleBinActor() {
        // Load textures for each bin type
        glassBin = new Sprite(new Texture(Gdx.files.internal("GlassBin.png")));
        paperBin = new Sprite(new Texture(Gdx.files.internal("PaperBin.png")));
        plasticBin = new Sprite(new Texture(Gdx.files.internal("PlasticBin.png")));
        metalBin = new Sprite(new Texture(Gdx.files.internal("MetalBin.png")));

        // Set size for each sprite
        glassBin.setSize(75, 75);
        paperBin.setSize(75, 75);
        plasticBin.setSize(75, 75);
        metalBin.setSize(75, 75);

        // This sets the size of the entire actor that contains all sprites
        setSize(4 * 75, 75); // Assuming each sprite is 75x75

        // Position the actor at the top middle of the screen
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, Gdx.graphics.getHeight() - getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw each bin sprite beside each other
        float startX = getX(); // Starting X position for the first bin
        batch.draw(glassBin, startX, getY(), 75, 75);
        batch.draw(paperBin, startX + 75, getY(), 75, 75);
        batch.draw(plasticBin, startX + 2 * 75, getY(), 75, 75);
        batch.draw(metalBin, startX + 3 * 75, getY(), 75, 75);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Add behavior here if needed
    }

    public void dispose() {
        // Dispose of the textures for all bins
        glassBin.getTexture().dispose();
        paperBin.getTexture().dispose();
        plasticBin.getTexture().dispose();
        metalBin.getTexture().dispose();
    }
}