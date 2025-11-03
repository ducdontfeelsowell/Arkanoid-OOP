package org.example.arkanoid.game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.arkanoid.config.Constants;

import java.util.Objects;
import java.util.Random;

public class SoundManager {

    private static SoundManager instance;

    private MediaPlayer backgroundMusicPlayer;
    private boolean isMuted = false;
    private double musicVolume = 0.5;

    private int lastTrackIndex = -1;
    private Random random = new Random();

    private SoundManager() {
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void playBackgroundMusic(String soundPath) {
        try {
            System.out.println("SoundManager DEBUG: Đang phát nhạc: " + soundPath);
            stopBackgroundMusic();

            Media media = new Media(Objects.requireNonNull(
                    getClass().getResource(soundPath)).toExternalForm());

            backgroundMusicPlayer = new MediaPlayer(media);
            backgroundMusicPlayer.setVolume(isMuted ? 0 : musicVolume);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            backgroundMusicPlayer.play();

        } catch (Exception e) {
            System.err.println("Không thể phát nhạc nền: " + soundPath);
            e.printStackTrace();
        }
    }

    public void playSoundEffect(String soundPath) {
        if (isMuted) {
            return;
        }
        try {
            AudioClip clip = new AudioClip(Objects.requireNonNull(
                    getClass().getResource(soundPath)).toExternalForm());
            clip.setVolume(musicVolume);
            clip.play();
        } catch (Exception e) {
            System.err.println("Không thể phát hiệu ứng âm thanh: " + soundPath);
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.dispose();
            backgroundMusicPlayer = null;
        }
    }

    public void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.pause();
        }
    }

    public void resumeBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }

    public void toggleMute() {
        isMuted = !isMuted;
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(isMuted ? 0 : musicVolume);
        }
    }

    public void setMusicVolume(double volume) {
        musicVolume = Math.max(0.0, Math.min(1.0, volume));
        if (backgroundMusicPlayer != null && !isMuted) {
            backgroundMusicPlayer.setVolume(musicVolume);
        }
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void playDefaultGameMusic() {
        playBackgroundMusic(Constants.PATH_TO_SOUND_BACKGROUND_1);
    }

    public void playRandomBackgroundMusic() {
        String[] musicTracks = {
                Constants.PATH_TO_SOUND_BACKGROUND_1,
                Constants.PATH_TO_SOUND_BACKGROUND_2,
                Constants.PATH_TO_SOUND_BACKGROUND_3,
                Constants.PATH_TO_SOUND_BACKGROUND_4
        };

        if (musicTracks.length == 0) {
            return;
        }

        if (musicTracks.length == 1) {
            lastTrackIndex = 0;
            playBackgroundMusic(musicTracks[0]);
            return;
        }

        int newIndex;
        do {
            newIndex = random.nextInt(musicTracks.length);
        } while (newIndex == lastTrackIndex);

        lastTrackIndex = newIndex;
        playBackgroundMusic(musicTracks[newIndex]);
    }
}