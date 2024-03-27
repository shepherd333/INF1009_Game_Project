package com.mygdx.game.EntityManagement.Bins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.EntityManagement.CollidableActor;

public class RecycleBinActor extends CollidableActor {
    private Array<Sprite> bins = new Array<>();

    public RecycleBinActor(TextureAtlas atlas) {
        String[] binTypes = {"Glass", "Paper", "Plastic", "Metal"};
        for (String type : binTypes) {
            Sprite bin = atlas.createSprite(type + "Bin");
            bin.setSize(200, 200);
            bins.add(bin);
        }

        setSize(bins.size * 200, 200);
        setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, Gdx.graphics.getHeight() - getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float startX = getX();
        for (int i = 0; i < bins.size; i++) {
            Sprite bin = bins.get(i);
            bin.setAlpha(parentAlpha);
            bin.setPosition(startX + i * 200, getY());
            bin.draw(batch);
        }
    }

    // act() and dispose() methods remain largely unchanged


    @Override
    public void act(float delta) {
        super.act(delta);
        // Add behavior here if needed
    }
}