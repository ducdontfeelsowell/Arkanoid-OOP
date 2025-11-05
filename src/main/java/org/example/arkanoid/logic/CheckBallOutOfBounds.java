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
    // --- SỬA ĐỔI: Thêm BulletManager bm ---
    public static void check(BallManager ballManager, Paddle paddle, GameManager gm, ItemManager im, BulletManager bm) {
        // Logic to check if the ball is out of bounds
//        if (ball.getY() + ball.getHeight() >= Constants.SCREEN_HEIGHT) {
//            SoundManager.playBallDrop();
//            gm.setLives(gm.getLives() - 1);
//
//            if (gm.getLives() <= 0) {
//                gm.setGameOver(true);
//            } else {
//                ResetBallAndPaddle.reset(ball, paddle);
//            }
//        }

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
