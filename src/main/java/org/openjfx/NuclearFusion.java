package org.openjfx;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class NuclearFusion {
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
    static ArrayList<ParticleWrapper> particles = new ArrayList<>();

    private static void buildScene() {
        root.getChildren().add(world);
    }

    private static void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-cameraDistance);
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);
    }

    private static void buildAxes() {
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

//        final PhongMaterial transparent = new PhongMaterial();
//        transparent.setDiffuseColor(Color.color(0.9, 0.9, 0.9, 0.1));

        final Box xAxis = new Box(universeSizeX * 2, 1, 1);
        final Box yAxis = new Box(1, universeSizeY * 2, 1);
        final Box zAxis = new Box(1, 1, universeSizeZ * 2);
//        final Box box = new Box(240, 240, 240);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);
//        box.setMaterial(transparent);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        world.getChildren().addAll(axisGroup);
    }

    private static void handleMouse(Scene scene, final Node root) {
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);

                double modifier = 1.0;
                double modifierFactor = 0.1;

                if (me.isControlDown()) {
                    modifier = 0.1;
                }
                if (me.isShiftDown()) {
                    modifier = 10.0;
                }
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0);  // +
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0);  // -
                } else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX * modifierFactor * modifier;
                    camera.setTranslateZ(newZ);
                } else if (me.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3);  // -
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3);  // -
                }
            }
        });
    }

    private static void particleCleanup() {
        for (int i = 0; i < particles.size(); i++) {
            ParticleWrapper p = particles.get(i);
            if (p.removal) {
                world.getChildren().remove(p.getSphere());
                particles.remove(i);
                i--;
            }
        }
    }

    /**
     * quark
     *
     */
    private static void updateParticles(Scene scene) {
        if (particles.size() == 0) {
            generateParticles(scene);
        } else {
            for (ParticleWrapper p : particles) {
                p.getSphere().setTranslateX(p.getSphere().getTranslateX() + (p.getSlopeX() * p.getSpeed()));
                p.getSphere().setTranslateY(p.getSphere().getTranslateY() + ((p.getSlopeY() * p.getSpeed())));
                p.getSphere().setTranslateZ(p.getSphere().getTranslateZ() + ((p.getSlopeZ() * p.getSpeed())));
                boundsChecker(p);
                collisionChecker(p, particles);
            }
        }
    }

    private static boolean collisionChecker(ParticleWrapper p, ArrayList<ParticleWrapper> particles) {
        for (ParticleWrapper particle : particles) {
            Sphere s1 = particle.getSphere();
            Sphere s2 = p.getSphere();
//            System.out.println(distance(s1, s2));
//            System.out.println(s1.getRadius() + s2.getRadius());
            if (!p.equals(particle)) {
                if (distance(s1, s2) < s1.getRadius() + s2.getRadius()) {
                    if (particle.getType().equals(p.getType())) {
                        System.out.println("Collided");
                        p.removal = true;
                        particle.removal = true;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static double distance(Sphere s1, Sphere s2) {
        return Math.sqrt(Math.pow(s1.getTranslateX() - s2.getTranslateX(), 2) + Math.pow(s1.getTranslateY() - s2.getTranslateY(), 2) + Math.pow(s1.getTranslateZ() - s2.getTranslateZ(), 2));
    }

    private static void boundsChecker(ParticleWrapper p) {
        if (Math.abs(p.getSphere().getTranslateX()) > universeSizeX) {
            p.setSlopeX(-p.getSlopeX());
        } else if (Math.abs(p.getSphere().getTranslateY()) > universeSizeY) {
            p.setSlopeY(-p.getSlopeY());
        } else if (Math.abs(p.getSphere().getTranslateZ()) > universeSizeZ) {
            p.setSlopeZ(-p.getSlopeZ());
        }
    }

    private static void generateParticles(Scene scene) {
        for (int i = 0; i < START_PARTICLES; i++) {
            addParticle(new ParticleWrapper(Particle.PROTON), scene);
            addParticle(new ParticleWrapper(Particle.NEUTRON), scene);
            addParticle(new ParticleWrapper(Particle.ELECTRON), scene);
        }
    }

    private static void addParticle(ParticleWrapper particleWrapper, Scene scene) {
        world.getChildren().addAll(particleWrapper.getSphere());
        particles.add(particleWrapper);
    }

    private static void handleKeyboard(Scene scene, final Node root) {
        final boolean moveCamera = true;
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Duration currentTime;
                switch (event.getCode()) {
                    case Z:
                        if (event.isShiftDown()) {
                            cameraXform.ry.setAngle(0.0);
                            cameraXform.rx.setAngle(0.0);
                            camera.setTranslateZ(-300.0);
                        }
                        cameraXform2.t.setX(0.0);
                        cameraXform2.t.setY(0.0);
                        break;
                    case X:
                        if (event.isControlDown()) {
                            if (axisGroup.isVisible()) {
                                axisGroup.setVisible(false);
                            } else {
                                axisGroup.setVisible(true);
                            }
                        }
                        break;
                    case S:
                        if (event.isControlDown()) {
                            if (moleculeGroup.isVisible()) {
                                moleculeGroup.setVisible(false);
                            } else {
                                moleculeGroup.setVisible(true);
                            }
                        }
                        break;
                    case SPACE:
                        if (timelinePlaying) {
                            timeline.pause();
                            timelinePlaying = false;
                        } else {
                            timeline.play();
                            timelinePlaying = true;
                        }
                        break;
                    case UP:
                        if (event.isControlDown() && event.isShiftDown()) {
                            cameraXform2.t.setY(cameraXform2.t.getY() - 10.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown() && event.isShiftDown()) {
                            cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 10.0 * ALT_MULTIPLIER);
                        } else if (event.isControlDown()) {
                            cameraXform2.t.setY(cameraXform2.t.getY() - 1.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown()) {
                            cameraXform.rx.setAngle(cameraXform.rx.getAngle() - 2.0 * ALT_MULTIPLIER);
                        } else if (event.isShiftDown()) {
                            double z = camera.getTranslateZ();
                            double newZ = z + 5.0 * SHIFT_MULTIPLIER;
                            camera.setTranslateZ(newZ);
                        }
                        break;
                    case DOWN:
                        if (event.isControlDown() && event.isShiftDown()) {
                            cameraXform2.t.setY(cameraXform2.t.getY() + 10.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown() && event.isShiftDown()) {
                            cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 10.0 * ALT_MULTIPLIER);
                        } else if (event.isControlDown()) {
                            cameraXform2.t.setY(cameraXform2.t.getY() + 1.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown()) {
                            cameraXform.rx.setAngle(cameraXform.rx.getAngle() + 2.0 * ALT_MULTIPLIER);
                        } else if (event.isShiftDown()) {
                            double z = camera.getTranslateZ();
                            double newZ = z - 5.0 * SHIFT_MULTIPLIER;
                            camera.setTranslateZ(newZ);
                        }
                        break;
                    case RIGHT:
                        if (event.isControlDown() && event.isShiftDown()) {
                            cameraXform2.t.setX(cameraXform2.t.getX() + 10.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown() && event.isShiftDown()) {
                            cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 10.0 * ALT_MULTIPLIER);
                        } else if (event.isControlDown()) {
                            cameraXform2.t.setX(cameraXform2.t.getX() + 1.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown()) {
                            cameraXform.ry.setAngle(cameraXform.ry.getAngle() - 2.0 * ALT_MULTIPLIER);
                        }
                        break;
                    case LEFT:
                        if (event.isControlDown() && event.isShiftDown()) {
                            cameraXform2.t.setX(cameraXform2.t.getX() - 10.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown() && event.isShiftDown()) {
                            cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 10.0 * ALT_MULTIPLIER);  // -
                        } else if (event.isControlDown()) {
                            cameraXform2.t.setX(cameraXform2.t.getX() - 1.0 * CONTROL_MULTIPLIER);
                        } else if (event.isAltDown()) {
                            cameraXform.ry.setAngle(cameraXform.ry.getAngle() + 2.0 * ALT_MULTIPLIER);  // -
                        }
                        break;
                }
            }
        });
    }

    public static void main(Stage stage) {
        buildScene();
        buildCamera();
        buildAxes();

        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.GREY);
        handleKeyboard(scene, world);
        handleMouse(scene, world);

        stage.setTitle("Molecule Sample Application");
        stage.setScene(scene);
        stage.show();

        scene.setCamera(camera);

        AnimationTimer animate = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                updateParticles(scene);
                System.out.println(particles.size());
                particleCleanup();
            }
        };
//        System.out.println("starting");
        animate.start();

    }
}
