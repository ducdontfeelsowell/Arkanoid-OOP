package org.example.arkanoid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.launch.Main;

import java.io.IOException;

public class LevelController {
    public Button map1Button;
    public Button map2Button;
    public Button map3Button;
    public Button map4Button;
    public Button map5Button;
    public Button map6Button;
    public Button map7Button;
    public Button map8Button;
    public Button map9Button;
    public Button map10Button;
    public Button map11Button;
    public Button map12Button;

    public Button backButton;

    public void onClickMap1() {
        Main.startGame(Constants.MAP1_PATH);
    }

    public void onClickMap2() {
        Main.startGame(Constants.MAP2_PATH);
    }

    public void onClickMap3() {
        Main.startGame(Constants.MAP3_PATH);
    }

    public void onClickMap4() {
        Main.startGame(Constants.MAP4_PATH);
    }

    public void onClickMap5() {
        Main.startGame(Constants.MAP5_PATH);
    }

    public void onClickMap6() {
        Main.startGame(Constants.MAP6_PATH);
    }

    public void onClickMap7() {
        Main.startGame(Constants.MAP7_PATH);
    }

    public void onClickMap8() {
        Main.startGame(Constants.MAP8_PATH);
    }

    public void onClickMap9() {
        Main.startGame(Constants.MAP9_PATH);
    }

    public void onClickMap10() {
        Main.startGame(Constants.MAP10_PATH);
    }

    public void onClickMap11() {
        Main.startGame(Constants.MAP11_PATH);
    }

    public void onClickMap12() {
        Main.startGame(Constants.MAP12_PATH);
    }


    public void onBackButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.PATH_TO_MAIN_MENU));
        Parent root = loader.load();
        backButton.getScene().setRoot(root);
    }
}
