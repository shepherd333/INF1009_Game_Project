package com.mygdx.game.CollisionManagement.CollisionCriterias;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.*;
import com.mygdx.game.InputManagement.InputManager;
import com.badlogic.gdx.scenes.scene2d.Stage;

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
        if (!(actor1 instanceof BucketActor) && !(actor2 instanceof BucketActor)) {
            return false;
        }
        boolean isSpacePressed = this.inputManager.isSpacePressed();
        System.out.println("D key pressed: " + isSpacePressed);

        BucketActor bucketActor = null;
        if (actor1 instanceof BucketActor) {
            bucketActor = (BucketActor) actor1;
        } else if (actor2 instanceof BucketActor) {
            bucketActor = (BucketActor) actor2;
        }

        boolean isBucketActor = bucketActor != null;
        System.out.println("Is BucketActor: " + isBucketActor);

        boolean hasValueAssigned = isBucketActor && bucketActor.getItemType() >= 1 && bucketActor.getItemType() <= 5;
        System.out.println("Has value assigned: " + hasValueAssigned);

        boolean isBinActor1 = actor1 instanceof PaperBinActor || actor1 instanceof MetalBinActor || actor1 instanceof GlassBinActor || actor1 instanceof PlasticBinActor || actor1 instanceof TrashBinActor;
        boolean isBinActor2 = actor2 instanceof PaperBinActor || actor2 instanceof MetalBinActor || actor2 instanceof GlassBinActor || actor2 instanceof PlasticBinActor || actor2 instanceof TrashBinActor;

        System.out.println("Is BinActor1: " + isBinActor1);
        System.out.println("Is BinActor2: " + isBinActor2);

        boolean meetsCriteria = isSpacePressed && hasValueAssigned && ((isBucketActor && isBinActor2) || (isBucketActor && isBinActor1));

        if (meetsCriteria) {
            System.out.println("Drop Criteria met successfully");
        }

        return meetsCriteria;
    }
}