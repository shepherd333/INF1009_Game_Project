// CollisionCriteria.java
package GameEngine.Collisions.CollisionCriterias;
import GameEngine.PlayerControl.PlayerActionHandler;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameLayer.GameEntities.Movers.PlayerActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ItemActor;
import GameEngine.PlayerControl.GdxInputHandler;

/**
 * CollisionCriteria is a class that implements Criterias and defines a criteria for collisions in a game.
 * This includes:
 * - Holding references to a PlayerActionHandler and a Stage.
 * - Providing a constructor to initialize the CollisionCriteria with a Stage. In this constructor, it also initializes the PlayerActionHandler and checks if the Stage is not null.
 * - Overriding a method to check if the criteria are met given two actors. In this method, it checks if the space bar is pressed and if one actor is a PlayerActor and the other is an ItemActor.
 */
public class CollisionCriteria implements Criterias {
    private PlayerActionHandler playerActionHandler;
    private Stage stage;

    public CollisionCriteria(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
        this.playerActionHandler = new PlayerActionHandler(new GdxInputHandler());
    }

    @Override
    public boolean meetsCriteria(Actor actor1, Actor actor2) {
        boolean isSpacePressed = this.playerActionHandler.isSpaceBarPressed();
        boolean isBucketActor1 = actor1 instanceof PlayerActor;
        boolean isBucketActor2 = actor2 instanceof PlayerActor;
        boolean isItemActor1 = actor1 instanceof ItemActor;
        boolean isItemActor2 = actor2 instanceof ItemActor;

        return isSpacePressed && ((isBucketActor1 && isItemActor2) || (isBucketActor2 && isItemActor1));
    }
}