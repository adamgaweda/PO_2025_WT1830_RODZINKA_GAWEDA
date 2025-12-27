package agh.ics.oop.model;

import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.*;

public class GrassField extends AbstractWorldMap {
    final Vector2d vectorGrass;
    Map<Vector2d, Grass> grass = new HashMap<>();


    public GrassField(int liczba) {
        final int intTraw = (int) sqrt(liczba*10) + 1;
        final UUID uuid = UUID.randomUUID();
        vectorGrass = new  Vector2d(intTraw, intTraw);
        for (int i = 0; i < liczba; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, intTraw*intTraw + 1);
            Vector2d grassPosition = new Vector2d(randomNum/intTraw, randomNum%intTraw);
            while (isOccupied(grassPosition)) {
                randomNum = ThreadLocalRandom.current().nextInt(0, intTraw^2 + 1);
                grassPosition = new Vector2d(randomNum/intTraw, randomNum%intTraw);
            }
            grass.put(grassPosition, new Grass(grassPosition));
        }
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        if (super.objectAt(position) != null) {
            return super.objectAt(position);
        }
        return grass.get(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grass.containsKey(position);
    }

    public Boundary getCurrentBounds() {
        List<WorldElement> elements = getElements();
        if  (elements.isEmpty())
            return new Boundary(new Vector2d(0,0), new Vector2d(0,0));
        Vector2d bottomLeftCorner=elements.getFirst().getPosition();
        Vector2d topRightCorner=elements.getLast().getPosition();
        for (WorldElement element : elements) {
            bottomLeftCorner=bottomLeftCorner.lowerLeft(element.getPosition());
            topRightCorner=topRightCorner.upperRight(element.getPosition());
        }
        return new Boundary(bottomLeftCorner, topRightCorner);
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> elements = super.getElements();
        elements.addAll(new ArrayList<>(grass.values()));
        return elements;
    }




}
