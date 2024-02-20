//package com.mygdx.game.CollisionManagement.handlers;
//
//import com.mygdx.game.EntityManagement.Entity;
//
//public class CollectCollisionHandler {
//    private Entity entity1;
//    private Entity entity2;
//
//    public CollectCollisionHandler(Entity entity1, Entity entity2) {
//        this.entity1 = entity1;
//        this.entity2 = entity2;
//    }
//
//    public void handleCollision() {
//        if (entity1.getBounds().overlaps(entity2.getBounds())) {
//            entity1.setActive(false); // Make the first entity disappear
//        }
//    }
//}