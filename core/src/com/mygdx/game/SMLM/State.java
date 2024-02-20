package com.mygdx.game.SMLM;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface State {
    void enterState(); // Called when entering the state
    void update(float delta); // Update the state logic
    void render(SpriteBatch batch); // Render the state visuals
    void nextState(); // Method to transition to the next state
}
