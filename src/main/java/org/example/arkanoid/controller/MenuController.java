package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.example.arkanoid.launch.Main;
import org.example.arkanoid.sound.SoundManager; // <-- Đảm bảo có import này

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

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

    // --- CÁC HÀM XỬ LÝ HOVER CHUỘT ---

    @FXML
    private void onPlayHoverEnter() {
        playHoverImage.setVisible(true);
        SoundManager.playMenuHover(); // <-- ĐÃ THÊM ÂM THANH VÀO ĐÂY
    }

    @FXML
    private void onPlayHoverExit() {
        playHoverImage.setVisible(false);
    }

    @FXML
    private void onHelpHoverEnter() { // <-- HÀM MỚI
        SoundManager.playMenuHover();
    }

    @FXML
    private void onSettingHoverEnter() { // <-- HÀM MỚI
        SoundManager.playMenuHover();
    }

    @FXML
    private void onExitHoverEnter() { // <-- HÀM MỚI
        SoundManager.playMenuHover();
    }

    // --- KẾT THÚC CÁC HÀM HOVER ---

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Tải âm thanh cho menu
        SoundManager.loadMenuSounds(); // <-- Đã thêm

        // Đảm bảo ảnh hover không bắt sự kiện chuột (nếu chưa đặt trong FXML)
        playHoverImage.setMouseTransparent(true);

        // Khi hover lên nút -> hiện ảnh hover và ẩn ảnh bình thường
        playHoverImage.visibleProperty().bind(playButton.hoverProperty());
        playImage.visibleProperty().bind(playButton.hoverProperty().not());
    }
}