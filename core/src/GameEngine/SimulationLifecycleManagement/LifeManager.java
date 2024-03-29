package GameEngine.SimulationLifecycleManagement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.Batch;
import GameEngine.SceneManagement.GameOverListener;

// LifeManager handles the life bar display and logic for an entity in the game.
public class LifeManager {
    private float maxHealth; // Maximum health value
    private float currentHealth; // Current health value
    private float lifeBarWidth; // Width of the life bar
    private float lifeBarHeight; // Height of the life bar
    private Color lifeBarColor; // Color of the life bar
    private ShapeRenderer shapeRenderer; // ShapeRenderer for rendering shapes
    private Color backgroundColor = new Color(0.3f, 0.3f, 0.3f, 1); // Dark gray background color
    private Color borderColor = new Color(0.7f, 0.7f, 0.7f, 1); // Light gray border color
    private float borderWidth = 2; // Border width for the life bar
    private GameOverListener gameOverListener; // Listener for game over events

    // Constructor to initialize LifeManager with specified parameters.
    public LifeManager(float maxHealth, float lifeBarWidth, float lifeBarHeight, Color lifeBarColor, GameOverListener listener) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth; // Start with full health
        this.lifeBarWidth = lifeBarWidth;
        this.lifeBarHeight = lifeBarHeight;
        this.lifeBarColor = lifeBarColor;
        this.shapeRenderer = new ShapeRenderer();
        this.gameOverListener = listener;
    }

    // Method to draw the life bar.
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

    // Method to get the color based on health percentage.
    private Color getColorForHealth(float healthPercentage) {
        if (healthPercentage > 0.66f) {
            return Color.GREEN; // Green color for high health
        } else if (healthPercentage > 0.33f) {
            return Color.YELLOW; // Yellow color for moderate health
        } else {
            return Color.RED; // Red color for low health
        }
    }

    // Method to decrease health by a specified amount.
    public void decreaseHealth(float amount) {
        // Decrease health but do not drop below 0
        this.currentHealth = Math.max(0, this.currentHealth - amount);
        if (this.currentHealth <= 0) {
            onGameOver(); // Call the game-over handler
        }
    }

    // Method called when the health reaches zero, triggering game over.
    private void onGameOver() {
        if (gameOverListener != null) {
            gameOverListener.onGameOver(); // Notify the game-over listener
        }
    }

    // Getter for current health value.
    public float getLife() {
        return currentHealth;
    }

    // Method to dispose of resources.
    public void dispose() {
        shapeRenderer.dispose(); // Dispose of the ShapeRenderer
    }
}
