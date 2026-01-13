package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation implements  Runnable {
    List<Animal> animals = new ArrayList<Animal>();
    GrassField worldMap;
    private int numberOfGrassEachDayValue;
    int i =0;
    private int day = 0;
    private ScheduledExecutorService executorService;
    private int speedOfSimulation = 1;
    private final List<MapChangeListener> listeners = new ArrayList<>();

    public Simulation(GrassField map, int numberOfGrassAtTheStartValue, int numberOfGrassEachDayValue,
                      int startingNumberOfAnimalsValue, int startEnergyOfAnimalsValue,int gnomesValue,int maxEnergyValue) {
        this.worldMap = map;
        this.numberOfGrassEachDayValue=numberOfGrassEachDayValue;
        if (numberOfGrassAtTheStartValue>map.getCurrentBounds().topRightEdgeOfTheMap().getY()*map.getCurrentBounds().topRightEdgeOfTheMap().getX()){
            throw new IllegalArgumentException("Initial number of grass greater then map size");
        }
        map.generateGrass(numberOfGrassAtTheStartValue);
        Vector2d mapSize = new Vector2d(map.getCurrentBounds().topRightEdgeOfTheMap().getX(),map.getCurrentBounds().topRightEdgeOfTheMap().getY());
        for(int i=0; i<startingNumberOfAnimalsValue;i++){
            Animal generatedAnimal = new Animal(mapSize,gnomesValue,maxEnergyValue,startEnergyOfAnimalsValue);
            animals.add(generatedAnimal);
            this.worldMap.place(generatedAnimal);
        }
    }
    public void run(){
        removeDeadAnimals();
        moveAnimal();
        eatGrass();
        animalReproduction();
        generateGrass();
        day++;
    }

    private boolean isRunning(){
        return executorService != null && !executorService.isShutdown();
    }

    public void start(){
        if (isRunning()) throw new RuntimeException("Simulation is already running");
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(this,500/speedOfSimulation,1000/speedOfSimulation, TimeUnit.MILLISECONDS);
    }

    public void setSpeedOfSimulation(int speed){
        this.speedOfSimulation = speed;
        if (isRunning()) {
            executorService.shutdown();
            start();
        }
    }

    private void removeDeadAnimals(){
        for (Animal animal : animals){
            if (animal.getEnergy()==0){
                worldMap.remove(animal);
                animal.setDeathDay(day);
            }
        }
        animals.removeIf(animal -> !animal.isAlive());
    }

    private void moveAnimal(){
        for (Animal animal : animals){
            worldMap.move(animal);
        }
    }

    private void eatGrass(){

    }

    private void animalReproduction(){

    }

    private void generateGrass(){

    }


    List<Animal> getAnimals() {
        return new ArrayList<Animal>(animals);
    }
}
