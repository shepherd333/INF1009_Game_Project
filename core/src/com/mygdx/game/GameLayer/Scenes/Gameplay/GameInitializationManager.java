package com.mygdx.game.GameLayer.Scenes.Gameplay;

import GameEngine.Collisions.handlers.BucketItemHandler;
import GameEngine.PlayerControl.PlayerController;
import GameEngine.SceneManagement.SceneManager;
import GameEngine.SimulationLifecycleManagement.AudioManager;
import GameEngine.SimulationLifecycleManagement.TimerManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.GameLayer.GameEntities.Movers.BucketActor;
import com.mygdx.game.GameLayer.Scenes.Leaderboard;

public class GameInitializationManager {
    private TimerManager timerManager;
    private PlayerController playerController;
    private BucketItemHandler bucketItemHandler;
    private SceneManager sceneManager;

    public GameInitializationManager(SceneManager sceneManager, AudioManager audioManager, BitmapFont font, SpriteBatch batch, BucketActor bucket) {
        this.sceneManager = sceneManager;
        this.timerManager = new TimerManager(11, audioManager, this::goToLeaderboard, font, batch);
        this.playerController = new PlayerController(bucket, 300);
        this.bucketItemHandler = new BucketItemHandler(bucket);
    }

    public TimerManager getTimerManager() {
        return timerManager;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public BucketItemHandler getBucketItemHandler() {
        return bucketItemHandler;
    }

    // A callback method that will be used by the TimerManager
    private void goToLeaderboard() {
        // logic to transition to the leaderboard scene
        sceneManager.pushScene(new Leaderboard(sceneManager));
    }
}
