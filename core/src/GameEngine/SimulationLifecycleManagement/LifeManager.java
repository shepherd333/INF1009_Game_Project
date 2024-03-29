package GameEngine.SimulationLifecycleManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.Batch;
import GameEngine.SceneManagement.GameOverListener;

public class LifeManager {
    private float maxHealth;
    private float currentHealth;
    private float lifeBarWidth;
    private float lifeBarHeight;
    private Color lifeBarColor;
    private ShapeRenderer shapeRenderer;
    private Color backgroundColor = new Color(0.3f, 0.3f, 0.3f, 1); // Dark gray
    private Color borderColor = new Color(0.7f, 0.7f, 0.7f, 1); // Light gray
    private float borderWidth = 2;
    private GameOverListener gameOverListener;

    public LifeManager(float maxHealth, float lifeBarWidth, float lifeBarHeight, Color lifeBarColor, GameOverListener listener) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth; // Start with full health
        this.lifeBarWidth = lifeBarWidth;
        this.lifeBarHeight = lifeBarHeight;
        this.lifeBarColor = lifeBarColor;
        this.shapeRenderer = new ShapeRenderer();
        this.gameOverListener = listener;

    }

    public void draw(Batch batch, float x, float y, float entityWidth, float entityHeight) {
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Draw background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.rect(x + (entityWidth - lifeBarWidth) / 2 - borderWidth,
                y + entityHeight + 5 - borderWidth,
                lifeBarWidth + 2 * borderWidth,
                lifeBarHeight + 2 * borderWidth);

        // Draw border
        shapeRenderer.setColor(borderColor);
        shapeRenderer.rect(x + (entityWidth - lifeBarWidth) / 2,
                y + entityHeight + 5,
                lifeBarWidth,
                lifeBarHeight);

        // Change color based on health percentage
        float healthPercentage = currentHealth / maxHealth;
        shapeRenderer.setColor(getColorForHealth(healthPercentage));
        float drawWidth = lifeBarWidth * healthPercentage;
        shapeRenderer.rect(x + (entityWidth - lifeBarWidth) / 2,
                y + entityHeight + 5,
                drawWidth,
                lifeBarHeight);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
    }

    private Color getColorForHealth(float healthPercentage) {
        if (healthPercentage > 0.66f) {
            return Color.GREEN;
        } else if (healthPercentage > 0.33f) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }

    public void decreaseHealth(float amount) {
        // Decrease health but do not drop below 0
        this.currentHealth = Math.max(0, this.currentHealth - amount);
        if (this.currentHealth <= 0) {
            onGameOver(); // Call the game-over handler
        }
    }

    private void onGameOver() {
        if (gameOverListener != null) {
            gameOverListener.onGameOver();
        }
    }

    public float getLife(){
        return currentHealth;
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
