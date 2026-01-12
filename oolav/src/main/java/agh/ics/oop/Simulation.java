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
    WorldMap worldMap;
    int i =0;
    public Simulation(List<Animal> animals, WorldMap map) {
        animal = animals;
        this.worldMap = map;
    }
    public void run(){
//        for (MoveDirection direction : moveDirections) {
//            Animal temp_animal=animal.get(i%animal.size());
//            worldMap.move(temp_animal,direction);
//            animal.set(i%animal.size(),temp_animal);
//            i++;
//        }
        for (Animal animal1: animal){
            worldMap.move(animal1,animal1.getWhatGenCurentlyOn());
            i++;
        }
    }
    List<Animal> getAnimals() {
        return new ArrayList<Animal>(animal);
    }
}
