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
            handlePickUp((BucketActor) actor1, (PaperItemsActor) actor2);
        } else if (actor1 instanceof PaperItemsActor && actor2 instanceof BucketActor) {
            handlePickUp((BucketActor) actor2, (PaperItemsActor) actor1);
        } else if (actor1 instanceof BucketActor && actor2 instanceof MetalItemsActor) {
            handlePickUpMetal((BucketActor) actor1, (MetalItemsActor) actor2);
        } else if (actor1 instanceof MetalItemsActor && actor2 instanceof BucketActor) {
        handlePickUpMetal((BucketActor) actor2, (MetalItemsActor) actor1);
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