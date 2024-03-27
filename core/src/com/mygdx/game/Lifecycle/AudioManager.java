package com.mygdx.game.Lifecycle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    private static AudioManager instance;
    private Music backgroundMusic;
    private boolean isMusicMuted;

    private AudioManager() {
        // Private constructor to prevent instantiation
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    public static final Sound collisionSound = Gdx.audio.newSound(Gdx.files.internal("HitImpactSound.mp3"));
    public static final Sound errorSound = Gdx.audio.newSound(Gdx.files.internal("ErrorSound.mp3"));
    public static final Sound correctBinSound = Gdx.audio.newSound(Gdx.files.internal("CorrectBinSound.mp3"));
    public static final Sound powerOffSound = Gdx.audio.newSound(Gdx.files.internal("PowerOffSound.mp3"));
    public static final Sound itemPickupSound = Gdx.audio.newSound(Gdx.files.internal("ItemPickUpSound.mp3"));
    public void playBackgroundMusic(String filePath, boolean isLooping, float volume) {
        if (backgroundMusic != null) {
            backgroundMusic.stop(); // Stop any currently playing music
        }
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
        backgroundMusic.setLooping(isLooping);
        backgroundMusic.setVolume(isMusicMuted ? 0 : volume);
        backgroundMusic.play();
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public void toggleMusicMute() {
        isMusicMuted = !isMusicMuted;
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(isMusicMuted ? 0 : 0.1f); // Adjust volume as necessary
        }
    }

    // Method to play sound effects
    public void playSoundEffect(String filePath, float volume) {
        Sound soundEffect = Gdx.audio.newSound(Gdx.files.internal(filePath));
        soundEffect.play(isMusicMuted ? 0 : volume);
    }

    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        if (collisionSound != null) {
            collisionSound.dispose();
        }
        if (errorSound != null) {
            errorSound.dispose();
        }
        if (correctBinSound != null) {
            correctBinSound.dispose();
        }
        if (powerOffSound != null) {
            powerOffSound.dispose();
        }
        if (itemPickupSound != null) {
            itemPickupSound.dispose();
        }


        // Dispose other sounds if you're keeping references to them
    }

    public boolean isMusicMuted() {
        return isMusicMuted;
    }
}

