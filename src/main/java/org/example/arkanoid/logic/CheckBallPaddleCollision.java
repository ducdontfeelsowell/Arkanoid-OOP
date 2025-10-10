package org.example.arkanoid.logic;

import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;

public class CheckBallPaddleCollision {
    public static void check(Ball ball, Paddle paddle) {
        if (ball.isCollidingWith(paddle) && ball.getDy() > 0) {
            ball.reverseY();

            // Điều chỉnh hướng X dựa trên vị trí chạm
            double paddleCenter = paddle.getX() + paddle.getWidth() / 2;
            double ballCenter = ball.getX() + ball.getWidth() / 2;
            double offset = (ballCenter - paddleCenter) / (paddle.getWidth() / 2);

            // Thay đổi góc phản xạ dựa vào vị trí va chạm
            ball.setDirectionX((int) Math.signum(offset));
            if (ball.getDirectionX() == 0) {
                ball.setDirectionX(1);
            }

            // Đặt lại vị trí bóng để không dính vào paddle
            ball.setY(paddle.getY() - ball.getHeight());
        }
    }
}
