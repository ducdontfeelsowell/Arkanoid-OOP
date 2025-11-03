package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.ItemManager; // THÊM MỚI
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Paddle;

public class ResetBallAndPaddle {
    // --- SỬA ĐỔI: Thêm ItemManager im ---
    public static void reset(Ball ball, Paddle paddle, ItemManager im) {
        Constants.isStarted = false;

        // Reset ball (Logic cũ)
        ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
        ball.setY(paddle.getY() - ball.getHeight() - 1);
        ball.setOffset(Constants.DEFAULT_BALL_OFFSET);
        CheckBallPaddleCollision.sideHit = false;
        ball.clearTrail();

        // --- THÊM MỚI: Yêu cầu của bạn ---

        // 1. Hủy tất cả item người chơi đang có (reset paddle về trạng thái gốc)
        paddle.resetState();

        // 2. Hủy tất cả item đang rơi trên màn hình
        im.clear();

        // 3. Kích hoạt nhấp nháy 1.5 giây
        long invincibilityDuration = 1_500_000_000L; // 1.5 giây (tính bằng nano giây)
        paddle.activateInvincibility(invincibilityDuration);
        // --- KẾT THÚC THÊM MỚI ---
    }
}