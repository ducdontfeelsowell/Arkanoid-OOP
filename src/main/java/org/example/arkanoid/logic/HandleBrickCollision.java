package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.game.EffectManager;
import org.example.arkanoid.object.Brick.InfBrick; // <-- THÊM MỚI

public class HandleBrickCollision {
    public static void handle(Ball ball, Brick[][] bricks, Brick brick, GameManager gm, ItemManager im, int row, int col) {

        // --- SỬA ĐỔI: Xóa âm thanh chung ở đây ---
        // SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_PADDLE_HIT);
        // --- KẾT THÚC SỬA ĐỔI ---

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
            } else {
                ball.setX(ball.getX() + overlapRight);
            }
            ball.reverseX();
        } else {
            // Chỉnh vị trí để bóng không dính vào trong paddle, trên
            if (overlapTop < overlapBottom) {
                ball.setY(ball.getY() - overlapTop);
            } else {
                ball.setY(ball.getY() + overlapBottom);
            }
            ball.reverseY();
        }

        // --- SỬA ĐỔI: Di chuyển âm thanh và thêm logic cho case 3 ---
        switch (brick.getType()) {
            case 1, 2: // Gạch thường và gạch mạnh
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_PADDLE_HIT);
                brick.takeHit();
                if (brick.isDestroyed()) {
                    gm.setScore(gm.getScore() + Constants.POINTS_PER_BRICK * brick.getType());

                    if (im != null) {
                        im.spawnItem(brick);
                    }
                    EffectManager.getInstance().spawnBrickDebris(brick);
                }
                break; // Thêm break

            case 3: // Gạch bất tử (InfBrick)
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_WALL_HIT); // Phát âm thanh va tường

                // Kích hoạt animation
                if (brick instanceof InfBrick) {
                    ((InfBrick) brick).triggerAnimation();
                }
                break; // Thêm break

            case 4: // Gạch nổ
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_EXPLOSION);

                double centerX = brick.getX() + brick.getWidth() / 2;
                double centerY = brick.getY() + brick.getHeight() / 2;
                EffectManager.getInstance().spawnExplosion(centerX, centerY);
                EffectManager.getInstance().shakeScreen(5, 150_000_000L);
                int points = 0;
                brick.takeHit();
                points = DestroyRegion.destroyer(bricks, row, col, points);
                gm.setScore(gm.getScore() + points);
                break; // Thêm break

            default: {}
        }
        // --- KẾT THÚC SỬA ĐỔI ---

        BreakBrick.breakIt(bricks, brick, gm, im, row, col);
    }
}
