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

    @FXML
    private Button trailButton;
    @FXML
    private Button ballButton;
    @FXML
    private Button paddleButton;

    @FXML
    private ImageView ballImage;
    @FXML
    private ImageView ballHoverImage;
    @FXML
    private ImageView trailImage;
    @FXML
    private ImageView trailHoverImage;
    @FXML
    private ImageView paddleImage;
    @FXML
    private ImageView paddleHoverImage;

    @FXML
    private ImageView backHoverImage;
    @FXML
    private ImageView backImage;

    @FXML
    private MediaView helpPlayerView;

    @FXML
    private AnchorPane ballContentPane;
    @FXML
    private AnchorPane trailContentPane;
    @FXML
    private AnchorPane paddleContentPane;

    // --- PHƯƠNG THỨC QUẢN LÝ TRẠNG THÁI SÁNG/TỐI ---

    /**
     * Đặt một nút Shop làm nút đang hoạt động (hiển thị ảnh sáng) và
     * buộc nút đang hoạt động trước đó (nếu có) trở về trạng thái tối.
     */
    private void setActiveButton(Button newActiveButton, Node newActiveImageOut, Node newActiveImageOn) {

        // Chỉ thực hiện nếu nút mới khác nút đang hoạt động
        if (currentActiveShopButton != newActiveButton) {

            // 1. Lưu nút cũ (nút đang sáng) vào biến tạm
            Button oldActiveButton = currentActiveShopButton;

            // 2. Tắt nút cũ dựa trên đối tượng nút cũ đã lưu
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

    // Tắt trạng thái trực quan (về tối) cho một cặp ảnh
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


    // --- PHƯƠNG THỨC KHỞI TẠO CURSOR ---
    private void initializeCursors(Scene scene) {
        // Khởi tạo con trỏ mặc định (img1)
        if (defaultGameCursor == null) {
            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_CURSOR);
                if (cursorUrl != null) {
                    Image customImage = new Image(cursorUrl.toExternalForm());
                    defaultGameCursor = Cursor.cursor(cursorUrl.toExternalForm());
                    scene.setCursor(defaultGameCursor);
                } else {
                    defaultGameCursor = Cursor.DEFAULT;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ mặc định (img1) trong ShopController: " + e.getMessage());
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
                System.err.println("Lỗi khi tải con trỏ hover (img2) trong ShopController: " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
    }
    // --- KẾT THÚC KHỞI TẠO CURSOR ---

    // PHƯƠNG THỨC XỬ LÝ HIỆU ỨNG HOVER
    private void addHoverEffect(Button button, Node imageOut, Node imageOn) {
        if (button != null) {

            // Khởi tạo trạng thái ban đầu: BALL mặc định sáng
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
                    // CHỈ ĐỔI ẢNH VỀ MẶC ĐỊNH nếu nút đó KHÔNG phải là nút đang được chọn
                    if (button != currentActiveShopButton) {
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

        // Áp dụng hiệu ứng Hover
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