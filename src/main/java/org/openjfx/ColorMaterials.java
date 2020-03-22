package org.openjfx;

import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class ColorMaterials {
    public static PhongMaterial RED = new PhongMaterial();

    public static final PhongMaterial BLUE = new PhongMaterial();
    public static final PhongMaterial GREEN = new PhongMaterial();
    public static final PhongMaterial YELLOW = new PhongMaterial();
    public static final PhongMaterial GRAY = new PhongMaterial();
    public static final PhongMaterial ORANGE = new PhongMaterial();

    public ColorMaterials() {
        RED.setSpecularColor(Color.RED);
        RED.setDiffuseColor(Color.RED);
        BLUE.setSpecularColor(Color.RED);
        BLUE.setDiffuseColor(Color.RED);
        YELLOW.setSpecularColor(Color.RED);
        YELLOW.setDiffuseColor(Color.RED);
        GRAY.setSpecularColor(Color.RED);
        GRAY.setDiffuseColor(Color.RED);
        GREEN.setSpecularColor(Color.RED);
        GREEN.setDiffuseColor(Color.RED);
        ORANGE.setSpecularColor(Color.RED);
        ORANGE.setDiffuseColor(Color.RED);
    }
}
