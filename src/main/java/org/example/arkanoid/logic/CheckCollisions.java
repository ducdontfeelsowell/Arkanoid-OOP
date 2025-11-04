package org.example.arkanoid.logic;

import org.example.arkanoid.game.BulletManager; // THÊM MỚI
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;

public class CheckCollisions {
    // --- SỬA ĐỔI: Thêm BulletManager bm ---
    public static void check(Ball ball, Paddle paddle, Brick[][] bricks, GameManager gm, ItemManager im, BulletManager bm) {
        CheckBallPaddleCollision.check(ball, paddle);
        CheckBallBrickCollision.check(ball, bricks, gm, im);
        // --- SỬA ĐỔI: Thêm 'bm' vào lời gọi ---
        CheckBallOutOfBounds.check(ball, paddle, gm, im, bm);
    }
}