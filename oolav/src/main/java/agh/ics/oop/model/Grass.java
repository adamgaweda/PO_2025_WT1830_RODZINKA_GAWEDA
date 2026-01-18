package agh.ics.oop.model;

public class Grass implements WorldElement {
    Vector2d position;
    private boolean isCandy=false;
    public  Grass(Vector2d position){
        this.position=position;
    }
    public Grass(Vector2d position, boolean isCandy){
        this.position=position;
        this.isCandy=isCandy;
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

    public boolean isCandy() {
        return isCandy;
    }

    @Override
    public boolean isAT(Vector2d position) {
        return position.equals(this.position);
    }


    @Override
    public String getImage(){
        if(isCandy){return "animal.jpg";}
        return "grass.png";
    }

}
