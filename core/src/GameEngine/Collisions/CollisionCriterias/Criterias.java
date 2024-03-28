package GameEngine.Collisions.CollisionCriterias;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface Criterias {
    boolean meetsCriteria(Actor actor1, Actor actor2);

}