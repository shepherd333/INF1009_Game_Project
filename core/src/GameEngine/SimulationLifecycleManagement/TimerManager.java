package GameEngine.SimulationLifecycleManagement;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// TimerManager manages the countdown timer functionality in the game.
public class TimerManager {
    private float timer; // Current timer value
    private boolean countdownPlayed = false; // Flag to track if countdown sound has been played
    private final AudioManager audioManager; // AudioManager instance for playing sounds
    private final Runnable onTimerEnd; // Callback to be executed when the timer reaches zero
    private BitmapFont font; // Font for rendering text
    private SpriteBatch batch; // SpriteBatch for rendering
    private int lastBeepSecond = -1; // Initialize to -1 to indicate no beep has been played yet


    // Constructor to initialize TimerManager with specified parameters.
    public TimerManager(float startTime, AudioManager audioManager, Runnable onTimerEnd, BitmapFont font, SpriteBatch batch) {
        this.timer = startTime; // Initialize timer with start time
        this.audioManager = audioManager; // Set AudioManager instance
        this.onTimerEnd = onTimerEnd; // Set callback for timer end
        this.font = ScoreManager.getInstance().getFont();
        this.batch = batch; // Set SpriteBatch for rendering
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
        batch.begin();
        String timerText = String.format("Time: %d", (int)Math.floor(timer)); // Format timer text
        GlyphLayout timerLayout = new GlyphLayout(font, timerText); // Create layout for timer text
        float timerX = screenWidth - timerLayout.width - 10; // Calculate x coordinate for timer text
        float timerY = screenHeight - timerLayout.height - 100; // Calculate y coordinate for timer text
        font.draw(batch, timerText, timerX, timerY); // Draw timer text on the screen
        batch.end();
    }
}