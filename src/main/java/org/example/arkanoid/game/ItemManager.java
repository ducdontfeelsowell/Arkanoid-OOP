package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Item;
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.game.SoundManager; // THÊM MỚI

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ItemManager {

    private List<Item> items;
    private Random random;

    public ItemManager() {
        this.items = new ArrayList<>();
        this.random = new Random();
    }

    public void spawnItem(Brick brick) {
        double x = random.nextDouble();
        if (x > Constants.DROP_CHANCE) {
            return;
        }

        Item.ItemType[] types = Item.ItemType.values();
        Item.ItemType randomType = types[random.nextInt(types.length)];

        System.out.println(randomType);
        double itemX = brick.getX() + brick.getWidth() / 2 - 15;
        double itemY = brick.getY();

        Item item = new Item(itemX, itemY, randomType);
        items.add(item);
    }

    public void update() {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            item.update();

            if (item.isOutOfBounds() || item.isCollected()) {
                iterator.remove();
            }
        }
    }

    public void render(GraphicsContext gc) {
        for (Item item : items) {
            if (!item.isCollected()) {
                item.render(gc);
            }
        }
    }

    public void checkCollisions(Paddle paddle, Ball ball, GameManager gm) {
        for (Item item : items) {
            if (!item.isCollected() && item.isCollidingWith(paddle)) {
                applyItemEffect(item, paddle, ball, gm);
                item.setCollected(true);
            }
        }
    }

    private void applyItemEffect(Item item, Paddle paddle, Ball ball, GameManager gm) {
        // --- THÊM MỚI: Phát âm thanh khi sử dụng item ---
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);

        switch (item.getType()) {
            case EXPAND_PADDLE:
                double newWidth = Math.min(paddle.getWidth() + 20, 150);
                paddle.setWidth(newWidth);
                break;

            case SHRINK_PADDLE:
                double shrinkWidth = Math.max(paddle.getWidth() - 20, 60);
                paddle.setWidth(shrinkWidth);
                break;

            case EXTRA_LIFE:
                gm.setLives(gm.getLives() + 1);
                break;
            case SHOOTER_PADDLE:
                paddle.activateShooter();
                break;
        }
    }

    public void clear() {
        items.clear();
    }

    public List<Item> getItems() {
        return items;
    }
}