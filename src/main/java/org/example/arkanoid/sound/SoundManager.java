package org.example.arkanoid.sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.net.URL;

public class SoundManager {

    // Sound Effects (SFX)
    private static AudioClip paddleHitSound;
    private static AudioClip brickHitSound;
    private static AudioClip ballDropSound;
    private static AudioClip menuHoverSound; // <-- THÊM MỚI

    // Background Music (BGM)
    private static MediaPlayer musicPlayer;

    private static boolean gameSoundsLoaded = false; // <-- Đổi tên từ isLoaded
    private static boolean menuSoundsLoaded = false; // <-- THÊM MỚI
    private static boolean bgmLoaded = false;        // <-- THÊM MỚI

    /**
     * Tải các âm thanh cần thiết cho Menu (như hover).
     * Hàm này có thể gọi ngay khi mở Menu mà không cần đợi vào game.
     */
    public static void loadMenuSounds() {
        if (menuSoundsLoaded) return; // Chỉ tải 1 lần

        try {
            // *** LƯU Ý: Đặt file âm thanh (ví dụ: menu_hover.wav) vào:
            // src/main/resources/Sounds/
            menuHoverSound = loadClip("/Sounds/menu_hover.wav"); // <-- THÊM MỚI
        } catch (Exception e) {
            System.err.println("Lỗi tải hiệu ứng âm thanh (SFX) cho Menu: " + e.getMessage());
        }
        menuSoundsLoaded = true;
    }

    /**
     * Tải các âm thanh nặng dùng trong Game (SFX và BGM).
     * Hàm này nên gọi khi nhấn nút Play (trong Main.startGame).
     */
    public static void loadGameSounds() {
        // Đảm bảo chỉ tải 1 lần
        if (gameSoundsLoaded) return;

        // Tải SFX (AudioClip)
        try {
            paddleHitSound = loadClip("/Sounds/hit_paddle.wav");
            brickHitSound = loadClip("/Sounds/hit_brick.wav");
            ballDropSound = loadClip("/Sounds/ball_drop.wav");
        } catch (Exception e) {
            System.err.println("Lỗi tải hiệu ứng âm thanh (SFX) trong game: " + e.getMessage());
        }
        gameSoundsLoaded = true;

        // Tải BGM (MediaPlayer)
        if (!bgmLoaded) { // Chỉ tải BGM nếu chưa tải
            try {
                URL musicURL = SoundManager.class.getResource("/Music/background.mp3");

                if (musicURL == null) {
                    System.err.println("Không tìm thấy file nhạc nền tại: /Music/background.mp3");
                } else {
                    Media media = new Media(musicURL.toExternalForm());
                    musicPlayer = new MediaPlayer(media);
                    musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
                    musicPlayer.setVolume(0.3);
                }
            } catch (Exception e) {
                System.err.println("Lỗi tải nhạc nền (BGM): " + e.getMessage());
                e.printStackTrace();
            }
            bgmLoaded = true;
        }
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
        if (gameSoundsLoaded && paddleHitSound != null) {
            paddleHitSound.play();
        }
    }

    public static void playBrickHit() {
        if (gameSoundsLoaded && brickHitSound != null) {
            brickHitSound.play();
        }
    }

    public static void playBallDrop() {
        if (gameSoundsLoaded && ballDropSound != null) {
            ballDropSound.play();
        }
    }

    /**
     * Phát âm thanh khi hover chuột qua nút menu.
     */
    public static void playMenuHover() { // <-- THÊM MỚI
        if (menuSoundsLoaded && menuHoverSound != null) {
            menuHoverSound.play();
        }
    }

    // --- Các hàm điều khiển nhạc nền (BGM) ---

    /**
     * Phát nhạc nền (có lặp lại).
     */
    public static void playMusic() {
        if (bgmLoaded && musicPlayer != null) {
            musicPlayer.play();
        }
    }

    /**
     * Dừng nhạc nền (khi quay về Menu).
     */
    public static void stopMusic() {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
    }
}