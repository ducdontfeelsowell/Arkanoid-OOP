package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.input.InputHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public Button resumeGameButton;
    public Button backButton1;
    public Button playAgainButton;
    public Button backButton2;
    public Button backButton3;

    public AnchorPane loseScreen;
    public AnchorPane pauseScreen;
    public AnchorPane winScreen;

    public static boolean paused = false;
    private boolean escapeWasPressed = false;

    private InputHandler inputHandler;

    @FXML
    private MediaView win_backgroundMediaView; // Đã thêm

    @FXML
    private MediaView lose_backgroundMediaView;

    private MediaPlayer win_mediaPlayer; // Đã thêm
    private Media win_backgroundVideo;

    private MediaPlayer lose_mediaPlayer; // Đã thêm
    private Media lose_backgroundVideo;

    public void update() {
        if (inputHandler != null) {
            boolean escapePressed = inputHandler.isEscapePressed();

            // Detect escape key press (edge detection)
            if (escapePressed && !escapeWasPressed) {
                togglePause();
            }

            escapeWasPressed = escapePressed;
        }
    }

    private void togglePause() {
        paused = !paused;
        pauseScreen.setVisible(paused);
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public static boolean isPaused() {
        return paused;
    }

    @FXML
    public void initialize() {
        if (pauseScreen != null) {
            pauseScreen.setVisible(false);
        }
    }

    public void onResumeClick() {
        paused = false;
        pauseScreen.setVisible(false);
    }

    public void onBackClick1() {
        paused = false;
        pauseScreen.setVisible(false);
        Main.returnToMenu();
    }

    public void onBackClick2() {
        paused = false;
        loseScreen.setVisible(false);
        Main.returnToMenu();
    }

    public void onPLayAgainClick() {
        loseScreen.setVisible(false);
        Main.restartGame();
    }

    private void initializeLoseScreenVideo() {
        if (lose_mediaPlayer != null) {
            return; // Đã khởi tạo rồi
        }

        try {
            String videoPath = "/Images/background/lose_background.mp4";
            URL videoUrl = getClass().getResource(videoPath);
            if (videoUrl == null) {
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }

            lose_backgroundVideo = new Media(videoUrl.toExternalForm());
            lose_mediaPlayer = new MediaPlayer(lose_backgroundVideo);
            lose_backgroundMediaView.setMediaPlayer(lose_mediaPlayer);

            // Thiết lập phát lặp lại và tắt tiếng
            lose_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            lose_mediaPlayer.setMute(true);

        } catch (IOException e) {
            System.err.println("Lỗi khi load video thua:");
            e.printStackTrace();
            // Xử lý lỗi (có thể hiển thị màn hình tĩnh thay thế)
        }
    }

    public void showLoseScreen() {
        // 1. Khởi tạo MediaPlayer (chỉ lần đầu)
        initializeLoseScreenVideo();

        // 2. Bật cờ và hiển thị
        paused = true;
        loseScreen.setVisible(true);

        // 3. Bắt đầu chạy video
        if (lose_mediaPlayer != null) {
            lose_mediaPlayer.play();
        }
    }

    public void showWinScreen() {
        initializeWinScreenVideo();

        // 2. Bật cờ và hiển thị
        paused = true;
        winScreen.setVisible(true);

        // 3. Bắt đầu chạy video
        if (win_mediaPlayer != null) {
            win_mediaPlayer.play();
        }
    }

    public void onBackClick3() {
        winScreen.setVisible(false);
        paused = false;
        Main.returnToMenu();
    }

    private void initializeWinScreenVideo() {
        if (win_mediaPlayer != null) {
            return; // Đã khởi tạo rồi
        }

        try {
            String videoPath = "/Images/background/win_background.mp4";
            URL videoUrl = getClass().getResource(videoPath);
            if (videoUrl == null) {
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }

            win_backgroundVideo = new Media(videoUrl.toExternalForm());
            win_mediaPlayer = new MediaPlayer(win_backgroundVideo);
            win_backgroundMediaView.setMediaPlayer(win_mediaPlayer);

            // Thiết lập phát lặp lại và tắt tiếng
            win_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            win_mediaPlayer.setMute(true);

        } catch (IOException e) {
            System.err.println("Lỗi khi load video thua:");
            e.printStackTrace();
            // Xử lý lỗi (có thể hiển thị màn hình tĩnh thay thế)
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}