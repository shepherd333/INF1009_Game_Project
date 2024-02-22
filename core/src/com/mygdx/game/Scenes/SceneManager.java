package com.mygdx.game.Scenes;
import com.badlogic.gdx.Gdx;
import java.util.HashMap;
import java.util.Map;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Lifecycle.HighScoreManager;

public class SceneManager {
    private Map<String, SceneInterface> scenes = new HashMap<>();
    private SceneInterface currentScene;
    private float alpha = 0;
    private boolean isTransitioning = false;
    private String nextSceneName;
    private float transitionDuration;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private boolean isPaused = false;
    private String previousSceneName = "GamePlay";
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();

    public SceneManager() {
        Gdx.app.log("SceneManager", "Initializing SceneManager");
        scenes = new HashMap<>();
        initializeScenes();
    }

    public SceneInterface getCurrentScene() {
        return currentScene;
    }

    public void initializeScenes() {
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

    public void addScene(String name, SceneInterface scene) {
        Gdx.app.log("SceneManager", "Adding scene: " + name);
        scenes.put(name, scene);
    }

    public void setCurrentScene(String sceneName) {
        Gdx.app.log("SceneManager", "Setting current scene to " + sceneName);
        if (scenes.containsKey(sceneName)) {
            previousSceneName = currentScene == null ? "" : currentScene.getClass().getSimpleName();
            currentScene = scenes.get(sceneName);
            currentScene.initialize();
            currentScene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public boolean isCurrentSceneGamePlay() {
        return currentScene instanceof GamePlay;
    }

    public void transitionTo(String sceneName, float duration) {
        Gdx.app.log("SceneManager", "Transitioning to " + sceneName);
        highScoreManager.saveScores();
        transitionDuration = duration;
        isTransitioning = true;
        nextSceneName = sceneName;
    }

    public void togglePause() {
        Gdx.app.log("SceneManager", "Toggling pause");
        isPaused = !isPaused;
        if (isPaused) {
            previousSceneName = currentScene.getClass().getSimpleName();
            setCurrentScene("PauseMenu");
        } else {
            setCurrentScene(previousSceneName);
        }
    }

    public boolean isPaused() {
        return isPaused;
    }

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
            }
        }

        if (currentScene != null && !isTransitioning) {
            currentScene.update(deltaTime);
        }
    }

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

    public void resize(int width, int height) {
        Gdx.app.log("SceneManager", "Resizing SceneManager");
        if (currentScene != null) {
            currentScene.resize(width, height);
        }
    }

    public void dispose() {
        for (SceneInterface scene : scenes.values()) {
            scene.dispose();
        }
        shapeRenderer.dispose();
    }
}