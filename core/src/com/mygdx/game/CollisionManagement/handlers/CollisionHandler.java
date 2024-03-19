package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.EntityManagement.BucketEntity;
import com.mygdx.game.EntityManagement.RaindropEntity;
import com.badlogic.gdx.Gdx;

public class CollisionHandler {
    private Actor actor1;
    private Actor actor2;
    private Array<RaindropEntity> raindrops;

    public CollisionHandler(Actor actor1, Actor actor2, Array<RaindropEntity> raindrops) {
        this.actor1 = actor1;
        this.actor2 = actor2;
        this.raindrops = raindrops;
        Gdx.app.log("CollisionHandler", "Initialized with actor1: " + actor1.getClass().getSimpleName() + ", actor2: " + actor2.getClass().getSimpleName());
    }

    public void handleCollision() {
        Gdx.app.log("CollisionHandler", "handleCollision method called");
        if (actor1 instanceof BucketEntity && actor2 instanceof RaindropEntity) {
            BucketEntity bucket = (BucketEntity) actor1;
            RaindropEntity raindrop = (RaindropEntity) actor2;
            if (bucket.getBounds().overlaps(raindrop.getBounds())) {
                raindrops.removeValue(raindrop, true); // Remove the raindrop from the array
                raindrop.remove(); // Remove the raindrop from the stage
                Gdx.app.log("CollisionHandler", "Collision detected: BucketEntity collided with RaindropEntity");
            }
        } else if (actor1 instanceof RaindropEntity && actor2 instanceof BucketEntity) {
            BucketEntity bucket = (BucketEntity) actor2;
            RaindropEntity raindrop = (RaindropEntity) actor1;
            if (bucket.getBounds().overlaps(raindrop.getBounds())) {
                raindrops.removeValue(raindrop, true); // Remove the raindrop from the array
                raindrop.remove(); // Remove the raindrop from the stage
                Gdx.app.log("CollisionHandler", "Collision detected: BucketEntity collided with BucketEntity");
            }
        }
    }
}