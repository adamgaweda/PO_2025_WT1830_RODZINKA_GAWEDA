package agh.ics.oop.model;


import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    private final Map<Vector2d, Animal> animals = new HashMap<>(1);
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    private final UUID uuid =  UUID.randomUUID();


    abstract public Boundary getCurrentBounds();

    public void addObserver(MapChangeListener mapChangeListener) {
        mapChangeListeners.add(mapChangeListener);
    }

    public void removeObserver(MapChangeListener mapChangeListener) {
        mapChangeListeners.remove(mapChangeListener);
    }

    protected void notifyMapChanged(String message) {
        for (MapChangeListener mapChangeListener : mapChangeListeners) {
            mapChangeListener.mapChanged(this, message);
        }
    }
    @Override
    public void place(Animal animal) throws IncorrectPositionException{
        if (canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            notifyMapChanged("Animal placed at: " + animal.getPosition());
        } else throw new IncorrectPositionException(animal.getPosition());
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        Vector2d vectorAnimal = animal.pos;
        switch (direction) {
            case FORWARD -> vectorAnimal = vectorAnimal.add(animal.facing.toUnitVector());
            case BACKWARD -> vectorAnimal = vectorAnimal.sub(animal.facing.toUnitVector());
            case LEFT-> animal.facing = animal.facing.prev();
            case RIGHT -> animal.facing = animal.facing.next();
        }
        if (canMoveTo(vectorAnimal)) {
            animals.remove(animal.pos);
            animal.pos = vectorAnimal;
            animals.put(vectorAnimal, animal);
        }

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !isOccupied(position);
    }

    @Override
    public List<WorldElement> getElements() {
        return new ArrayList<>(animals.values());
    }

    @Override
    public String toString() {
        Boundary boundary = getCurrentBounds();
        return mapVisualizer.draw(boundary.bottomLeftEdgeOfTheMap(), boundary.topRightEdgeOfTheMap());
    }

    @Override
    public UUID getId() {
        return this.uuid;
    }
}
