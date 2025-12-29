package agh.ics.oop;

import agh.ics.oop.model.Vector2d;

public enum MapDirection {
    North,East,South,West,NorthEast,NorthWest,SouthEast,SouthWest;
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

    public MapDirection prev() {
        return switch (this){
            case North -> NorthWest;
            case NorthWest -> West;
            case West -> SouthWest;
            case SouthWest -> South;
            case South -> SouthEast;
            case SouthEast -> East;
            case East -> NorthEast;
            case NorthEast -> North;
        }; // obrow w lewo o 45 stopni
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
}

