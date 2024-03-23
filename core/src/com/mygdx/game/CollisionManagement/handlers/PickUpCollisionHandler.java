package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.GlassItemsActor;
import com.mygdx.game.EntityManagement.MetalItemsActor;
import com.mygdx.game.EntityManagement.PaperItemsActor;
import com.badlogic.gdx.utils.Array;

public class PickUpCollisionHandler extends BaseCollisionHandler {
    private Array<PaperItemsActor> paperitems;
    private Array<MetalItemsActor> metalitems;
    private Array<GlassItemsActor> glassitems;

    public PickUpCollisionHandler(Actor actor1, Actor actor2, Array<PaperItemsActor> paperitems, Array<MetalItemsActor> metalitems, Array<GlassItemsActor> glassitems) {
        super(actor1, actor2);
        this.paperitems = paperitems;
        this.metalitems = metalitems;
        this.glassitems = glassitems;
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
        } else if (actor1 instanceof BucketActor && actor2 instanceof GlassItemsActor) {
            handlePickUpGlass((BucketActor) actor1, (GlassItemsActor) actor2);
        } else if (actor1 instanceof GlassItemsActor && actor2 instanceof BucketActor) {
            handlePickUpGlass((BucketActor) actor2, (GlassItemsActor) actor1);
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
}