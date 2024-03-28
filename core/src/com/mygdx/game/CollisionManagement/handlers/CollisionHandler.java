package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.EntityManagement.Movers.BucketActor;
import com.mygdx.game.EntityManagement.Movers.ItemActor;

public class CollisionHandler extends BaseCollisionHandler {
    private Stage stage;

    public CollisionHandler(Actor actor1, Actor actor2, Stage stage) {
        super(actor1, actor2);
        this.stage = stage;
    }

    @Override
    public void handleCollision() {
        if (actor1 instanceof BucketActor && actor2 instanceof ItemActor) {
            handlePickUp((BucketActor) actor1, (ItemActor) actor2);
        } else if (actor1 instanceof ItemActor && actor2 instanceof BucketActor) {
            handlePickUp((BucketActor) actor2, (ItemActor) actor1);
        }
    }

    private void handlePickUp(BucketActor bucket, ItemActor item) {
        if (!bucket.isItemPickedUp()) {
            // Assign item's properties to the bucket
            bucket.setHeldItemType(item.getItemType());
            bucket.setHeldItemSprite(item.getTextureRegion());
            bucket.setItemPickedUp(true);
            bucket.holdItem(item);
            item.removeItem();
            Gdx.app.log("CollisionHandler", "Item picked up: " + item.getItemType());
        }
    }
}
