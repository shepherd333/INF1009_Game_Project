package GameEngine.SimulationLifecycleManagement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static AudioManager instance;
    private Music backgroundMusic;
    private boolean isMusicMuted;
    private Map<String, Sound> soundEffects;

    private AudioManager() {
        soundEffects = new HashMap<>();
        loadSoundEffects();
    }

    private void loadSoundEffects() {
        soundEffects.put("collision", Gdx.audio.newSound(Gdx.files.internal("HitImpactSound.mp3")));
        soundEffects.put("errorSound", Gdx.audio.newSound(Gdx.files.internal("ErrorSound.mp3")));
        soundEffects.put("correctBin", Gdx.audio.newSound(Gdx.files.internal("CorrectBinSound.mp3")));
        soundEffects.put("powerOff", Gdx.audio.newSound(Gdx.files.internal("PowerOffSound.mp3")));
        soundEffects.put("itemPickup", Gdx.audio.newSound(Gdx.files.internal("ItemPickupSound.mp3")));
        soundEffects.put("countdown", Gdx.audio.newSound(Gdx.files.internal("countdown.mp3")));
        // Add other sound effects here
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    public void playBackgroundMusic(String filePath, boolean isLooping, float volume) {
        if (backgroundMusic != null) {
            backgroundMusic.stop(); // Stop any currently playing music
        }
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        backgroundMusic.setLooping(isLooping);
        backgroundMusic.setVolume(isMusicMuted ? 0 : volume);
        backgroundMusic.play();
    }


    public void stopCountdownSound() {
        Sound countdownSound = soundEffects.get("countdown");
        if (countdownSound != null) {
            countdownSound.stop();
        }
    }

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
    public boolean isMusicMuted() {
        return isMusicMuted;
    }

}

