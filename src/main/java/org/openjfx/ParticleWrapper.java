package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Text;

public class ParticleWrapper {
    private Sphere sphere;
    private double speed;
    private Particle type;
    private ShapeType shapeType;
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
                generateOneSphere();
                this.speed = 10;
                p.setSpecularColor(Color.BLUE);
                p.setDiffuseColor(Color.BLUE);
                break;
            case GAMMA:
                generateOneSphere();
                this.speed = 20;
                p.setSpecularColor(Color.YELLOW);
                p.setDiffuseColor(Color.YELLOW);
                break;
            case PROTON:
                generateOneSphere();
                this.speed = 5;
                p.setSpecularColor(Color.RED);
                p.setDiffuseColor(Color.RED);
                break;
            case NEUTRON:
                generateOneSphere();
                this.speed = 5;
                p.setSpecularColor(Color.GRAY);
                p.setDiffuseColor(Color.GRAY);
                break;
            case NEUTRINO:
                generateOneSphere();
                this.speed = 30;
                p.setSpecularColor(Color.SILVER);
                p.setDiffuseColor(Color.SILVER);
                break;
            case HYDROGEN:
                generateComplexParticle(Particle.HYDROGEN);
            case DEUTERIUM:
        }

        this.speed /= 10;
    }

    private void generateOneSphere() {
        this.sphere = new Sphere(1, 32);

        sphere.setTranslateX((Math.random() * NuclearFusion.universeSizeX - 1) * getPolarity());
        sphere.setTranslateY((Math.random() * NuclearFusion.universeSizeY - 1) * getPolarity());
        sphere.setTranslateZ((Math.random() * NuclearFusion.universeSizeZ - 1) * getPolarity());

        sphere.setMaterial(p);

        this.shapeType = ShapeType.SIMPLE;
    }

    private void generateComplexParticle(Particle type) {
        switch (type) {
            case HYDROGEN:

        }

        this.shapeType = ShapeType.COMPLEX;
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
