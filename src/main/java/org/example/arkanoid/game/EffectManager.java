package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Particle; // <-- THAY ĐỔI IMPORT

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EffectManager {

    private static EffectManager instance;

    // --- THAY ĐỔI: Danh sách này giờ chứa Particle ---
    private List<Particle> particles;

    // --- THÊM MỚI: Biến random ---
    private Random random = new Random();

    // Biến cho Screen Shake (Giữ nguyên)
    private double shakeIntensity = 0;
    private long shakeEndTime = 0;

    private EffectManager() {
        // --- THAY ĐỔI: Khởi tạo danh sách Particle ---
        particles = new ArrayList<>();
    }

    public static EffectManager getInstance() {
        if (instance == null) {
            instance = new EffectManager();
        }
        return instance;
    }


    public void spawnBrickDebris(Brick brick) {
        double x = brick.getX() + brick.getWidth() / 2;
        double y = brick.getY() + brick.getHeight() / 2;
        Color color = brick.getBrickColor();
        int particleCount = 10; // 10 mảnh vỡ
        double life = 60 + random.nextInt(30); // Sống 1 - 1.5 giây
        double speed = 2.0; // Bay ra chậm

        for (int i = 0; i < particleCount; i++) {
            particles.add(new Particle(x, y, color, life, speed));
        }
    }

    // --- THÊM MỚI: Phương thức tạo vụ nổ (cho gạch nổ) ---
    public void spawnExplosion(double x, double y) {
        int particleCount = 40; // 40 mảnh vỡ
        double life = 80 + random.nextInt(40); // Sống 1.3 - 2 giây
        double speed = 5.0; // Bay ra nhanh

        // Mảng màu của vụ nổ
        Color[] explosionColors = { Color.YELLOW, Color.ORANGE, Color.RED, Color.WHITE };

        for (int i = 0; i < particleCount; i++) {
            Color color = explosionColors[random.nextInt(explosionColors.length)];
            particles.add(new Particle(x, y, color, life, speed));
        }
    }


    public void update() {
        // --- THAY ĐỔI: Cập nhật danh sách Particle ---
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle p = iterator.next();
            p.update();
            if (p.isFinished()) {
                iterator.remove();
            }
        }
    }


    public void render(GraphicsContext gc) {
        // --- THAY ĐỔI: Vẽ các Particle ---
        for (Particle p : particles) {
            p.render(gc);
        }
    }


    public void clear() {
        // --- THAY ĐỔI: Xóa các Particle ---
        particles.clear();
    }

    public void shakeScreen(double intensity, long durationNano) {
        this.shakeIntensity = intensity;
        this.shakeEndTime = System.nanoTime() + durationNano;
    }

    public void applyShake(GraphicsContext gc) {
        if (shakeEndTime == 0) return;

        long now = System.nanoTime();
        if (now > shakeEndTime) {
            shakeEndTime = 0;
            return; // Hết thời gian rung
        }

        // Tạo vị trí rung ngẫu nhiên
        double offsetX = (Math.random() - 0.5) * 2 * shakeIntensity;
        double offsetY = (Math.random() - 0.5) * 2 * shakeIntensity;

        // Di chuyển toàn bộ canvas
        gc.translate(offsetX, offsetY);
    }
}