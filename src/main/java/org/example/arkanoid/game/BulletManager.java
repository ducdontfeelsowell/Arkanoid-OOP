package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Bullet;
import org.example.arkanoid.config.Constants;

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

            // Tọa độ Y (đỉnh của paddle) là như nhau
            double spawnY = paddleY;

            // Tọa độ X cho súng bên trái (25% chiều rộng)
            double spawnX_Left = paddleX + (paddleWidth * 0.25);
            // Tọa độ X cho súng bên phải (75% chiều rộng)
            double spawnX_Right = paddleX + (paddleWidth * 0.75);

            // Tạo và thêm 2 viên đạn
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

                        brick.takeHit();

                        if (brick.isDestroyed()) {
                            gm.setScore(gm.getScore() + brick.getScore());
                            im.spawnItem(brick);
                        }

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