package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.IncorrectPositionException;
import javafx.animation.AnimationTimer;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static java.lang.Math.min;

public class Simulation extends AnimationTimer implements  Runnable {
    List<Animal> animals = new ArrayList<Animal>();
    List<Animal> deadAnimals = new ArrayList<>();
    List<Animal> allAnimals = new ArrayList<>();
    GrassField worldMap;
    private final int numberOfGrassEachDayValue;
    int i =0;
    private int day = 0;
    private final List<MapChangeListener> listeners = new ArrayList<>();
    private final int energyPerGrassEatenValue;
    private long FRAMES_PER_SECOND=50L;
    private long INTERVAL=10000000L / FRAMES_PER_SECOND;
    private long last = 0;
    private final GrassGenerator grassGenerator;
    private final int energyRequiredToChargeValue;
    private final int energyIntervalToChargeValue;
    private final int maxChargeRangeValue;
    private int energyLoss;
    private final int gnomesValue;
    private final int energyRequiredToMateValue;
    private final int energyLostDuringMateValue;
    private final int mutationsInChildrenValue;
    private final int maxEnergyValue;


    public Simulation(GrassField map, int numberOfGrassAtTheStartValue, int numberOfGrassEachDayValue,
                      int startingNumberOfAnimalsValue, int startEnergyOfAnimalsValue,int gnomesValue,int maxEnergyValue,int energyRequiredToChargeValue,int energyIntervalToChargeValue,int maxChargeRangeValue,
                      int energyPerGrassEatenValue, int chanceToGenerateGrassInTheJungle, int jungleHeightValue,int energyRequiredToMateValue,int energyLostDuringMatingValue,int mutationsInChildrenValue) {
        this.worldMap = map;
        this.numberOfGrassEachDayValue=numberOfGrassEachDayValue;
        this.energyPerGrassEatenValue=energyPerGrassEatenValue;
        this.energyRequiredToChargeValue = energyRequiredToChargeValue;
        this.energyIntervalToChargeValue = energyIntervalToChargeValue;
        this.maxChargeRangeValue = maxChargeRangeValue;
        this.energyLoss = map.getEnergyLoss();
        this.gnomesValue = gnomesValue;
        this.energyRequiredToMateValue = energyRequiredToMateValue;
        this.energyLostDuringMateValue = energyLostDuringMatingValue;
        this.mutationsInChildrenValue = mutationsInChildrenValue;
        this.maxEnergyValue = maxEnergyValue;
        this.grassGenerator = new GrassGenerator(map,jungleHeightValue,map.getWidth(),chanceToGenerateGrassInTheJungle);
        if (numberOfGrassAtTheStartValue>map.getCurrentBounds().topRightEdgeOfTheMap().getY()*map.getCurrentBounds().topRightEdgeOfTheMap().getX()){
            throw new IllegalArgumentException("Initial number of grass greater then map size");
        }
        for (int i = 0; i < numberOfGrassAtTheStartValue;i++)
            worldMap.place(grassGenerator.generateGrass());
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



    @Override
    public void handle(long now) {
        if (now - last > INTERVAL){
            run();
            last = now;
        }
    }

    private void removeDeadAnimals(){
        for (Animal animal : animals){
            if (animal.getEnergy()<=0){
                worldMap.remove(animal);
                animal.setDeathDay(day);
                deadAnimals.add(animal);
            }
        }
        animals.removeIf(animal -> !animal.isAlive());
    }

    private void moveAnimal(){
        for (Animal animal : animals){
            int steps = animal.getEnergy();
            // zamiast robić 2 zmienne, robię jedną która zmienia swoje zastosowanie
            if(steps>=energyRequiredToChargeValue){
                steps = min((steps-energyRequiredToChargeValue)/energyIntervalToChargeValue + 1,maxChargeRangeValue);
                int tillCrash = energyLoss;
                for(int i=0; i<steps;i++){
                    if(worldMap.isAnimalAt(animal.getPosition().add(animal.getWhatGenCurrentlyOn().toUnitVector()))){
                        animal.changeEnergy(-tillCrash);
                    }else{
                        tillCrash+=energyLoss;
                        worldMap.move(animal);
                        try{
                            if(worldMap.isGrassAt(animal.getPosition())){
                                worldMap.remove(worldMap.grassAt(animal.getPosition()));
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            else{
                worldMap.move(animal);
            }
        }
    }

    private void eatGrass(){
        List<Grass> grasses = worldMap.getGrasses();
        for (Grass grass : grasses){

            Set<Animal> animalsOnGrass = worldMap.getAnimalsAt(grass.getPosition());
            if (animalsOnGrass.isEmpty()){
                continue;
            }

            Animal animal = Collections.max(animalsOnGrass);
            animal.eatGrass(energyPerGrassEatenValue);
            worldMap.remove(grass);

        }
    }

    private void animalReproduction(){
        Set<Animal> dzieciaki = new HashSet<>();
        for (Animal animal : animals){
            Vector2d vector2d = animal.getPosition();
            Set<Animal> animalsAtVector2d = worldMap.getAnimalsAt(vector2d);
            if (animalsAtVector2d.isEmpty() || animalsAtVector2d.size()<2){
                continue;
            }
            Animal father,mother;
            father = animalsAtVector2d.stream().sorted(Comparator.reverseOrder()).limit(1).findFirst().get();
            mother = animalsAtVector2d.stream().sorted(Comparator.reverseOrder()).limit(2).skip(1).findFirst().get();
            int fatherEnergy = father.getEnergy();
            int motherEnergy = mother.getEnergy();
            if(motherEnergy<energyRequiredToMateValue || fatherEnergy<energyRequiredToMateValue){continue;}
            int gnomesProportion = (int) ( (double) gnomesValue*fatherEnergy/(motherEnergy+fatherEnergy));
            List<MapDirection> gnomeList = new ArrayList();
            Random random = new Random();
            if(random.nextBoolean()){
                gnomeList.addAll(father.getGnomes().subList(0,gnomesProportion));
                gnomeList.addAll(mother.getGnomes().subList(gnomesProportion,gnomesValue));
            }
            else{
                gnomeList.addAll(mother.getGnomes().subList(0,gnomesValue-gnomesProportion));
                gnomeList.addAll(father.getGnomes().subList(gnomesValue-gnomesProportion,gnomesValue));
            }
            int iterations=random.nextInt(0,mutationsInChildrenValue);
            for(int i=0;i<iterations;i++){
                gnomeList.set(random.nextInt(gnomesValue),MapDirection.getRandom());
            }
            father.changeEnergy(-energyLostDuringMateValue);
            mother.changeEnergy(-energyLostDuringMateValue);
            Animal child = new Animal(vector2d,gnomesValue,maxEnergyValue,2*energyLostDuringMateValue,day,gnomeList);
            dzieciaki.add(child);
        }
        for(Animal animal: dzieciaki){
            animals.add(animal);
            this.worldMap.place(animal);
            allAnimals.add(animal);
        }
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

    public List<Animal> getAnimals() {
        return new ArrayList<Animal>(animals);
    }

    public static boolean getTruePercentOfTheTime(int percent){
        if (percent > 100 || percent < 0){
            throw new IllegalArgumentException("percent chance must be within [0,100]");
        }
        return Math.random()*100 < percent;
    }
}
