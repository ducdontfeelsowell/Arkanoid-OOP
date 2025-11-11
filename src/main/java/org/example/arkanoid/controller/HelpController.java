package org.example.arkanoid.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class HelpController implements Initializable {
    private static Cursor defaultGameCursor; // Con trỏ mặc định của game (img1)
    private static Cursor buttonHoverCursor; // Con trỏ khi hover (img2)
    private boolean isTransisioning = false;

    @FXML
    private Label helpLabel;

    @FXML
    private Button backButton;

    @FXML
    private ImageView backHoverImage; // Ảnh khi hover

    @FXML
    private ImageView backImage; // Ảnh mặc định (out)

    @FXML
    private MediaView helpPlayerView;

    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        backButton.getScene().setCursor(defaultGameCursor);
        backButton.getScene().setRoot(root);
    }

    // --- PHƯƠNG THỨC KHỞI TẠO CURSOR ---
    private void initializeCursors(Scene scene) {
        // Khởi tạo con trỏ mặc định (img1)
        if (defaultGameCursor == null) {
            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_CURSOR);
                if (cursorUrl != null) {
                    Image customImage = new Image(cursorUrl.toExternalForm());
                    defaultGameCursor = Cursor.cursor(cursorUrl.toExternalForm());
                    // Áp dụng con trỏ mặc định cho Scene
                    scene.setCursor(defaultGameCursor);
                } else {
                    defaultGameCursor = Cursor.DEFAULT;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ mặc định (img1) trong HelpController: " + e.getMessage());
                defaultGameCursor = Cursor.DEFAULT;
            }
        }

        // Khởi tạo con trỏ hover (img2)
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
                System.err.println("Lỗi khi tải con trỏ hover (img2) trong HelpController: " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
    }
    // --- KẾT THÚC KHỞI TẠO CURSOR ---

    // PHƯƠNG THỨC ĐÃ SỬA: Thêm logic đổi con trỏ
    private void addHoverEffect(Button button, Node imageOut, Node imageOn) {
        if (button != null) {
            // Đảm bảo ảnh ON/HOVER ban đầu bị ẩn
            if (imageOn != null) {
                imageOn.setVisible(false);
            }

            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    // KHI HOVER VÀO: Đổi ảnh và đổi con trỏ
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                    if (imageOut != null) {
                        imageOut.setVisible(false);
                    }
                    if (imageOn != null) {
                        imageOn.setVisible(true);
                    }
                    if (buttonHoverCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(buttonHoverCursor);
                    }
                } else {
                    // KHI RỜI KHỎI HOVER: Đổi ảnh và đổi con trỏ về mặc định
                    if (imageOut != null) {
                        imageOut.setVisible(true);
                    }
                    if (imageOn != null) {
                        imageOn.setVisible(false);
                    }
                    if (defaultGameCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(defaultGameCursor);
                    }
                }
            });
        }
    }

    // Phương thức chặn phím Space/Enter (Giữ nguyên)
    private void preventKeyActivation(Button button) {
        if (button != null) {
            button.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                    event.consume();
                }
            });
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        // THÊM MỚI: Lắng nghe Scene Property
        if (backButton != null) {
            backButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    initializeCursors(newScene);
                }
            });
        }

        try {
            String resourcePath = "/Images/background/video_main_menu.mp4";

            URL videoResource = getClass().getResource(resourcePath);
            Media media = new Media(videoResource.toExternalForm());
            MediaPlayer help_mediaPlayer = new MediaPlayer(media);
            helpPlayerView.setMediaPlayer(help_mediaPlayer);
            help_mediaPlayer.setAutoPlay(true);
            help_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
            help_mediaPlayer.setMute(true); // Tắt tiếng video nền
            help_mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải hoặc phát video.");
        }
        // Loại bỏ các ràng buộc visibleProperty().bind() thủ công
        // backHoverImage.setMouseTransparent(true); // Giữ lại vì liên quan đến bố cục

        // Áp dụng hiệu ứng Hover mới (Âm thanh + Ẩn/Hiện + Con trỏ)
        addHoverEffect(backButton, backImage, backHoverImage);

        // Gọi phương thức chặn phím
        preventKeyActivation(backButton);
    }
}