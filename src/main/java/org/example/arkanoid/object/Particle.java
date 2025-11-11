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



    public Particle(double x, double y, Color color, double maxLife, double initialSpeed) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.maxLife = maxLife;
        this.life = maxLife;

        this.size = random.nextDouble() * 3 + 2;

        double angle = random.nextDouble() * 2 * Math.PI;
        double speed = random.nextDouble() * initialSpeed;

        this.dx = Math.cos(angle) * speed;
        this.dy = Math.sin(angle) * speed - (random.nextDouble() * 2);
    }

    public boolean isFinished() {
        return life <= 0;
    }

    public void update() {
        if (isFinished()) return;

        dy += GRAVITY;

        x += dx;
        y += dy;

        life--;
    }

    public void render(GraphicsContext gc) {
        if (isFinished()) return;

        double opacity = Math.max(0, life / maxLife);

        gc.setFill(color.deriveColor(0, 1, 1, opacity));

        gc.fillRect(x - size / 2, y - size / 2, size, size);
    }
}