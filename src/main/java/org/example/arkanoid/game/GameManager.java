package org.example.arkanoid.game;

import org.example.arkanoid.logic.CheckCollisions;
import org.example.arkanoid.logic.CheckWinCondition;
import org.example.arkanoid.logic.UpdatePhysics;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.controller.GameController;
import org.example.arkanoid.input.InputHandler;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;

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
