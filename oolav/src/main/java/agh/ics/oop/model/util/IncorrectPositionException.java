package agh.ics.oop.model.util;

import agh.ics.oop.model.Vector2d;

public class IncorrectPositionException extends RuntimeException {
    public IncorrectPositionException(Vector2d vector) {
        super(String.format("Vector: (%d, %d) is outside the map bounds",vector.getX(),vector.getY()));
    }
}
