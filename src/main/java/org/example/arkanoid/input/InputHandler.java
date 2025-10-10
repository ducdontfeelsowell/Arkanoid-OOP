package org.example.arkanoid.input;

import javafx.scene.Scene;
import org.example.arkanoid.object.Paddle;

public class InputHandler {
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean enterPressed;
    private boolean escapePressed;

    public InputHandler(Scene scene) {
        attach(scene);
    }

    public void attach(Scene scene) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> leftPressed = true;
                case RIGHT -> rightPressed = true;
                case ENTER -> enterPressed = true;
                case ESCAPE -> escapePressed = true;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT -> leftPressed = false;
                case RIGHT -> rightPressed = false;
                case ENTER -> enterPressed = false;
                case ESCAPE -> escapePressed = false;
            }
        });
    }

    public void handleInput(Paddle paddle) {
        if (isLeftPressed()) {
            paddle.moveLeft();
        } else if (isRightPressed()) {
            paddle.moveRight();
        }
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public boolean isEscapePressed() {
        return escapePressed;
    }
}
