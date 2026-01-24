package agh.ics.oop;

import agh.ics.oop.model.Vector2d;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum MapDirection {
    North,NorthEast,East,SouthEast,South,SouthWest,West,NorthWest;

    private static final List<MapDirection> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    @Override
    public String toString() {
        return switch (this) {
            case North -> "N";
            case East -> "E";
            case South-> "S";
            case West -> "W";
            case NorthEast -> "NE";
            case NorthWest -> "NW";
            case SouthEast -> "SE";
            case SouthWest -> "SW";
        };
    }
    public MapDirection next() {
        return switch (this) {
            case North -> NorthEast;
            case NorthEast -> East;
            case East -> SouthEast;
            case SouthEast -> South;
            case South -> SouthWest;
            case SouthWest -> West;
            case West -> NorthWest;
            case NorthWest -> North;
        }; //obrot w prawo o 45 stopni
    }


    public Vector2d toUnitVector() {
        return switch (this) {
            case North -> new Vector2d(0,1);
            case NorthEast -> new Vector2d(1,1);
            case East -> new Vector2d(1,0);
            case SouthEast -> new Vector2d(1,-1);
            case South-> new Vector2d(0,-1);
            case SouthWest -> new Vector2d(-1,-1);
            case West -> new Vector2d(-1,0);
            case NorthWest -> new Vector2d(-1,1);
        };
    }

    public MapDirection turnAround(){
        return switch (this){
            case North -> South;
            case NorthEast -> SouthWest;
            case East -> West;
            case SouthEast -> NorthWest;
            case South -> North;
            case SouthWest -> NorthEast;
            case West -> East;
            case NorthWest -> SouthEast;
        };
    }

    public static MapDirection getRandom(){
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    public MapDirection turnIntTimes(int times){
        MapDirection direction = this;
        for (int i = 0 ; i < times; i++){
            direction=direction.next();
        }
        return direction;
    }
}

