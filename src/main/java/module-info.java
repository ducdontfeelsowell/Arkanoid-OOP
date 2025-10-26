module org.example.arkanoid {
    requires javafx.controls;
    requires javafx.fxml;

    //requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
//    requires org.kordamp.bootstrapfx.core;
//    requires com.almasb.fxgl.all;
    requires javafx.base;
    requires javafx.graphics;
    requires jdk.compiler;
    requires jdk.jconsole;
    requires javafx.media;

    opens org.example.arkanoid.game to javafx.fxml;
    exports org.example.arkanoid.game;
    exports org.example.arkanoid.controller;
    exports org.example.arkanoid.input;
    exports org.example.arkanoid.object.Brick;
    exports org.example.arkanoid.object;
    opens org.example.arkanoid.controller to javafx.fxml;
    exports org.example.arkanoid.launch;
    opens org.example.arkanoid.launch to javafx.fxml;
}