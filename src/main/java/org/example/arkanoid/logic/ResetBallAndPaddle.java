package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;

public class ResetBallAndPaddle {
    public static void reset(Ball ball, Paddle paddle) {
        paddle.setX(Constants.DEFAULT_PADDLE_POSITION_X);
        paddle.setY(Constants.DEFAULT_PADDLE_POSITION_Y);

        // Reset ball
        ball.setX(Constants.DEFAULT_BALL_POSITION_X);
        ball.setY(Constants.DEFAULT_BALL_POSITION_Y);
        ball.setDirectionX(Constants.DEFAULT_BALL_DIRECTION_X);
        ball.setDirectionY(Constants.DEFAULT_BALL_DIRECTION_Y);
    }
}
