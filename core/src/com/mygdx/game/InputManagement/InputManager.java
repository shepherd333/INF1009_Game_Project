package com.mygdx.game.InputManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.EntityManagement.BucketEntity;
import com.mygdx.game.Scenes.SceneManager;

// InputManager is responsible for handling all input-related functionality across the game.
// It processes player inputs and controls scene transitions based on those inputs.
public class InputManager {

    // Reference to SceneManager to manage scene transitions based on input.
    private SceneManager sm;

    // Constructor takes a SceneManager instance to allow input-based scene transitions.
    public InputManager(SceneManager sm) {
        this.sm = sm;
    }

    // Static method to handle player inputs for moving a BucketEntity.
    // Note: Making this method static restricts it from accessing instance variables.
    public static void handlePlayerInput(BucketEntity bucket) {
        // Calculate the time passed since the last frame to ensure smooth movement.
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Move the bucket left if the LEFT key is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.setX((float) (bucket.getX() - bucket.getSpeed() * deltaTime - 2));
        }
        // Move the bucket right if the RIGHT key is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.setX((float) (bucket.getX() + bucket.getSpeed() * deltaTime + 2));
        }
        // Move the bucket up if the UP key is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            bucket.setY((float) (bucket.getY() + bucket.getSpeed() * deltaTime + 2));
        }
        // Move the bucket down if the DOWN key is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            bucket.setY((float) (bucket.getY() - bucket.getSpeed() * deltaTime - 2));
        }
    }

    // Handles inputs related to opening different scenes like gameplay, leaderboard, etc.
    public void handleMMInput() {
        // Transition to the GamePlay scene if the Z key is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            sm.transitionTo("GamePlay", 1);
        }
        // Transition to the Leaderboard scene if the X key is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            sm.transitionTo("Leaderboard", 1);
        }
        // Additional scene transitions can be added here for other keys.
    }

    public void handleLBInput() {
        // Transition to the GamePlay scene if the Z key is pressed.
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            sm.transitionTo("GamePlay", 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            sm.transitionTo("MainMenu", 1);
        }
        // Additional scene transitions can be added here for other keys.
    }

    public void handleEndInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            sm.transitionTo("GamePlay", 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            sm.transitionTo("MainMenu", 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            sm.transitionTo("Leaderboard", 1);
        }
    }

    // The following methods are placeholders for touch and mouse events.
    // Currently, they return false to indicate they do not handle the event,
    // but they can be implemented to add touch or mouse-based interactions.
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    public boolean mouseMoved(int screenX, int screenY) { return false; }
}

