package org.example.arkanoid.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.launch.Main;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button playButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button settingButton;

    @FXML
    private Button exitButton;

    @FXML
    public void onPlayGameButtonClick() throws IOException {
//        Main.startGame();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_LEVEL_VIEW));
        Parent root = loader.load();
        playButton.getScene().setRoot(root);
    }

    @FXML
    public void onHelpButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_HELP_VIEW));
        Parent root = loader.load();
        helpButton.getScene().setRoot(root);
    }

    @FXML
    public void onSettingButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_SETTING_VIEW));
        Parent root = loader.load();
        settingButton.getScene().setRoot(root);
    }

    @FXML
    public void onExitGameButtonClick() {
        System.exit(0);
    }
}