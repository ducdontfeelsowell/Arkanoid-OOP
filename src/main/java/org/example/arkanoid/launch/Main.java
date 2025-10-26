package org.example.arkanoid.launch;

import com.sun.tools.jconsole.JConsoleContext;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.controller.GameController;
import org.example.arkanoid.game.BulletManager;
import org.example.arkanoid.game.GameManager;
import org.example.arkanoid.game.GameRenderer;
import org.example.arkanoid.game.ItemManager;
import org.example.arkanoid.input.InputHandler;
import org.example.arkanoid.input.MapLoader;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;

import java.util.Objects;

public class Main extends Application {

    private static Stage primaryStage;
    private static Scene menuScene;
    private static AnimationTimer timer;
    private static String currentMapPath;

    private static GameController gameController;
    private static InputHandler inputHandler;
    private static Paddle paddle;
    private static Ball ball;
    private static Brick[][] bricks;
    private static GameRenderer renderer;
    private static GameManager gameManager;
    private static ItemManager itemManager;
    private static BulletManager bulletManager;

    private static MediaPlayer backgroundVideoPlayer;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Load menu scene
        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource(Constants.PATH_TO_MAIN_MENU)));
        menuScene = new Scene(root);

        // Set stage properties
        stage.getIcons().add(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream(Constants.PATH_TO_LOGO))));
        stage.setScene(menuScene);
        stage.setTitle(Constants.TITLE_SCREEN);
        stage.setResizable(false);
        stage.setX(Constants.DEFAULT_SCREEN_X);
        stage.setY(Constants.DEFAULT_SCREEN_Y);
        stage.show();
    }

    /**
     * Khởi tạo và bắt đầu game
     */
    public static void startGame(String mapPath) {
        try {
            currentMapPath = mapPath;

            if (backgroundVideoPlayer != null) {
                backgroundVideoPlayer.stop();
            }

            String videoPath = Constants.PATH_TO_VIDEO;

            Media media = new Media(Objects.requireNonNull(
                    Main.class.getResource(videoPath)).toExternalForm());

            // 2. Tạo MediaPlayer
            backgroundVideoPlayer = new MediaPlayer(media);
            backgroundVideoPlayer.setAutoPlay(true);
            backgroundVideoPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
            backgroundVideoPlayer.setMute(true); // Tắt tiếng video nền

            // 3. Tạo MediaView
            MediaView mediaView = new MediaView(backgroundVideoPlayer);
            mediaView.setFitWidth(Constants.SCREEN_WIDTH);
            mediaView.setFitHeight(Constants.SCREEN_HEIGHT);
            mediaView.setPreserveRatio(false); // Kéo dãn video cho vừa màn hình

            backgroundVideoPlayer.play(); // Bắt đầu phát video
            // Create canvas for rendering
            Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Create root pane
            Pane gameRoot = new Pane();

            gameRoot.getChildren().add(mediaView);

            gameRoot.getChildren().add(canvas);

            // Load pause screen overlay
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource(Constants.PATH_TO_GAME_VIEW));
            Parent pauseOverlay = loader.load();
            gameController = loader.getController();

            gameRoot.getChildren().add(pauseOverlay);

            // Create game scene
            Scene gameScene = new Scene(gameRoot);

            gameScene.setFill(Color.TRANSPARENT);

            inputHandler = new InputHandler(gameScene);
            gameController.setInputHandler(inputHandler);

            // Initialize game objects
            paddle = new Paddle();

            ball = new Ball();

            bricks = MapLoader.loadMap(mapPath);

            renderer = new GameRenderer(gc);

            itemManager = new ItemManager();

            bulletManager = new BulletManager();

            gameManager = new GameManager(gameController, inputHandler,
                    paddle, ball, bricks, renderer, itemManager, bulletManager);

            gameManager.Init();

            // Set scene
            primaryStage.setX(Constants.DEFAULT_SCREEN_X);
            primaryStage.setY(Constants.DEFAULT_SCREEN_Y);
            primaryStage.setScene(gameScene);

            if (timer != null) timer.stop();

            timer = new AnimationTimer() {
                private double fps = Constants.FPS;
                private double interval = Constants.INTERVAL;
                private long lastUpdate = 0;

                private int frameCount = 0;
                private long lastFpsTime = 0;

                @Override
                public void handle(long now) {
                    if (now - lastUpdate >= interval) {
                        gameManager.updateGame();
                        lastUpdate = now;
                        frameCount++;

                        long delayNs = (long) interval - (System.nanoTime() - now);
                        if (delayNs > 0) {
                            try {
                                Thread.sleep(delayNs / 1_000_000, (int) (delayNs % 1_000_000));
                            } catch (InterruptedException ignored) {}
                        }
                    }

                    if (now - lastFpsTime >= 1000000000) {
                        System.out.println("FPS: " + frameCount);
                        frameCount = 0;
                        lastFpsTime = now;
                    }
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
        if (timer != null) timer.stop();
        if (backgroundVideoPlayer != null) {
            backgroundVideoPlayer.stop();
            backgroundVideoPlayer = null;
        }
        if (primaryStage != null && menuScene != null) {
            primaryStage.setScene(menuScene);
        }
        Constants.isStarted = false;
    }

    public static void restartGame() {
        GameController.paused = false;
        if (currentMapPath != null) {
            startGame(currentMapPath);
            Constants.isStarted = false;
        }
    }

    public static GameController getGameController() {
        return gameController;
    }

    public Paddle getPaddle() { return paddle; }
    public Ball getBall() { return ball; }
    public Brick[][] getBricks() { return bricks; }
    public GameRenderer getRenderer() { return renderer; }
    public GameManager getGameManager() { return gameManager; }
    public InputHandler getInputHandler() { return inputHandler; }
}