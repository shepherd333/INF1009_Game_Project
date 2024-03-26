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
        boolean isMetalBinActor = actor2 instanceof MetalBinActor;
        boolean isGlassBinActor = actor2 instanceof GlassBinActor;
        boolean isPlasticBinActor = actor2 instanceof PlasticBinActor;
        boolean isTrashBinActor = actor2 instanceof  TrashBinActor;
        return isSpacePressed && (
                ((actor1 instanceof BucketActor && actor2 instanceof PaperBinActor) ||
                        (actor1 instanceof PaperBinActor && actor2 instanceof BucketActor)) ||
                        (isMetalBinActor && (actor1 instanceof BucketActor)) ||
                        (isGlassBinActor && (actor1 instanceof BucketActor)) ||
                        (isPlasticBinActor && (actor1 instanceof BucketActor)) ||
                        (isTrashBinActor && (actor1 instanceof BucketActor))
        );
    }
}
