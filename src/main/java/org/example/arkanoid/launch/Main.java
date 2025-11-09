package org.example.arkanoid.launch;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import org.example.arkanoid.game.ProgressManager;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.Objects;

public class Main extends Application {

    private static Stage primaryStage;
    private static Scene menuScene;
    private static AnimationTimer renderTimer;
    private static Timeline loop;
    private static String currentMapPath; // Đường dẫn map hiện tại


    private static Paddle paddle;
    private static BallManager ballManager;
    private static Brick[][] bricks;

    private static InputHandler inputHandler;
    private static GameController gameController;
    private static GameRenderer gameRenderer;
    private static GameManager gameManager;
    private static ItemManager itemManager;
    private static BulletManager bulletManager;
    private static SoundManager soundManager;
    private static MediaPlayer backgroundVideoPlayer;
    private static int curr_level = 0;

    private static long lastLogicTime = 0;
    private static int logicCount = 0;

    private static long lastRenderTimeCounter = 0;
    private static int renderCount = 0;

    private static Thread logicThread;
    private static volatile boolean running = false;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        for(int i = 1 ; i <=12 ; i++){
            Constants.MAP_PATH[i] = "src/main/resources/Maps/map" +String.valueOf(i)+".txt";
        }
        ProgressManager.loadProgress();

        // 1. Khởi tạo và phát nhạc ngẫu nhiên cho menu
        soundManager = SoundManager.getInstance();
        soundManager.playRandomBackgroundMusic();

        // Load menu scene
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                getClass().getResource(Constants.PATH_TO_MAIN_MENU)));

        Parent root = loader.load();
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
     * Khởi tạo và bắt đầu game với mapPath cụ thể
     */
    public static void startGame(int level) {
        try {
            // Stop previous threads
            stopGameThreads();

            curr_level = level;
            currentMapPath = Constants.MAP_PATH[curr_level];

            // DỪNG VIDEO MAIN MENU KHI BẮT ĐẦU GAME

            if (backgroundVideoPlayer != null) {
                backgroundVideoPlayer.stop();
            }

            String videoPath = Constants.PATH_TO_GAME_VIDEO;


            URL videoUrl = Main.class.getResource(videoPath);
            if (videoUrl == null) {
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }
            Media media = new Media(videoUrl.toExternalForm());

            backgroundVideoPlayer = new MediaPlayer(media);
            backgroundVideoPlayer.setAutoPlay(true);
            backgroundVideoPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
            backgroundVideoPlayer.setMute(true); // Tắt tiếng video nền

            // 3. Tạo MediaView
            MediaView mediaView = new MediaView(backgroundVideoPlayer);
            mediaView.setFitWidth(Constants.SCREEN_WIDTH);
            mediaView.setFitHeight(Constants.SCREEN_HEIGHT);
            mediaView.setPreserveRatio(false); // Kéo dãn video cho vừa màn hình

            backgroundVideoPlayer.play();
            Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Create root pane
            Pane gameRoot = new Pane();

            String imagePath = Constants.PATH_TO_IMAGE_BACKGROUND;

            Image image = new Image(Objects.requireNonNull(Main.class.getResourceAsStream(imagePath)));
            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(Constants.SCREEN_WIDTH+18);
            imageView.setFitHeight(Constants.SCREEN_HEIGHT);
            imageView.setPreserveRatio(false);

            // Lớp 1: Ảnh tĩnh (Nền)
            gameRoot.getChildren().add(imageView);

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

            bricks = MapLoader.loadMap(currentMapPath);

            gameRenderer = new GameRenderer(gc);

            itemManager = new ItemManager();

            bulletManager = new BulletManager();

            gameManager = new GameManager(gameController, inputHandler,
                    paddle, ballManager, bricks, gameRenderer, itemManager, bulletManager, level);

            gameManager.Init();

            // Phát nhạc cố định cho game
            soundManager.playBackgroundMusic(Constants.PATH_TO_SOUND_BACKGROUND_3);

            // Set scene
            primaryStage.setX(Constants.DEFAULT_SCREEN_X);
            primaryStage.setY(Constants.DEFAULT_SCREEN_Y);
            primaryStage.setScene(gameScene);

            if (loop != null) {
                loop.stop();
            }

            // Start new threads
            startGameThreads();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void startGameThreads() {
        running = true;
        final Object lock = new Object();

        // Logic thread
        logicThread = new Thread(() -> {
            final double dt = 1.0 / Constants.FPS; // 60 updates/sec
            final long stepNs = (long) (dt * 1_000_000_000);

            lastLogicTime = System.nanoTime();
            logicCount = 0;

            while (running) {
                long start = System.nanoTime();

                synchronized (lock) {
                    gameManager.updateGame();
                }

                // FPS counting (optional)
                logicCount++;
                long now = System.nanoTime();
                if (now - lastLogicTime >= 1_000_000_000L) {
                    System.out.println("UPS: " + logicCount); // UPS = Updates per second
                    logicCount = 0;
                    lastLogicTime = now;
                }

                long elapsed = System.nanoTime() - start;
                long sleep = stepNs - elapsed;
                if (sleep > 0) {
                    try {
                        Thread.sleep(sleep / 1_000_000, (int) (sleep % 1_000_000));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        logicThread.setDaemon(true);
        logicThread.start();

        // Render loop (AnimationTimer)
        renderTimer = new AnimationTimer() {
            private long lastRenderTime = 0;
            private final long targetNs = (long) (1_000_000_000 / Constants.FPS);
            private long accumulatedTime = 0;

            @Override
            public void handle(long now) {
                if (lastRenderTime == 0) lastRenderTime = now;
                long delta = now - lastRenderTime;
                accumulatedTime += delta;

                if (accumulatedTime >= targetNs) {
                    synchronized(lock) {
                        gameManager.render();
                    }
                    accumulatedTime -= targetNs;
                    lastRenderTime = now;

                    renderCount++;
                    if (now - lastRenderTimeCounter >= 1_000_000_000L) {
                        System.out.println("FPS: " + renderCount);
                        renderCount = 0;
                        lastRenderTimeCounter = now;
                    }
                } else {
                    lastRenderTime = now; // update lastRenderTime to avoid drift
                }
            }
        };
        renderTimer.start();
    }

    // Stop logic/render threads safely
    private static void stopGameThreads() {
        running = false;

        if (renderTimer != null) {
            renderTimer.stop();
            renderTimer = null;
        }

        if (logicThread != null && logicThread.isAlive()) {
            try {
                logicThread.join(50); // wait max 50ms for cleanup
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            logicThread = null;
        }
    }

    /**
     * Chuyển sang level tiếp theo
     */
    public static void loadNextLevel() {
        System.out.println("OKK");
        if (currentMapPath == null) {
            System.err.println("Lỗi: Không xác định được map hiện tại. Quay về Menu.");
            returnToMenu();
            return;
        }

        try {
            // currentMapPath có dạng: "src/main/resources/Maps/mapX.txt"
            int nextLevel = curr_level + 1;

            if (nextLevel <= 12) { // Giả định có 12 map
                Constants.isStarted = false;
                startGame(nextLevel);
            } else {
                // Hoàn thành tất cả các map
                System.out.println("Chúc mừng! Bạn đã hoàn thành tất cả các màn chơi.");
                returnToMenu(); // Quay về menu sau khi hoàn thành
            }
        } catch (NumberFormatException e) {
            System.err.println("Lỗi khi phân tích số cấp độ từ đường dẫn: " + currentMapPath);
            returnToMenu();
        }
    }


    /**
     * Quay về menu chính
     */
    public static void returnToMenu() {
        if (renderTimer != null) renderTimer.stop();
        if (backgroundVideoPlayer != null) {
            backgroundVideoPlayer.stop();
            backgroundVideoPlayer = null;
        }

        // Phát nhạc ngẫu nhiên khi quay về menu
        if (soundManager != null) {
            soundManager.playRandomBackgroundMusic(); // Phát nhạc 1, 2
        }

        // Sửa lỗi: Tải lại Menu chính
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(Constants.PATH_TO_MAIN_MENU));
            Parent root = loader.load();
            menuScene.setRoot(root); // Đặt lại root của scene cũ
        } catch (IOException e) {
            System.err.println("Lỗi nghiêm trọng: Không thể tải lại main menu!");
            e.printStackTrace();
        }

        // Chủ động reset cờ static
        GameController.paused = false;

        if (primaryStage != null && menuScene != null) {
            primaryStage.setScene(menuScene);
        }
        Constants.isStarted = false;
    }

    public static void restartGame() {
        GameController.paused = false;
        startGame(curr_level);
        Constants.isStarted = false;
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