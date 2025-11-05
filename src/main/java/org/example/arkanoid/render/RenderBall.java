package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.game.BallManager;

public class RenderBall {
    public static void render(BallManager ballManager, GraphicsContext gc) {
        ballManager.render(gc);
    }

    // --- THÊM MỚI: Overload để xử lý trạng thái bất tử ---
    public static void render(BallManager ballManager, GraphicsContext gc, boolean isInvincible) {
        // Gọi phương thức render mới của Ball
        ballManager.render(gc, isInvincible);
    }
    // --- KẾT THÚC THÊM MỚI ---
}
