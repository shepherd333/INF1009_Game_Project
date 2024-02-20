package com.mygdx.game.SMLM;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameplayState implements State {
    private SimulationLifecycleManager manager;

    public GameplayState(SimulationLifecycleManager manager) {
        this.manager = manager;
    }

    @Override
    public void enterState() {
        // Initialize menu
    }

    @Override
    public void update(float delta) {
        // Handle menu updates
    }

    @Override
    public void render(SpriteBatch batch) {
        // Render menu
    }

    @Override
    public void nextState() {
        // Transition to the game play state
        manager.setState(new GameOverState(manager));
    }
}
