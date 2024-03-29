package GameEngine.SimulationLifecycleManagement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;

// AudioManager manages audio in the game, including background music and sound effects.
public class AudioManager {
    private static AudioManager instance; // Singleton instance of AudioManager
    private Music backgroundMusic; // Background music
    private boolean isMusicMuted; // Flag to indicate whether music is muted
    private Map<String, Sound> soundEffects; // Map to store loaded sound effects

    // Private constructor to prevent direct instantiation
    private AudioManager() {
        soundEffects = new HashMap<>();
        loadSoundEffects(); // Load sound effects
    }

    // Method to load sound effects into the map
    private void loadSoundEffects() {
        soundEffects.put("collision", Gdx.audio.newSound(Gdx.files.internal("HitImpactSound.mp3")));
        soundEffects.put("errorSound", Gdx.audio.newSound(Gdx.files.internal("ErrorSound.mp3")));
        soundEffects.put("correctBin", Gdx.audio.newSound(Gdx.files.internal("CorrectBinSound.mp3")));
        soundEffects.put("powerOff", Gdx.audio.newSound(Gdx.files.internal("PowerOffSound.mp3")));
        soundEffects.put("itemPickup", Gdx.audio.newSound(Gdx.files.internal("ItemPickupSound.mp3")));
        soundEffects.put("beep", Gdx.audio.newSound(Gdx.files.internal("beep.mp3")));
        soundEffects.put("buttonSound", Gdx.audio.newSound(Gdx.files.internal("ButtonSound.mp3")));
        // Add other sound effects here
    }

    // Singleton getInstance method
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    // Method to play background music
    public void playBackgroundMusic(String filePath, boolean isLooping, float volume) {
        if (backgroundMusic != null) {
            backgroundMusic.stop(); // Stop any currently playing music
        }
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        backgroundMusic.setLooping(isLooping);
        backgroundMusic.setVolume(isMusicMuted ? 0 : volume);
        backgroundMusic.play();
    }

    // Method to stop countdown sound effect
    public void stopCountdownSound() {
        Sound countdownSound = soundEffects.get("countdown");
        if (countdownSound != null) {
            countdownSound.stop();
        }
    }

    // Method to toggle music mute state
    public void toggleMusicMute() {
        isMusicMuted = !isMusicMuted;
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(isMusicMuted ? 0 : 0.1f); // Adjust volume as necessary
        }
    }

    // Method to play sound effects
    public void playSoundEffect(String name, float volume) {
        Sound soundEffect = soundEffects.get(name);
        if (soundEffect != null) {
            soundEffect.play();
        }
    }

    // Method to dispose of resources
    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        for (Sound sound : soundEffects.values()) {
            if (sound != null) {
                sound.dispose();
            }
        }
    }

    // Getter for isMusicMuted flag
    public boolean isMusicMuted() {
        return isMusicMuted;
    }
}

