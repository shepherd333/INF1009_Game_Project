package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.graphics.Texture;

public abstract class ButtonEntity extends Entity {

    private boolean activated;
    public ButtonEntity(Texture texture, float x, float y) {
        super(x, y, 0);
        this.texture = texture;
        this.activated = false;
    }

    // Implement activation logic specific to each button type
    public abstract void activate();

    // Implement deactivation logic specific to each button type
    public abstract void deactivate();

    // Getter and setter for activation status
    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

}
