package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.sound.SoundManager; // <-- THÊM IMPORT NÀY
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;

public class HandleBrickCollision {
    public static void handle(Ball ball, Brick[][] bricks, Brick brick, GameManager gm, ItemManager im, int row, int col) {
        System.out.println("Ball collided with brick of type: " + brick.getType());
        double overlapLeft = ball.getX() + ball.getWidth() - brick.getX();
        double overlapRight = brick.getX() + brick.getWidth() - ball.getX();
        double overlapTop = ball.getY() + ball.getHeight() - brick.getY();
        double overlapBottom = brick.getY() + brick.getHeight() - ball.getY();

        double minOverlapX = Math.min(overlapLeft, overlapRight);
        double minOverlapY = Math.min(overlapTop, overlapBottom);

        // Đảo chiều dựa trên hướng va chạm
        if (minOverlapX < minOverlapY) {
            // Chỉnh vị trí để bóng không dính vào trong paddle, trái phải
            if (overlapLeft < overlapRight) {
                ball.setX(ball.getX() - overlapLeft);
                ball.reverseX();
            } else {
                ball.setX(ball.getX() + overlapRight);
                ball.reverseX();
            }
        } else {
            // Chỉnh vị trí để bóng không dính vào trong paddle, trên
            if (overlapTop < overlapBottom) {
                ball.setY(ball.getY() - overlapTop);
                ball.reverseY();
            } else {
                ball.setY(ball.getY() + overlapBottom);
                ball.reverseY();
            }
        }

//        // Giảm hit points và cộng điểm
//        if (brick.getType() != 3) { // Không phải indestructible brick
//            brick.takeHit();
//            if (brick.isDestroyed()) {
//                gm.setScore(gm.getScore() + Constants.POINTS_PER_BRICK * brick.getType());
//
//                // THÊM MỚI: Spawn item khi gạch bị phá
//                if (im != null) {
//                    System.out.println("Spawning item from destroyed brick.");
//                    im.spawnItem(brick);
//                }
//            }
//        }

        switch (brick.getType()) {
            case 1, 2 -> {
                brick.takeHit();
                if (brick.isDestroyed()) {
                    gm.setScore(gm.getScore() + Constants.POINTS_PER_BRICK * brick.getType());

                    // THÊM MỚI: Spawn item khi gạch bị phá
                    if (im != null) {
                        im.spawnItem(brick);
                    }
                }
            }

            case 4 -> {
                int points = 0;
                brick.takeHit();
                points = destroyRegion.destroyer(bricks, row, col, points);
                gm.setScore(gm.getScore() + points);
            }

            default -> {}
        }

    }
}
