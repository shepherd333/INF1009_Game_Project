package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.MetalBinActor;
import com.mygdx.game.EntityManagement.PaperBinActor;
import com.mygdx.game.EntityManagement.PlasticBinActor;
import com.mygdx.game.EntityManagement.GlassBinActor;

public class DropCollisionHandler extends BaseCollisionHandler {

    private GlassBinActor glassBin;
    private PaperBinActor paperBin;
    private PlasticBinActor plasticBin;
    private MetalBinActor metalBin;

    public DropCollisionHandler(Actor actor1, Actor actor2) {
        super(actor1, actor2);
    }

    @Override
    public void handleCollision() {
        if (actor1 instanceof BucketActor && actor2 instanceof MetalBinActor) {
            BucketActor bucketActor = (BucketActor) actor1;
            if (bucketActor.isHoldingItem()) {
                // Implement criteria for dropping item if needed
                bucketActor.setHoldingItem(false); // Drop the held item
                bucketActor.setHeldItem(null);
            }
        } else if (actor1 instanceof BucketActor && actor2 instanceof PaperBinActor) {
            BucketActor bucketActor = (BucketActor) actor1;
            if (bucketActor.isHoldingItem()) {
                // Implement criteria for dropping item if needed
                bucketActor.setHoldingItem(false); // Drop the held item
                bucketActor.setHeldItem(null);
            }
        } else if (actor1 instanceof BucketActor && actor2 instanceof PlasticBinActor) {
            BucketActor bucketActor = (BucketActor) actor1;
            if (bucketActor.isHoldingItem()) {
                // Implement criteria for dropping item if needed
                bucketActor.setHoldingItem(false); // Drop the held item
                bucketActor.setHeldItem(null);
            }
        }  else if (actor1 instanceof BucketActor && actor2 instanceof GlassBinActor) {
            BucketActor bucketActor = (BucketActor) actor1;
            if (bucketActor.isHoldingItem()) {
                // Implement criteria for dropping item if needed
                bucketActor.setHoldingItem(false); // Drop the held item
                bucketActor.setHeldItem(null);
            }
        }
    }
}
