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

        boolean isBucketActor1 = actor1 instanceof BucketActor;
        boolean isBucketActor2 = actor2 instanceof BucketActor;

        boolean isItemActor1 = actor1 instanceof PaperItemsActor || actor1 instanceof MetalItemsActor || actor1 instanceof GlassItemsActor || actor1 instanceof PlasticItemsActor || actor1 instanceof TrashItemsActor;
        boolean isItemActor2 = actor2 instanceof PaperItemsActor || actor2 instanceof MetalItemsActor || actor2 instanceof GlassItemsActor || actor2 instanceof PlasticItemsActor || actor2 instanceof TrashItemsActor;

        boolean meetsCriteria = isSpacePressed && ((isBucketActor1 && isItemActor2) || (isBucketActor2 && isItemActor1));

        if (meetsCriteria) {
            System.out.println("Pick Up Criteria met successfully");
        }

        return meetsCriteria;
    }
}
