package agh.ics.oop.model;


import agh.ics.oop.MapDirection;
import agh.ics.oop.model.util.IncorrectPositionException;
import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    private final Map<Vector2d, Animal> animals = new HashMap<>(1);
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    private final UUID uuid =  UUID.randomUUID();
    public int energyLoss;

    public void changeEnergyLoss(int energyLost) {
        this.energyLoss = energyLost;
    }

    public int getEnergyLoss(){
        return energyLoss;
    }

    abstract public Boundary getCurrentBounds();

    public void addObserver(MapChangeListener mapChangeListener) {
        mapChangeListeners.add(mapChangeListener);
    }

    public void removeObserver(MapChangeListener mapChangeListener) {
        mapChangeListeners.remove(mapChangeListener);
    }

    protected void notifyMapChanged(String message, Vector2d vector2d) {
        for (MapChangeListener mapChangeListener : mapChangeListeners) {
            mapChangeListener.mapChanged(this, message, vector2d);
        }
    }

    @Override
    public int getHeight() {
        return getCurrentBounds().getHeight();
    }

    @Override
    public int getWidth(){
        return getCurrentBounds().getWidth();
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException{
        animals.put(animal.getPosition(), animal);
        notifyMapChanged("Animal placed at: " + animal.getPosition(), animal.getPosition());
    }

    @Override
    public void move(Animal animal, MapDirection mapDirection) {
        Vector2d vectorAnimal = animal.pos;
        vectorAnimal.add(mapDirection.toUnitVector());
        Vector2d topRight = getCurrentBounds().topRightEdgeOfTheMap();
        if (vectorAnimal.getX()>topRight.getX()){
            vectorAnimal= new Vector2d(0,vectorAnimal.getY());
        }
        if (vectorAnimal.getX()<0){
            vectorAnimal= new Vector2d(topRight.getX(), vectorAnimal.getY());

        }
        if (vectorAnimal.getY()> topRight.getY()){
            vectorAnimal = new Vector2d(vectorAnimal.getX(), topRight.getY());
            animal.facing = animal.facing.turnAround();
        }
        if (vectorAnimal.getY()<0){
            vectorAnimal = new Vector2d(vectorAnimal.getX(),0);
            animal.facing = animal.facing.turnAround();
        }
        animals.remove(animal.pos);
        animal.pos = vectorAnimal;
        animal.changeEnergy(-energyLoss);
        if (animal.energy>-1){
            place(animal);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.get(position) != null;
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
       if (animals.get(position) == null) {
           return Optional.empty();
       }
       return Optional.of(animals.get(position));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return true;
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

    @Override
    public List<Animal> getOrderedAnimals(){
        return animals.values().stream()
                .sorted(Comparator.comparing((Animal animal) -> animal.getPosition().getX())
                        .thenComparing(animal -> animal.getPosition().getY())).toList()
                ;
    }
}
