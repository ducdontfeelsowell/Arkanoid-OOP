package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.EffectManager;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Brick.Brick;

public class BreakBrick {

    public static void breakIt(Brick[][] bricks, Brick brick, GameManager gm, ItemManager im, int row, int col) {
        switch (brick.getType()) {
            case 1, 2 -> {
                brick.takeHit();
                if (brick.isDestroyed()) {
                    gm.setScore(gm.getScore() + Constants.POINTS_PER_BRICK * brick.getType());

                    // THÊM MỚI: Spawn item khi gạch bị phá
                    if (im != null) {
                        im.spawnItem(brick);
                    }
                    EffectManager.getInstance().spawnBrickDebris(brick);
                }
            }

            case 4 -> {
                double centerX = brick.getX() + brick.getWidth() / 2;
                double centerY = brick.getY() + brick.getHeight() / 2;
                EffectManager.getInstance().spawnExplosion(centerX, centerY);
                EffectManager.getInstance().shakeScreen(5, 150_000_000L);                int points = 0;
                brick.takeHit();
                points = DestroyRegion.destroyer(bricks, row, col, points);
                gm.setScore(gm.getScore() + points);
            }

            default -> {}
        }
    }
}
