package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Brick.ExplodeBrick;
import org.example.arkanoid.object.Brick.InfBrick;

public class destroyRegion {
    public static int destroyer(Brick[][] bricks, int row, int col, int points) {
        int rows = bricks.length;
        int cols = bricks[0].length;
        for (int r = Math.max(0, row - 1); r <= Math.min(rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(cols - 1, col + 1); c++) {
                Brick brick = bricks[r][c];
                if (brick != null && !brick.isDestroyed()) {
                    if (brick instanceof InfBrick) {
                        continue;
                    } else {
                        brick.setHitPoints(0);
                        brick.setDestroyed(true);
                    }

                    points += Constants.POINTS_PER_BRICK;
                    if (brick instanceof ExplodeBrick) {
                        destroyer(bricks, r, c, points);
                    }
                }
            }
        }

        return points;
    }
}
