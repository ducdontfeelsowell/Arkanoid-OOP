package org.example.arkanoid.controller;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.example.arkanoid.launch.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    // Biến đang chuyển cảnh
    @FXML
    private Button exitButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button playButton;

    @FXML
    private ImageView playHoverImage;

    @FXML
    private ImageView playImage;

    @FXML
    private Button settingButton;

    @FXML
    public void onPlayGameButtonClick() {
        Main.startGame();
    }

    @FXML
    public void onHelpButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/arkanoid/help-view.fxml"));
        Parent newRoot = loader.load();
        // Gọi hàm chuyển cảnh có hiệu ứng
        helpButton.getScene().setRoot(newRoot);
    }

    @FXML
    public void onSettingButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/arkanoid/setting-view.fxml"));
        Parent newRoot = loader.load();
        settingButton.getScene().setRoot(newRoot);
    }

    @FXML
    public void onExitGameButtonClick() {
        System.exit(0);
    }

    @FXML
    private void onPlayHoverEnter() {
        playHoverImage.setVisible(true);
    }

    @FXML
    private void onPlayHoverExit() {
        playHoverImage.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Đảm bảo ảnh hover không bắt sự kiện chuột (nếu chưa đặt trong FXML)
        playHoverImage.setMouseTransparent(true);

        // Khi hover lên nút -> hiện ảnh hover và ẩn ảnh bình thường
        playHoverImage.visibleProperty().bind(playButton.hoverProperty());
        playImage.visibleProperty().bind(playButton.hoverProperty().not());
    }
}