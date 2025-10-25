package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.input.InputHandler;

public class GameController {
    public Button resumeGameButton;
    public Button backButton1;
    public Button playAgainButton;
    public Button backButton2;
    public Button backButton3;
    public VBox loseScreen;
    public VBox pauseScreen;
    public VBox winScreen;

    public static boolean paused = false;
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