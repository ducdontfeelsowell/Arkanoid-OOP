package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Node;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.input.InputHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import org.example.arkanoid.controller.LevelController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private static Cursor defaultGameCursor;
    private static Cursor buttonHoverCursor;

    public Button resumeGameButton;
    public Button backButton1;
    public Button playAgainButton;
    public Button backButton2;
    public Button backButton3;
    public Button nextLevelButton;

    public AnchorPane loseScreen;
    public AnchorPane pauseScreen;
    public AnchorPane winScreen;

    public static boolean paused = false;
    private boolean escapeWasPressed = false;

    private InputHandler inputHandler;

    @FXML
    private MediaView win_backgroundMediaView;

    @FXML
    private MediaView lose_backgroundMediaView;

    @FXML
    private ImageView backHoverImage1;
    @FXML
    private ImageView backHoverImage2;
    @FXML
    private ImageView backHoverImage3;
    @FXML
    private ImageView resumeHoverImage1;
    @FXML
    private ImageView playAgainHoverImage;
    @FXML
    private ImageView nextLevelHoverImage;

    private MediaPlayer win_mediaPlayer;
    private MediaPlayer lose_mediaPlayer;

    public void update() {
        if (inputHandler != null) {
            boolean escapePressed = inputHandler.isEscapePressed();

            if (escapePressed && !escapeWasPressed) {
                togglePause();
            }

            escapeWasPressed = escapePressed;
        }
    }

    private void togglePause() {
        paused = !paused;
        pauseScreen.setVisible(paused);

        if (paused) {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_PAUSE);
            SoundManager.getInstance().pauseBackgroundMusic();
        } else {
            SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_UNPAUSE);
            SoundManager.getInstance().resumeBackgroundMusic();
        }
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public static boolean isPaused() {
        return paused;
    }

    private void initializeCursors(Scene scene) {
        try {
            URL cursorUrl = getClass().getResource(Constants.PATH_TO_CURSOR);
            if (cursorUrl != null) {
                Image customImage = new Image(cursorUrl.toExternalForm());
                defaultGameCursor = Cursor.cursor(cursorUrl.toExternalForm());
                if (scene != null) {
                    scene.setCursor(defaultGameCursor);
                }
            } else {
                defaultGameCursor = Cursor.DEFAULT;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải con trỏ mặc định (img1) trong GameController: " + e.getMessage());
            defaultGameCursor = Cursor.DEFAULT;
        }

        if (buttonHoverCursor == null) {
            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_HOVER_CURSOR);
                if (cursorUrl != null) {
                    Image customImage = new Image(cursorUrl.toExternalForm());
                    buttonHoverCursor = Cursor.cursor(cursorUrl.toExternalForm());
                } else {
                    buttonHoverCursor = Cursor.HAND;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ hover (img2) trong GameController: " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
    }

    private void addHoverEffect(Button button, Node hoverNode) {
        if (button != null) {
            if (hoverNode != null) {
                hoverNode.setVisible(false);
            }
            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                    if (hoverNode != null) {
                        hoverNode.setVisible(true);
                    }
                    if (buttonHoverCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(buttonHoverCursor);
                    }
                } else {
                    if (hoverNode != null) {
                        hoverNode.setVisible(false);
                    }
                    if (defaultGameCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(defaultGameCursor);
                    }
                }
            });
        }
    }

    private void preventKeyActivation(Button button) {
        if (button != null) {
            button.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                    event.consume();
                }
            });
        }
    }

    public void onResumeClick() {
        paused = false;
        pauseScreen.setVisible(false);
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_UNPAUSE);
        SoundManager.getInstance().resumeBackgroundMusic();
    }

    private void returnToLevelScreen(Button originatingButton) {
        Constants.isStarted = false;
        this.inputHandler = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_LEVEL_VIEW));
            Parent root = loader.load();

            LevelController controller = loader.getController();
            if (controller != null) {
                controller.updateLockStatus();
            }

            if (originatingButton != null && originatingButton.getScene() != null) {
                if (defaultGameCursor != null) {
                    originatingButton.getScene().setCursor(defaultGameCursor);
                }
                originatingButton.getScene().setRoot(root);
            } else {
                Main.returnToMenu();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Main.returnToMenu();
        }
    }

    public void onBackClick1() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        pauseScreen.setVisible(false);
        returnToLevelScreen(backButton1);
    }

    public void onBackClick2() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        loseScreen.setVisible(false);
        returnToLevelScreen(backButton2);
    }

    public void onPLayAgainClick() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        paused = false;
        Constants.isStarted = false;
        loseScreen.setVisible(false);
        Main.restartGame();
    }

    private void initializeLoseScreenVideo() {
        if (lose_mediaPlayer != null) {
            return;
        }
        try {
            String videoPath = "/Images/background/lose_background.mp4";
            URL videoUrl = getClass().getResource(videoPath);
            if (videoUrl == null) {
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }

            Media lose_backgroundVideo = new Media(videoUrl.toExternalForm());
            lose_mediaPlayer = new MediaPlayer(lose_backgroundVideo);
            lose_backgroundMediaView.setMediaPlayer(lose_mediaPlayer);

            lose_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            lose_mediaPlayer.setMute(true);

        } catch (IOException e) {
            System.err.println("Lỗi khi load video thua:");
            e.printStackTrace();
        }
    }

    public void showLoseScreen() {
        initializeLoseScreenVideo();
        loseScreen.setVisible(true);
        paused = true;
        if (lose_mediaPlayer != null) {
            lose_mediaPlayer.play();
        }
    }

    public void showWinScreen() {
        initializeWinScreenVideo();
        paused = true;
        winScreen.setVisible(true);
        if (win_mediaPlayer != null) {
            win_mediaPlayer.play();
        }
    }

    public void onBackClick3() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        winScreen.setVisible(false);
        returnToLevelScreen(backButton3);
    }

    private void initializeWinScreenVideo() {
        if (win_mediaPlayer != null) {
            return;
        }

        try {
            String videoPath = "/Images/background/win_background.mp4";
            URL videoUrl = getClass().getResource(videoPath);
            if (videoUrl == null) {
                throw new IOException("Không tìm thấy file video. Vui lòng kiểm tra đường dẫn: " + videoPath);
            }

            Media win_backgroundVideo = new Media(videoUrl.toExternalForm());
            win_mediaPlayer = new MediaPlayer(win_backgroundVideo);
            win_backgroundMediaView.setMediaPlayer(win_mediaPlayer);

            win_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            win_mediaPlayer.setMute(true);

        } catch (IOException e) {
            System.err.println("Lỗi khi load video thua:");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paused = false;

        if (pauseScreen != null) {
            pauseScreen.setVisible(false);
        }

        if (resumeGameButton != null) {
            resumeGameButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    initializeCursors(newScene);
                }
            });
        }

        addHoverEffect(resumeGameButton, resumeHoverImage1);
        addHoverEffect(backButton1, backHoverImage1);
        addHoverEffect(playAgainButton, playAgainHoverImage);
        addHoverEffect(backButton2, backHoverImage2);
        addHoverEffect(backButton3, backHoverImage3);
        addHoverEffect(nextLevelButton, nextLevelHoverImage);

        preventKeyActivation(resumeGameButton);
        preventKeyActivation(backButton1);
        preventKeyActivation(playAgainButton);
        preventKeyActivation(backButton2);
        preventKeyActivation(backButton3);
        preventKeyActivation(nextLevelButton);
    }
}