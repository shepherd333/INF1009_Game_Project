package GameEngine.EntityManagement;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

// Class responsible for managing all entities (actors) within a Stage in the game.
public class EntityManager {
    private Stage stage;

    // Constructor initializes the stage with a specific viewport.
    public EntityManager(Viewport viewport) {
        this.stage = new Stage(viewport);
    }

    // Adds an actor (entity) to the stage.
    public void addEntity(Actor entity) {
        this.stage.addActor(entity);
    }

    // Updates the stage, which in turn updates all actors within it.
    public void update(float delta) {
        this.stage.act(delta);
    }

    // Draws all actors within the stage.
    public void draw() {
        this.stage.draw();
    }

    // Optionally, get access to the Stage if needed for more advanced operations.
    public Stage getStage() {
        return this.stage;
    }

    // Removes an actor (entity) from the stage.
    public void removeEntity(Actor entity) {
        entity.remove(); // Actors can be removed directly.
    }

    // Method to clear all entities from the stage.
    public void clearEntities() {
        this.stage.clear();
    }
}
