package org.example.arkanoid.controller;

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
    @FXML
    private Button exitButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button playButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button levelButton; // Đã có

    @FXML
    private Button shopButton; // Đã có

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
    private ImageView levelHoverImage;

    @FXML
    private ImageView levelImage;

    @FXML
    private ImageView exitImage;

    @FXML
    private ImageView exitHoverImage;

    // Các phương thức xử lý sự kiện đã có
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

    // START: Các phương thức xử lý Level và Shop đã thiếu <--- CẦN THIẾT
    @FXML
    public void onLevelButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/arkanoid/level-view.fxml"));
        Parent newRoot = loader.load();
        levelButton.getScene().setRoot(newRoot);
    }

    @FXML
    public void onShopButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/arkanoid/shop-view.fxml"));
        Parent newRoot = loader.load();
        shopButton.getScene().setRoot(newRoot);
    }
    // END: Các phương thức Level và Shop đã thiếu

    @FXML
    public void onExitGameButtonClick() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Play button hover setup
        System.out.println("DEBUG: SettingController Initialized.");
        playHoverImage.setMouseTransparent(true);
        playHoverImage.visibleProperty().bind(playButton.hoverProperty());
        playImage.visibleProperty().bind(playButton.hoverProperty().not());

        // Setting button hover setup
        settingHoverImage.setMouseTransparent(true);
        settingHoverImage.visibleProperty().bind(settingButton.hoverProperty());
        settingImage.visibleProperty().bind(settingButton.hoverProperty().not());

        // Help button hover setup
        helpHoverImage.setMouseTransparent(true);
        helpHoverImage.visibleProperty().bind(helpButton.hoverProperty());
        helpImage.visibleProperty().bind(helpButton.hoverProperty().not());

        // Level button hover setup
        levelHoverImage.setMouseTransparent(true);
        levelHoverImage.visibleProperty().bind(levelButton.hoverProperty());
        levelImage.visibleProperty().bind(levelButton.hoverProperty().not());

        // Shop button hover setup
        shopHoverImage.setMouseTransparent(true);
        shopHoverImage.visibleProperty().bind(shopButton.hoverProperty());
        shopImage.visibleProperty().bind(shopButton.hoverProperty().not());

        exitHoverImage.setMouseTransparent(true);
        exitHoverImage.visibleProperty().bind(exitButton.hoverProperty());
        exitImage.visibleProperty().bind(exitButton.hoverProperty().not());
    }
}