package org.example.arkanoid.game;

import javafx.scene.canvas.GraphicsContext;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.object.Item;
import org.example.arkanoid.object.Paddle;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.game.SoundManager;

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

    /**
     * Tạo item ngẫu nhiên tại vị trí gạch bị phá
     */
    public void spawnItem(Brick brick) {
        double x = random.nextDouble();
        if (x > Constants.CURRENT_DROP_CHANCE) {
            return;
        }

        // Chọn loại item ngẫu nhiên
        Item.ItemType[] types = Item.ItemType.values();
        Item.ItemType randomType = types[random.nextInt(types.length)];

        System.out.println(randomType);
        // Tạo item tại vị trí giữa gạch
        double itemX = brick.getX() + brick.getWidth() / 2 - 15;
        double itemY = brick.getY();

        Item item = new Item(itemX, itemY, randomType);
        items.add(item);
    }

    /**
     * Cập nhật tất cả items
     */
    public void update() {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            item.update();

            // Xóa item nếu ra khỏi màn hình hoặc đã thu thập
            if (item.isOutOfBounds() || item.isCollected()) {
                iterator.remove();
            }
        }
    }

    /**
     * Vẽ tất cả items
     */
    public void render(GraphicsContext gc) {
        for (Item item : items) {
            if (!item.isCollected()) {
                item.render(gc);
            }
        }
    }

    /**
     * Kiểm tra va chạm với paddle và áp dụng hiệu ứng
     */
    public void checkCollisions(Paddle paddle, BallManager ballManager, GameManager gm) {
        for (Item item : items) {
            if (!item.isCollected() && item.isCollidingWith(paddle)) {
                applyItemEffect(item, paddle, ballManager, gm);
                item.setCollected(true);
            }
        }
    }

    /**
     * Áp dụng hiệu ứng của item
     */
    private void applyItemEffect(Item item, Paddle paddle, BallManager ballManager, GameManager gm) {

        switch (item.getType()) {
            case EXPAND_PADDLE:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);

                // Hủy timeout SHRINK nếu có (kích thước < mặc định)
                if (paddle.getWidth() < Constants.DEFAULT_PADDLE_WIDTH) {
                    paddle.cancelSizeTimeout();
                }

                double newWidth = Math.min(paddle.getWidth() + 20, 150);
                paddle.setSizeWithTimeout(newWidth);
                break;

            case SHRINK_PADDLE:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_NEGATIVE_BUFF);

                // Hủy timeout EXPAND nếu có (kích thước > mặc định)
                if (paddle.getWidth() > Constants.DEFAULT_PADDLE_WIDTH) {
                    paddle.cancelSizeTimeout();
                }

                double shrinkWidth = Math.max(paddle.getWidth() - 20, 60);
                paddle.setSizeWithTimeout(shrinkWidth);
                break;

            case EXTRA_LIFE:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);

                int currentLives = gm.getLives();
                int newLives = Math.min(currentLives + 1, 6);
                gm.setLives(newLives);
                break;

            case SHOOTER_PADDLE:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_GUN_ITEM);
                paddle.activateShooter();
                break;

            case MULTI_BALL:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);
                ballManager.addBall(paddle);
                break;

            case SAFETY_NET:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);
                gm.activateSafetyNet(Constants.DEFAULT_SAFETY_NET_DURATION);
                break;

            case SLOW_SPEED:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_NEGATIVE_BUFF);

                double slowMultiplier = 0.5; // Giảm 50% tốc độ Paddle

                if (paddle.getSpeed() > Constants.DEFAULT_PADDLE_SPEED) {
                    paddle.cancelSpeedTimeout();
                }

                double newPaddleSpeed = Constants.DEFAULT_PADDLE_SPEED * slowMultiplier;
                paddle.setSpeedWithTimeout(newPaddleSpeed);

                break;
            case COIN_50:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);
                ProgressManager.addCoins(50);
                break;
            case COIN_100:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);
                ProgressManager.addCoins(100);
                break;
            case COIN_250:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);
                ProgressManager.addCoins(250);
                break;
            case COIN_500:
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);
                ProgressManager.addCoins(500);
                break;
        }
    }

    /**
     * Xóa tất cả items (khi reset game)
     */
    public void clear() {
        items.clear();
    }

    public List<Item> getItems() {
        return items;
    }
}