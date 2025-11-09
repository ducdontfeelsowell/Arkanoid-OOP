package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.SoundManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/*
public class ScoreboardController implements Initializable {
    private static Cursor defaultGameCursor; // Con trỏ mặc định của game (img1)
    private static Cursor buttonHoverCursor; // Con trỏ khi hover (img2)

    @FXML
    private Button backButton;
    @FXML
    private ImageView backHoverImage; // Ảnh khi hover
    @FXML
    private ImageView backImage; // Ảnh mặc định (out)

    @FXML
    public void onBackButton() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        backButton.getScene().setCursor(defaultGameCursor);
        backButton.getScene().setRoot(root);
    }

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

    private void preventKeyActivation(Button button) {
        if (button != null) {
            button.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
                    event.consume();
                }
            });
        }
    }
    // ... (Phương thức preventKeyActivation(RadioButton) giữ nguyên) ...

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
                System.err.println("Lỗi khi tải con trỏ mặc định (img1) trong SettingController: " + e.getMessage());
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
                System.err.println("Lỗi khi tải con trỏ hover (img2) trong SettingController: " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
*/
