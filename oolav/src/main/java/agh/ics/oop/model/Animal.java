package agh.ics.oop.model;

import agh.ics.oop.MapDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Animal implements WorldElement {
    MapDirection facing = MapDirection.North;
    Vector2d pos= new  Vector2d(0,0);
    int energy = 0;
    List<MapDirection> gen = new ArrayList<>();
    int maxEnergy = 0;
    final UUID uuid;
    int whatGenCurentlyOn = 0;

    public void defaultAnimal() {
        this.pos = new Vector2d(2,2);
    }

    public Animal(Vector2d pos) {
        this.pos = pos;
         uuid =  UUID.randomUUID();
    }

    public UUID getUuid(){return uuid;}

    public Vector2d getPosition() {
        return pos;
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

    public boolean isAT(Vector2d position) {
        return pos.equals(position);
    }

    public MapDirection getWhatGenCurentlyOn(){
        MapDirection currentGen=gen.get(whatGenCurentlyOn);
        whatGenCurentlyOn = (whatGenCurentlyOn+1)%gen.size();
        return currentGen;
    }


    public void generateGenes(int numberOfGenes){
        for (int i = 0; i<numberOfGenes;i++){
            gen.add(MapDirection.getRandom());
        }
    }
}
