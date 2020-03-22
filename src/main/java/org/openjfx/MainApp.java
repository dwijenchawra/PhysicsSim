package org.openjfx;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.stage.Stage;


public class MainApp extends Application {
    static int universeSizeX = 60;
    static int universeSizeY = 60;
    static int universeSizeZ = 60;

    static final Group root = new Group();
    static final Group axisGroup = new Group();
    static final Xform world = new Xform();
    static final PerspectiveCamera camera = new PerspectiveCamera(true);
    static final Xform cameraXform = new Xform();
    static final Xform cameraXform2 = new Xform();
    static final Xform cameraXform3 = new Xform();
    static final double cameraDistance = 450;
    static final Xform moleculeGroup = new Xform();
    private static final int START_PARTICLES = 333;
    static private Timeline timeline;
    static boolean timelinePlaying = false;
    static double ONE_FRAME = 1.0 / 24.0;
    static double DELTA_MULTIPLIER = 200.0;
    static double CONTROL_MULTIPLIER = 0.1;
    static double SHIFT_MULTIPLIER = 0.1;
    static double ALT_MULTIPLIER = 0.5;
    static double mousePosX;
    static double mousePosY;
    static double mouseOldX;
    static double mouseOldY;
    static double mouseDeltaX;
    static double mouseDeltaY;

    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage stage) {
        NuclearFusion.main(stage);
    }

}
