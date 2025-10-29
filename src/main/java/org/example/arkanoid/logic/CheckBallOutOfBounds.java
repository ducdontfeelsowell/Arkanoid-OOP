package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.sound.SoundManager;

public class CheckBallOutOfBounds {
    public static void check(Ball ball, Paddle paddle, GameManager gm) {
        // Logic to check if the ball is out of bounds
        if (ball.getY() + ball.getHeight() >= Constants.SCREEN_HEIGHT) {
            SoundManager.playBallDrop();
            gm.setLives(gm.getLives() - 1);

            if (gm.getLives() <= 0) {
                gm.setGameOver(true);
            } else {
                ResetBallAndPaddle.reset(ball, paddle);
            }
        }
    }
}
