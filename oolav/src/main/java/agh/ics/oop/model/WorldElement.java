package agh.ics.oop.model;

public interface WorldElement {
    public Vector2d getPosition();

    void setPos(Vector2d position);

    boolean isAT(Vector2d position);
}
