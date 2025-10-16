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

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        Parent root = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource(Constants.PATH_TO_MAIN_MENU)));
        menuScene = new Scene(root);

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

            // Create canvas for rendering
            Canvas canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
            GraphicsContext gc = canvas.getGraphicsContext2D();

            // Create root pane
            Pane gameRoot = new Pane();
            gameRoot.getChildren().add(canvas);

            // Load pause screen overlay
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource(Constants.PATH_TO_GAME_VIEW));
            Parent pauseOverlay = loader.load();
            gameController = loader.getController();

            gameRoot.getChildren().add(pauseOverlay);

            Scene gameScene = new Scene(gameRoot);

            inputHandler = new InputHandler(gameScene);
            gameController.setInputHandler(inputHandler);

            // Initialize game objects
            paddle = new Paddle(
                    Constants.DEFAULT_PADDLE_POSITION_X,
                    Constants.DEFAULT_PADDLE_POSITION_Y,
                    Constants.DEFAULT_PADDLE_WIDTH,
                    Constants.DEFAULT_PADDLE_HEIGHT,
                    Constants.DEFAULT_PADDLE_DX,
                    Constants.DEFAULT_PADDLE_DY,
                    Constants.DEFAULT_PADDLE_SPEED);

            ball = new Ball(
                    Constants.DEFAULT_BALL_POSITION_X,
                    Constants.DEFAULT_BALL_POSITION_Y,
                    Constants.DEFAULT_BALL_SIZE,
                    Constants.DEFAULT_BALL_SIZE,
                    Constants.DEFAULT_BALL_DX,
                    Constants.DEFAULT_BALL_DY,
                    Constants.DEFAULT_BALL_SPEED,
                    Constants.DEFAULT_BALL_DIRECTION_X,
                    Constants.DEFAULT_BALL_DIRECTION_Y);

            bricks = MapLoader.loadMap(mapPath);

            renderer = new GameRenderer(gc);

            gameManager = new GameManager(gameController, inputHandler, paddle, ball, bricks, renderer);

            // Set scene
            primaryStage.setX(Constants.DEFAULT_SCREEN_X);
            primaryStage.setY(Constants.DEFAULT_SCREEN_Y);
            primaryStage.setScene(gameScene);

            if (timer != null) timer.stop();

            timer = new AnimationTimer() {
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

    public static void returnToMenu() {
        if (timer != null) timer.stop();
        if (primaryStage != null && menuScene != null) {
            primaryStage.setScene(menuScene);
        }
    }

    public static void restartGame() {
        GameController.paused = false;
        if (currentMapPath != null) {
            startGame(currentMapPath);
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
