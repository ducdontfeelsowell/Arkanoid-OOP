package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.EffectManager;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;

public class CheckBallPaddleCollision {
    public static void check(Ball ball, Paddle paddle) {



        if ((ball.isCollidingWith(paddle) && ball.getDy() > 0) && !ball.getSideHit()) {

            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_PADDLE_HIT);

            double impactX = ball.getX() + ball.getWidth() / 2;
            double impactY = paddle.getY() - 5;

            ball.reverseY();

            double overlapLeft = ball.getX() + ball.getWidth() - paddle.getX();
            double overlapRight = paddle.getX() + paddle.getWidth() - ball.getX();
            double overlapTop = ball.getY() + ball.getHeight() - paddle.getY();
            double overlapBottom = paddle.getY() + paddle.getHeight() - ball.getY();

            double overlapX = Math.min(overlapLeft, overlapRight);
            double overlapY = Math.min(overlapTop, overlapBottom);


            if (overlapX < overlapY) {
                ball.setSideHit(true);
                ball.setDy(Math.abs(ball.getDy()));
                if (overlapLeft < overlapRight) {
                    ball.setX(ball.getX() - overlapLeft);
                    ball.setDx(-Math.abs(ball.getDx()));
                } else {
                    ball.setX(ball.getX() + overlapRight);
                    ball.setDx(Math.abs(ball.getDx()));
                }

            } else {
                double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
                double ballCenter = ball.getX() + ball.getWidth() / 2;
                double offset = (ballCenter - paddleCenter) / (paddle.getWidth() / 2);

                if (Math.abs(offset) < Constants.DEFAULT_BALL_OFFSET) {
                    offset = Math.signum(offset) * Constants.DEFAULT_BALL_OFFSET;
                } else if (Math.abs(offset) >= Constants.DEFAULT_BALL_OFFSET_CAP) {
                    offset = Math.signum(offset) * Constants.DEFAULT_BALL_OFFSET_CAP;
                }
                ball.setOffset(offset);
            }
        }

        else if (!ball.isCollidingWith(paddle)) {
            ball.setSideHit(false);
        }
    }
}