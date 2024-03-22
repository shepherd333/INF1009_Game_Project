package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import java.util.Stack;

public class SceneManager {
    private Stack<Scene> sceneStack = new Stack<>();

    public SceneManager() {
        Gdx.app.log("SceneManager", "Initializing SceneManager");
        initializeScenes(); // This will push the initial scene onto the stack
    }

    public void initializeScenes() {
        // Initialize your starting scene here, for example:
        pushScene(new MainMenu(this));
    }

    public void pushScene(Scene scene) {
        sceneStack.push(scene);
    }

    public void popScene() {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose();
        }
//        if (!sceneStack.isEmpty()) {
//            Scene currentScene = sceneStack.peek();
//            currentScene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        }
    }

    public Scene getCurrentScene() {
        if (!sceneStack.isEmpty()) {
            return sceneStack.peek();
        }
        return null;
    }

    public void set(Scene scene) {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose();
        }
        sceneStack.push(scene);
    }

    public void update(float deltaTime) {
        sceneStack.peek().update(deltaTime);
    }

    public void render() {
        Scene currentScene = getCurrentScene();
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
