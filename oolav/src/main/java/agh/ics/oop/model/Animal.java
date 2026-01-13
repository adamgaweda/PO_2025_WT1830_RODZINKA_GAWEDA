package agh.ics.oop.model;

import agh.ics.oop.MapDirection;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement {
    MapDirection facing = MapDirection.North;
    Vector2d pos= new  Vector2d(0,0);
    int energy = 0;
    List<MapDirection> gen = new ArrayList<>();
    int maxEnergy = 0;
    int whatGenCurrentlyOn = 0;
    Integer deathDay = null;
    int numberOfGrassEaten = 0;

    public void defaultAnimal() {
        this.pos = new Vector2d(2,2);
    }

    public Animal(Vector2d pos) {
        this.pos = pos;
    }

    public Animal(Vector2d birthVector, int gnomesValue,int maxEnergyValue,int startEnergyOfAnimalsValue){
        this.pos = birthVector;
        this.facing = MapDirection.getRandom();
        generateGenes(gnomesValue);
        setEnergy(maxEnergyValue);
        setEnergy(startEnergyOfAnimalsValue);
    }
    public Vector2d getPosition() {
        return pos;
    }

    public void setDeathDay(int day){
        deathDay=day;
    }

    public boolean isAlive(){
        return deathDay==null;
    }

    public void increaseNumberOfGrassEaten(){
        numberOfGrassEaten++;
    }

    public int getNumberOfGrassEaten(){
        return numberOfGrassEaten;
    }
    @Override
    public void setPos(Vector2d position) {
        this.pos = position;
    }

    @Override
    public String toString() {
        return facing.toString();
    }

    public void setEnergy(int energy){
        this.energy=energy;
        if (this.energy> maxEnergy){
            maxEnergy = this.energy;
        }
    }

    public void changeEnergy(int energy){
        this.energy+=energy;
        if (this.energy> maxEnergy){
            maxEnergy = this.energy;
        }
    }

    public int getMaxEnergy(){
        return maxEnergy;
    }

    public int getEnergy(){return energy;}

    public boolean isAT(Vector2d position) {
        return pos.equals(position);
    }

    public MapDirection getWhatGenCurrentlyOn(){
        MapDirection currentGen=gen.get(whatGenCurrentlyOn);
        whatGenCurrentlyOn = (whatGenCurrentlyOn +1)%gen.size();
        return currentGen;
    }


    public void generateGenes(int numberOfGenes){
        for (int i = 0; i<numberOfGenes;i++){
            gen.add(MapDirection.getRandom());
        }
    }
}
