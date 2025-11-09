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
import javafx.scene.layout.AnchorPane;
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

public class ShopController implements Initializable {
    private static Cursor defaultGameCursor;
    private static Cursor buttonHoverCursor;

    // Theo dõi nút shop đang được chọn để giữ trạng thái sáng
    private Button currentActiveShopButton;

    @FXML
    private Label helpLabel;

    @FXML
    private Button backButton;

    // Các Button cho Shop Menu (Đã giữ nguyên)
    @FXML
    private Button trailButton;
    @FXML
    private Button ballButton;
    @FXML
    private Button paddleButton;

    // Các ảnh cho hiệu ứng Hover của các nút Shop Menu (Đã giữ nguyên)
    @FXML
    private ImageView ballImage; // Ảnh mặc định BALL
    @FXML
    private ImageView ballHoverImage; // Ảnh hover BALL
    @FXML
    private ImageView trailImage; // Ảnh mặc định TRAIL
    @FXML
    private ImageView trailHoverImage; // Ảnh hover TRAIL
    @FXML
    private ImageView paddleImage; // Ảnh mặc định PADDLE
    @FXML
    private ImageView paddleHoverImage; // Ảnh hover PADDLE

    @FXML
    private ImageView backHoverImage;
    @FXML
    private ImageView backImage;

    @FXML
    private MediaView helpPlayerView;

    // Các AnchorPane chứa nội dung từng mục (Đã giữ nguyên)
    @FXML
    private AnchorPane ballContentPane;
    @FXML
    private AnchorPane trailContentPane;
    @FXML
    private AnchorPane paddleContentPane;

    // --- PHƯƠNG THỨC QUẢN LÝ TRẠNG THÁI SÁNG/TỐI ---

    private void setActiveButton(Button newActiveButton, Node newActiveImageOut, Node newActiveImageOn) {

        if (currentActiveShopButton != newActiveButton) {

            Button oldActiveButton = currentActiveShopButton;

            // 2. Tắt nút cũ
            if (oldActiveButton == ballButton) {
                resetVisualState(ballImage, ballHoverImage);
            } else if (oldActiveButton == trailButton) {
                resetVisualState(trailImage, trailHoverImage);
            } else if (oldActiveButton == paddleButton) {
                resetVisualState(paddleImage, paddleHoverImage);
            }

            // 3. Đặt nút mới về trạng thái Sáng và cập nhật nút đang hoạt động
            if (newActiveButton != null) {
                newActiveImageOut.setVisible(false);
                newActiveImageOn.setVisible(true);
                currentActiveShopButton = newActiveButton;
            }
        }
    }

    private void resetVisualState(Node imageOut, Node imageOn) {
        imageOut.setVisible(true);
        imageOn.setVisible(false);
    }
    // ----------------------------------------------------------------------


    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        backButton.getScene().setCursor(defaultGameCursor);
        backButton.getScene().setRoot(root);
    }

    @FXML
    public void onBallButtonClick(ActionEvent event) {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        // Hiển thị nội dung
        ballContentPane.setVisible(true);
        trailContentPane.setVisible(false);
        paddleContentPane.setVisible(false);

        // Đặt trạng thái nút BALL là sáng
        setActiveButton(ballButton, ballImage, ballHoverImage);
    }

    @FXML
    public void onTrailButtonClick(ActionEvent event) {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        // Hiển thị nội dung
        ballContentPane.setVisible(false);
        trailContentPane.setVisible(true);
        paddleContentPane.setVisible(false);

        // Đặt trạng thái nút TRAIL là sáng
        setActiveButton(trailButton, trailImage, trailHoverImage);
    }

    @FXML
    public void onPaddleButtonClick(ActionEvent event) {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        // Hiển thị nội dung
        ballContentPane.setVisible(false);
        trailContentPane.setVisible(false);
        paddleContentPane.setVisible(true);

        // Đặt trạng thái nút PADDLE là sáng
        setActiveButton(paddleButton, paddleImage, paddleHoverImage);
    }

    // --- PHƯƠNG THỨC KHỞI TẠO CURSOR (Giữ nguyên) ---
    private void initializeCursors(Scene scene) {
        //... (Giữ nguyên)
    }
    // --- KẾT THÚC KHỞI TẠO CURSOR ---

    // PHƯƠNG THỨC XỬ LÝ HIỆU ỨNG HOVER (Giữ nguyên logic kiểm tra currentActiveShopButton)
    private void addHoverEffect(Button button, Node imageOut, Node imageOn) {
        if (button != null) {

            // Khởi tạo trạng thái ban đầu: BALL mặc định sáng
            // [Suy luận] Chỉ áp dụng cho 3 nút shop chính
            if (button == ballButton) {
                imageOn.setVisible(true);
                imageOut.setVisible(false);
            } else if (imageOn != null) {
                imageOn.setVisible(false);
            }

            // Xử lý hover
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
                    // KHI RỜI KHỎI HOVER:
                    // CHỈ ĐỔI ẢNH VỀ MẶC ĐỊNH nếu nút đó KHÔNG phải là nút đang được chọn (chỉ áp dụng cho 3 nút shop)
                    if (button != currentActiveShopButton || button == backButton) { // Nút back luôn tắt khi rời hover
                        if (imageOut != null) {
                            imageOut.setVisible(true);
                        }
                        if (imageOn != null) {
                            imageOn.setVisible(false);
                        }
                    }
                    // LUÔN ĐỔI CON TRỎ về mặc định
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
        // Thiết lập nút BALL là nút mặc định được chọn lần đầu
        currentActiveShopButton = ballButton;

        // Lắng nghe Scene Property
        if (backButton != null) {
            backButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    initializeCursors(newScene);
                }
            });
        }

        try {
            // Khởi tạo video nền
            String resourcePath = "/Images/background/video_main_menu.mp4";
            URL videoResource = getClass().getResource(resourcePath);
            Media media = new Media(videoResource.toExternalForm());
            MediaPlayer help_mediaPlayer = new MediaPlayer(media);
            helpPlayerView.setMediaPlayer(help_mediaPlayer);
            help_mediaPlayer.setAutoPlay(true);
            help_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            help_mediaPlayer.setMute(true);
            help_mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải hoặc phát video.");
        }

        // Áp dụng hiệu ứng Hover cho các nút chính
        addHoverEffect(backButton, backImage, backHoverImage);
        addHoverEffect(ballButton, ballImage, ballHoverImage);
        addHoverEffect(trailButton, trailImage, trailHoverImage);
        addHoverEffect(paddleButton, paddleImage, paddleHoverImage);


        // Gọi phương thức chặn phím
        preventKeyActivation(backButton);
        preventKeyActivation(ballButton);
        preventKeyActivation(trailButton);
        preventKeyActivation(paddleButton);
    }
}