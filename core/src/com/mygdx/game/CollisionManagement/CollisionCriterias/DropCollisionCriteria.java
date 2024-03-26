package com.mygdx.game.CollisionManagement.CollisionCriterias;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.EntityManagement.*;
import com.mygdx.game.InputManagement.InputManager;

public class DropCollisionCriteria implements Criterias {
    private InputManager inputManager;
    private Stage stage;

    public DropCollisionCriteria(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
        this.inputManager = new InputManager(this.stage); // Pass the stage to the InputManager constructor
    }

    @Override
    public boolean meetsCriteria(Actor actor1, Actor actor2) {
        boolean isSpacePressed = this.inputManager.isSpacePressed();
        System.out.println("Space pressed: " + isSpacePressed);

        // Check if actor2 is a MetalItemActor
        boolean isMetalItemsActor = actor2 instanceof MetalItemsActor;
        boolean isGlassItemsActor = actor2 instanceof GlassItemsActor;
        boolean isPlasticItemsActor = actor2 instanceof PlasticItemsActor;
        boolean isTrashItemsActor = actor2 instanceof  TrashItemsActor;
        if (actor1 instanceof BucketActor && actor2 instanceof MetalBinActor) {
            BucketActor bucketActor = (BucketActor) actor1;
            return bucketActor.isHoldingItem(); // Return true if the bucket is holding an item
        }
        return false;
    }
}
