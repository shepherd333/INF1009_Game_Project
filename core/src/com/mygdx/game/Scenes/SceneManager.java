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
    private String currentSceneName = "";
    private String previousSceneName = "GamePlay";
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();
    private SceneManager sm;



    public SceneManager() {
        scenes = new HashMap<>();
        initializeScenes();
    }
    public SceneInterface getCurrentScene() {
        return currentScene;
    }
    public void initializeScenes() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setSceneManager(this); // Pass reference to SceneManager
        addScene("MainMenu", mainMenu);

        GamePlay gamePlay = new GamePlay();
        gamePlay.setSceneManager(this); // Pass reference to SceneManager
        addScene("GamePlay", gamePlay);

        PauseMenu pauseMenu = new PauseMenu();
        pauseMenu.setSceneManager(this); // Pass reference to SceneManager
        addScene("PauseMenu", pauseMenu);

        Leaderboard lb = new Leaderboard();
        lb.setSceneManager(this); // Pass reference to SceneManager
        addScene("Leaderboard", lb);

        EndMenu em = new EndMenu();
        em.setSceneManager(this); // Pass reference to SceneManager
        addScene("EndMenu", em);
    }

    public void addScene(String name, SceneInterface scene) {
        scenes.put(name, scene);
    }

    // Ensure this method is correctly defined
    public void setCurrentScene(String sceneName) {
        if (scenes.containsKey(sceneName)) {
            if (currentScene != null) {
                currentScene.dispose();
            }
            currentScene = scenes.get(sceneName);
            currentScene.initialize();

            currentScene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public String getCurrentSceneName() {
        return currentSceneName;
    }
    public boolean isCurrentSceneGamePlay() {
        // Assuming you have a way to get the name or type of the current scene
        return currentScene instanceof GamePlay; // Or compare a stored scene name, etc.
    }
    // Other methods...
    public void transitionTo(String sceneName, float duration) {
        highScoreManager.saveScores();
        transitionDuration = duration;
        isTransitioning = true; // set to true to activate if (isTransitioning) function in update
        nextSceneName = sceneName;
        // Start fade out effect, adjust alpha over time in update()
    }

    public void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            // Assuming PauseMenu is only accessible from GamePlay
            setCurrentScene("PauseMenu");
        } else {
            // Make sure this transitions back to GamePlay or the appropriate scene
            setCurrentScene(previousSceneName);
        }
    }

    public boolean isPaused() {
        return isPaused;
    }
    public void update(float deltaTime) {
        if (isTransitioning) {
            // Update alpha for fade effect, check transition state
            if (alpha < 1) {
                alpha += deltaTime / transitionDuration;
                alpha = Math.min(alpha, 1); // Clamp alpha to not exceed 1
            } else {
                setCurrentScene(nextSceneName);
                isTransitioning = false;
                alpha = 0; // Reset for next transition
            }
        }

        if (currentScene != null && !isTransitioning) {
            currentScene.update(deltaTime);
        }

        if(isPaused){
            setCurrentScene("PauseMenu");
        }
    }

    public void render() {
        if (currentScene != null) {
            currentScene.render();
        }
        if (isTransitioning) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            // Adjust the color and alpha as needed; here it's black with variable alpha
            shapeRenderer.setColor(0, 0, 0, alpha);
            shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
    public void resize(int width, int height) {
        if (currentScene != null) {
            currentScene.resize(width, height); // Delegate to the current active scene
        }
    }

    public void dispose() {
        if (currentScene != null) {
            currentScene.dispose();
        }
        shapeRenderer.dispose();
    }
}
