package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation extends AnimationTimer implements  Runnable {
    List<Animal> animals = new ArrayList<Animal>();
    List<Animal> deadAnimals = new ArrayList<>();
    List<Animal> allAnimals = new ArrayList<>();
    GrassField worldMap;
    private final int numberOfGrassEachDayValue;
    int i =0;
    private int day = 0;
    private ScheduledExecutorService executorService;
    private int speedOfSimulation = 1;
    private final List<MapChangeListener> listeners = new ArrayList<>();
    private final int energyPerGrassEatenValue;
    private long FRAMES_PER_SECOND=50L;
    private long INTERVAL=10000000L / FRAMES_PER_SECOND;
    private long last = 0;
    private final int chanceToGenerateGrassInTheJungle;
    private final GrassGenerator grassGenerator;

    public Simulation(GrassField map, int numberOfGrassAtTheStartValue, int numberOfGrassEachDayValue,
                      int startingNumberOfAnimalsValue, int startEnergyOfAnimalsValue,int gnomesValue,int maxEnergyValue,
                      int energyPerGrassEatenValue, int chanceToGenerateGrassInTheJungle, int jungleHeightValue) {
        this.worldMap = map;
        this.numberOfGrassEachDayValue=numberOfGrassEachDayValue;
        this.energyPerGrassEatenValue=energyPerGrassEatenValue;
        this.chanceToGenerateGrassInTheJungle=chanceToGenerateGrassInTheJungle;
        this.grassGenerator = new GrassGenerator(map,jungleHeightValue,map.getWidth(),chanceToGenerateGrassInTheJungle);
        if (numberOfGrassAtTheStartValue>map.getCurrentBounds().topRightEdgeOfTheMap().getY()*map.getCurrentBounds().topRightEdgeOfTheMap().getX()){
            throw new IllegalArgumentException("Initial number of grass greater then map size");
        }
        map.generateGrass(numberOfGrassAtTheStartValue,chanceToGenerateGrassInTheJungle);
        Vector2d mapSize = new Vector2d(map.getCurrentBounds().topRightEdgeOfTheMap().getX(),map.getCurrentBounds().topRightEdgeOfTheMap().getY());
        for(int i=0; i<startingNumberOfAnimalsValue;i++){
            Animal generatedAnimal = new Animal(mapSize,gnomesValue,maxEnergyValue,startEnergyOfAnimalsValue,day);
            animals.add(generatedAnimal);
            this.worldMap.place(generatedAnimal);
            allAnimals.add(generatedAnimal);
        }
    }
    public void run(){
        System.out.println("aaaaa");
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
        executorService.scheduleWithFixedDelay(this,500/speedOfSimulation,
                1000/speedOfSimulation, TimeUnit.MILLISECONDS);
    }

    public void setSpeedOfSimulation(int speed){
        this.speedOfSimulation = speed;
        if (isRunning()) {
            executorService.shutdown();
            start();
        }
    }


    @Override
    public void handle(long now) {
        if (now - last > INTERVAL){
            run();
            last = now;
        }
    }

    private void removeDeadAnimals(){
        for (Animal animal : animals){
            if (animal.getEnergy()==0){
                worldMap.remove(animal);
                animal.setDeathDay(day);
                deadAnimals.add(animal);
            }
        }
        animals.removeIf(animal -> !animal.isAlive());
    }

    private void moveAnimal(){
        for (Animal animal : animals){
            Animal animal1 = worldMap.move(animal);
            animal=animal1;
            animals.set(animals.indexOf(animal), animal1);
        }
    }

    private void eatGrass(){
        List<Grass> grasses = worldMap.getGrasses();
        for (Grass grass : grasses){
            Set<Animal> animalsOnGrass = worldMap.getAnimalsAt(grass.getPosition());
            if (animalsOnGrass.isEmpty()){
                continue;
            }
            Animal animal = animalsOnGrass.stream().findFirst().get();
            animal.changeEnergy(energyPerGrassEatenValue);
            worldMap.remove(grass);
        }
    }

    private void animalReproduction(){

    }

    private void generateGrass(){
        int toGenerate = Math.min(numberOfGrassEachDayValue,worldMap.getWidth()* worldMap.getHeight() - worldMap.numberOfGrasses());
        for (int i = 0; i < toGenerate; i++)
            worldMap.place(grassGenerator.generateGrass());
    }

    public double getAverageAnimalLiveSpan(){
        return deadAnimals.stream().mapToDouble(Animal::getLiveSpan).average().orElse(0);
    }

    public double getAverageAnimalChildrenNumber(){
        return allAnimals.stream().mapToDouble(Animal::getNumberOfChildren).average().orElse(0);
    }

    public double getAverageEnergy(){
        return animals.stream().mapToDouble(Animal::getEnergy).average().orElse(0);
    }

    List<Animal> getAnimals() {
        return new ArrayList<Animal>(animals);
    }

    public static boolean getTruePercentOfTheTime(int percent){
        if (percent > 100 || percent < 0){
            throw new IllegalArgumentException("percent chance must be within [0,100]");
        }
        return Math.random() < (double) (percent /100);
    }
}
