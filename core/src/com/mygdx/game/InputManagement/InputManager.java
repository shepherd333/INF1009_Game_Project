package com.mygdx.game.InputManagement;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.EntityManagement.BucketEntity;

public class InputManager {
    private Stage stage;

    public InputManager(Stage stage) {
        this.stage = stage;
    }

    public void handleInput(float deltaTime) {
        // Assuming you have a way to reference your BucketEntity, e.g., directly or through the stage's actors
        for (Actor actor : stage.getActors()) {
            if (actor instanceof BucketEntity) {
                BucketEntity bucket = (BucketEntity) actor;
                processBucketMovement(bucket, deltaTime);
            }
        }
    }

    private void processBucketMovement(BucketEntity bucket, float deltaTime) {
        float speed = bucket.getSpeed(); // Ensure BucketEntity has a getSpeed method

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.moveBy(-speed * deltaTime, 0); // Move left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.moveBy(speed * deltaTime, 0); // Move right
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            bucket.moveBy(0, speed * deltaTime); // Move up
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            bucket.moveBy(0, -speed * deltaTime); // Move down
        }
    }
}

     //Handles inputs related to opening different scenes like gameplay, leaderboard, etc.
//    public void handleMMInput() {
//        // Transition to the GamePlay scene if the Z key is pressed.
//        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
//            sm.transitionTo("GamePlay", 1);
//        }
//        // Transition to the Leaderboard scene if the X key is pressed.
//        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
//            sm.transitionTo("Leaderboard", 1);
//        }
//        // Additional scene transitions can be added here for other keys.
//    }
//
//    public void handleLBInput() {
//        // Transition to the GamePlay scene if the Z key is pressed.
//        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
//            sm.transitionTo("GamePlay", 1);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//            sm.transitionTo("MainMenu", 1);
//        }
//        // Additional scene transitions can be added here for other keys.
//    }
//
//    public void handleEndInput(){
//        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
//            sm.transitionTo("GamePlay", 1);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
//            sm.transitionTo("MainMenu", 1);
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
//            sm.transitionTo("Leaderboard", 1);
//        }
//    }
//
//    public void handleGameInput(){
//        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
//            // The condition here assumes isCurrentSceneGamePlay() returns true if GamePlay scene is active
//            // For unpausing, this check is not strictly necessary unless you want to restrict it further
//            if (sm.getCurrentScene() instanceof PauseMenu || sm.isCurrentSceneGamePlay()) {
//                sm.togglePause();
//            }
//        }
//    }

//    // The following methods are placeholders for touch and mouse events.
//    // Currently, they return false to indicate they do not handle the event,
//    // but they can be implemented to add touch or mouse-based interactions.
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
//    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
//    public boolean mouseMoved(int screenX, int screenY) { return false; }
//}

