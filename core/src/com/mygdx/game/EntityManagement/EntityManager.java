package com.mygdx.game.EntityManagement;

import java.util.List;

public class EntityManager {
    private List<Entity> entityList;

    public EntityManager(List<Entity> entityList) {
        this.entityList = entityList;
    }

    public void updateEntities() {
        for (Entity entity : entityList) {
            entity.update();
            entity.movement();
            if (entity instanceof MovableEntities) {
                ((MovableEntities) entity).move();
            }
        }
    }
}
