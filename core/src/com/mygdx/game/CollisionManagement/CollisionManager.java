package com.mygdx.game.CollisionManagement;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.CollisionManagement.handlers.CollisionHandler;
import com.mygdx.game.EntityManagement.RaindropEntity;
import com.mygdx.game.EntityManagement.Collidable;


import java.util.List;

public class CollisionManager {
    private List<Collidable> actors;
    private Array<RaindropEntity> raindrops;

    public CollisionManager(List<Collidable> actors, Array<RaindropEntity> raindrops) {
        this.actors = actors;
        this.raindrops = raindrops;
    }

    public void handleCollisions() {
        for (int i = 0; i < actors.size(); i++) {
            for (int j = i + 1; j < actors.size(); j++) {
                if (actors.get(i).getBounds().overlaps(actors.get(j).getBounds())) {
                    actors.get(i).handleCollisionWith(actors.get(j));
                    actors.get(j).handleCollisionWith(actors.get(i));
                }
            }
        }
    }
}