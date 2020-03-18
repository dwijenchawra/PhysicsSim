package org.openjfx;

import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;

public class DragTest {

    public static void main(Stage stage) {

        final double[] orgMousePressX = new double[1];
        final double[] orgMousePressY = new double[1];
        final double[] orgTranslateX = new double[1];
        final double[] orgTranslateY = new double[1];

        EventHandler<MouseEvent> circleOnMousePressedEventHandler =
                t -> {
                    orgMousePressX[0] = t.getSceneX();
                    orgMousePressY[0] = t.getSceneY();
                    orgTranslateX[0] = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY[0] = ((Circle)(t.getSource())).getTranslateY();
                };

        EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
                t -> {
                    double offsetX = t.getSceneX() - orgMousePressX[0];
                    double offsetY = t.getSceneY() - orgMousePressY[0];
                    double newTranslateX = orgTranslateX[0] + offsetX;
                    double newTranslateY = orgTranslateY[0] + offsetY;

                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);
                };

        EventHandler<MouseEvent> sphereOnMousePressedEventHandler =
                t -> {
                    orgMousePressX[0] = t.getSceneX();
                    orgMousePressY[0] = t.getSceneY();
                    orgTranslateX[0] = ((Sphere)(t.getSource())).getTranslateX();
                    orgTranslateY[0] = ((Sphere)(t.getSource())).getTranslateY();
                };

        EventHandler<MouseEvent> sphereOnMouseDraggedEventHandler =
                t -> {
                    double offsetX = t.getSceneX() - orgMousePressX[0];
                    double offsetY = t.getSceneY() - orgMousePressY[0];
                    double newTranslateX = orgTranslateX[0] + offsetX;
                    double newTranslateY = orgTranslateY[0] + offsetY;

                    ((Sphere) (t.getSource())).setTranslateX(newTranslateX);
                    ((Sphere) (t.getSource())).setTranslateY(newTranslateY);
                };

        Circle circle_Red = new Circle(50.0f, Color.GREEN);
        circle_Red.setCursor(Cursor.HAND);
        circle_Red.setOnMousePressed(circleOnMousePressedEventHandler);
        circle_Red.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        Circle circle_Green = new Circle(50.0f, Color.GREEN);
        circle_Green.setCursor(Cursor.MOVE);
        circle_Green.setCenterX(150);
        circle_Green.setCenterY(150);
        circle_Green.setOnMousePressed(circleOnMousePressedEventHandler);
        circle_Green.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        Circle circle_Blue = new Circle(50.0f, Color.GREEN);
        circle_Blue.setCursor(Cursor.CROSSHAIR);
        circle_Blue.setTranslateX(300);
        circle_Blue.setTranslateY(100);
        circle_Blue.setOnMousePressed(circleOnMousePressedEventHandler);
        circle_Blue.setOnMouseDragged(circleOnMouseDraggedEventHandler);

        Sphere s = new Sphere(50.0);
        s.setCursor(Cursor.CROSSHAIR);
        s.setOnMousePressed(sphereOnMousePressedEventHandler);
        s.setOnMouseDragged(sphereOnMouseDraggedEventHandler);

        //Drawing a Box
        Box box2 = new Box();

        //Setting the properties of the Box
        box2.setWidth(100.0);
        box2.setHeight(100.0);
        box2.setDepth(100.0);

        //Setting the position of the box
        box2.setTranslateX(200); //450
        box2.setTranslateY(100);//150
        box2.setTranslateZ(500);

        //Setting the drawing mode of the box
        box2.setDrawMode(DrawMode.FILL);



        Group root = new Group();
        root.getChildren().addAll(box2);

        Scene scene = new Scene(root, 400,350);


        //Create new Camera
        Camera camera = new PerspectiveCamera(true);
        scene.setFill(Color.SILVER);
        //Attach to scene
        scene.setCamera(camera);
        //Move back a little to get a good view of the sphere
        camera.translateZProperty().set(-500);
        //Set the clipping planes
        camera.setNearClip(1);
        camera.setFarClip(10000);
        stage.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            orgMousePressX[0] = event.getSceneX();
            orgMousePressY[0] = event.getSceneY();
            orgTranslateX[0] = camera.getTranslateX();
            orgTranslateY[0] = camera.getTranslateY();
        });
        stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double offsetX = orgMousePressX[0] - event.getSceneX();
            double offsetY = orgMousePressY[0] - event.getSceneY();
            double newTranslateX = orgTranslateX[0] + offsetX;
            double newTranslateY = orgTranslateY[0] + offsetY;

            camera.setTranslateX(newTranslateX);
            camera.setTranslateY(newTranslateY);
        });


        stage.setResizable(true);
        stage.setScene(scene);
        stage.setTitle("draggable");
        stage.show();
    }
}
