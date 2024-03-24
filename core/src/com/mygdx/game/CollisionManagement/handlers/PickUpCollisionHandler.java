package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.*;
import com.badlogic.gdx.utils.Array;

public class PickUpCollisionHandler extends BaseCollisionHandler {
    private Array<PaperItemsActor> paperitems;
    private Array<MetalItemsActor> metalitems;
    private Array<GlassItemsActor> glassitems;
    private Array<PlasticItemsActor> plasticitems;

    public PickUpCollisionHandler(Actor actor1, Actor actor2, Array<PaperItemsActor> paperitems, Array<MetalItemsActor> metalitems, Array<GlassItemsActor> glassitems, Array<PlasticItemsActor> plasticitems) {
        super(actor1, actor2);
        this.paperitems = paperitems;
        this.metalitems = metalitems;
        this.glassitems = glassitems;
        this.plasticitems = plasticitems;
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
        }
        else if (actor1 instanceof BucketActor && actor2 instanceof MetalItemsActor) {
            BucketActor bucket = (BucketActor) actor1;
            MetalItemsActor metalitem = (MetalItemsActor) actor2;
            if (!bucket.isItemPickedUp()) {
                handlePickUpMetal(bucket, metalitem);
                bucket.setItemPickedUp(true); // Mark item as picked up
            }
        }
        else if (actor1 instanceof BucketActor && actor2 instanceof GlassItemsActor) {
            BucketActor bucket = (BucketActor) actor1;
            GlassItemsActor glassitem = (GlassItemsActor) actor2;
            if (!bucket.isItemPickedUp()) {
                handlePickUpGlass(bucket, glassitem);
                bucket.setItemPickedUp(true); // Mark item as picked up
            }
        }
        else if (actor1 instanceof BucketActor && actor2 instanceof PlasticItemsActor) {
            BucketActor bucket = (BucketActor) actor1;
            PlasticItemsActor plasticitem = (PlasticItemsActor) actor2;
            if (!bucket.isItemPickedUp()) {
                handlePickUpPlastic(bucket, plasticitem);
                bucket.setItemPickedUp(true); // Mark item as picked up
            }
        }
    }

    private void handlePickUp(BucketActor bucket, PaperItemsActor paperitem) {
        // Remove the paper actor
        paperitem.remove();
        // Remove the raindrop from the array
        paperitems.removeValue(paperitem, true);
        // Assign a unique value to the bucket actor
        bucket.setPossessionValue(paperitem.getUniqueValue());
    }

    private void handlePickUpMetal(BucketActor bucket, MetalItemsActor metalitem) {
        // Remove the metal actor
        metalitem.remove();
        // Remove the raindrop from the array
        metalitems.removeValue(metalitem, true);
        // Assign a unique value to the bucket actor
        bucket.setPossessionValue(metalitem.getUniqueValue());
    }

    private void handlePickUpGlass(BucketActor bucket, GlassItemsActor glassitem) {
        // Remove the glass actor
        glassitem.remove();
        // Remove the raindrop from the array
        glassitems.removeValue(glassitem, true);
        // Assign a unique value to the bucket actor
        bucket.setPossessionValue(glassitem.getUniqueValue());
    }

    private void handlePickUpPlastic(BucketActor bucket, PlasticItemsActor plasticitem) {
        // Remove the plastic actor
        plasticitem.remove();
        // Remove the raindrop from the array
        plasticitems.removeValue(plasticitem, true);
        // Assign a unique value to the bucket actor
        bucket.setPossessionValue(plasticitem.getUniqueValue());
    }
}