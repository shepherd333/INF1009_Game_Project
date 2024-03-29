package GameEngine.Collisions.CollisionCriterias;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Criterias is an interface for defining criteria to be met during collisions between actors in a game.
 * This includes:
 * - Providing a method to check if the criteria are met given two actors. This method returns a boolean value.
 */
public interface Criterias {
    boolean meetsCriteria(Actor actor1, Actor actor2);

}