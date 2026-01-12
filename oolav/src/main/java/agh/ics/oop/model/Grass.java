package agh.ics.oop.model;

public class Grass implements WorldElement {
    Vector2d position;
    public  Grass(Vector2d position){
        this.position=position;
    }

    public Vector2d getPosition(){
        return position;
    }
    @Override
    public void setPos(Vector2d position){
        this.position=position;
    }
    @Override
    public String toString() {
        return "Trawa";
    }

    @Override
    public boolean isAT(Vector2d position) {
        return position.equals(this.position);
    }

    @Override
    public String getImage(){
        return "grass.png";
    }
}
