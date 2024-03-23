package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.MetalItemsActor;
import com.mygdx.game.EntityManagement.PaperItemsActor;
import com.badlogic.gdx.utils.Array;

public class PickUpCollisionHandler extends BaseCollisionHandler {
    private Array<PaperItemsActor> paperitems;
    private Array<MetalItemsActor> metalitems;

    public PickUpCollisionHandler(Actor actor1, Actor actor2, Array<PaperItemsActor> raindrops, Array<MetalItemsActor> metalitems) {
        super(actor1, actor2);
        this.paperitems = raindrops;
        this.metalitems = metalitems;
    }

    @Override
    public void handleCollision() {
        if (actor1 instanceof BucketActor && actor2 instanceof PaperItemsActor) {
            BucketActor bucket = (BucketActor) actor1;
            PaperItemsActor paperitem = (PaperItemsActor) actor2;
            if (!bucket.isItemPickedUp()) {
                handlePickUp(bucket, paperitem);
                bucket.setItemPickedUp(true); // Mark item as picked up
            }
        } else if (actor1 instanceof BucketActor && actor2 instanceof MetalItemsActor) {
            BucketActor bucket = (BucketActor) actor1;
            MetalItemsActor metalitem = (MetalItemsActor) actor2;
            if (!bucket.isItemPickedUp()) {
                handlePickUpMetal(bucket, metalitem);
                bucket.setItemPickedUp(true); // Mark item as picked up
            }
        }
    }

    private void handlePickUp(BucketActor bucket, PaperItemsActor paperitem) {
        // Remove the raindrop actor
        paperitem.remove();
        // Remove the raindrop from the array
        paperitems.removeValue(paperitem, true);
        // Assign a unique value to the bucket actor
        bucket.setPossessionValue(paperitem.getUniqueValue());
    }

    private void handlePickUpMetal(BucketActor bucket, MetalItemsActor metalitem) {
        // Remove the raindrop actor
        metalitem.remove();
        // Remove the raindrop from the array
        metalitems.removeValue(metalitem, true);
        // Assign a unique value to the bucket actor
        bucket.setPossessionValue(metalitem.getUniqueValue());
    }
}