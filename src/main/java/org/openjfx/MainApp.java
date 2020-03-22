package org.openjfx;

import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage stage) {
        NuclearFusion.main(stage);
    }

}
