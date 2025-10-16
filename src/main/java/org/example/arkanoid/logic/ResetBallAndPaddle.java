package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;

public class ResetBallAndPaddle {
    public static void reset(Ball ball, Paddle paddle) {
        Constants.isStarted = false;
        // Reset ball
        ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
        ball.setY(paddle.getY() - ball.getHeight() - 1);
        ball.setOffset(Constants.DEFAULT_BALL_OFFSET);
        CheckBallPaddleCollision.sideHit = false;
    }
}
