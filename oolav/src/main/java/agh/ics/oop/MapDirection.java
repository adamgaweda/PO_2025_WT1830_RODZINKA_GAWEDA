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

    public MapDirection next90(){
        return switch (this){
            case North -> East;
            case NorthEast -> SouthEast;
            case East -> South;
            case SouthEast -> SouthWest;
            case South -> West;
            case SouthWest -> NorthWest;
            case West -> North;
            case NorthWest -> NorthEast;
        };
    }

    public MapDirection prev90(){
        return switch (this){
            case North -> West;
            case NorthWest -> SouthWest;
            case West -> South;
            case SouthWest -> SouthEast;
            case South -> East;
            case SouthEast -> NorthEast;
            case East -> North;
            case NorthEast -> NorthWest;
        };
    }
}

