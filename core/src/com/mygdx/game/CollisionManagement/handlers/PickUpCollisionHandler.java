package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.PaperActor;
import com.badlogic.gdx.utils.Array;

public class PickUpCollisionHandler extends BaseCollisionHandler {
    private Array<PaperActor> papers;
    public PickUpCollisionHandler(Actor actor1, Actor actor2, Array<PaperActor> papers) {
        super(actor1, actor2);
        this.papers = papers;
    }

    @Override
    public void handleCollision() {
        if (actor1 instanceof BucketActor && actor2 instanceof PaperActor) {
            handlePickUp((BucketActor) actor1, (PaperActor) actor2);
        } else if (actor1 instanceof PaperActor && actor2 instanceof BucketActor) {
            handlePickUp((BucketActor) actor2, (PaperActor) actor1);
        }
    }

    private void handlePickUp(BucketActor bucket, PaperActor paper) {
        // Remove the paper actor
        paper.remove();
        // Assign a unique value to the bucket actor
        bucket.setPossessionValue(paper.getUniqueValue());
    }
}