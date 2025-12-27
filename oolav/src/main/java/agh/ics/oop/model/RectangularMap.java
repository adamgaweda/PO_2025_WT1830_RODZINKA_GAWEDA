package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.UUID;

public class RectangularMap extends AbstractWorldMap {
    final private Vector2d topEdgeOfTheMap;
    final private Vector2d bottomLeftEdgeOfTheMap = new Vector2d(0,0);

    public RectangularMap(int width, int height) {
        this.topEdgeOfTheMap = new Vector2d(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(bottomLeftEdgeOfTheMap) && position.precedes(topEdgeOfTheMap) && super.canMoveTo(position);
    }

    public Boundary getCurrentBounds() {
        return new  Boundary(bottomLeftEdgeOfTheMap, topEdgeOfTheMap);
    }

}
