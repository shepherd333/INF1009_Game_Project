package GameEngine.AIControl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.GameLayer.GameEntities.Movers.Static.TrashMonsterActor;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;

public class AIManager {
    private Stage stage;
    private Actor player; // The player actor that enemies will follow.
    private TrashMonsterActor trashMonster; // Add this line to reference the TrashMonsterActor

    public AIManager(Stage stage, Actor player, TrashMonsterActor trashMonster) { // Updated constructor
        this.stage = stage;
        this.player = player;
        this.trashMonster = trashMonster;
    }

    public void updateMonsterFollowBehavior(float deltaTime, BucketActor bucket) {
        float followSpeed = 50; // Speed at which the monster follows the bucket
        float targetX = bucket.getX() + bucket.getWidth() / 2;
        float targetY = bucket.getY() + bucket.getHeight() / 2;
        float dx = targetX - (trashMonster.getX() + trashMonster.getWidth() / 2);
        float dy = targetY - (trashMonster.getY() + trashMonster.getHeight() / 2);
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > 1) {
            float speed = Math.min(followSpeed * deltaTime, distance);
            float angle = (float) Math.atan2(dy, dx);
            float dx1 = (float) (speed * Math.cos(angle));
            float dy1 = (float) (speed * Math.sin(angle));
            trashMonster.setPosition(trashMonster.getX() + dx1, trashMonster.getY() + dy1);
        }
    }
}
