package org.example.arkanoid.input;

import javafx.scene.Scene;
import org.example.arkanoid.game.BulletManager;
import org.example.arkanoid.object.Paddle;

import static org.example.arkanoid.config.Constants.isStarted;

public class InputHandler {
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean enterPressed;
    private boolean escapePressed;
    private boolean spacePressed;

    private boolean spaceWasPressed = false;

    public InputHandler(Scene scene) {
        attach(scene);
    }

    public void attach(Scene scene) {

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case SPACE -> spacePressed = true;
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
                case SPACE -> spacePressed = false;
            }
        });
    }

    public void handleInput(Paddle paddle, BulletManager bm) {
        if (isSpacePressed()) {
            if (!spaceWasPressed) {
                if (!isStarted) {
                    isStarted = true;
                } else if (paddle.isShooter()) {
                    bm.shoot(paddle.getX(), paddle.getY(), paddle.getWidth());
                }
            }
            spaceWasPressed = true;
        } else {
            spaceWasPressed = false;
        }

        if (isLeftPressed()) {
            paddle.moveLeft();
        } else if (isRightPressed()) {
            paddle.moveRight();
        } else {
            paddle.stopMove();
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

    public boolean isSpacePressed() {
        return spacePressed;
    }
}