package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements  Runnable {
    List<Animal> animal = new ArrayList<Animal>();
    List<MoveDirection> moveDirections;
    WorldMap worldMap;
    public Simulation(List<Vector2d> vector, List<MoveDirection> directions, WorldMap map) {
        for (Vector2d vector_zwierza : vector) {
            Animal temp_animal = new Animal(vector_zwierza);
                try {
                    map.place(temp_animal);
                    animal.add(temp_animal);
                } catch (IncorrectPositionException e) {
                    e.printStackTrace();
                }
        }
        this.moveDirections = directions;
        this.worldMap = map;
    }
    public void run(){
        int i=0;

        for (MoveDirection direction : moveDirections) {
            Animal temp_animal=animal.get(i%animal.size());
            worldMap.move(temp_animal,direction);
            animal.set(i%animal.size(),temp_animal);
            i++;
        }
    }
    List<Animal> getAnimals() {
        return new ArrayList<Animal>(animal);
    }
}
