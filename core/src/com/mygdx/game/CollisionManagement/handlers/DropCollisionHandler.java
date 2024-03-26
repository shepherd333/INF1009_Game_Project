package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.CollidableActor;

public class DropCollisionHandler extends BaseCollisionHandler {
    private int points;

    public DropCollisionHandler(Actor actor1, Actor actor2) {
        super(actor1, actor2);
        points = 0;
        Gdx.app.log("DropCollisionHandler", "DropCollisionHandler created");
    }

    @Override
    public void handleCollision() {
        Gdx.app.log("DropCollisionHandler", "handleCollision called");
        BucketActor bucket = (BucketActor) actor1;
        CollidableActor bin = (CollidableActor) actor2;

        updatePoints(bucket, bin);

        bucket.setItemPickedUp(false); // Allow the bucket to pick up other actors again
        bucket.setHeldItemSprite(null); // Remove the held item sprite

        // Log the state of the bucket
        Gdx.app.log("DropCollisionHandler", "Item picked up: " + bucket.isItemPickedUp());
    }

    private void updatePoints(BucketActor bucket, CollidableActor bin) {
        if (bucket.getItemType() == bin.getValue()) {
            points += 10;
            bucket.setItemType(0); // Reset the bucket's value
        } else {
            points -= 10;
        }
        bucket.setItemType(0); // Reset the bucket's value
    }

    public int getPoints() {
        return points;
    }
}