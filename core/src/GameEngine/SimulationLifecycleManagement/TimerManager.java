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

    // Constructor to initialize TimerManager with specified parameters.
    public TimerManager(float startTime, AudioManager audioManager, Runnable onTimerEnd, BitmapFont font, SpriteBatch batch) {
        this.timer = startTime; // Initialize timer with start time
        this.audioManager = audioManager; // Set AudioManager instance
        this.onTimerEnd = onTimerEnd; // Set callback for timer end
        this.font = font; // Set font for rendering text
        this.batch = batch; // Set SpriteBatch for rendering
    }

    // Method to update the timer based on elapsed time.
    public void update(float deltaTime) {
        if (timer > 0) {
            timer -= deltaTime; // Decrease timer by elapsed time

            // Trigger countdown sound at 10 seconds if not played already
            if ((int) Math.floor(timer) == 10 && !countdownPlayed) {
                audioManager.playSoundEffect("countdown", 1.0f); // Play countdown sound
                countdownPlayed = true; // Set countdown sound played flag
            }

            // Handle timer reaching zero
            if (timer <= 0) {
                timer = 0; // Set timer to zero
                audioManager.playSoundEffect("powerOff", 1.0f); // Play power off sound
                int currentScore = ScoreManager.getInstance().getCurrentScore();
                ScoreManager.getInstance().addScore(currentScore); // Add current score to high scores
                ScoreManager.getInstance().resetCurrentScore(); // Optionally reset current score
                if (onTimerEnd != null) {
                    onTimerEnd.run(); // Execute callback for timer end
                }
            }
        }
    }

    // Method to draw the timer on the screen.
    public void drawTimer(float screenWidth, float screenHeight) {
        String timerText = String.format("Time: %d", (int)Math.floor(timer)); // Format timer text
        GlyphLayout timerLayout = new GlyphLayout(font, timerText); // Create layout for timer text
        float timerX = screenWidth - timerLayout.width - 10; // Calculate x coordinate for timer text
        float timerY = screenHeight - timerLayout.height - 100; // Calculate y coordinate for timer text
        font.draw(batch, timerText, timerX, timerY); // Draw timer text on the screen
    }
}