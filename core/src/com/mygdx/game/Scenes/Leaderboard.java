package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Lifecycle.HighScore.HighScoreManager;

public class Leaderboard extends Scene {
    public Vector3 tmp = new Vector3();
    public HighScoreManager highScoreManager = HighScoreManager.getInstance();

    public Leaderboard(SceneManager sceneManager) {
        super(sceneManager, "Leaderboard.png", "This is the LeaderBoard Scene.");
    }

    @Override
    public void initialize() {
        // Initialize any additional resources if needed
    }

    @Override
    public void update(float deltaTime) {
        // Handle any animations or transitions in the menu.
    }

    @Override
    public void render() {
        super.render();
        batch.begin();
        font.getData().setScale(1.3f);
        highScoreManager.renderHighestScore(batch, font, viewport);
        batch.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            camera.unproject(tmp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            // Here, you can add additional logic to respond to touch input, such as buttons on the leaderboard scene.
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}