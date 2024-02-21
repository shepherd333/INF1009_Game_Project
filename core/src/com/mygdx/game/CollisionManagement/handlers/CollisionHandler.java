//package com.mygdx.game.CollisionManagement.handlers;
//
//import com.mygdx.game.EntityManagement.Entity;
//import com.mygdx.game.EntityManagement.BucketEntity;
//import com.mygdx.game.EntityManagement.RaindropEntity;
//
//public class CollisionHandler {
//    private Entity entity1;
//    private Entity entity2;
//
//    public CollisionHandler(Entity entity1, Entity entity2) {
//        this.entity1 = entity1;
//        this.entity2 = entity2;
//    }
//
//    public void handleCollision() {
//        if (entity1.getBounds().overlaps(entity2.getBounds())) {
//            // Call the appropriate response method based on the type of collision
//            if (entity1 instanceof RaindropEntity && entity2 instanceof BucketEntity) {
//                CollectCollisionHandler collectHandler = new CollectCollisionHandler(entity1, entity2);
//                collectHandler.handleCollision();
//            } else {
//                // Handle other types of collisions here
//                System.out.println("Other type of collision detected");
//            }
//        }
//    }
//}