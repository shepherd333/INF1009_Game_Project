package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class RecycleBinActor extends CollidableActor {
    private Sprite[] sprites;
    private int numberOfBins = 4;

    public RecycleBinActor() {
        sprites = new Sprite[numberOfBins];
        Texture glassbinTexture = new Texture(Gdx.files.internal("GlassBin.png"));
        Texture paperbinTexture = new Texture(Gdx.files.internal("PaperBin.png"));
        Texture plasticbinTexture = new Texture(Gdx.files.internal("PlasticBin.png"));
        Texture metalbinTexture = new Texture(Gdx.files.internal("MetalBin.png"));

        // Create sprites for each bin type
        sprites[0] = new Sprite(glassbinTexture);
        sprites[1] = new Sprite(paperbinTexture);
        sprites[2] = new Sprite(plasticbinTexture);
        sprites[3] = new Sprite(metalbinTexture);

        for (Sprite sprite : sprites) {
            sprite.setSize(75, 75); // Set size for each sprite, adjust as needed
        }

        // This sets the size of the entire actor that contains all sprites
        setSize(numberOfBins * 75, 75); // Adjust based on actual sprite sizes

        // Position the actor at the top middle of the screen
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, Gdx.graphics.getHeight() - getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Draw each bin sprite beside each other
        for (int i = 0; i < numberOfBins; i++) {
            float x = getX() + i * 75; // Calculate the x position for each sprite
            batch.draw(sprites[i], x, getY(), 75, 75);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // You can add more behavior here if needed
    }

    public void dispose() {
        // Dispose of the textures of all sprites
        for (Sprite sprite : sprites) {
            sprite.getTexture().dispose();
        }
    }
}