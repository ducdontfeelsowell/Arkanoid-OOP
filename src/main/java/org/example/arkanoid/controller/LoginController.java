package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.game.ProgressManager;
import org.example.arkanoid.game.SoundManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static Cursor defaultGameCursor;
    private static Cursor buttonHoverCursor;

    @FXML
    private Button okButton;

    @FXML
    private ImageView okImage;

    @FXML
    private ImageView okHoverImage;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label errorLabel;

    /**
     * Xử lý khi nhấn nút OK/Bắt đầu
     */
    @FXML
    public void onClickOkButton() throws IOException {
        SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_CLICK);
        String playerName = nameTextField.getText().trim();

        if (playerName.isEmpty()) {
            errorLabel.setText("Tên không hợp lệ");
            return;
        }

        ProgressManager.login(playerName);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        okButton.getScene().setRoot(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        okButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                initializeCursors(newScene);
            }
        });

        if (okHoverImage != null) {
            okHoverImage.setMouseTransparent(true);
        }
        addHoverEffect(okButton, okImage, okHoverImage);

        preventKeyActivation(okButton);
    }


    private void addHoverEffect(Button button, Node imageOut, Node imageOn) {
        if (button != null) {
            if (imageOn != null) {
                imageOn.setVisible(false);
            }
            button.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    SoundManager.getInstance().playSoundEffect(Constants.PATH_TO_SOUND_HOVER);
                    if (imageOut != null) imageOut.setVisible(false);
                    if (imageOn != null) imageOn.setVisible(true);
                    if (buttonHoverCursor != null && button.getScene() != null) {
                        button.getScene().setCursor(buttonHoverCursor);
                    }
                } else {
                    if (imageOut != null) imageOut.setVisible(true);
                    if (imageOn != null) imageOn.setVisible(false);
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

    private void initializeCursors(Scene scene) {
        if (defaultGameCursor == null) {
            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_CURSOR);
                if (cursorUrl != null) {
                    defaultGameCursor = Cursor.cursor(cursorUrl.toExternalForm());
                    scene.setCursor(defaultGameCursor);
                } else {
                    defaultGameCursor = Cursor.DEFAULT;
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ mặc định (img1): " + e.getMessage());
                defaultGameCursor = Cursor.DEFAULT;
            }
        }
        if (buttonHoverCursor == null) {
            try {
                URL cursorUrl = getClass().getResource(Constants.PATH_TO_HOVER_CURSOR);
                if (cursorUrl == null) {
                    buttonHoverCursor = Cursor.HAND;
                    throw new IOException("Không tìm thấy file con trỏ nút.");
                }
                buttonHoverCursor = Cursor.cursor(cursorUrl.toExternalForm());
            } catch (Exception e) {
                System.err.println("Lỗi khi tải con trỏ hover (img2): " + e.getMessage());
                buttonHoverCursor = Cursor.HAND;
            }
        }
    }
}