package com.mygdx.game.EntityManagement.Bins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.EntityManagement.CollidableActor;
import com.mygdx.game.enums.ItemType;

public class BinActor extends CollidableActor {
    private Sprite sprite;
    private ItemType acceptsType;
    private int positionIndex; // Position index to determine xPosition

    public BinActor(ItemType itemType, int positionIndex) {
        this.acceptsType = itemType;
        this.positionIndex = positionIndex;
        String texturePath = itemType.toString() + "Bin.png"; // Assuming naming convention holds
        sprite = new Sprite(new Texture(Gdx.files.internal(texturePath)));
        sprite.setSize(120, 120);

        // Position this actor
        float xPosition = 20 + (75 + 10) * positionIndex;
        float yPosition = Gdx.graphics.getHeight() - sprite.getHeight() - 20; // 20 pixels from the top
        setPosition(xPosition, yPosition);
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

    public ItemType getAcceptsType() {
        return this.acceptsType;
    }

    public boolean acceptsItemType(ItemType itemType) {
        return this.acceptsType == itemType;
    }
}
