package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;

public class CheckBallPaddleCollision {
    public static boolean sideHit = false;

    public static void check(Ball ball, Paddle paddle) {
        if (ball.isCollidingWith(paddle) && ball.getDy() > 0) {

            double overlapLeft = ball.getX() + ball.getWidth() - paddle.getX();
            double overlapRight = paddle.getX() + paddle.getWidth() - ball.getX();
            double overlapTop = ball.getY() + ball.getHeight() - paddle.getY();
            double overlapBottom = paddle.getY() + paddle.getHeight() - ball.getY();

            double overlapX = Math.min(overlapLeft, overlapRight);
            double overlapY = Math.min(overlapTop, overlapBottom);

            //Chạm side rồi thì chỉ chòn đẩy bóng, nếu paddle nhanh hơn
            if (sideHit) {
                if(overlapLeft < overlapRight) {
                    ball.setX(ball.getX() - overlapLeft);
                } else {
                    ball.setX(ball.getX() + overlapRight);
                }
                return;
            }

            // Bóng chạm cạnh hay chạm trên
            if (overlapX < overlapY) {
                sideHit = true;
                // Chỉnh vị trí để bóng không dính vào trong paddle
                if (overlapLeft < overlapRight) {
                    ball.setX(ball.getX() - overlapLeft);
                    ball.setDx(-Math.abs(ball.getDx()));
                } else {
                    ball.setX(ball.getX() + overlapRight);
                    ball.setDx(Math.abs(ball.getDx()));
                }

            } else {
                // Điều chỉnh hướng X dựa trên vị trí chạm
                double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
                double ballCenter = ball.getX() + ball.getWidth() / 2;
                double offset = (ballCenter - paddleCenter) / (paddle.getWidth() / 2);

                // Thay đổi góc phản xạ dựa vào vị trí va chạm
                if (Math.abs(offset) < Constants.DEFAULT_BALL_OFFSET) {
                    offset = Math.signum(offset) * Constants.DEFAULT_BALL_OFFSET;
                } else if (Math.abs(offset) >= Constants.DEFAULT_BALL_OFFSET_CAP) {
                    offset = Math.signum(offset) * Constants.DEFAULT_BALL_OFFSET_CAP;
                }
                ball.setOffset(offset);
            }
        }
    }
}
