package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameActor extends Actor {
    private boolean collected;

    public GameActor() {
        super();
        this.collected = false;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}