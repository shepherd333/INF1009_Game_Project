package com.mygdx.game.CollisionManagement.CollisionCriterias;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.*;
import com.mygdx.game.InputManagement.InputManager;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PickUpCollisionCriteria implements Criterias {
    private InputManager inputManager;
    private Stage stage;

    public PickUpCollisionCriteria(Stage stage) {
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


        return isSpacePressed && (
                ((actor1 instanceof BucketActor && actor2 instanceof PaperItemsActor) ||
                        (actor1 instanceof PaperItemsActor && actor2 instanceof BucketActor)) ||
                        (isMetalItemsActor && (actor1 instanceof BucketActor || actor1 instanceof PaperItemsActor)) ||
                        (isGlassItemsActor && (actor1 instanceof BucketActor || actor1 instanceof PaperItemsActor)) ||
                        (isPlasticItemsActor && (actor1 instanceof BucketActor || actor1 instanceof PaperItemsActor))
        );
    }




}
