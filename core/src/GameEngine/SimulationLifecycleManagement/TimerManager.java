package GameEngine.SimulationLifecycleManagement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * TimerManager is responsible for managing the countdown timer functionality in the game.
 * This includes:
 * - Maintaining the current timer value.
 * - Updating the timer based on elapsed time.
 * - Playing a beep sound in the last 10 seconds of the countdown.
 * - Handling the event when the timer reaches zero.
 * - Drawing the timer on the screen.
 * - Disposing of resources when they are no longer needed.
 */
public class TimerManager {
    private float timer; // Current timer value
    private boolean countdownPlayed = false; // Flag to track if countdown sound has been played
    private final AudioManager audioManager; // AudioManager instance for playing sounds
    private final Runnable onTimerEnd; // Callback to be executed when the timer reaches zero
    private BitmapFont font; // Font for rendering text
    private SpriteBatch batch; // SpriteBatch for rendering
    private int lastBeepSecond = -1; // Initialize to -1 to indicate no beep has been played yet
    private ShapeRenderer shapeRenderer;


    // Constructor to initialize TimerManager with specified parameters.
    public TimerManager(float startTime, AudioManager audioManager, Runnable onTimerEnd, BitmapFont font, SpriteBatch batch, ShapeRenderer shapeRenderer) {
        this.timer = startTime; // Initialize timer with start time
        this.audioManager = audioManager; // Set AudioManager instance
        this.onTimerEnd = onTimerEnd; // Set callback for timer end
        this.font = ScoreManager.getInstance().getFont();
        this.batch = batch; // Set SpriteBatch for rendering
        this.shapeRenderer = shapeRenderer;
    }

    // Method to update the timer based on elapsed time.
    public void update(float deltaTime) {
        if (timer > 0) {
            timer -= deltaTime; // Decrease timer by elapsed time

            int currentSecond = (int)Math.floor(timer);

            // Only play the beep in the last 10 seconds and when moving to a new second
            if (currentSecond > 0 && currentSecond < 10 && currentSecond != lastBeepSecond) {
                audioManager.playSoundEffect("beep", 0.5f); // Play the beep sound
                lastBeepSecond = currentSecond; // Ensure this is inside the if condition
            }

            // Handle timer reaching zero
            if (timer <= 0) {
                timer = 0; // Set timer to zero
                audioManager.playSoundEffect("powerOff", 1.0f); // Play power off sound
                // Handle score and callback
                handleTimerEnd(); // This is to keep the code clean and focused
            }
        }
    }

    private void handleTimerEnd() {
        int currentScore = ScoreManager.getInstance().getCurrentScore();
        ScoreManager.getInstance().addScore(currentScore); // Add current score to high scores
        ScoreManager.getInstance().resetCurrentScore(); // Optionally reset current score
        if (onTimerEnd != null) {
            onTimerEnd.run(); // Execute callback for timer end
        }
    }


    // Method to draw the timer on the screen.
    public void drawTimer(float screenWidth, float screenHeight) {
        // Assuming you have initialized 'shapeRenderer' somewhere

        // First, calculate the size and position of the rectangle background
        String timerText = String.format("Time: %d", (int)Math.floor(timer)); // Format timer text
        GlyphLayout timerLayout = new GlyphLayout(font, timerText); // Create layout for timer text
        float padding = 10; // Padding around the text for the background
        float timerX = screenWidth - timerLayout.width - 130; // Calculate x coordinate for timer text
        float timerY = screenHeight - timerLayout.height - 10; // Calculate y coordinate for timer text
        float backgroundWidth = timerLayout.width + 2 * padding;
        float backgroundHeight = timerLayout.height + 2 * padding;

        // Draw the rectangle background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK); // Set the color of the rectangle
        shapeRenderer.rect(timerX - padding, timerY - padding, backgroundWidth, backgroundHeight);
        shapeRenderer.end();

        // You need to flush the batch before drawing with the ShapeRenderer if batch.begin() was called before
        batch.begin();
        font.draw(batch, timerText, timerX, timerY + timerLayout.height); // Correct Y position for drawing text
        batch.end();
    }
}