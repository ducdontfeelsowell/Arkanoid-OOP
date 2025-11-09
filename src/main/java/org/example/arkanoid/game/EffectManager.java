package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Particle; // <-- Giữ nguyên import này
import org.example.arkanoid.object.ExplosionAnimation; // <-- THÊM IMPORT MỚI

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.example.arkanoid.config.Constants;
public class EffectManager {

    private static EffectManager instance;

    // --- Danh sách cho mảnh vỡ (giữ nguyên) ---
    private List<Particle> particles;

    // --- THÊM MỚI: Danh sách cho animation nổ ---
    private List<ExplosionAnimation> explosions;

    private Random random = new Random();

    // Biến cho Screen Shake (Giữ nguyên)
    private double shakeIntensity = 0;
    private long shakeEndTime = 0;

    private EffectManager() {
        particles = new ArrayList<>();

        // --- THÊM MỚI: Khởi tạo danh sách nổ ---
        explosions = new ArrayList<>();
    }

    public static EffectManager getInstance() {
        if (instance == null) {
            instance = new EffectManager();
        }
        return instance;
    }

    /**
     * Tạo mảnh vỡ (Giữ nguyên)
     */
    public void spawnBrickDebris(Brick brick) {
        double x = brick.getX() + brick.getWidth() / 2;
        double y = brick.getY() + brick.getHeight() / 2;
        Color color = brick.getBrickColor();
        int particleCount = 10;
        double life = 60 + random.nextInt(30);
        double speed = 2.0;

        for (int i = 0; i < particleCount; i++) {
            particles.add(new Particle(x, y, color, life, speed));
        }
    }


    // --- SỬA ĐỔI: Phương thức tạo vụ nổ (cho gạch nổ) ---
    public void spawnExplosion(double x, double y) {
        // Xóa mã tạo particle cũ

        // THÊM MỚI: Tạo một đối tượng ExplosionAnimation
        // (x, y) được truyền vào đây là TÂM của viên gạch
        ExplosionAnimation anim = new ExplosionAnimation(x, y);
        explosions.add(anim);
    }
    // --- KẾT THÚC SỬA ĐỔI ---


    public void update() {
        // --- Cập nhật mảnh vỡ (giữ nguyên) ---
        Iterator<Particle> pIterator = particles.iterator();
        while (pIterator.hasNext()) {
            Particle p = pIterator.next();
            p.update();
            if (p.isFinished()) {
                pIterator.remove();
            }
        }

        // --- THÊM MỚI: Cập nhật các animation nổ ---
        Iterator<ExplosionAnimation> eIterator = explosions.iterator();
        while (eIterator.hasNext()) {
            ExplosionAnimation anim = eIterator.next();
            anim.update();
            if (anim.isFinished()) {
                eIterator.remove();
            }
        }
    }


    public void render(GraphicsContext gc) {
        // --- Vẽ các Particle (giữ nguyên) ---
        for (Particle p : particles) {
            p.render(gc);
        }

        // --- THÊM MỚI: Vẽ các animation nổ ---
        for (ExplosionAnimation anim : explosions) {
            anim.render(gc);
        }
    }


    public void clear() {
        particles.clear();

        // --- THÊM MỚI: Xóa các vụ nổ ---
        explosions.clear();
    }

    // ... (Các phương thức shakeScreen và applyShake giữ nguyên) ...
    public void shakeScreen(double intensity, long durationNano) {
        this.shakeIntensity = intensity;
        this.shakeEndTime = System.nanoTime() + durationNano;
    }

    public void applyShake(GraphicsContext gc) {
        if (shakeEndTime == 0) return;

        long now = System.nanoTime();
        if (now > shakeEndTime) {
            shakeEndTime = 0;
            return;
        }

        double offsetX = (Math.random() - 0.5) * 2 * shakeIntensity;
        double offsetY = (Math.random() - 0.5) * 2 * shakeIntensity;

        gc.translate(offsetX, offsetY);
    }
}