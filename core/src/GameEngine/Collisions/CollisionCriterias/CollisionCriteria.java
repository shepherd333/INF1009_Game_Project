// CollisionCriteria.java
package GameEngine.Collisions.CollisionCriterias;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ItemActor;
import GameEngine.PlayerControl.BucketActionHandler;
import GameEngine.PlayerControl.GdxInputHandler;

//A criteria implementation that checks if the space bar is pressed and if the collision involves a bucket and an item actor.
public class CollisionCriteria implements Criterias {
    private BucketActionHandler bucketActionHandler;
    private Stage stage;

    public CollisionCriteria(Stage stage) {
        if (stage == null) {
            throw new IllegalArgumentException("Stage cannot be null");
        }
        this.stage = stage;
        this.bucketActionHandler = new BucketActionHandler(new GdxInputHandler());
    }

    @Override
    public boolean meetsCriteria(Actor actor1, Actor actor2) {
        boolean isSpacePressed = this.bucketActionHandler.isSpaceBarPressed();
        boolean isBucketActor1 = actor1 instanceof BucketActor;
        boolean isBucketActor2 = actor2 instanceof BucketActor;
        boolean isItemActor1 = actor1 instanceof ItemActor;
        boolean isItemActor2 = actor2 instanceof ItemActor;

        return isSpacePressed && ((isBucketActor1 && isItemActor2) || (isBucketActor2 && isItemActor1));
    }
}