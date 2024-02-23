package com.mygdx.game.Scenes;
import com.badlogic.gdx.Gdx;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Lifecycle.HighScoreManager;
import com.mygdx.game.Lifecycle.LifeManager;

// Manages all scenes within the game, including transitions between scenes.
public class SceneManager {
    private Map<String, SceneInterface> scenes = new HashMap<>();     // Stores all the scenes by their names.
    private SceneInterface currentScene; // The currently active scene.
    private float alpha = 0; // Used for scene transitions, representing the opacity of the transition overlay.
    private boolean isTransitioning = false; // Indicates whether a scene transition is in progress.
    private String nextSceneName; // The name of the scene to transition to.
    private float transitionDuration; // Duration of the transition animation.
    private ShapeRenderer shapeRenderer = new ShapeRenderer(); // Renderer for drawing shapes, used here for transition effects.
    private boolean isPaused = false; // Indicates if the game is currently paused.
    private String currentSceneName = "";
    private String previousSceneName = "GamePlay";
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();  // Singleton instances for managing high scores.
    public LifeManager lifeManager = LifeManager.getInstance();  // Singleton instances for managing player lives.

    // Constructor initializes the SceneManager and sets up available scenes.
    public SceneManager() {
        Gdx.app.log("SceneManager", "Initializing SceneManager");
        scenes = new HashMap<>();
        initializeScenes();
    }
    // Returns the currently active scene.
    public SceneInterface getCurrentScene() {
        return currentScene;
    }

    // Initializes and adds available scenes to the manager.
    public void initializeScenes() {
        // Create instances of scenes and add them to the scenes map.
        // Each scene is identified by a unique name.
        Gdx.app.log("SceneManager", "Initializing scenes");
        MainMenu mainMenu = new MainMenu(this);
        addScene("MainMenu", mainMenu);

        GamePlay gamePlay = new GamePlay(this);
        addScene("GamePlay", gamePlay);

        PauseMenu pauseMenu = new PauseMenu(this);
        addScene("PauseMenu", pauseMenu);

        Leaderboard lb = new Leaderboard(this);
        addScene("Leaderboard", lb);

        EndMenu em = new EndMenu(this);
        addScene("EndMenu", em);
    }

    // Adds a scene to the manager.
    public void addScene(String name, SceneInterface scene) {
        // Log for debugging.
        Gdx.app.log("SceneManager", "Adding scene: " + name);
        scenes.put(name, scene);
    }

    // Sets the current scene to be displayed and handled by the manager.
    public void setCurrentScene(String sceneName) {
        // Log for debugging.
        Gdx.app.log("SceneManager", "Setting current scene to " + sceneName);
        // Change the current scene if the specified name exists in the scenes map.
        if (scenes.containsKey(sceneName)) {
            previousSceneName = currentScene == null ? "" : currentScene.getClass().getSimpleName();
            currentScene = scenes.get(sceneName);
            currentSceneName = sceneName; // Update the currentSceneName to reflect the new current scene
            currentScene.initialize();
            currentScene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
    // Returns the name of the currently active scene.
    public String getCurrentSceneName() {
        return currentSceneName;
    }

    // Checks if the current scene is the GamePlay scene.
    public boolean isCurrentSceneGamePlay() {
        return currentScene instanceof GamePlay;
    }

    // Begins a transition to a specified scene over a given duration.
    public void transitionTo(String sceneName, float duration) {
        // Log for debugging.
        Gdx.app.log("SceneManager", "Transitioning to " + sceneName);
        highScoreManager.saveScores();
        transitionDuration = duration;
        isTransitioning = true;
        nextSceneName = sceneName;
    }

    // Toggles the game's pause state.
    public void togglePause() {
        Gdx.app.log("SceneManager", "Toggling pause");
        isPaused = !isPaused;
        if (isPaused) {
            previousSceneName = currentScene.getClass().getSimpleName();
            setCurrentScene("PauseMenu");
        } else {
            setCurrentScene(previousSceneName);
            lifeManager.addPLife();
        }
    }

    // Returns true if the game is currently paused.
    public boolean isPaused() {
        return isPaused;
    }

    // Updates the current scene and manages transitions.
    public void update(float deltaTime) {
        Gdx.app.log("SceneManager", "Updating SceneManager");
        if (isTransitioning) {
            if (alpha < 1) {
                alpha += deltaTime / transitionDuration;
                alpha = Math.min(alpha, 1);
            } else {
                setCurrentScene(nextSceneName);
                isTransitioning = false;
                alpha = 0;

                // Log the current scene name for debugging
                Gdx.app.log("SceneManager", "Transition complete. Current scene: " + currentSceneName);
            }
        }

        if (currentScene != null && !isTransitioning) {
            currentScene.update(deltaTime);
        }
    }

    // Renders the current scene and transition effects if necessary.
    public void render() {
        Gdx.app.log("SceneManager", "Rendering SceneManager");
        if (currentScene != null) {
            currentScene.render();
        }
        if (isTransitioning) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, alpha);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
    // Resizes the current scene based on the window size.
    public void resize(int width, int height) {
        Gdx.app.log("SceneManager", "Resizing SceneManager");
        if (currentScene != null) {
            currentScene.resize(width, height);
        }
    }
    // Disposes of resources used by all scenes and the SceneManager itself.
    public void dispose() {
        for (SceneInterface scene : scenes.values()) {
            scene.dispose();
        }
        shapeRenderer.dispose();
    }
}