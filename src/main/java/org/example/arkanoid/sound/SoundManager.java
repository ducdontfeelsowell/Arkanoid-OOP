package org.example.arkanoid.sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;       // <-- THÊM MỚI
import javafx.scene.media.MediaPlayer;  // <-- THÊM MỚI
import javafx.util.Duration;           // <-- THÊM MỚI
import java.net.URL;

public class SoundManager {

    // Sound Effects (SFX)
    private static AudioClip paddleHitSound;
    private static AudioClip brickHitSound;
    private static AudioClip ballDropSound;

    // Background Music (BGM)
    private static MediaPlayer musicPlayer; // <-- THÊM MỚI

    private static boolean isLoaded = false;

    public static void loadSounds() {
        // Đảm bảo chỉ tải 1 lần
        if (isLoaded) return;

        // Tải SFX (AudioClip)
        try {
            paddleHitSound = loadClip("/Sounds/hit_paddle.wav");
            brickHitSound = loadClip("/Sounds/hit_brick.wav");
            ballDropSound = loadClip("/Sounds/ball_drop.wav");
        } catch (Exception e) {
            System.err.println("Lỗi tải hiệu ứng âm thanh (SFX): " + e.getMessage());
        }

        // Tải BGM (MediaPlayer)
        try {
            // *** LƯU Ý: Đặt file nhạc nền (ví dụ: background.mp3) vào thư mục:
            // src/main/resources/Music/
            URL musicURL = SoundManager.class.getResource("/Music/background.mp3"); // <-- THÊM MỚI

            if (musicURL == null) {
                System.err.println("Không tìm thấy file nhạc nền tại: /Music/background.mp3");
            } else {
                Media media = new Media(musicURL.toExternalForm()); // <-- THÊM MỚI
                musicPlayer = new MediaPlayer(media); // <-- THÊM MỚI

                // Cài đặt để nhạc tự động lặp lại (loop)
                musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO)); // <-- THÊM MỚI

                // Đặt âm lượng nhạc nền (ví dụ: 30% để không át tiếng SFX)
                musicPlayer.setVolume(0.3); // <-- THÊM MỚI
            }
        } catch (Exception e) {
            System.err.println("Lỗi tải nhạc nền (BGM): " + e.getMessage());
            e.printStackTrace();
        }

        isLoaded = true;
    }

    /**
     * Hàm trợ giúp nội bộ để tải một AudioClip từ đường dẫn resource.
     */
    private static AudioClip loadClip(String resourcePath) {
        URL resourceURL = SoundManager.class.getResource(resourcePath);
        if (resourceURL == null) {
            System.err.println("Không tìm thấy file âm thanh tại: " + resourcePath);
            return null;
        }
        return new AudioClip(resourceURL.toExternalForm());
    }

    // --- Các hàm phát SFX ---
    public static void playPaddleHit() {
        if (isLoaded && paddleHitSound != null) {
            paddleHitSound.play();
        }
    }

    public static void playBrickHit() {
        if (isLoaded && brickHitSound != null) {
            brickHitSound.play();
        }
    }

    public static void playBallDrop() {
        if (isLoaded && ballDropSound != null) {
            ballDropSound.play();
        }
    }

    // --- Các hàm điều khiển nhạc nền (BGM) ---

    /**
     * Phát nhạc nền (có lặp lại).
     */
    public static void playMusic() { // <-- THÊM MỚI (toàn bộ hàm)
        if (musicPlayer != null) {
            musicPlayer.play();
        }
    }

    /**
     * Dừng nhạc nền (khi quay về Menu).
     */
    public static void stopMusic() { // <-- THÊM MỚI (toàn bộ hàm)
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }
}