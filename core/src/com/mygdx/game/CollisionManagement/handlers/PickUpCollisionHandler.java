package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
        else if (actor1 instanceof BucketActor && actor2 instanceof GlassItemsActor) {
            BucketActor bucket = (BucketActor) actor1;
            GlassItemsActor glassitem = (GlassItemsActor) actor2;
            if (!bucket.isItemPickedUp()) {
                handlePickUpGlass(bucket, glassitem);
                bucket.setItemPickedUp(true); // Mark item as picked up
            }
        }
    }

    private void handlePickUp(BucketActor bucket, PaperItemsActor paperitem) {
        // Remove the paper actor
        paperitem.remove();
        // Remove the raindrop from the array
        paperitems.removeValue(paperitem, true);
        // Assign value '1' to the bucket actor
        bucket.setItemType(1);
        bucket.setHeldItemSprite(new Texture(Gdx.files.internal("paperBag.png")));
        Gdx.app.log("PickUpCollisionHandler", "Item type assigned: " + bucket.getItemType());
    }

    private void handlePickUpMetal(BucketActor bucket, MetalItemsActor metalitem) {
        // Remove the metal actor
        metalitem.remove();
        // Remove the raindrop from the array
        metalitems.removeValue(metalitem, true);
        // Assign value '2' to the bucket actor
        bucket.setItemType(2);
        bucket.setHeldItemSprite(new Texture(Gdx.files.internal("metalCanSmall.png")));
        Gdx.app.log("PickUpCollisionHandler", "Item type assigned: " + bucket.getItemType());
    }

    private void handlePickUpGlass(BucketActor bucket, GlassItemsActor glassitem) {
        // Remove the glass actor
        glassitem.remove();
        // Remove the raindrop from the array
        glassitems.removeValue(glassitem, true);
        // Assign value '3' to the bucket actor
        bucket.setItemType(3);
        bucket.setHeldItemSprite(new Texture(Gdx.files.internal("glassSodaBottle.png")));
        Gdx.app.log("PickUpCollisionHandler", "Item type assigned: " + bucket.getItemType());
    }
}