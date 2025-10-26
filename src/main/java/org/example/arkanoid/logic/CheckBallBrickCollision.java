package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;

public class CheckBallBrickCollision {
    /*
    public static void check(Ball ball, Brick[][] bricks, GameManager gm, ItemManager im) {
        for (Brick[] row : bricks) {
            for (Brick brick : row) {
                if (brick != null && !brick.isDestroyed() && ball.isCollidingWith(brick)) {
                    HandleBrickCollision.handle(ball, brick, gm, im);
                    return; // Chỉ xử lý 1 brick mỗi frame
                }
            }
        }
    }
    */

    public static void check(Ball ball, Brick[][] bricks, GameManager gm, ItemManager im) {
        int rows = bricks.length;
        int cols = bricks[0].length;
        int col = (int)(ball.getX() / Constants.BRICK_WIDTH);
        int row = (int)(ball.getY() / Constants.BRICK_HEIGHT);

        // Check within 3x3
        for (int r = Math.max(0, row - 1); r <= Math.min(rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(cols - 1, col + 1); c++) {
                Brick brick = bricks[r][c];
                if (brick != null && !brick.isDestroyed() && ball.isCollidingWith(brick)) {
                    HandleBrickCollision.handle(ball, bricks, brick, gm, im, r, c);
                    return;
                }
            }
        }
    }

}
