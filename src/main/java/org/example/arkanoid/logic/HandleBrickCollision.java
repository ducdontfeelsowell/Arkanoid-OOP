package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.game.EffectManager;
import org.example.arkanoid.object.Brick.InfBrick;

public class HandleBrickCollision {
    public static void handle(Ball ball, Brick[][] bricks, Brick brick, GameManager gm, ItemManager im, int row, int col) {


        System.out.println("Ball collided with brick of type: " + brick.getType());
        double overlapLeft = ball.getX() + ball.getWidth() - brick.getX();
        double overlapRight = brick.getX() + brick.getWidth() - ball.getX();
        double overlapTop = ball.getY() + ball.getHeight() - brick.getY();
        double overlapBottom = brick.getY() + brick.getHeight() - ball.getY();

        double minOverlapX = Math.min(overlapLeft, overlapRight);
        double minOverlapY = Math.min(overlapTop, overlapBottom);

        // Đảo chiều dựa trên hướng va chạm
        if (minOverlapX < minOverlapY) {
            if (overlapLeft < overlapRight) {
                ball.setX(ball.getX() - overlapLeft - 1);
            } else {
                ball.setX(ball.getX() + overlapRight + 1);
            }
            ball.reverseX();
        } else {
            if (overlapTop < overlapBottom) {
                ball.setY(ball.getY() - overlapTop - 1);
            } else {
                ball.setY(ball.getY() + overlapBottom + 1);
            }
            ball.reverseY();
        }

        BreakBrick.breakIt(bricks, brick, gm, im, row, col);
    }
}
