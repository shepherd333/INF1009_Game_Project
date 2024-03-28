package GameEngine.SceneManagement;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.GameLayer.Scenes.MainMenu;

import java.util.Stack;

public class SceneManager {
    private Stack<SceneInterface> sceneStack = new Stack<>();

    public SceneManager() {
        Gdx.app.log("SceneManager", "Initializing SceneManager");
        initializeScenes(); // This will push the initial scene onto the stack
    }

    public void initializeScenes() {
        // Initialize your starting scene here, for example:
        pushScene(new MainMenu(this));
    }

    public void pushScene(SceneInterface scene) {
        sceneStack.push(scene);
    }

    public void popScene() {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose();
        }
    }

    public SceneInterface getCurrentScene() {
        if (!sceneStack.isEmpty()) {
            return sceneStack.peek();
        }
        return null;
    }

    public void set(SceneInterface scene) {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose();
        }
        sceneStack.push(scene);
    }

    public void update(float deltaTime) {
        sceneStack.peek().update(deltaTime);
    }

    public void render() {
        SceneInterface currentScene = getCurrentScene();
        if (currentScene != null) {
            currentScene.render();
        }
    }

    public void resize(int width, int height) {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().resize(width, height);
        }
    }

    public void dispose() {
        while (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose();
        }
    }
}