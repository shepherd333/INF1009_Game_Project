package GameEngine.Collisions.CollisionCriterias;
import com.badlogic.gdx.scenes.scene2d.Actor;

//Interface for defining criteria to be met during collisions between actors.
public interface Criterias {
    boolean meetsCriteria(Actor actor1, Actor actor2);

}