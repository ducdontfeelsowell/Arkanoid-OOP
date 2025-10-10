package org.example.arkanoid.logic;

import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.object.Brick.Brick;

public class CheckWinCondition {
    public static void check(Brick[][] bricks, GameManager gm) {
        boolean allBricksDestroyed = true;

        for (Brick[] row : bricks) {
            for (Brick brick : row) {
                if (brick != null && !brick.isDestroyed() && brick.getType() != 3) {
                    allBricksDestroyed = false;
                    break;
                }
            }
            if (!allBricksDestroyed) break;
        }

        if (allBricksDestroyed) {
            gm.setWon(true);
        }
    }
}
