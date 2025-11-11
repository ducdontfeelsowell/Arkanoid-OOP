package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Particle;
import org.example.arkanoid.object.ExplosionAnimation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.example.arkanoid.config.Constants;
public class EffectManager {

    private static EffectManager instance;

    private List<Particle> particles;

    private List<ExplosionAnimation> explosions;

    private Random random = new Random();

    // Biến cho Screen Shake (Giữ nguyên)
    private double shakeIntensity = 0;
    private double shakeRemainingTime = 0;

    private EffectManager() {
        particles = new ArrayList<>();

        //  Khởi tạo danh sách nổ
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


    //  Phương thức tạo vụ nổ (cho gạch nổ)
    public void spawnExplosion(double x, double y) {

        ExplosionAnimation anim = new ExplosionAnimation(x, y);
        explosions.add(anim);
    }


    public void update(long deltaTime) {

        // Cập nhật screen shake
        if (shakeRemainingTime > 0) {
            shakeRemainingTime -= deltaTime;
            if (shakeRemainingTime <= 0) {
                shakeIntensity = 0;
                shakeRemainingTime = 0;
            }
        }

        Iterator<Particle> pIterator = particles.iterator();
        while (pIterator.hasNext()) {
            Particle p = pIterator.next();
            p.update();
            if (p.isFinished()) {
                pIterator.remove();
            }
        }

        // Cập nhật các animation nổ
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
        //  Vẽ các Particle
        for (Particle p : particles) {
            p.render(gc);
        }

        // Vẽ các animation nổ
        for (ExplosionAnimation anim : explosions) {
            anim.render(gc);
        }
    }


    public void clear() {
        particles.clear();

        // Xóa các vụ nổ
        explosions.clear();

        // Xóa shake
        shakeIntensity = 0;
        shakeRemainingTime = 0;
    }


    public void shakeScreen(double intensity, long durationNano) {
        this.shakeIntensity = intensity;
        this.shakeRemainingTime = durationNano;
    }

    public void applyShake(GraphicsContext gc) {

        if (shakeRemainingTime <= 0) return;



        double offsetX = (Math.random() - 0.5) * 2 * shakeIntensity;
        double offsetY = (Math.random() - 0.5) * 2 * shakeIntensity;

        gc.translate(offsetX, offsetY);
    }
}