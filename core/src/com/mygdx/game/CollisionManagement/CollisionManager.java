package com.mygdx.game.CollisionManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.EntityManagement.BucketEntity;
import com.mygdx.game.EntityManagement.RaindropEntity;

import java.util.Iterator;

public class CollisionManager {
    private BucketEntity bucket;
    private Array<RaindropEntity> raindrops;

    public CollisionManager(BucketEntity bucket, Array<RaindropEntity> raindrops) {
        this.bucket = bucket;
        this.raindrops = raindrops;
    }

    public void checkCollisions() {
        Iterator<RaindropEntity> iter = raindrops.iterator();
        while (iter.hasNext()) {
            RaindropEntity raindrop = iter.next();
            if (raindrop.getBounds().overlaps(bucket.getBounds())) {
                Gdx.app.log("Collision Check", "Bucket at " + bucket.getBounds() + " Raindrop at " + raindrop.getBounds());
                raindrop.remove(); // Removes the actor from the stage
                iter.remove(); // Safely removes the raindrop from the array
                // Handle any additional collision response here
            }
        }
    }


}
