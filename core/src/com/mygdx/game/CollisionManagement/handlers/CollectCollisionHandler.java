//package com.mygdx.game.CollisionManagement.handlers;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.math.MathUtils;
//import com.badlogic.gdx.utils.Timer;
//import com.mygdx.game.EntityManagement.Entity;
//import com.mygdx.game.Lifecycle.HighScore.HighScoreManager;
//
//public class CollectCollisionHandler extends BaseCollisionHandler {
//    private HighScoreManager highScoreManager;
//
//    public CollectCollisionHandler(Entity entity1, Entity entity2) {
//        super(entity1, entity2);
//        highScoreManager = HighScoreManager.getInstance();
//    }
//
//    @Override
//    public void handleCollision() {
//
//        if (entity1.isActive()) { // Check if the entity is active before proceeding
//            System.out.println("Collision detected. Deactivating entity1.");
//            entity1.setActive(false); // Deactivate the entity
//            highScoreManager.addToCurrentScore(10); // Add to the score only if the entity was active
//            resetEntityToTop(entity1); // Reset entity position
//        }
//        // If entity1.isActive() is false, this means the entity has already been deactivated,
//        // and therefore, the collision has already been handled, so we do nothing.
//    }
//
//
//    public void resetEntityToTop(Entity entity1) {
//
//        Timer.schedule(new Timer.Task() {
//            @Override
//            public void run() {
//                Gdx.app.postRunnable(() -> {
//
//                    float newX = MathUtils.random(0, Gdx.graphics.getWidth() - entity1.getWidth());
//                    entity1.setX(newX);
//                    entity1.setY(Gdx.graphics.getHeight() + 150);
//                    entity1.setActive(true);
//                });
//            }
//        }, 1f);
//    }
//}