package com.mygdx.game.SMLM;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimulationLifecycleManager {
    private State currentState;

    public SimulationLifecycleManager() {
        // Initially set to the main menu state or any starting state
        this.currentState = new OpeningState(this);
    }

    // Sets the current state of the game
    public void setState(State newState) {
        this.currentState = newState;
        this.currentState.enterState();
    }

    // Called to update the game state
    public void update(float delta) {
        currentState.update(delta);
    }

    // Called to render the game state
    public void render(SpriteBatch batch) {
        currentState.render(batch);
    }

    // A generic method to transition to the next state, which can be customized
    public void nextState() {
        currentState.nextState();
    }
}
