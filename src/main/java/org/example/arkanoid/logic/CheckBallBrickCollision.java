package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;

import java.util.ArrayList;
import java.util.List;

public class CheckBallBrickCollision {

    public static boolean check(Ball ball, Brick[][] bricks, GameManager gm, ItemManager im) {
        int rows = bricks.length;
        if (rows == 0) return false;
        int cols = bricks[0].length;
        if (cols == 0) return false;

        double relativeBallX = ball.getX() - Constants.PLAY_AREA_LEFT;

        if (relativeBallX < 0) {
            return false;
        }

        int col = (int) (relativeBallX / Constants.BRICK_WIDTH);
        int row = (int) (ball.getY() / Constants.BRICK_HEIGHT);

        if (row < 0) row = 0;
        if (row >= rows) row = rows - 1;
        if (col < 0) col = 0;
        if (col >= cols) col = cols - 1;

        List<int[]> collidedBricks = new ArrayList<>();

        for (int r = Math.max(0, row - 1); r <= Math.min(rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(cols - 1, col + 1); c++) {

                if (bricks[r] == null || c >= bricks[r].length) {
                    continue;
                }

                Brick brick = bricks[r][c];
                if (brick == null || brick.isDestroyed()) {
                    continue;
                }

                if (ball.isCollidingWith(brick)) {
                    collidedBricks.add(new int[]{r, c});
                }
            }
        }

        if (collidedBricks.isEmpty()) return false;

        int[] main = collidedBricks.get(0);
        Brick mainBrick = bricks[main[0]][main[1]];

        HandleBrickCollision.handle(ball, bricks, mainBrick, gm, im, main[0], main[1]);

        for (int i = 1; i < collidedBricks.size(); i++) {
            int[] rc = collidedBricks.get(i);
            Brick b = bricks[rc[0]][rc[1]];
            if (b != null && !b.isDestroyed()) {
                BreakBrick.breakIt(bricks, b, gm, im, rc[0], rc[1]);
            }
        }

        return true;
    }
}
