package com.mygdx.game.CollisionManagement.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.EntityManagement.RaindropActor;
import com.mygdx.game.Lifecycle.HighScore.HighScoreManager;
import com.mygdx.game.EntityManagement.CollidableActor;

public class CollectCollisionHandler extends BaseCollisionHandler {
    private HighScoreManager highScoreManager;
    private Array<RaindropActor> raindrops;

    public CollectCollisionHandler(Actor actor1, Actor actor2, Array<RaindropActor> raindrops) {
        super(actor1, actor2);
        this.raindrops = raindrops;
        highScoreManager = HighScoreManager.getInstance();
    }

    @Override
    public void handleCollision() {
        Actor actorToBeRemoved = null;

        if (actor1 instanceof RaindropActor && !((CollidableActor) actor1).isCollected()) {
            actorToBeRemoved = actor1;
        } else if (actor2 instanceof RaindropActor && !((CollidableActor) actor2).isCollected()) {
            actorToBeRemoved = actor2;
        }

        if (actorToBeRemoved != null) {
            System.out.println("Collision detected. Marking actor as collected.");
            ((CollidableActor) actorToBeRemoved).setCollected(true);
            highScoreManager.addToCurrentScore(10);

            // Remove the raindrop from the stage and the raindrops array
            actorToBeRemoved.remove();
            raindrops.removeValue((RaindropActor) actorToBeRemoved, true);

            resetActorToTop(actorToBeRemoved);
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
                    ((CollidableActor) actor1).setCollected(false);
                });
            }
        }, 1f);
    }
}