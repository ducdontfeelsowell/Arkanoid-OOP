package org.example.arkanoid.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;
import org.example.arkanoid.logic.CheckCollisions;
import org.example.arkanoid.logic.CheckWinCondition;
import org.example.arkanoid.logic.UpdatePhysics;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.controller.GameController;
import org.example.arkanoid.input.InputHandler;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GameManager {

    private final GameController gameController;
    private final InputHandler inputHandler;
    private final Paddle paddle;
    private final Ball ball;
    private final Brick[][] bricks;
    private final GameRenderer renderer;
    private final ItemManager im;
    private final BulletManager bm;

    private int score;
    private int lives;
    private boolean gameOver;
    private boolean won;

    public GameManager(GameController gameController, InputHandler inputHandler,
                       Paddle paddle, Ball ball, Brick[][] bricks, GameRenderer renderer,
                       ItemManager im, BulletManager bm) {
        this.gameController = gameController;
        this.inputHandler = inputHandler;
        this.paddle = paddle;
        this.ball = ball;
        this.bricks = bricks;
        this.renderer = renderer;
        this.im = im;
        this.bm = bm;

        this.score = 0;
        this.lives = Constants.INITIAL_LIVES;
        this.gameOver = false;
        this.won = false;
    }

    public void updateGame() {
        if (!GameController.isPaused() && !gameOver && !won) {
            inputHandler.handleInput(paddle, bm);
            paddle.update(); // DÒNG NÀY ĐÃ ĐƯỢC THÊM VÀO

            // đợi bắt đầu bóng
            if(Constants.isStarted) {
                UpdatePhysics.update(ball);
                CheckCollisions.check(ball, paddle, bricks, this, im);
                CheckWinCondition.check(bricks, this);
                im.update();
                im.checkCollisions(paddle, ball, this);
                bm.update();
                bm.checkCollisions(bricks, this, im);
            } else {
                ball.setX(paddle.getX() + paddle.getWidth() / 2 - ball.getWidth() / 2);
                ball.setY(paddle.getY() - ball.getHeight() - 1);
            }
        }

        // kiểm tra pause/unpause
        gameController.update();

        // Render
        if (gameOver) {
            renderer.renderGameOver(score);
            gameController.showLoseScreen();
        } else if (won) {
            renderer.renderWin(score);
            gameController.showWinScreen();
        } else {
            renderer.renderObject(paddle, ball, bricks, im, bm, score, lives);
        }
    }
    static Image createCroppedImage(Image sourceImage, Rectangle2D viewport) {
        if (sourceImage == null || viewport == null) {
            return null;
        }

        int newWidth = (int) viewport.getWidth();
        int newHeight = (int) viewport.getHeight();

        int startX = (int) viewport.getMinX();
        int startY = (int) viewport.getMinY();

        WritableImage croppedImage = new WritableImage(newWidth, newHeight);
        PixelReader pixelReader = sourceImage.getPixelReader();
        PixelWriter pixelWriter = croppedImage.getPixelWriter();

        pixelWriter.setPixels(
                0, 0,
                newWidth, newHeight,
                pixelReader,
                startX, startY
        );

        return croppedImage;
    }

    public void Init(){
        InputStream inputStream = getClass().getResourceAsStream("/Images/background/3333.jpg");

        Image fullImage = null;
        if (inputStream != null) {
            fullImage = new Image(inputStream);
        }

        if (fullImage == null || fullImage.isError()) {
            System.out.println("FAIL: Không tìm thấy hoặc lỗi tải ảnh.");
            return;
        }

        double fullWidth = fullImage.getWidth();
        double fullHeight = fullImage.getHeight();

        for(int i= 0 ; i < 10 ; i++){
            Rectangle2D halfTopLeft = new Rectangle2D(231 + i*58,0,45,21);
            Constants.brick_state_list1[i] = createCroppedImage(fullImage, halfTopLeft);
        }
        for(int i= 0 ; i < 10 ; i++){
            Rectangle2D halfTopLeft = new Rectangle2D(231 + i*58,37,45,21);
            Constants.brick_state_list2[i] = createCroppedImage(fullImage, halfTopLeft);
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }
}
