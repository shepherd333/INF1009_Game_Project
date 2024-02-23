package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Lifecycle.HighScoreManager;

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
        // Scaling font (reduce quality, consider using a larger font as mentioned)
        font.getData().setScale(1.3f);

        String highScoreDisplay = "High Score: " + highScoreManager.getInstance().getHighestScoreFormatted();
        GlyphLayout highScoreLayout = new GlyphLayout(font, highScoreDisplay);
        float highScoreX = viewport.getWorldWidth() - highScoreLayout.width - 250;
        float highScoreY = viewport.getWorldHeight() - highScoreLayout.height - 200;
        font.draw(batch, highScoreDisplay, highScoreX, highScoreY);

        batch.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            camera.unproject(tmp.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            // Add any additional touch input handling here
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}