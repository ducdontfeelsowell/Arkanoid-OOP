package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.BallManager;
import org.example.arkanoid.game.BulletManager; // THÊM MỚI
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;

public class ResetBallAndPaddle {
    public static void reset(BallManager ballManager, Paddle paddle, ItemManager im, BulletManager bm) {
        Constants.isStarted = false;

        // Reset ball
//        ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
//        ball.setY(paddle.getY() - ball.getHeight() - 1);
//        ball.setOffset(Constants.DEFAULT_BALL_OFFSET);
//        CheckBallPaddleCollision.sideHit = false;// locate sideHit to ball
        ballManager.addBall(paddle);

        //ball.clearTrail();

        // 1. Hủy tất cả item người chơi đang có
        paddle.resetState();

        // 2. Hủy tất cả item đang rơi trên màn hình
        im.clear();

        // --- THÊM MỚI: Hủy tất cả đạn trên màn hình ---
        bm.clear();

        // 3. Kích hoạt nhấp nháy 1.5 giây
        long invincibilityDuration = 1_500_000_000L; // 1.5 giây (tính bằng nano giây)
        paddle.activateInvincibility(invincibilityDuration);
    }
}
