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
        glassBin.setSize(200, 200);
        paperBin.setSize(200, 200);
        plasticBin.setSize(200, 200);
        metalBin.setSize(200, 200);

        // This sets the size of the entire actor that contains all sprites
        setSize(4 * 200, 200); // Assuming each sprite is 75x75

        // Position the actor at the top middle of the screen
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, Gdx.graphics.getHeight() - getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw each bin sprite beside each other
        glassBin.setAlpha(parentAlpha);
        paperBin.setAlpha(parentAlpha);
        plasticBin.setAlpha(parentAlpha);
        metalBin.setAlpha(parentAlpha);

        // Start drawing with the batch
        batch.begin();

        // Calculate starting X position for the first bin
        float startX = getX();

        // Draw each bin sprite beside each other using their draw method
        glassBin.setPosition(startX, getY());
        glassBin.draw(batch);

        paperBin.setPosition(startX + 200, getY());
        paperBin.draw(batch);

        plasticBin.setPosition(startX + 2 * 200, getY());
        plasticBin.draw(batch);

        metalBin.setPosition(startX + 3 * 200, getY());
        metalBin.draw(batch);

        // End drawing
        batch.end();
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