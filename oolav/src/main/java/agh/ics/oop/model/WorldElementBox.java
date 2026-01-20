package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;


public class WorldElementBox{
    private static final int imageSize = 20;
    private static Map<String,Image> imageCache = new HashMap<String,Image>();
    private final WorldElement element;
    private Image image;
    private String text;


    public WorldElementBox(WorldElement element) {
        this.element = element;
        String imageName= element.getImage();
        this.image = getFromCache(imageName);
        if(element instanceof Animal){
            text=element.toString();
        }
    }

    private Image getFromCache(String name){
        return imageCache.computeIfAbsent(name,image -> new Image(name));
    }

    public void draw(GraphicsContext gc, double x, double y, double width, double height){
        gc.drawImage(image,x,y,width,height);
        gc.fillText(
                text,
                x,
                y+height
        );

    }
}
