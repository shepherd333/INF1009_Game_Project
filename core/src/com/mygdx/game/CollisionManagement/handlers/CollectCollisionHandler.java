package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Lifecycle.HighScore.HighScoreManager;
import com.mygdx.game.EntityManagement.GameActor;
import com.mygdx.game.EntityManagement.RaindropEntity;

public class CollectCollisionHandler extends BaseCollisionHandler {
    private HighScoreManager highScoreManager;
    private Array<RaindropEntity> raindrops;

    public CollectCollisionHandler(Actor actor1, Actor actor2, Array<RaindropEntity> raindrops) {
        super(actor1, actor2);
        this.raindrops = raindrops;
        highScoreManager = HighScoreManager.getInstance();
    }

    @Override
    public void handleCollision() {
        if (!((GameActor) actor1).isCollected()) {
            System.out.println("Collision detected. Marking actor1 as collected.");
            ((GameActor) actor1).setCollected(true);
            highScoreManager.addToCurrentScore(10);

            // Remove the raindrop from the stage and the raindrops array
            actor1.remove();
            raindrops.removeValue((RaindropEntity) actor1, true);

            resetActorToTop(actor1);
        }
    }

    public void resetActorToTop(Actor actor1) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    float newX = MathUtils.random(0, Gdx.graphics.getWidth() - actor1.getWidth());
                    actor1.setX(newX);
                    actor1.setY(Gdx.graphics.getHeight() + 150);
                    ((GameActor) actor1).setCollected(false);
                });
            }
        }, 1f);
    }
}