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
    private MediaPlayer sequentialPlayer; // Trình phát cho âm thanh tuần tự (Win/Lose)
    private boolean isMuted = false;
    private double masterVolume = 1;
    private double musicVolume = 1;
    private double musicVolumeRaw = 1.0;     // 0.0 - 1.0 (slider value)
    private double soundEffectVolume = 1;
    private double sfxVolumeRaw = 1.0;       // 0.0 - 1.0 (slider value)

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
            // Dòng debug để xem tệp nhạc nào đang được phát
            System.out.println("SoundManager DEBUG: Đang phát nhạc: " + soundPath);

            stopBackgroundMusic(); // Dừng cả nhạc nền và nhạc tuần tự

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

    /**
     * Phát hai bản nhạc tuần tự (dùng cho Win/Lose).
     * Bản nhạc 1 phát, khi kết thúc, Bản nhạc 2 sẽ phát.
     */
    public void playMusicSequence(String path1, String path2) {
        if (isMuted) return;

        try {
            stopBackgroundMusic(); // Dừng mọi thứ đang phát

            Media media1 = new Media(Objects.requireNonNull(
                    getClass().getResource(path1)).toExternalForm());

            sequentialPlayer = new MediaPlayer(media1);
            sequentialPlayer.setVolume(musicVolume); // Same as music

            // Đặt sự kiện khi media1 kết thúc
            sequentialPlayer.setOnEndOfMedia(() -> {
                // Hủy trình phát cũ
                sequentialPlayer.stop();
                sequentialPlayer.dispose();

                // Tạo và phát media2
                try {
                    Media media2 = new Media(Objects.requireNonNull(
                            getClass().getResource(path2)).toExternalForm());
                    sequentialPlayer = new MediaPlayer(media2);
                    sequentialPlayer.setVolume(musicVolume);

                    // --- THÊM MỚI: Đặt lặp lại vô tận cho nhạc afterwin/afterlose ---
                    sequentialPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                    // --- KẾT THÚC THÊM MỚI ---

                    sequentialPlayer.play();
                } catch (Exception e) {
                    System.err.println("Không thể phát phần 2 (after) của nhạc: " + path2);
                    e.printStackTrace();
                }
            });

            // Bắt đầu phát media1
            sequentialPlayer.play();

        } catch (Exception e) {
            System.err.println("Không thể phát phần 1 (chính) của nhạc: " + path1);
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
            clip.setVolume(soundEffectVolume);
            clip.play();
        } catch (Exception e) {
            System.err.println("Không thể phát hiệu ứng âm thanh: " + soundPath);
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        // Dừng nhạc nền
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.dispose();
            backgroundMusicPlayer = null;
        }
        // Dừng cả nhạc tuần tự (nếu đang phát)
        if (sequentialPlayer != null) {
            sequentialPlayer.stop();
            sequentialPlayer.dispose();
            sequentialPlayer = null;
        }
    }

    public void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.pause();
        }
        // Tạm dừng cả nhạc tuần tự (nếu đang phát)
        if (sequentialPlayer != null) {
            sequentialPlayer.pause();
        }
    }

    public void resumeBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
        // Tiếp tục cả nhạc tuần tự (nếu đang phát)
        if (sequentialPlayer != null) {
            sequentialPlayer.play();
        }
    }

    public void toggleMute() {
        isMuted = !isMuted;
        double newVolume = isMuted ? 0 : musicVolume;

        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(newVolume);
        }
        if (sequentialPlayer != null) {
            sequentialPlayer.setVolume(newVolume);
        }
    }

    public void setMusicVolume(double volume) {
        musicVolume = Math.max(0.0, Math.min(1.0, volume));
        if (backgroundMusicPlayer != null && !isMuted) {
            backgroundMusicPlayer.setVolume(musicVolume);
        }
        if (sequentialPlayer != null && !isMuted) {
            sequentialPlayer.setVolume(musicVolume);
        }
    }

    public double getMasterVolume() {
        return masterVolume;
    }

    public double getMusicVolume() {
        return musicVolume;
    }

    public double getSoundEffectVolume() {
        return soundEffectVolume;
    }

    public void setMasterVolume(double masterVolume) {
        this.masterVolume = masterVolume;
    }

    public void setSoundEffectVolume(double soundEffectVolume) {
        this.soundEffectVolume = soundEffectVolume;
    }

    public double getSfxVolumeRaw() {
        return sfxVolumeRaw;
    }

    public void setSfxVolumeRaw(double sfxVolumeRaw) {
        this.sfxVolumeRaw = sfxVolumeRaw;
    }

    public double getMusicVolumeRaw() {
        return musicVolumeRaw;
    }

    public void setMusicVolumeRaw(double musicVolumeRaw) {
        this.musicVolumeRaw = musicVolumeRaw;
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void playDefaultGameMusic() {
        playBackgroundMusic(Constants.PATH_TO_SOUND_BACKGROUND_1);
    }

    public void playRandomBackgroundMusic() {
        // Chỉ random nhạc 1 và 2 cho menu
        String[] musicTracks = {
                Constants.PATH_TO_SOUND_BACKGROUND_1,
                Constants.PATH_TO_SOUND_BACKGROUND_2
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
