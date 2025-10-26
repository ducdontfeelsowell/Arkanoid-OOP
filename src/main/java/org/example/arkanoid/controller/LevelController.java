package org.example.arkanoid.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.example.arkanoid.config.Constants;
import org.example.arkanoid.launch.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LevelController implements Initializable{
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

    @FXML
    private ImageView Level1_on;

    @FXML
    private ImageView Level2_on;

    @FXML
    private ImageView Level3_on;

    @FXML
    private ImageView Level4_on;

    @FXML
    private ImageView Level5_on;

    @FXML
    private ImageView Level6_on;

    @FXML
    private ImageView Level7_on;

    @FXML
    private ImageView Level8_on;

    @FXML
    private ImageView Level9_on;

    @FXML
    private ImageView Level10_on;

    @FXML
    private ImageView Level11_on;

    @FXML
    private ImageView Level12_on;

    @FXML
    private ImageView Level1_out;

    @FXML
    private ImageView Level2_out;

    @FXML
    private ImageView Level3_out;

    @FXML
    private ImageView Level4_out;

    @FXML
    private ImageView Level5_out;

    @FXML
    private ImageView Level6_out;

    @FXML
    private ImageView Level7_out;

    @FXML
    private ImageView Level8_out;

    @FXML
    private ImageView Level9_out;

    @FXML
    private ImageView Level10_out;

    @FXML
    private ImageView Level11_out;

    @FXML
    private ImageView Level12_out;

    @FXML
    private ImageView back_button_out;

    @FXML
    private ImageView back_button_on;

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

    public void initialize(URL location, ResourceBundle resources) {
        // Play button hover setup
        System.out.println("DEBUG: SettingController Initialized.");
        Level1_on.setMouseTransparent(true);
        Level1_on.visibleProperty().bind(map1Button.hoverProperty());
        Level1_out.visibleProperty().bind(map1Button.hoverProperty().not());

        // Setting button hover setup
        Level2_on.setMouseTransparent(true);
        Level2_on.visibleProperty().bind(map2Button.hoverProperty());
        Level2_out.visibleProperty().bind(map2Button.hoverProperty().not());

        // Help button hover setup
        Level3_on.setMouseTransparent(true);
        Level3_on.visibleProperty().bind(map3Button.hoverProperty());
        Level3_out.visibleProperty().bind(map3Button.hoverProperty().not());
        // Shop button hover setup
        Level4_on.setMouseTransparent(true);
        Level4_on.visibleProperty().bind(map4Button.hoverProperty());
        Level4_out.visibleProperty().bind(map4Button.hoverProperty().not());

        Level5_on.setMouseTransparent(true);
        Level5_on.visibleProperty().bind(map5Button.hoverProperty());
        Level5_out.visibleProperty().bind(map5Button.hoverProperty().not());

        Level6_on.setMouseTransparent(true);
        Level6_on.visibleProperty().bind(map6Button.hoverProperty());
        Level6_out.visibleProperty().bind(map6Button.hoverProperty().not());

        Level7_on.setMouseTransparent(true);
        Level7_on.visibleProperty().bind(map7Button.hoverProperty());
        Level7_out.visibleProperty().bind(map7Button.hoverProperty().not());

        // Setting button hover setup
        Level8_on.setMouseTransparent(true);
        Level8_on.visibleProperty().bind(map8Button.hoverProperty());
        Level8_out.visibleProperty().bind(map8Button.hoverProperty().not());

        // Help button hover setup
        Level9_on.setMouseTransparent(true);
        Level9_on.visibleProperty().bind(map9Button.hoverProperty());
        Level9_out.visibleProperty().bind(map9Button.hoverProperty().not());
        // Shop button hover setup
        Level10_on.setMouseTransparent(true);
        Level10_on.visibleProperty().bind(map10Button.hoverProperty());
        Level10_out.visibleProperty().bind(map10Button.hoverProperty().not());

        Level11_on.setMouseTransparent(true);
        Level11_on.visibleProperty().bind(map11Button.hoverProperty());
        Level11_out.visibleProperty().bind(map11Button.hoverProperty().not());

        Level12_on.setMouseTransparent(true);
        Level12_on.visibleProperty().bind(map12Button.hoverProperty());
        Level12_out.visibleProperty().bind(map12Button.hoverProperty().not());

        back_button_on.setMouseTransparent(true);
        back_button_on.visibleProperty().bind(backButton.hoverProperty());
        back_button_out.visibleProperty().bind(backButton.hoverProperty().not());
    }
}
