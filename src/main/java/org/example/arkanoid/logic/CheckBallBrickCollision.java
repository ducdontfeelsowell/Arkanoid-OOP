package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;

public class CheckBallBrickCollision {

    public static void check(Ball ball, Brick[][] bricks, GameManager gm, ItemManager im) {
        int rows = bricks.length;
        if (rows == 0) return;
        int cols = bricks[0].length;
        if (cols == 0) return;

        double relativeBallX = ball.getX() - Constants.PLAY_AREA_LEFT;

        if (relativeBallX < 0) {
            return;
        }

        int col = (int) (relativeBallX / Constants.BRICK_WIDTH);
        int row = (int) (ball.getY() / Constants.BRICK_HEIGHT);

        if (row < 0) row = 0;
        if (row >= rows) row = rows - 1;
        if (col < 0) col = 0;
        if (col >= cols) col = cols - 1;

        for (int r = Math.max(0, row - 1); r <= Math.min(rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(cols - 1, col + 1); c++) {

                if (bricks[r] == null || c >= bricks[r].length) {
                    continue;
                }

                Brick brick = bricks[r][c];
                if (brick != null && !brick.isDestroyed() && ball.isCollidingWith(brick)) {
                    HandleBrickCollision.handle(ball, bricks, brick, gm, im, r, c);
                    return;
                }
            }
        }
    }
}