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

    public void defaultAnimal() {
        this.pos = new Vector2d(2,2);
    }

    public Animal(Vector2d pos) {
        this.pos = pos;
    }

    public Vector2d getPosition() {
        return pos;
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
        this.energy=energy;
        if (this.energy> maxEnergy){
            maxEnergy =this.energy;
        }
    }

    public void changeEnergy(int energy){
        this.energy+=energy;
        if (this.energy> maxEnergy){
            maxEnergy =this.energy;
        }
    }

    public int getMaxEnergy(){
        return maxEnergy;
    }

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

    public void move(MoveDirection direction,MoveValidator move) {
        switch (direction) {
            case FORWARD:
                if (move.canMoveTo(pos.add(facing.toUnitVector()))){
                    this.pos = pos.add(facing.toUnitVector());
                }
                return;
            case BACKWARD:
                if (move.canMoveTo(pos.sub(facing.toUnitVector()))) {
                    this.pos = pos.sub(facing.toUnitVector());
                }
                return;
            case LEFT:
                this.facing = this.facing.prev();
                return;
            case RIGHT:
                this.facing = this.facing.next();
        }
    }

    public void genarateGenes(int numberOfGenes){
        for (int i = 0; i<numberOfGenes;i++){
            int randomNum = (int)(Math.random()*8);
            switch (randomNum){
                case 0 -> gen.add(MapDirection.North);
                case 1 -> gen.add(MapDirection.NorthEast);
                case 2 -> gen.add(MapDirection.East);
                case 3 -> gen.add(MapDirection.SouthEast);
                case 4 -> gen.add(MapDirection.South);
                case 5 -> gen.add(MapDirection.SouthWest);
                case 6 -> gen.add(MapDirection.West);
                case 7 -> gen.add(MapDirection.NorthWest);
            }
        }
    }
}
