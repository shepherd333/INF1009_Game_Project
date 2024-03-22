package com.mygdx.game.CollisionManagement.CollisionCriterias;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.EntityManagement.BucketActor;
import com.mygdx.game.EntityManagement.RaindropActor;
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
        return isSpacePressed && ((actor1 instanceof BucketActor && actor2 instanceof RaindropActor) ||
                (actor1 instanceof RaindropActor && actor2 instanceof BucketActor));
    }
}
