package com.mygdx.game.EntityManagement;

// iMovable is an interface defining a contract for all entities in the game that can move.
// This interface ensures that any class that implements it will provide an implementation for the move method.
public interface iMovable {
    // The move method is intended to contain logic for updating the entity's position.
    // How the move operation is implemented will depend on the specific entity's behavior.
    // For example, a player-controlled character might move based on keyboard input,
    // while an AI-controlled enemy might move based on some algorithm.
    void move();
}
