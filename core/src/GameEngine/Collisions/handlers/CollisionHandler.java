package GameEngine.Collisions.handlers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.ItemActor;

// Handles collision between a bucket actor and an item actor.
public class CollisionHandler extends BaseCollisionHandler {
    private Stage stage;

    // Constructor to initialize the collision handler with the actors involved and the stage.
    public CollisionHandler(Actor actor1, Actor actor2, Stage stage) {
        super(actor1, actor2);
        this.stage = stage;
    }

    // Handles the collision between the actors.
    @Override
    public void handleCollision() {
        // Check if one actor is a bucket and the other is an item to handle item pickup.
        if (actor1 instanceof BucketActor && actor2 instanceof ItemActor) {
            handlePickUp((BucketActor) actor1, (ItemActor) actor2);
        } else if (actor1 instanceof ItemActor && actor2 instanceof BucketActor) {
            handlePickUp((BucketActor) actor2, (ItemActor) actor1);
        }
    }

    // Handles the pickup of an item by a bucket actor.
    private void handlePickUp(BucketActor bucket, ItemActor item) {
        // If the bucket is not already holding an item, pick up the item.
        if (!bucket.isItemPickedUp()) {
            // Assign the item's properties to the bucket.
            bucket.setHeldItemType(item.getItemType());
            bucket.setHeldItemSprite(item.getTextureRegion());
            bucket.setItemPickedUp(true);
            bucket.holdItem(item);
            item.removeItem(); // Remove the item from the stage.
            Gdx.app.log("CollisionHandler", "Item picked up: " + item.getItemType());
        }
    }
}