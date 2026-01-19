package agh.ics.oop.model;

import agh.ics.oop.MapDirection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Animal implements WorldElement,Comparable<Animal>{
    MapDirection facing;
    Vector2d pos;
    int energy = 0;
    List<MapDirection> gen = new ArrayList<>();
    final int maxEnergy;
    int whatGenCurrentlyOn = 0;
    Integer deathDay = null;
    int numberOfGrassEaten = 0;
    int numberOfChildren = 0;
    int dayOfBirth = 0;

    public Animal(Vector2d birthVector, int gnomesValue,int maxEnergyValue,int startEnergyOfAnimalsValue,
                  int dayOfBirth){
        this.pos = birthVector.randomVectorWithinGivenVector();
        this.facing = MapDirection.getRandom();
        generateGenes(gnomesValue);
        this.maxEnergy=maxEnergyValue;
        setEnergy(startEnergyOfAnimalsValue);
        this.dayOfBirth=dayOfBirth;
    }
    public Animal(Vector2d birthVector, int gnomesValue,int maxEnergyValue,int startEnergyOfAnimalsValue,
                  int dayOfBirth,List<MapDirection> gnomeList){
        this.pos = birthVector;
        this.facing = MapDirection.getRandom();
        gen.addAll(gnomeList);
        this.maxEnergy=maxEnergyValue;
        setEnergy(startEnergyOfAnimalsValue);
        this.dayOfBirth=dayOfBirth;
    }

    public Vector2d getPosition() {
        return pos;
    }

    public void setDeathDay(int day){
        deathDay=day;
    }

    public int getDeathDay(){
        return deathDay;
    }

    public int getDayOfBirth(){
        return dayOfBirth;
    }

    public int getLiveSpan(){
        if (deathDay == null){
            return -dayOfBirth;
        }
        return deathDay-dayOfBirth;
    }

    public int getNumberOfChildren(){
        return numberOfChildren;
    }

    public boolean isAlive(){
        return deathDay==null;
    }

    public int getNumberOfGrassEaten(){
        return numberOfGrassEaten;
    }

    public void eatGrass(int energyFromGrass){
        numberOfGrassEaten++;
        changeEnergy(energyFromGrass);
    }
    @Override
    public void setPos(Vector2d position) {
        this.pos = position;
    }

    @Override
    public String toString() {
        return "Z: "+facing.toString();
    }

    public void setEnergy(int energy){
        this.energy = Math.min(energy,maxEnergy);
    }

    public void changeEnergy(int energy){
        this.energy = Math.min(this.energy+energy,maxEnergy);
    }

    public int getMaxEnergy(){
        return maxEnergy;
    }

    public int getEnergy(){return energy;}

    public boolean isAT(Vector2d position) {
        return pos.equals(position);
    }

    @Override
    public String getImage(){
        return switch(facing) {
            case North -> "up.png";
            case South -> "down.png";
            case NorthEast -> "animal.jpg";
            case NorthWest -> "animal.jpg";
            case East -> "left.png";
            case West -> "right.png";
            case SouthEast -> "animal.jpg";
            case SouthWest -> "animal.jpg";
        };
    }

    public MapDirection getWhatGenCurrentlyOn(){
        MapDirection currentGen=gen.get(whatGenCurrentlyOn);
        whatGenCurrentlyOn = (whatGenCurrentlyOn +1)%gen.size();
        return currentGen;
    }
    public List<MapDirection> getGnomes(){
        return gen;
    }


    public void generateGenes(int numberOfGenes){
        for (int i = 0; i<numberOfGenes;i++){
            gen.add(MapDirection.getRandom());
        }
    }

    @Override
    public int compareTo(Animal otherAnimal) {
        return Comparator.comparingInt((Animal animal) -> animal.energy)
                .thenComparingInt(Animal::getLiveSpan)
                .thenComparingInt(Animal::getNumberOfChildren)
//                .thenComparingInt(Animal::getMaxEnergy)
                .compare(this,otherAnimal);
    }
}
