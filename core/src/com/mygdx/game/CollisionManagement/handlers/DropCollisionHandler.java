package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.CollidableActor;

public class DropCollisionHandler {
    private int points;

    public DropCollisionHandler() {
        points = 0;
    }

    public void handleCollision(BucketActor bucket, CollidableActor paperBin, CollidableActor metalBin, CollidableActor glassBin) {
        if (bucket.getBounds().overlaps(paperBin.getBounds())) {
            updatePoints(bucket, paperBin);
        } else if (bucket.getBounds().overlaps(metalBin.getBounds())) {
            updatePoints(bucket, metalBin);
        } else if (bucket.getBounds().overlaps(glassBin.getBounds())) {
            updatePoints(bucket, glassBin);
        }
        bucket.setItemPickedUp(false); // Allow the bucket to pick up other actors again
        bucket.setHeldItemSprite(null); // Remove the held item sprite

        // Log the state of the bucket
        Gdx.app.log("DropCollisionHandler", "Item picked up: " + bucket.isItemPickedUp());
    }

    private void updatePoints(BucketActor bucket, CollidableActor bin) {
        if (bucket.getItemType() == bin.getValue()) {
            points += 10;
        } else {
            points -= 10;
        }
        bucket.setItemType(0); // Reset the bucket's value
    }

    public int getPoints() {
        return points;
    }
}