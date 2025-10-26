package org.example.arkanoid.launch;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.controller.GameController;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.GameRenderer;
import org.example.arkanoid.input.InputHandler;
import org.example.arkanoid.input.MapLoader;
import org.example.arkanoid.sound.SoundManager;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;

import java.util.Objects;

public class Main extends Application {

    private static Stage primaryStage;
    private static Scene menuScene;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Load menu scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/org/example/arkanoid/main-menu-view.fxml")));
        menuScene = new Scene(root);

        // Set stage properties
        stage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/Images/logo/jarkanoid_logo.png"))));
        stage.setScene(menuScene);
        stage.setTitle("Arkanoid");
        stage.setResizable(false);
        stage.setX(Constants.DEFAULT_SCREEN_X);
        stage.setY(Constants.DEFAULT_SCREEN_Y);
        stage.show();
    }

    /**
     * Khởi tạo và bắt đầu game
     */
    public static void startGame() {
        try {
            // TẢI ÂM THANH KHI BẮT ĐẦU GAME
            SoundManager.loadGameSounds(); // <-- Đã sửa: tải âm thanh game
            SoundManager.playMusic();

            // Create canvas for rendering
            Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Create root pane
            Pane gameRoot = new Pane();
            gameRoot.getChildren().add(canvas);

            // Load pause screen overlay
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource("/org/example/arkanoid/game-view.fxml"));
            Parent pauseOverlay = loader.load();
            GameController gameController = loader.getController();

            gameRoot.getChildren().add(pauseOverlay);

            // Create game scene
            Scene gameScene = new Scene(gameRoot);

            // Initialize input handler
            InputHandler inputHandler = new InputHandler(gameScene);
            gameController.setInputHandler(inputHandler);

            // Initialize game objects
            Paddle paddle = new Paddle(
                    Constants.DEFAULT_PADDLE_POSITION_X,
                    Constants.DEFAULT_PADDLE_POSITION_Y,
                    Constants.DEFAULT_PADDLE_WIDTH,
                    Constants.DEFAULT_PADDLE_HEIGHT,
                    Constants.DEFAULT_PADDLE_DX,
                    Constants.DEFAULT_PADDLE_DY,
                    Constants.DEFAULT_PADDLE_SPEED);

            Ball ball = new Ball(
                    Constants.DEFAULT_BALL_POSITION_X,
                    Constants.DEFAULT_BALL_POSITION_Y,
                    Constants.DEFAULT_BALL_SIZE,
                    Constants.DEFAULT_BALL_SIZE,
                    Constants.DEFAULT_BALL_DX,
                    Constants.DEFAULT_BALL_DY,
                    Constants.DEFAULT_BALL_SPEED,
                    Constants.DEFAULT_BALL_DIRECTION_X,
                    Constants.DEFAULT_BALL_DIRECTION_Y);

            // Load map
            Brick[][] bricks = MapLoader.loadMap(Constants.MAP_PATH);

            // Create renderer
            GameRenderer renderer = new GameRenderer(gc);

            // Create game manager
            GameManager gameManager = new GameManager(
                    gameController, inputHandler, paddle, ball, bricks, renderer);


            // Set scene
            primaryStage.setX(Constants.DEFAULT_SCREEN_X);
            primaryStage.setY(Constants.DEFAULT_SCREEN_Y);
            primaryStage.setScene(gameScene);

            // Start game loop
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    gameManager.updateGame();
                }
            };
            timer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Quay về menu chính
     */
    public static void returnToMenu() {
        SoundManager.stopMusic();
        if (primaryStage != null && menuScene != null) {
            primaryStage.setScene(menuScene);
        }
    }
}