package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;

public class ParticleWrapper {
    private Sphere sphere;
    private double speed;
    private Particle type;
    private Color color;
    private Text t;
    public boolean removal;
    private Xform molecule;

    final PhongMaterial p = new PhongMaterial();
    private double slopeX = Math.random();
    private double slopeY = Math.random();
    private double slopeZ = Math.random();

    public ParticleWrapper(Particle particle) {
        this.type = particle;

        switch (particle) {
            case ELECTRON:
                this.speed = 10;
                p.setSpecularColor(Color.BLUE);
                p.setDiffuseColor(Color.BLUE);
                break;
            case GAMMA:
                this.speed = 20;
                p.setSpecularColor(Color.YELLOW);
                p.setDiffuseColor(Color.YELLOW);
                break;
            case PROTON:
                this.speed = 5;
                p.setSpecularColor(Color.RED);
                p.setDiffuseColor(Color.RED);
                break;
            case NEUTRON:
                this.speed = 5;
                p.setSpecularColor(Color.GRAY);
                p.setDiffuseColor(Color.GRAY);
                break;
            case NEUTRINO:
                this.speed = 30;
                p.setSpecularColor(Color.SILVER);
                p.setDiffuseColor(Color.SILVER);
                break;
            case HYDROGEN:
                Sphere sphere1 = new Sphere();
                Sphere sphere2 = new Sphere();
                molecule.getChildren().addAll(sphere1, sphere2);
            case DEUTERIUM:
        }
        this.sphere = new Sphere(1, 32);

        sphere.setTranslateX(Math.random() * NuclearFusion.universeSizeX * getPolarity());
        sphere.setTranslateY(Math.random() * NuclearFusion.universeSizeY * getPolarity());
        sphere.setTranslateZ(Math.random() * NuclearFusion.universeSizeZ * getPolarity());

        sphere.setMaterial(p);

        this.speed /= 10;
    }

    private int getPolarity() {
        if (Math.random() > 0.5) {
            return 1;
        } else {
            return -1;
        }
    }

    public Sphere getSphere() {
        return sphere;
    }

    public double getSpeed() {
        return speed;
    }

    public Color getColor() {
        return color;
    }

    public double getSlopeX() {
        return slopeX;
    }

    public double getSlopeY() {
        return slopeY;
    }

    public double getSlopeZ() {
        return slopeZ;
    }

    public void setSlopeX(double slopeX) {
        this.slopeX = slopeX;
    }

    public void setSlopeY(double slopeY) {
        this.slopeY = slopeY;
    }

    public void setSlopeZ(double slopeZ) {
        this.slopeZ = slopeZ;
    }

    public Particle getType() {
        return type;
    }
}
