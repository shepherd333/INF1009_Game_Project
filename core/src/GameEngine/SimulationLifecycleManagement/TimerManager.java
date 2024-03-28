package GameEngine.SimulationLifecycleManagement;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TimerManager {
    private float timer;
    private boolean countdownPlayed = false;
    private final AudioManager audioManager;
    private final Runnable onTimerEnd;
    private BitmapFont font;
    private SpriteBatch batch;

    public TimerManager(float startTime, AudioManager audioManager, Runnable onTimerEnd, BitmapFont font, SpriteBatch batch) {
        this.timer = startTime;
        this.audioManager = audioManager;
        this.onTimerEnd = onTimerEnd;
        this.font = font;
        this.batch = batch;
    }

    public void update(float deltaTime) {
        if (timer > 0) {
            timer -= deltaTime;

            // Trigger countdown sound at 10 seconds
            if ((int) Math.floor(timer) == 10 && !countdownPlayed) {
                audioManager.playCountdownSound();
                countdownPlayed = true;
            }

            // Handle timer reaching zero
            if (timer <= 0) {
                timer = 0;
                audioManager.stopCountdownSound();
                audioManager.powerOffSound.play();
                if (onTimerEnd != null) {
                    onTimerEnd.run();
                }
            }
        }
    }

    public void drawTimer(float screenWidth, float screenHeight) {
        String timerText = String.format("Time: %d", (int)Math.floor(timer));
        GlyphLayout timerLayout = new GlyphLayout(font, timerText);
        float timerX = screenWidth - timerLayout.width - 10;
        float timerY = screenHeight - timerLayout.height - 100;
        font.draw(batch, timerText, timerX, timerY);
    }

    public float getTimer() {
        return timer;
    }

    public void setTimer(float timer) {
        this.timer = timer;
    }

    public void increaseTimer(int seconds) {
        this.timer += seconds;
    }

    public void decreaseTimer(int seconds) {
        this.timer -= seconds;
    }
}
