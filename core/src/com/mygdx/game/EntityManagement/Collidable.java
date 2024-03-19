package com.mygdx.game.EntityManagement;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable {
    void handleCollisionWith(Collidable collidable);
    Rectangle getBounds();
}
