package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Bullet;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.object.Brick.ExplodeBrick; // <-- THÊM MỚI
import org.example.arkanoid.logic.DestroyRegion;       // <-- THÊM MỚI
import org.example.arkanoid.game.EffectManager;       // <-- THÊM MỚI

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BulletManager {

    private List<Bullet> bullets;
    private long lastShotTime = 0;

    public BulletManager() {
        this.bullets = new ArrayList<>();
    }

    public void shoot(double paddleX, double paddleY, double paddleWidth) {
        long now = System.nanoTime();
        if (now - lastShotTime > Constants.DEFAULT_BULLET_COOLDOWN) {

            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_SHOOT);

            double spawnY = paddleY;

            double spawnX_Left = paddleX + (paddleWidth * 0.05);
            double spawnX_Right = paddleX + (paddleWidth * 0.95);

            Bullet newBulletLeft = new Bullet(spawnX_Left, spawnY);
            Bullet newBulletRight = new Bullet(spawnX_Right, spawnY);

            this.bullets.add(newBulletLeft);
            this.bullets.add(newBulletRight);

            lastShotTime = now;
        }
    }

    public void update() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();

            if (bullet.isOffScreen()) {
                iterator.remove();
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (Bullet bullet : bullets) {
            bullet.render(gc);
        }
    }

    public void checkCollisions(Brick[][] bricks, GameManager gm, ItemManager im) {
        Iterator<Bullet> bulletIterator = bullets.iterator();

        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            boolean hit = false;

            for (int i = 0; i < bricks.length; i++) {
                for (int j = 0; j < bricks[i].length; j++) {
                    Brick brick = bricks[i][j];

                    if (brick != null && !brick.isDestroyed() && bullet.isCollidingWith(brick)) {

                        // --- SỬA ĐỔI: Xử lý va chạm gạch nổ ---
                        if (brick instanceof ExplodeBrick) {
                            // 1. Tạo hiệu ứng nổ và rung
                            double centerX = brick.getX() + brick.getWidth() / 2;
                            double centerY = brick.getY() + brick.getHeight() / 2;
                            EffectManager.getInstance().spawnExplosion(centerX, centerY);
                            EffectManager.getInstance().shakeScreen(5, 150_000_000L);

                            // 2. Phá hủy gạch
                            int points = 0;
                            brick.takeHit();

                            // 3. Phá hủy gạch xung quanh
                            points = DestroyRegion.destroyer(bricks, i, j, points);
                            gm.setScore(gm.getScore() + points);

                        } else {
                            // Logic cũ cho gạch thường
                            brick.takeHit();

                            if (brick.isDestroyed()) {
                                gm.setScore(gm.getScore() + brick.getScore());
                                im.spawnItem(brick);

                                // Thêm hiệu ứng mảnh vỡ cho gạch thường
                                EffectManager.getInstance().spawnBrickDebris(brick);
                            }
                        }
                        // --- KẾT THÚC SỬA ĐỔI ---

                        hit = true;
                        break;
                    }
                }
                if(hit) break;
            }

            if (hit) {
                bulletIterator.remove();
            }
        }
    }

    public void clear() {
        bullets.clear();
    }
}