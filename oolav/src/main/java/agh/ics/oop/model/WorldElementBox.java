package agh.ics.oop.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class WorldElementBox {
    private static final int imageSize = 20;
    private VBox container = new VBox();

    public WorldElementBox(WorldElement element) {
        Image image = new Image(element.getImage());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSize);
        imageView.setFitHeight(imageSize);
        imageView.setPreserveRatio(true);
        Label label = new Label (element.getPosition().toString());


    }

    public void draw(GraphicsContext gc){

    }
}
