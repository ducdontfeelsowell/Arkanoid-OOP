package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;

public class HandleBrickCollision {
    public static void handle(Ball ball, Brick brick, GameManager gm) {
        double overlapLeft = (ball.getX() + ball.getWidth()) - brick.getX();
        double overlapRight = (brick.getX() + brick.getWidth()) - ball.getX();
        double overlapTop = (ball.getY() + ball.getHeight()) - brick.getY();
        double overlapBottom = (brick.getY() + brick.getHeight()) - ball.getY();

        double minOverlapX = Math.min(overlapLeft, overlapRight);
        double minOverlapY = Math.min(overlapTop, overlapBottom);

        // Đảo chiều dựa trên hướng va chạm
        if (minOverlapX < minOverlapY) {
            ball.reverseX();
        } else {
            ball.reverseY();
        }

        // Giảm hit points và cộng điểm
        if (brick.getType() != 3) { // Không phải indestructible brick
            brick.takeHit();
            if (brick.isDestroyed()) {
                gm.setScore(gm.getScore() + Constants.POINTS_PER_BRICK * brick.getType());
            }
        }
    }
}
