package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.input.InputHandler;

public class GameController {
    @FXML
    private AnchorPane pauseScreen;

    @FXML
    private Button resumeGameButton;

    @FXML
    private Button resetLevelButton;

    @FXML
    private Button backToMenuButton;

    private static boolean paused = false;
    private boolean escapeWasPressed = false;

    private InputHandler inputHandler;

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

    @FXML
    public void onResumeGameClick() {
        paused = false;
        pauseScreen.setVisible(false);
    }

    @FXML
    public void onBackToMenuClick() {
        paused = false;
        pauseScreen.setVisible(false);
        Main.returnToMenu();
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
}