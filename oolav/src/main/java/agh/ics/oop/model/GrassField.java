package agh.ics.oop.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.*;

public class GrassField extends AbstractWorldMap {
    final Vector2d topLeftCorner;
    Map<Vector2d, Grass> grass = new HashMap<>();

    public GrassField(int liczba) {
        final int numberOfGrass = (int) sqrt(liczba*10) + 1;
        topLeftCorner = new Vector2d(numberOfGrass,numberOfGrass);
        final UUID uuid = UUID.randomUUID();
        generateGrass(numberOfGrass);
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
        if (super.objectAt(position).isPresent()) {
            return super.objectAt(position);
        }
        return Optional.of(grass.get(position));
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
//        List<WorldElement> elements = super.getElements();
//        elements.addAll(new ArrayList<>(grass.values()));
//        return elements;
        return Stream.concat(super.getElements().stream(), grass.values().stream())
                .collect(Collectors.toList());
    }


    public void generateGrass(int numberOfGrassToGenerate){
        for (int i = 0; i < numberOfGrassToGenerate; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, topLeftCorner.getX()* topLeftCorner.getY() + 1);
            Vector2d grassPosition = new Vector2d(randomNum/ topLeftCorner.getX(), randomNum% topLeftCorner.getY());
            while (isOccupied(grassPosition)) {
                randomNum = ThreadLocalRandom.current().nextInt(0, topLeftCorner.getX()* topLeftCorner.getY() + 1);
                grassPosition = new Vector2d(randomNum/ topLeftCorner.getX(), randomNum% topLeftCorner.getY());
            }
            grass.put(grassPosition, new Grass(grassPosition));
        }
    }



}
