package agh.ics.oop.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class WorldElementBox {
    private static final int imageSize = 20;

    public WorldElementBox(WorldElement element) {
        Image image = new Image(element.getImage());

    }

    public void draw(GraphicsContext gc){

    }
}
