package com.mygdx.game.EntityManagement.Static;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.EntityManagement.Foundation.CollidableActor;
import com.mygdx.game.enums.ItemType;

public class BinActor extends CollidableActor {
    private Sprite sprite;
    private ItemType acceptsType;
    private int positionIndex; // Position index to determine xPosition
    private ItemType itemType;

    public BinActor(ItemType itemType, int positionIndex) {
        this.acceptsType = itemType;
        this.positionIndex = positionIndex;
        String texturePath = itemType.toString() + "Bin.png"; // Assuming naming convention holds
        sprite = new Sprite(new Texture(Gdx.files.internal(texturePath)));
        sprite.setSize(200, 200);

        // Position this actor
        float xPosition = 20 + (75 + 150) * positionIndex;
        float yPosition = Gdx.graphics.getHeight() - sprite.getHeight() - 50; // 20 pixels from the top
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

    public ItemType getItemType() {
        return itemType;
    }
}
