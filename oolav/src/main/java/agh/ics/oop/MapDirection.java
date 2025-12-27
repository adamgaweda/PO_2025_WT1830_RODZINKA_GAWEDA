package agh.ics.oop;

import agh.ics.oop.model.Vector2d;

public enum MapDirection {
    North,East,South,West;
    @Override
    public String toString() {
        return switch (this) {
            case North -> "N";
            case East -> "E";
            case South-> "S";
            case West -> "W";
        };
    }
    public MapDirection next() {
        return switch (this) {
            case North -> East;
            case East -> South;
            case South -> West;
            case West -> North;
        }; //obrot w prawo
    }

    public MapDirection prev() {
        return switch (this){
            case North -> West;
            case East -> North;
            case South-> East;
            case West -> South;
        }; // obrow w lewo
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case North -> new Vector2d(0,1);
            case East -> new Vector2d(1,0);
            case South-> new Vector2d(0,-1);
            case West -> new Vector2d(-1,0);
        };
    }
}

