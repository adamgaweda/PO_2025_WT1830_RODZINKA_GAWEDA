package agh.ics.oop.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;


public class WorldElementBox {
    private static final int imageSize = 20;
    private static Map<String,Image> imageCache = new HashMap<String,Image>();
    private VBox container = new VBox();

    public WorldElementBox(WorldElement element) {
        String imageName= element.getImage();
        Image image = getFromCache(imageName);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(imageSize);
        imageView.setFitHeight(imageSize);
        imageView.setPreserveRatio(true);
        Label label = new Label (element.toString());
    }

    private Image getFromCache(String name){
        return imageCache.computeIfAbsent(name,image -> new Image(name));
    }

    public void draw(GraphicsContext gc){

    }
}
