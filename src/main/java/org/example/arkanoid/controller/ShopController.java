package org.example.arkanoid.controller;

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
import org.example.arkanoid.game.ProgressManager;
import org.example.arkanoid.game.SoundManager;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ShopController implements Initializable {
    private static Cursor defaultGameCursor;
    private static Cursor buttonHoverCursor;

    private Button currentActiveShopButton;

    @FXML
    private MediaView helpPlayerView;

    // Các Button cho Shop Menu
    @FXML
    private Button backButton;
    @FXML
    private Button trailButton;
    @FXML
    private Button ballButton;
    @FXML
    private Button paddleButton;

    // Các ảnh cho hiệu ứng Hover của các nút Shop Menu
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

    // Các AnchorPane chứa nội dung từng mục
    @FXML
    private AnchorPane ballContentPane;
    @FXML
    private AnchorPane trailContentPane;
    @FXML
    private AnchorPane paddleContentPane;

    // --- Các nhãn hiển thị (TỪ FXML MỚI) ---
    @FXML private Label playerCoinLabel;   // Trong ballContentPane
    @FXML private Label shopNotificationLabel; // Trong ballContentPane
    @FXML private Label playerCoinLabel1;  // Trong trailContentPane
    @FXML private Label shopNotificationLabel1;// Trong trailContentPane
    @FXML private Label playerCoinLabel2;  // Trong paddleContentPane
    @FXML private Label shopNotificationLabel2;// Trong paddleContentPane


    // --- FXML ID cho các Labels (Ball) ---
    @FXML private Label label_BALL_EARTH;
    @FXML private Label label_BALL_SOCCER;
    @FXML private Label label_BALL_PANCAKE;
    @FXML private Label label_BALL_ENDERMAN2;
    @FXML private Label label_BALL_ENDERMAN1;
    @FXML private Label label_BALL_CHROME;

    // --- FXML ID cho các Buttons (Ball) ---
    @FXML private Button button_BALL_EARTH;
    @FXML private Button button_BALL_SOCCER;
    @FXML private Button button_BALL_PANCAKE;
    @FXML private Button button_BALL_ENDERMAN2;
    @FXML private Button button_BALL_ENDERMAN1;
    @FXML private Button button_BALL_CHROME;

    // --- FXML ID cho các Labels (Trail) ---
    @FXML private Label label_TRAIL_LGBT;
    @FXML private Label label_TRAIL_DIAMOND;
    @FXML private Label label_TRAIL_LIGHTNING;
    @FXML private Label label_TRAIL_PRIMOGEM;
    @FXML private Label label_TRAIL_DOLLA;
    @FXML private Label label_TRAIL_LUCKYCLOVER;

    // --- FXML ID cho các Buttons (Trail) ---
    @FXML private Button button_TRAIL_LGBT;
    @FXML private Button button_TRAIL_DIAMOND;
    @FXML private Button button_TRAIL_LIGHTNING;
    @FXML private Button button_TRAIL_PRIMOGEM;
    @FXML private Button button_TRAIL_DOLLA;
    @FXML private Button button_TRAIL_LUCKYCLOVER;

    // --- FXML ID cho các Labels (Paddle) ---
    @FXML private Label label_PADDLE_DEFAULT;
    @FXML private Label label_PADDLE_SKIN2;

    // --- FXML ID cho các Buttons (Paddle) ---
    @FXML private Button button_PADDLE_DEFAULT;
    @FXML private Button button_PADDLE_SKIN2;


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
            // 3. Đặt nút mới
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


    @FXML
    public void onBackButtonClick(ActionEvent event) throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        if (backButton.getScene() != null && defaultGameCursor != null) {
            backButton.getScene().setCursor(defaultGameCursor);
        }
        backButton.getScene().setRoot(root);
    }

    // --- CÁC NÚT ĐIỀU HƯỚNG SHOP ---
    @FXML
    public void onBallButtonClick(ActionEvent event) {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        updateAllNotificationLabels(""); // <-- THÊM DÒNG NÀY
        // Hiển thị nội dung
        ballContentPane.setVisible(true);
        trailContentPane.setVisible(false);
        paddleContentPane.setVisible(false);
        setActiveButton(ballButton, ballImage, ballHoverImage);
    }

    @FXML
    public void onTrailButtonClick(ActionEvent event) {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        updateAllNotificationLabels(""); // <-- THÊM DÒNG NÀY
        // Hiển thị nội dung
        ballContentPane.setVisible(false);
        trailContentPane.setVisible(true);
        paddleContentPane.setVisible(false);
        setActiveButton(trailButton, trailImage, trailHoverImage);
    }

    @FXML
    public void onPaddleButtonClick(ActionEvent event) {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        updateAllNotificationLabels(""); // <-- THÊM DÒNG NÀY
        // Hiển thị nội dung
        ballContentPane.setVisible(false);
        trailContentPane.setVisible(false);
        paddleContentPane.setVisible(true);
        setActiveButton(paddleButton, paddleImage, paddleHoverImage);
    }

    // --- PHƯƠNG THỨC KHỞI TẠO CURSOR ---
    private void initializeCursors(Scene scene) {
        if (defaultGameCursor == null) {
            try {
                Image cursorImage = new Image(getClass().getResourceAsStream(Constants.PATH_TO_CURSOR));
                defaultGameCursor = new javafx.scene.ImageCursor(cursorImage);
            } catch (Exception e) {
                System.err.println("Không thể tải con trỏ mặc định: " + e.getMessage());
                defaultGameCursor = Cursor.DEFAULT;
            }
        }
        if (buttonHoverCursor == null) {
            try {
                Image hoverCursorImage = new Image(getClass().getResourceAsStream(Constants.PATH_TO_HOVER_CURSOR));
                buttonHoverCursor = new javafx.scene.ImageCursor(hoverCursorImage);
            } catch (Exception e) {
                System.err.println("Không thể tải con trỏ hover: " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
        scene.setCursor(defaultGameCursor);
    }

    // PHƯƠNG THỨC XỬ LÝ HIỆU ỨNG HOVER
    private void addHoverEffect(Button button, Node imageOut, Node imageOn) {
        if (button != null) {
            // Khởi tạo trạng thái ban đầu
            if (button == ballButton) { // Nút Ball được chọn mặc định
                imageOn.setVisible(true);
                imageOut.setVisible(false);
            } else if (imageOn != null) {
                imageOn.setVisible(false);
                imageOut.setVisible(true);
            }

            // Xử lý hover
            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                    if (imageOut != null) imageOut.setVisible(false);
                    if (imageOn != null) imageOn.setVisible(true);
                    if (buttonHoverCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(buttonHoverCursor);
                    }
                } else {
                    // Nếu không phải là nút đang active, hoặc là nút back, thì tắt
                    if (button != currentActiveShopButton || button == backButton) {
                        if (imageOut != null) imageOut.setVisible(true);
                        if (imageOn != null) imageOn.setVisible(false);
                    }
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

    // --- CÁC HÀM TRỢ GIÚP MỚI ĐỂ XỬ LÝ CÁC LABEL BỊ TRÙNG LẶP ---
    private void updateAllCoinLabels(String text) {
        if (playerCoinLabel != null) playerCoinLabel.setText(text);
        if (playerCoinLabel1 != null) playerCoinLabel1.setText(text);
        if (playerCoinLabel2 != null) playerCoinLabel2.setText(text);
    }

    private void updateAllNotificationLabels(String text) {
        if (shopNotificationLabel != null) shopNotificationLabel.setText(text);
        if (shopNotificationLabel1 != null) shopNotificationLabel1.setText(text);
        if (shopNotificationLabel2 != null) shopNotificationLabel2.setText(text);
    }
    // ----------------------------------------------------------


    // --- PHƯƠNG THỨC KHỞI TẠO ---
    @Override
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
        } else {
            System.err.println("ShopController: backButton là null. Không thể khởi tạo cursors.");
        }

        try {
            // Khởi tạo video nền
            String resourcePath = "/Images/background/video_main_menu.mp4";
            URL videoResource = getClass().getResource(resourcePath);
            if(videoResource != null) {
                Media media = new Media(videoResource.toExternalForm());
                MediaPlayer help_mediaPlayer = new MediaPlayer(media);
                helpPlayerView.setMediaPlayer(help_mediaPlayer);
                help_mediaPlayer.setAutoPlay(true);
                help_mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                help_mediaPlayer.setMute(true);
                help_mediaPlayer.play();
            } else {
                System.err.println("Không tìm thấy file video: " + resourcePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Không thể tải hoặc phát video.");
        }

        // Áp dụng hiệu ứng Hover cho các nút chính
        addHoverEffect(backButton, backImage, backHoverImage);
        addHoverEffect(ballButton, ballImage, ballHoverImage);
        addHoverEffect(trailButton, trailImage, trailHoverImage);
        addHoverEffect(paddleButton, paddleImage, paddleHoverImage);

        // Chặn phím
        preventKeyActivation(backButton);
        preventKeyActivation(ballButton);
        preventKeyActivation(trailButton);
        preventKeyActivation(paddleButton);

        // ===============================================
        // === CẬP NHẬT LOGIC SHOP KHI KHỞI TẠO ===
        // ===============================================
        updateAllNotificationLabels(""); // Xóa thông báo cũ
        updateShopUI(); // Cập nhật trạng thái các nút (Buy/Equip/Equipped)
        // ===============================================
    }


    // ===================================================================
    // === CÁC PHƯƠNG THỨC MỚI CHO LOGIC SHOP ===
    // ===================================================================

    /**
     * Cập nhật toàn bộ giao diện Shop (Tiền, các nút)
     */
    public void updateShopUI() {
        // 1. Cập nhật tiền (Sử dụng hàm trợ giúp mới)
        updateAllCoinLabels("Coins: " + ProgressManager.getCurrentCoins_Static());

        // 2. Cập nhật các nút Ball
        updateItemUI(label_BALL_EARTH, button_BALL_EARTH, Constants.BALL_SKIN_EARTH, "ball");
        updateItemUI(label_BALL_PANCAKE, button_BALL_PANCAKE, Constants.BALL_SKIN_PANCAKE, "ball");
        // *** SỬA: Đảm bảo dùng đúng ID từ FXML (Enderman1) ***
        updateItemUI(label_BALL_ENDERMAN1, button_BALL_ENDERMAN1, Constants.BALL_SKIN_ENDERMAN1, "ball");
        updateItemUI(label_BALL_SOCCER, button_BALL_SOCCER, Constants.BALL_SKIN_SOCCER, "ball");
        // *** SỬA: Đảm bảo dùng đúng ID từ FXML (Enderman2) ***
        updateItemUI(label_BALL_ENDERMAN2, button_BALL_ENDERMAN2, Constants.BALL_SKIN_ENDERMAN2, "ball");
        updateItemUI(label_BALL_CHROME, button_BALL_CHROME, Constants.BALL_SKIN_CHROME, "ball");

        // 3. Cập nhật các nút Trail
        updateItemUI(label_TRAIL_LGBT, button_TRAIL_LGBT, Constants.TRAIL_SKIN_LGBT, "trail");
        updateItemUI(label_TRAIL_LIGHTNING, button_TRAIL_LIGHTNING, Constants.TRAIL_SKIN_LIGHTNING, "trail");
        updateItemUI(label_TRAIL_DOLLA, button_TRAIL_DOLLA, Constants.TRAIL_SKIN_DOLLA, "trail");
        updateItemUI(label_TRAIL_DIAMOND, button_TRAIL_DIAMOND, Constants.TRAIL_SKIN_DIAMOND, "trail");
        updateItemUI(label_TRAIL_PRIMOGEM, button_TRAIL_PRIMOGEM, Constants.TRAIL_SKIN_PRIMOGEM, "trail");
        updateItemUI(label_TRAIL_LUCKYCLOVER, button_TRAIL_LUCKYCLOVER, Constants.TRAIL_SKIN_LUCKYCLOVER, "trail");

        // 4. Cập nhật Paddle
        // *** SỬA: Dùng hằng số đã thêm vào Constants.java ***
        updateItemUI(label_PADDLE_DEFAULT, button_PADDLE_DEFAULT, Constants.PADDLE_SKIN_DEFAULT, "paddle");
        updateItemUI(label_PADDLE_SKIN2, button_PADDLE_SKIN2, Constants.PADDLE_SKIN_2, "paddle");
    }

    /**
     * Phương thức trợ giúp (helper) để cập nhật một cặp Label/Button vật phẩm
     */
    private void updateItemUI(Label label, Button button, String itemId, String itemType) {
        if (label == null || button == null) {
            return;
        }

        boolean isOwned;
        String equippedId;

        // Lấy trạng thái từ ProgressManager
        if ("ball".equals(itemType)) {
            isOwned = ProgressManager.isBallOwned(itemId);
            equippedId = ProgressManager.getEquippedBall();
            if (equippedId == null) equippedId = Constants.BALL_SKIN_EARTH; // Mặc định
        } else if ("trail".equals(itemType)) {
            isOwned = ProgressManager.isTrailOwned(itemId);
            equippedId = ProgressManager.getEquippedTrail();
            if (equippedId == null) equippedId = Constants.TRAIL_SKIN_LGBT; // Mặc định
        } else { // "paddle"
            // *** SỬA: Logic này giờ đã hoạt động vì ProgressManager đã được cập nhật ***
            isOwned = ProgressManager.isPaddleOwned(itemId);
            equippedId = ProgressManager.getEquippedPaddle();
            if (equippedId == null) equippedId = Constants.PADDLE_SKIN_DEFAULT;
        }

        if (isOwned) {
            if (equippedId.equals(itemId)) {
                label.setText("Equipped");
                button.setDisable(true);
            } else {
                label.setText("Equip");
                button.setDisable(false);
            }
        } else {
            // Chưa sở hữu
            Integer cost = Constants.ITEM_COSTS.get(itemId);
            if (cost != null) {
                if (cost == 0) {
                    label.setText("Equip");
                    button.setDisable(false);
                } else {
                    // Hiển thị giá
                    label.setText("$" + cost);
                    button.setDisable(false);
                }
            } else {
                // Vật phẩm không được định nghĩa trong Constants.ITEM_COSTS
                label.setText("N/A");
                button.setDisable(true);
            }
        }
    }

    /**
     * Phương thức trợ giúp chung để xử lý click mua/trang bị
     */
    private void handleItemClick(Label label, Button button, String itemId, String itemType) {
        // Xóa thông báo cũ (Sử dụng hàm trợ giúp mới)
        updateAllNotificationLabels("");
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);

        boolean isOwned;
        if ("ball".equals(itemType)) {
            isOwned = ProgressManager.isBallOwned(itemId);
        } else if ("trail".equals(itemType)) {
            isOwned = ProgressManager.isTrailOwned(itemId);
        } else { // "paddle"
            isOwned = ProgressManager.isPaddleOwned(itemId);
        }

        if (isOwned) {
            // Đã sở hữu -> Click là để "Equip"
            ProgressManager.equipItem(itemId, itemType);
        } else {
            // Chưa sở hữu -> Click là để "Buy"
            boolean success = ProgressManager.purchaseAndEquipItem(itemId, itemType);

            if (!success) {
                // Không đủ tiền
                Integer cost = Constants.ITEM_COSTS.get(itemId);
                int currentCoins = ProgressManager.getCurrentCoins_Static();

                if (cost != null && cost > currentCoins) {
                    int missing = cost - currentCoins;
                    // Cập nhật tất cả các nhãn thông báo
                    updateAllNotificationLabels("Không đủ! Còn thiếu " + missing + " coins.");
                } else {
                    // Trường hợp lỗi khác (ví dụ: vật phẩm không có giá)
                    updateAllNotificationLabels("Không đủ tiền!");
                }
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_NEGATIVE_BUFF);
            } else {
                // Mua thành công
                SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_USE_ITEM);
            }
        }

        // Sau khi thực hiện hành động, cập nhật lại TOÀN BỘ giao diện
        updateShopUI();
    }

    // ===================================================================
    // === CÁC HÀNH ĐỘNG (ACTION) MỚI CHO NÚT VẬT PHẨM (DỰA TRÊN FXML) ===
    // ===================================================================

    // --- Ball Handlers ---
    @FXML private void onBuyEquip_BallEarth() { handleItemClick(label_BALL_EARTH, button_BALL_EARTH, Constants.BALL_SKIN_EARTH, "ball"); }
    @FXML private void onBuyEquip_BallPancake() { handleItemClick(label_BALL_PANCAKE, button_BALL_PANCAKE, Constants.BALL_SKIN_PANCAKE, "ball"); }
    // *** SỬA: Đảm bảo dùng đúng ID từ FXML (Enderman1) ***
    @FXML private void onBuyEquip_BallEnderman1() { handleItemClick(label_BALL_ENDERMAN1, button_BALL_ENDERMAN1, Constants.BALL_SKIN_ENDERMAN1, "ball"); }
    @FXML private void onBuyEquip_BallSoccer() { handleItemClick(label_BALL_SOCCER, button_BALL_SOCCER, Constants.BALL_SKIN_SOCCER, "ball"); }
    // *** SỬA: Đảm bảo dùng đúng ID từ FXML (Enderman2) ***
    @FXML private void onBuyEquip_BallEnderman2() { handleItemClick(label_BALL_ENDERMAN2, button_BALL_ENDERMAN2, Constants.BALL_SKIN_ENDERMAN2, "ball"); }
    @FXML private void onBuyEquip_BallChrome() { handleItemClick(label_BALL_CHROME, button_BALL_CHROME, Constants.BALL_SKIN_CHROME, "ball"); }

    // --- Trail Handlers ---
    @FXML private void onBuyEquip_TrailLgbt() { handleItemClick(label_TRAIL_LGBT, button_TRAIL_LGBT, Constants.TRAIL_SKIN_LGBT, "trail"); }
    @FXML private void onBuyEquip_TrailLightning() { handleItemClick(label_TRAIL_LIGHTNING, button_TRAIL_LIGHTNING, Constants.TRAIL_SKIN_LIGHTNING, "trail"); }
    @FXML private void onBuyEquip_TrailDolla() { handleItemClick(label_TRAIL_DOLLA, button_TRAIL_DOLLA, Constants.TRAIL_SKIN_DOLLA, "trail"); }
    @FXML private void onBuyEquip_TrailDiamond() { handleItemClick(label_TRAIL_DIAMOND, button_TRAIL_DIAMOND, Constants.TRAIL_SKIN_DIAMOND, "trail"); }
    @FXML private void onBuyEquip_TrailPrimogem() { handleItemClick(label_TRAIL_PRIMOGEM, button_TRAIL_PRIMOGEM, Constants.TRAIL_SKIN_PRIMOGEM, "trail"); }
    @FXML private void onBuyEquip_TrailLuckyClover() { handleItemClick(label_TRAIL_LUCKYCLOVER, button_TRAIL_LUCKYCLOVER, Constants.TRAIL_SKIN_LUCKYCLOVER, "trail"); }

    // --- Paddle Handlers ---
    // *** SỬA: Dùng hằng số đã thêm vào Constants.java ***
    @FXML private void onBuyEquip_PaddleDefault() { handleItemClick(label_PADDLE_DEFAULT, button_PADDLE_DEFAULT, Constants.PADDLE_SKIN_DEFAULT, "paddle"); }
    @FXML private void onBuyEquip_PaddleSkin2() { handleItemClick(label_PADDLE_SKIN2, button_PADDLE_SKIN2, Constants.PADDLE_SKIN_2, "paddle"); }

}