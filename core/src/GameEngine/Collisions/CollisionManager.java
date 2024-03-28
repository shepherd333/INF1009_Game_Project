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
    private List<CollidableActor> actors;
    private Stage stage;
    private Map<Class<? extends Criterias>, Class<? extends ICollisionHandler>> criteriaToHandlers = new HashMap<>();

    public CollisionManager(List<CollidableActor> actors, Stage stage) {
        this.actors = actors;
        this.stage = stage;
        initializeCriteriaToHandlersMap();
    }

    private void initializeCriteriaToHandlersMap() {
        // Map each criteria class to its corresponding handler class.
        // This design allows for flexibility and easy addition of new collision criteria and handlers.
        this.criteriaToHandlers.put(CollisionCriteria.class, CollisionHandler.class);
    }

    public void handleCollisions() {
        for (int i = 0; i < actors.size(); i++) {
            for (int j = i + 1; j < actors.size(); j++) {
                CollidableActor actor1 = actors.get(i);
                CollidableActor actor2 = actors.get(j);

                if (actor1.getBounds().overlaps(actor2.getBounds())) {
                    processCollision(actor1, actor2);
                }
            }
        }
    }

    private void processCollision(CollidableActor actor1, CollidableActor actor2) {
        for (Map.Entry<Class<? extends Criterias>, Class<? extends ICollisionHandler>> entry : criteriaToHandlers.entrySet()) {
            try {
                // Instantiate the criteria using the stage constructor
                Criterias criteria = entry.getKey().getConstructor(Stage.class).newInstance(stage);

                if (criteria.meetsCriteria(actor1, actor2)) {
                    // Reflectively instantiate the handler using the correct constructor
                    // that includes both Actor instances and the Stage instance.
                    ICollisionHandler handler = entry.getValue()
                            .getDeclaredConstructor(Actor.class, Actor.class, Stage.class)
                            .newInstance(actor1, actor2, stage);
                    handler.handleCollision();
                    break; // Assuming only one criteria can be met at a time
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}