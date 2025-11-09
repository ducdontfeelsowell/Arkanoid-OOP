package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Node; // THÊM MỚI
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.example.arkanoid.config.Constants;
import javafx.scene.image.ImageView;
import org.example.arkanoid.game.SoundManager;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.controller.LevelController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MenuController implements Initializable{
    private static Cursor defaultGameCursor; // Con trỏ mặc định của game (img1)
    private static Cursor buttonHoverCursor; // Con trỏ khi hover (img2)

    @FXML
    public Button exitButton;

    @FXML
    private Button playButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button shopButton;

    @FXML
    private ImageView playHoverImage;

    @FXML
    private ImageView playImage;

    @FXML
    private ImageView settingImage;

    @FXML
    private ImageView settingHoverImage;

    @FXML
    private ImageView helpHoverImage;

    @FXML
    private ImageView helpImage;

    @FXML
    private ImageView shopHoverImage;

    @FXML
    private ImageView shopImage;

    @FXML
    private ImageView exitImage;

    @FXML
    private ImageView exitHoverImage;

    @FXML
    private MediaView menuPlayerView;

    // Các phương thức xử lý sự kiện đã có
    @FXML
    public void onPlayGameButtonClick() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_LEVEL_VIEW));
        Parent root = loader.load();
        LevelController controller = loader.getController();
        controller.updateLockStatus();
        playButton.getScene().setRoot(root);
    }

    @FXML
    public void onHelpButtonClick() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_HELP_VIEW));
        Parent root = loader.load();
        helpButton.getScene().setRoot(root);
    }

    @FXML
    public void onSettingButtonClick() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_SETTING_VIEW));
        Parent root = loader.load();
        settingButton.getScene().setRoot(root);
    }

    @FXML
    public void onShopButtonClick() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/arkanoid/shop-view.fxml"));
        Parent newRoot = loader.load();
        shopButton.getScene().setRoot(newRoot);
    }

    @FXML
    public void onExitGameButtonClick() {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        System.exit(0);
    }

    // PHƯƠNG THỨC ĐÃ SỬA: Xử lý cả âm thanh, trạng thái hiển thị VÀ con trỏ chuột
    private void addHoverEffect(Button button, Node imageOut, Node imageOn) {
        if (button != null) {
            // Đảm bảo ảnh ON/HOVER ban đầu bị ẩn
            if (imageOn != null) {
                imageOn.setVisible(false);
            }

            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    // KHI HOVER VÀO:
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                    if (imageOut != null) {
                        imageOut.setVisible(false);
                    }
                    if (imageOn != null) {
                        imageOn.setVisible(true);
                    }

                    // THÊM MỚI: THAY ĐỔI CON TRỎ THÀNH IMG2 (Hover Cursor)
                    if (buttonHoverCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(buttonHoverCursor);
                    }

                } else {
                    // KHI RỜI KHỎI HOVER:
                    if (imageOut != null) {
                        imageOut.setVisible(true);
                    }
                    if (imageOn != null) {
                        imageOn.setVisible(false);
                    }
                    System.out.println("PPPPPPPPPP");
                    // THÊM MỚI: ĐẶT LẠI CON TRỎ MẶC ĐỊNH (IMG1) CỦA SCENE
                    if (defaultGameCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(defaultGameCursor);
                        System.out.println("TTTTTTTTTTTTTT");
                    }
                }
            });
        }
    }


    // Phương thức chặn phím Space/Enter
    private void preventKeyActivation(Button button) {
        if (button != null) {
            button.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                    event.consume();
                }
            });
        }
    }

    private void initializeCursors(Scene scene) {
        if (defaultGameCursor == null) {
            try {
                // Tải ảnh img1
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_CURSOR);
                if (cursorUrl != null) {
                    Image customImage = new Image(cursorUrl.toExternalForm());
                    // Tạo Cursor img1
                    defaultGameCursor = Cursor.cursor(cursorUrl.toExternalForm());
                    // Áp dụng con trỏ mặc định của game cho Scene
                    scene.setCursor(defaultGameCursor);
                } else {
                    // Fallback nếu không tìm thấy img1
                    defaultGameCursor = Cursor.DEFAULT;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ mặc định (img1): " + e.getMessage());
                defaultGameCursor = Cursor.DEFAULT;
            }
        }

        // KIỂM TRA THỨ HAI: Tải con trỏ hover (img2)
        if (buttonHoverCursor == null) {
            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_HOVER_CURSOR);
                if (cursorUrl == null) {
                    buttonHoverCursor = Cursor.HAND;
                    throw new IOException("Không tìm thấy file con trỏ nút. Dùng Cursor.HAND.");
                }

                Image customImage = new Image(cursorUrl.toExternalForm());
                buttonHoverCursor = Cursor.cursor(cursorUrl.toExternalForm());

            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ hover (img2): " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("DEBUG: SettingController Initialized.");

        try {
            String resourcePath = "/Images/background/video_main_menu.mp4";

            URL videoResource = getClass().getResource(resourcePath);
            Media media = new Media(videoResource.toExternalForm());
            MediaPlayer menu_mediaPlayer = new MediaPlayer(media);
            menuPlayerView.setMediaPlayer(menu_mediaPlayer);
            menu_mediaPlayer.setAutoPlay(true);
            menu_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
            menu_mediaPlayer.setMute(true); // Tắt tiếng video nền
            menu_mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải hoặc phát video.");
        }

        playButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                initializeCursors(newScene);
            }
        });

        // Giữ lại setMouseTransparent(true) nếu cần cho bố cục
        playHoverImage.setMouseTransparent(true);
        settingHoverImage.setMouseTransparent(true);
        helpHoverImage.setMouseTransparent(true);
        shopHoverImage.setMouseTransparent(true);
        exitHoverImage.setMouseTransparent(true);

        // ÁP DỤNG HIỆU ỨNG HOVER MỚI (Âm thanh + Ẩn/Hiện + Con trỏ)
        addHoverEffect(playButton, playImage, playHoverImage);
        addHoverEffect(settingButton, settingImage, settingHoverImage);
        addHoverEffect(helpButton, helpImage, helpHoverImage);
        addHoverEffect(shopButton, shopImage, shopHoverImage);
        addHoverEffect(exitButton, exitImage, exitHoverImage);

        // Gọi phương thức chặn phím
        preventKeyActivation(playButton);
        preventKeyActivation(helpButton);
        preventKeyActivation(settingButton);
        preventKeyActivation(shopButton);
        preventKeyActivation(exitButton);
    }
}