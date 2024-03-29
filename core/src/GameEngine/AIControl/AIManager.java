package GameEngine.AIControl;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameLayer.GameEntities.Movers.Static.TrashMonsterActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

public class AIManager {
    private Stage stage; // Reference to the stage where AI entities exist
    private Actor player; // The player actor that enemies will follow
    private TrashMonsterActor trashMonster; // Reference to the TrashMonsterActor entity

    // Constructor to initialize the AIManager
    public AIManager(Stage stage, Actor player, TrashMonsterActor trashMonster) {
        this.stage = stage;
        this.player = player;
        this.trashMonster = trashMonster;
    }

    // Method to update the behavior of the trash monster following the player's bucket
    public void updateMonsterFollowBehavior(float deltaTime, BucketActor bucket) {
        // Speed at which the monster follows the bucket
        float followSpeed = 50;

        // Calculate the target position (center of the bucket)
        float targetX = bucket.getX() + bucket.getWidth() / 2;
        float targetY = bucket.getY() + bucket.getHeight() / 2;

        // Calculate the distance between the trash monster and the target position
        float dx = targetX - (trashMonster.getX() + trashMonster.getWidth() / 2);
        float dy = targetY - (trashMonster.getY() + trashMonster.getHeight() / 2);
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        // Move the trash monster towards the target position
        if (distance > 1) {
            // Determine movement speed (limited by followSpeed and deltaTime)
            float speed = Math.min(followSpeed * deltaTime, distance);

            // Calculate movement angle
            float angle = (float) Math.atan2(dy, dx);

            // Calculate movement components along x and y axes
            float dx1 = (float) (speed * Math.cos(angle));
            float dy1 = (float) (speed * Math.sin(angle));

            // Update the position of the trash monster
            trashMonster.setPosition(trashMonster.getX() + dx1, trashMonster.getY() + dy1);
        }
    }
}
