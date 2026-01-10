package agh.ics.oop.model;

import java.util.Objects;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Vector2d { //na wykladzie pokazano, ze mozna uzyc recordu zamiast klasy, jednak nie zrobilem tego na lekcji. Wiec kontynuowalem robiac to klasa
    final private int x;
    final private int y;
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    @Override
    public String toString() {
        return String.format("(%d,%d)", x, y);
    }
    public boolean follows(Vector2d other) {
        return (this.x>=other.x && this.y>=other.y);
    }
    public boolean precedes(Vector2d other){
        return (this.x<=other.x && this.y<=other.y);
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(x+other.x, y+other.y);
    }
    public Vector2d sub(Vector2d other){
        return new Vector2d(x-other.x, y-other.y);
    }
    public Vector2d upperRight(Vector2d other){
        return new Vector2d(max(x,other.x), max(y,other.y));
    }
    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(min(x,other.x), min(y,other.y));
    }
    public Vector2d opposite(){
        return new Vector2d(-this.x,-this.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Vector2d other)){
            return false;
        }
        return (this.x==other.x && this.y==other.y);
    }


    public int comparteTo(Vector2d s2) {
        if (this.getX()== s2.getX()){
            if (this.y<s2.y) return 1; else return 0;
        }
        if (this.x<s2.x) return 1;
        return 0;
    }

    public Vector2d randomVectorWithinGivenVector(){
        Random random = new Random();
        return new Vector2d(random.nextInt(this.x),random.nextInt(this.y));
    }
}
