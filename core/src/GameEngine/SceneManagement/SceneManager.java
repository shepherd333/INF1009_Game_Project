package GameEngine.SceneManagement;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameLayer.Scenes.MainMenu;
import java.util.Stack;

// SceneManager manages the scenes in the game.
public class SceneManager {
    private Stack<SceneInterface> sceneStack = new Stack<>(); // Stack to manage scenes

    // Constructor initializes SceneManager and pushes the initial scene onto the stack.
    public SceneManager() {
        Gdx.app.log("SceneManager", "Initializing SceneManager");
        initializeScenes(); // Initialize scenes, pushing the initial scene onto the stack
    }

    // Method to initialize scenes, pushing the initial scene onto the stack.
    public void initializeScenes() {
        // Initialize your starting scene here, for example:
        pushScene(new MainMenu(this)); // Push the MainMenu onto the stack
    }

    // Method to push a scene onto the stack.
    public void pushScene(SceneInterface scene) {
        sceneStack.push(scene);
    }

    // Method to pop the current scene from the stack.
    public void popScene() {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose(); // Dispose of the current scene
        }
    }

    // Method to get the current scene from the stack.
    public SceneInterface getCurrentScene() {
        if (!sceneStack.isEmpty()) {
            return sceneStack.peek(); // Return the top scene on the stack
        }
        return null;
    }

    // Method to set a new scene, disposing of the current scene.
    public void set(SceneInterface scene) {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose(); // Dispose of the current scene
        }
        sceneStack.push(scene); // Push the new scene onto the stack
    }

    // Method to update the current scene.
    public void update(float deltaTime) {
        sceneStack.peek().update(deltaTime); // Update the top scene on the stack
    }

    // Method to render the current scene.
    public void render() {
        SceneInterface currentScene = getCurrentScene(); // Get the current scene
        if (currentScene != null) {
            currentScene.render(); // Render the current scene
        }
    }

    // Method to resize the current scene.
    public void resize(int width, int height) {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().resize(width, height); // Resize the top scene on the stack
        }
    }

    // Method to dispose of all scenes.
    public void dispose() {
        while (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose(); // Dispose of each scene on the stack
        }
    }
}