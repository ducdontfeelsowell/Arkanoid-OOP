package org.example.arkanoid.launch;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import org.example.arkanoid.game.*;
import org.example.arkanoid.input.InputHandler;
import org.example.arkanoid.input.MapLoader;
import org.example.arkanoid.object.Ball;
import org.example.arkanoid.object.Brick.Brick;
import org.example.arkanoid.object.Paddle;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.Objects;

public class Main extends Application {

    private static Stage         primaryStage;
    private static Scene         menuScene;
    private static AnimationTimer timer; // to be removed, i guess
    private static Timeline      loop;
    private static String        currentMapPath;

    private static Paddle        paddle;
    private static BallManager   ballManager;
    private static Brick[][]     bricks;

    private static InputHandler   inputHandler;
    private static GameController gameController;
    private static GameRenderer   gameRenderer;
    private static GameManager    gameManager;
    private static ItemManager    itemManager;
    private static BulletManager  bulletManager;
    private static MediaPlayer    mediaPlayer;
    private static SoundManager   soundManager;
    private static MediaPlayer backgroundVideoPlayer;

    private static long lastFpsTime = 0;
    private static int frameCount = 0;

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

        // Khởi tạo và phát nhạc ngẫu nhiên cho menu
        soundManager = SoundManager.getInstance();
        soundManager.playRandomBackgroundMusic(); // Phát nhạc 1, 2
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

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            String videoPath = Constants.PATH_TO_VIDEO;

            URL videoUrl = Main.class.getResource(videoPath);
            if (videoUrl == null) {
                // Ném lỗi rõ ràng nếu không tìm thấy, không dựa vào Objects.requireNonNull
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }
            Media media = new Media(videoUrl.toExternalForm());

            // 2. Tạo MediaPlayer
            backgroundVideoPlayer = new MediaPlayer(media);
            backgroundVideoPlayer.setAutoPlay(true);
            backgroundVideoPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
            backgroundVideoPlayer.setMute(true); // Tắt tiếng video nền

            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);

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

            ballManager = new BallManager();
            ballManager.addBall(paddle);

            bricks = MapLoader.loadMap(mapPath);

            gameRenderer = new GameRenderer(gc);

            itemManager = new ItemManager();

            bulletManager = new BulletManager();

            gameManager = new GameManager(gameController, inputHandler,
                    paddle, ballManager, bricks, gameRenderer, itemManager, bulletManager);

            gameManager.Init();

            // Phát nhạc cố định cho game
            soundManager.playBackgroundMusic(Constants.PATH_TO_SOUND_BACKGROUND_3);

            // Set scene
            primaryStage.setX(Constants.DEFAULT_SCREEN_X);
            primaryStage.setY(Constants.DEFAULT_SCREEN_Y);
            primaryStage.setScene(gameScene);

            // Old fps cap, jaggy one
            /*
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
             */

            if (loop != null) {
                loop.stop();
            }

            // Beautiful FPS cap
            loop = new Timeline(new KeyFrame(Duration.millis(1000.0 / Constants.FPS), e -> {
                gameManager.updateGame();

                // FPS counting
                frameCount++;
                long now = System.nanoTime();
                if (lastFpsTime == 0) lastFpsTime = now;

                if (now - lastFpsTime >= 1_000_000_000) {  // every 1 second
                    System.out.println("FPS: " + frameCount);
                    frameCount = 0;
                    lastFpsTime = now;
                }
            }));
            loop.setCycleCount(Animation.INDEFINITE);
            loop.play();

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
        // Phát nhạc ngẫu nhiên khi quay về menu
        if (soundManager != null) {
            soundManager.playRandomBackgroundMusic(); // Phát nhạc 1, 2
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
    public BallManager getBallManager() { return ballManager; }
    public Brick[][] getBricks() { return bricks; }
    public GameRenderer getRenderer() { return gameRenderer; }
    public GameManager getGameManager() { return gameManager; }
    public InputHandler getInputHandler() { return inputHandler; }
}
