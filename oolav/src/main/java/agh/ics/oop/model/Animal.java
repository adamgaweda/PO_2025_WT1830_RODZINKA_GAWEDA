package agh.ics.oop.model;

import agh.ics.oop.MapDirection;

public class Animal implements WorldElement {
    MapDirection facing = MapDirection.North;
    Vector2d pos= new  Vector2d(0,0);

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
        return facing.toString();
    }

    public boolean isAT(Vector2d position) {
        return pos.equals(position);
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
}
