//package com.mygdx.game;
//
//import GameEngine.Collisions.CollisionManager;
//import com.mygdx.game.EntityManagement.Movers.BucketActor;
//import com.mygdx.game.EntityManagement.RaindropActor;
//import GameEngine.EntityManagement.Entity;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CollisionManagerTest {
//
//    @Test
//    public void testHandleCollisions() {
//        List<Entity> entities = new ArrayList<>();
//        entities.add(new RaindropActor(0, 0, 10, 10));
//        entities.add(new BucketActor(5, 5, 10, 10));
//
//        CollisionManager collisionManager = new CollisionManager(entities);
//        collisionManager.handleCollisions();
//
//        System.out.println("Entity 0 active status: " + entities.get(0).isActive());
//        assertFalse(entities.get(0).isActive(), "The first entity should be inactive after collision");
//    }
//}