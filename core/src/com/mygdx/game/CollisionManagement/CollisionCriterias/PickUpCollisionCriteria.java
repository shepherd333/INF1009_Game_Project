package com.mygdx.game.CollisionManagement.CollisionCriterias;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.CollisionManagement.CollisionCriterias.Criterias;
import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.Items.ItemActor;
import com.mygdx.game.InputManagement.InputManager;

public class PickUpCollisionCriteria implements Criterias {
    private InputManager inputManager;
    private Stage stage;

    public PickUpCollisionCriteria(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
        this.inputManager = new InputManager(this.stage);
    }

    @Override
    public boolean meetsCriteria(Actor actor1, Actor actor2) {
        boolean isSpacePressed = this.inputManager.isSpacePressed();
        boolean isBucketActor1 = actor1 instanceof BucketActor;
        boolean isBucketActor2 = actor2 instanceof BucketActor;
        boolean isItemActor1 = actor1 instanceof ItemActor;
        boolean isItemActor2 = actor2 instanceof ItemActor;

        return isSpacePressed && ((isBucketActor1 && isItemActor2) || (isBucketActor2 && isItemActor1));
    }
}

