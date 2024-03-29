package GameEngine.Collisions;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import GameEngine.Collisions.handlers.ICollisionHandler;
import GameEngine.Collisions.handlers.CollisionHandler;
import GameEngine.Collisions.CollisionCriterias.CollisionCriteria;
import GameEngine.Collisions.CollisionCriterias.Criterias;
import GameEngine.EntityManagement.CollidableActor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionManager {
    private List<CollidableActor> actors; // List of collidable actors in the game
    private Stage stage; // The stage where the collision occurs
    private Map<Class<? extends Criterias>, Class<? extends ICollisionHandler>> criteriaToHandlers = new HashMap<>();

    // Constructor for CollisionManager class.
    public CollisionManager(List<CollidableActor> actors, Stage stage) {
        this.actors = actors;
        this.stage = stage;
        initializeCriteriaToHandlersMap(); // Initialize the map of criteria to handlers
    }

    // Method to initialize the map of criteria to handlers.
    private void initializeCriteriaToHandlersMap() {
        // Map each criteria class to its corresponding handler class.
        // This design allows for flexibility and easy addition of new collision criteria and handlers.
        this.criteriaToHandlers.put(CollisionCriteria.class, CollisionHandler.class);
    }

    // Method to handle collisions between collidable actors.
    public void handleCollisions() {
        for (int i = 0; i < actors.size(); i++) {
            for (int j = i + 1; j < actors.size(); j++) {
                CollidableActor actor1 = actors.get(i);
                CollidableActor actor2 = actors.get(j);

                // Check if the bounds of actor1 and actor2 overlap
                if (actor1.getBounds().overlaps(actor2.getBounds())) {
                    processCollision(actor1, actor2); // Process the collision
                }
            }
        }
    }

    // Method to process a collision between two collidable actors.
    private void processCollision(CollidableActor actor1, CollidableActor actor2) {
        // Iterate through the map of criteria to handlers
        for (Map.Entry<Class<? extends Criterias>, Class<? extends ICollisionHandler>> entry : criteriaToHandlers.entrySet()) {
            try {
                // Instantiate the criteria using the stage constructor
                Criterias criteria = entry.getKey().getConstructor(Stage.class).newInstance(stage);

                // Check if the criteria meets for the given actors
                if (criteria.meetsCriteria(actor1, actor2)) {
                    // Reflectively instantiate the handler using the correct constructor
                    // that includes both Actor instances and the Stage instance.
                    ICollisionHandler handler = entry.getValue()
                            .getDeclaredConstructor(Actor.class, Actor.class, Stage.class)
                            .newInstance(actor1, actor2, stage);
                    handler.handleCollision(); // Handle the collision using the handler
                    break; // Assuming only one criteria can be met at a time
                }
            } catch (Exception e) {
                e.printStackTrace(); // Print stack trace if an exception occurs
            }
        }
    }
}
