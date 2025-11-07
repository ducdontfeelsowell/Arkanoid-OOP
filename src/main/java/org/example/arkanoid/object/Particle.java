package org.example.arkanoid.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class Particle {

    private double x, y;
    private double dx, dy;
    private double size;
    private double life;
    private double maxLife;
    private Color color;

    private static Random random = new Random();
    private static final double GRAVITY = 0.1;

    /**
     * Constructor cho một hạt (particle)
     * @param x Tọa độ X ban đầu
     * @param y Tọa độ Y ban đầu
     * @param color Màu của hạt
     * @param maxLife Thời gian tồn tại (tính bằng số frame)
     * @param initialSpeed Tốc độ bay ra ban đầu
     */
    public Particle(double x, double y, Color color, double maxLife, double initialSpeed) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.maxLife = maxLife;
        this.life = maxLife;

        // Kích thước ngẫu nhiên từ 2x2 đến 5x5
        this.size = random.nextDouble() * 3 + 2;

        // Vận tốc bay ra ngẫu nhiên
        double angle = random.nextDouble() * 2 * Math.PI; // Góc bay ngẫu nhiên
        double speed = random.nextDouble() * initialSpeed;

        this.dx = Math.cos(angle) * speed;
        // Bay lên trên một chút
        this.dy = Math.sin(angle) * speed - (random.nextDouble() * 2);
    }

    public boolean isFinished() {
        return life <= 0;
    }

    public void update() {
        if (isFinished()) return;

        // Áp dụng trọng lực
        dy += GRAVITY;

        // Di chuyển
        x += dx;
        y += dy;

        // Giảm thời gian sống
        life--;
    }

    public void render(GraphicsContext gc) {
        if (isFinished()) return;

        // Tính độ mờ (opacity) dựa trên thời gian sống
        double opacity = Math.max(0, life / maxLife);

        // Đặt màu và độ mờ
        gc.setFill(color.deriveColor(0, 1, 1, opacity));

        // Vẽ hạt (là một hình vuông nhỏ)
        gc.fillRect(x - size / 2, y - size / 2, size, size);
    }
}