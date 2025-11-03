package org.example.arkanoid.render;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.object.Ball;

public class RenderBall {
    public static void render(Ball ball, GraphicsContext gc) {
        ball.render(gc);
    }

    // --- THÊM MỚI: Overload để xử lý trạng thái bất tử ---
    public static void render(Ball ball, GraphicsContext gc, boolean isInvincible) {
        // Gọi phương thức render mới của Ball
        ball.render(gc, isInvincible);
    }
    // --- KẾT THÚC THÊM MỚI ---
}