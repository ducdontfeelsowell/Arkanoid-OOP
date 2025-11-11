package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.BallManager;
import org.example.arkanoid.game.BulletManager; // THÊM MỚI
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.game.SoundManager;

import java.util.List;

public class CheckBallOutOfBounds {
    public static void check(BallManager ballManager, Paddle paddle, GameManager gm, ItemManager im, BulletManager bm) {


        if (ballManager.isEmpty()) {
            gm.setLives(gm.getLives() - 1);

            if (gm.getLives() <= 0) {
                gm.setGameOver(true);
            } else {
                ResetBallAndPaddle.reset(ballManager, paddle, im, bm);
            }
        }
    }
}
