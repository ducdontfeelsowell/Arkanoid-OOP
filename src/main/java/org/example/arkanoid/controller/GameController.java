package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.input.InputHandler;

public class GameController {
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

    public void update() {
        if (inputHandler != null) {
            boolean escapePressed = inputHandler.isEscapePressed();

            if (escapePressed && !escapeWasPressed) {
                togglePause();
            }

            escapeWasPressed = escapePressed;
        }
    }

    private void togglePause() {
        paused = !paused;
        pauseScreen.setVisible(paused);

        // --- THÊM MỚI: Phát âm thanh Pause/Unpause ---
        if (paused) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_PAUSE);
        } else {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_UNPAUSE);
        }
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public static boolean isPaused() {
        return paused;
    }

    private void addHoverSound(Button button) {
        if (button != null) {
            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                }
            });
        }
    }

    @FXML
    public void initialize() {
        if (pauseScreen != null) {
            pauseScreen.setVisible(false);
        }

        addHoverSound(resumeGameButton);
        addHoverSound(backButton1);

        addHoverSound(playAgainButton);
        addHoverSound(backButton2);

        addHoverSound(backButton3);
    }

    public void onResumeClick() {
        paused = false;
        pauseScreen.setVisible(false);
        // --- THÊM MỚI: Phát âm thanh Unpause khi nhấn nút ---
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_UNPAUSE);
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

    public void showLoseScreen() {
        loseScreen.setVisible(true);
        paused = true;
    }

    public void showWinScreen() {
        winScreen.setVisible(true);
        paused = true;
    }

    public void onBackClick3() {
        winScreen.setVisible(false);
        paused = false;
        Main.returnToMenu();
    }
}