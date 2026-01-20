package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;


public class WorldElementBox{
    private static final int imageSize = 20;
    private static Map<String,Image> imageCache = new HashMap<String,Image>();
    private final WorldElement element;
    private Image image;
    private String text;
    private double factor;
    private boolean isAnimal=false;


    public WorldElementBox(WorldElement element) {
        this.element = element;
        String imageName= element.getImage();
        this.image = getFromCache(imageName);
        if(element instanceof Animal){
            text=element.toString();
            factor=((Animal) element).getEnergyfactor();
            isAnimal=true;
        }
    }

    private Image getFromCache(String name){
        return imageCache.computeIfAbsent(name,image -> new Image(name));
    }

    public void draw(GraphicsContext gc, double x, double y, double width, double height){
        if(isAnimal){
            double red = 1.0-factor;
            double green = factor;
            double blue =0;
            Color kolor = new Color(red,green,blue,1);

            gc.setFill(kolor);
            gc.fillRoundRect(x,y+height,width,height/20,5,5);
        }
        gc.drawImage(image,x,y,width,height);
        gc.setFill(Color.BLACK);
        gc.fillText(
                text,
                x,
                y+height
        );

    }
}
