package agh.ics.oop.model;

import agh.ics.oop.MapDirection;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2d, Set<Animal>> animals = new HashMap<>();
    final Vector2d topRightCorner;
    final Vector2d bottomLeftCorner = new Vector2d(0,0);
    private final Map<Vector2d, Grass> grasses = new HashMap<>();
    private int emptySquares;
    private final GrassGenerator grassGenerator;

    public GrassField(int width,int height, int chanceToGenerateInJungle) {
        if (width< 1 || height < 1){
            throw new IllegalArgumentException("Map dimensions must be positive");
        }
        topRightCorner = new Vector2d(width,height);
        final UUID uuid = UUID.randomUUID();
        this.emptySquares=width*height;
        this.grassGenerator = new GrassGenerator(this, getHeight(),getWidth(), chanceToGenerateInJungle);
    }

    public void remove(Animal animal){
        Vector2d vector2d = animal.getPosition();
        if (!animals.containsKey(vector2d) || animals.get(vector2d).contains(animal)){
            throw new IllegalArgumentException("This animal is not present at " + vector2d);
        }
        animals.get(vector2d).remove(animal);
        if (animals.get(vector2d).isEmpty() && !grasses.containsKey(vector2d)){
            emptySquares++;
        }

    }

    @Override
    public void place(Animal animal) {
        Vector2d vector2d = animal.getPosition();
        if (!animals.containsKey(vector2d))
            animals.put(vector2d, new HashSet<>());
        if (animals.get(vector2d).isEmpty() && grasses.containsKey(vector2d)){
            emptySquares--;
        }
        animals.get(vector2d).add(animal);
        notifyMapChanged("Animal placed at: " + animal.getPosition(), animal.getPosition());
    }

    public Animal move(Animal animal){
        Vector2d vectorAnimal = animal.pos;
        MapDirection mapDirection = animal.getWhatGenCurrentlyOn();
        switch (mapDirection){
            case North -> animal.facing = animal.facing.turnIntTimes(0);
            case NorthEast -> animal.facing = animal.facing.turnIntTimes(1);
            case East -> animal.facing = animal.facing.turnIntTimes(2);
            case SouthEast -> animal.facing = animal.facing.turnIntTimes(3);
            case South -> animal.facing = animal.facing.turnIntTimes(4);
            case SouthWest -> animal.facing = animal.facing.turnIntTimes(5);
            case West -> animal.facing = animal.facing.turnIntTimes(6);
            case NorthWest -> animal.facing = animal.facing.turnIntTimes(7);
        }
        animal.pos = animal.pos.add(animal.facing.toUnitVector());
        if (vectorAnimal.getX()>topRightCorner.getX()){
            vectorAnimal= new Vector2d(0,vectorAnimal.getY());
        }
        if (vectorAnimal.getX()<0){
            vectorAnimal= new Vector2d(topRightCorner.getX(), vectorAnimal.getY());
        }
        if (vectorAnimal.getY()> topRightCorner.getY()){
            vectorAnimal = new Vector2d(vectorAnimal.getX(), topRightCorner.getY());
            animal.facing = animal.facing.turnAround();
        }
        if (vectorAnimal.getY()<0){
            vectorAnimal = new Vector2d(vectorAnimal.getX(),0);
            animal.facing = animal.facing.turnAround();
        }
        animals.remove(animal.pos);
        animal.pos = vectorAnimal;
        animal.changeEnergy(-energyLoss);
        return animal;
    }

    public void place(Grass grass){
        Vector2d vector2d = grass.getPosition();
        if (grasses.containsKey(vector2d))
            throw new IllegalArgumentException("grass is already there");
        grasses.put(vector2d,grass);
        if (!animals.containsKey(vector2d) || animals.get(vector2d).isEmpty()){
            emptySquares--;
        }
        notifyMapChanged("grass placed at: "+vector2d, vector2d);
    }

    public void remove(Grass grass){
        Vector2d vector2d = grass.getPosition();
        if (!grasses.containsKey(vector2d)){
            throw new IllegalArgumentException("There is no grass at " + vector2d);
        }
        grasses.remove(vector2d);
        if (!animals.containsKey(vector2d) || animals.get(vector2d).isEmpty()){
            emptySquares++;
        }
        notifyMapChanged("grass removed from: "+vector2d, vector2d);
    }

    @Override
    public Optional<WorldElement> objectAt(Vector2d position) {
//        if (super.objectAt(position).isPresent()) {
//            return super.objectAt(position);
//        }
        return Optional.of(grasses.get(position));
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grasses.containsKey(position);
    }

    public Boundary getCurrentBounds() {
        return new Boundary(bottomLeftCorner, topRightCorner);
    }

    public Set<Vector2d> getAnimalPositions(){
        return animals.keySet().stream()
                .filter(position -> !animals.get(position).isEmpty())
                .collect(Collectors.toSet());
    }

    public Set<Animal> getAnimalsAt(Vector2d vector2d){
        return Collections.unmodifiableSet(animals.getOrDefault(vector2d, new HashSet<>()));
    }

    public boolean isAnimalAt(Vector2d position){
        return animals.containsKey(position) && !animals.get(position).isEmpty();
    }

    public List<Grass> getGrasses(){
        return List.copyOf(grasses.values());
    }

    public int numberOfGrasses(){
        return grasses.size();
    }

    public int numberOfAnimals(){
        return Math.toIntExact(animals.keySet().stream()
                .filter(position -> !animals.get(position).isEmpty())
                .count());
    }

    public int getEmptySquares(){
        return emptySquares;
    }

    public boolean grassAt(Vector2d position){
        return grasses.containsKey(position);
    }

    @Override
    public int getWidth() {
        return topRightCorner.getX() - bottomLeftCorner.getX();
    }



    public void generateGrass(int numberOfGrassToGenerate, int chanceToGenerateInTheJungle){
        for (int i = 0; i < numberOfGrassToGenerate; i++) {
            place(grassGenerator.generateGrass());
        }
    }



}
