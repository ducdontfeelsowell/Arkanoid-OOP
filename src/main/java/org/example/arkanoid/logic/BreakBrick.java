package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.EffectManager;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Brick.InfBrick;

public class BreakBrick {

    public static void breakIt(Brick[][] bricks, Brick brick, GameManager gm, ItemManager im, int row, int col) {
        // --- SỬA ĐỔI: Di chuyển âm thanh và thêm logic cho case 3 ---
        switch (brick.getType()) {
            case 1, 2: // Gạch thường và gạch mạnh
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_PADDLE_HIT);
                brick.takeHit();
                if (brick.isDestroyed()) {
                    gm.setScore(gm.getScore() + brick.getScore());

                    if (im != null) {
                        im.spawnItem(brick);
                    }
                    EffectManager.getInstance().spawnBrickDebris(brick);
                }
                break; // Thêm break

            case 3: // Gạch bất tử (InfBrick)
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_BRICK_3); // Phát âm thanh va tường

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
                int points = brick.getScore();
                brick.takeHit();
                points += DestroyRegion.destroyer(bricks, row, col, points);
                gm.setScore(gm.getScore() + points);
                break; // Thêm break

            default: {}
        }
        // --- KẾT THÚC SỬA ĐỔI ---
    }
}
