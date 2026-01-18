package agh.ics.oop.model;

public record Boundary(Vector2d bottomLeftEdgeOfTheMap, Vector2d topRightEdgeOfTheMap) {
    public int getHeight(){return topRightEdgeOfTheMap().getY()-bottomLeftEdgeOfTheMap.getY()+1;}
    public int getWidth(){return topRightEdgeOfTheMap().getX()- bottomLeftEdgeOfTheMap.getX()+1;}
}
