package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.RaindropActor;
import com.badlogic.gdx.utils.Array;

public class PickUpCollisionHandler extends BaseCollisionHandler {
    private Array<RaindropActor> raindrops;

    public PickUpCollisionHandler(Actor actor1, Actor actor2, Array<RaindropActor> raindrops) {
        super(actor1, actor2);
        this.raindrops = raindrops;
    }

    @Override
    public void handleCollision() {
        if (actor1 instanceof BucketActor && actor2 instanceof RaindropActor) {
            handlePickUp((BucketActor) actor1, (RaindropActor) actor2);
        } else if (actor1 instanceof RaindropActor && actor2 instanceof BucketActor) {
            handlePickUp((BucketActor) actor2, (RaindropActor) actor1);
        }
    }

    private void handlePickUp(BucketActor bucket, RaindropActor raindrop) {
        // Remove the raindrop actor
        raindrop.remove();
        // Remove the raindrop from the array
        raindrops.removeValue(raindrop, true);
        // Assign a unique value to the bucket actor
        bucket.setPossessionValue(raindrop.getUniqueValue());
    }
}