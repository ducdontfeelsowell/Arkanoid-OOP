package org.example.arkanoid.logic;

import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Brick.ExplodeBrick;
import org.example.arkanoid.object.Brick.InfBrick;
import org.example.arkanoid.game.EffectManager;

public class DestroyRegion {
    public static int destroyer(Brick[][] bricks, int row, int col, int points) {
        int rows = bricks.length;
        int cols = bricks[0].length;

        for (int r = Math.max(0, row - 1); r <= Math.min(rows - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(cols - 1, col + 1); c++) {

                if (r == row && c == col) {
                    continue;
                }

                Brick brick = bricks[r][c];

                if (brick != null && !brick.isDestroyed()) {
                    if (brick instanceof InfBrick) {
                        continue;
                    } else {
                        brick.setHitPoints(0);
                        brick.setDestroyed(true);
                    }

                    points += brick.getScore();

                    // --- SỬA ĐỔI: Tạo hiệu ứng nổ cho TẤT CẢ gạch bị phá hủy ---

                    // 1. Tạo hiệu ứng nổ tại vị trí gạch hàng xóm
                    double centerX = brick.getX() + brick.getWidth() / 2;
                    double centerY = brick.getY() + brick.getHeight() / 2;
                    EffectManager.getInstance().spawnExplosion(centerX, centerY);

                    // 2. Nếu gạch đó cũng là gạch nổ, gọi đệ quy để tiếp tục lan
                    if (brick instanceof ExplodeBrick) {
                        points += destroyer(bricks, r, c, points);
                    }
                    // --- KẾT THÚC SỬA ĐỔI ---
                }
            }
        }

        return points;
    }
}
