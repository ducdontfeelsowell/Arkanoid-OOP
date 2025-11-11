package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.BallManager;
import org.example.arkanoid.game.BulletManager; // THÊM MỚI
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.game.EffectManager;

public class ResetBallAndPaddle {
    public static void reset(BallManager ballManager, Paddle paddle, ItemManager im, BulletManager bm) {
        Constants.isStarted = false;


        ballManager.addBall(paddle);

        paddle.resetState();

        im.clear();
        bm.clear();

        EffectManager.getInstance().clear();
        long invincibilityDuration = 1_500_000_000L;
        paddle.activateInvincibility(invincibilityDuration);
    }
}
