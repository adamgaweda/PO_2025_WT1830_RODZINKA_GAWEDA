package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassfieldTest {

    @Test
    void grassCount() {
        GrassField map = new GrassField(10);
        assertEquals(10, map.getElements().size());
    }

    @Test
    void inBounds() {
        GrassField map = new GrassField(10);
        Vector2d lowerLeft = new Vector2d(0, 0);
        Vector2d upperRight = new Vector2d(10, 10);
        for (WorldElement element : map.getElements()) {
            assertTrue(element.getPosition().precedes(upperRight));
            assertTrue(element.getPosition().follows(lowerLeft));
        }
    }

    @Test
    void isOccupied() {
        GrassField map = new GrassField(10);
        for (WorldElement element : map.getElements())
            assertFalse(map.isOccupied(element.getPosition()));

        Animal[] animals = {new Animal(new Vector2d(1, 2)), new Animal(new Vector2d(3, 4))};
        for (Animal animal : animals) {
            map.place(animal);
            assertTrue(map.isOccupied(animal.getPosition()));
        }
    }


    @Test
    void objectAt() {
        GrassField map = new GrassField(10);
        for (WorldElement element : map.getElements())
            assertEquals(element, map.objectAt(element.getPosition()));

        Animal[] animals = {new Animal(new Vector2d(1, 2)), new Animal(new Vector2d(3, 4))};
        for (Animal animal : animals) {
            map.place(animal);
            assertEquals(animal.getPosition(), map.objectAt(animal.getPosition()).getPosition());
        }
    }

    @Test
    void place() {
        GrassField map = new GrassField(10);
        Animal[] animals = {new Animal(new Vector2d(1, 2)), new Animal(new Vector2d(3, 4))};
        for (Animal testAnimal : animals) {
            assertFalse(map.isOccupied(testAnimal.getPosition()));
            map.place(testAnimal);
        }
        for (Animal testAnimal : animals)
            assertTrue(map.isOccupied(testAnimal.getPosition()));
    }

    @Test
    void animalOnGrass() {
        GrassField map = new GrassField(10);
        Vector2d grassPosition = map.getElements().getFirst().getPosition();
        assertFalse(map.isOccupied(grassPosition));
        map.place(new Animal(grassPosition));
        assertTrue(map.isOccupied(grassPosition));
    }

    @Test
    void move() {
        GrassField map = new GrassField(10);
        Animal testAnimal = new Animal(new Vector2d(3, 1));
        map.place(testAnimal);
        map.move(testAnimal, MoveDirection.FORWARD);
        assertEquals(new Vector2d(3, 2), testAnimal.getPosition());
    }

    @Test
    void moveOutOfBounds() {
        GrassField map = new GrassField(10);
        Animal testAnimal = new Animal(new Vector2d(2, 10));
        map.place(testAnimal);
        map.move(testAnimal, MoveDirection.FORWARD);
        assertEquals(new Vector2d(2, 11), testAnimal.getPosition());

        Animal testAnimal2 = new Animal(new Vector2d(3, 0));
        map.place(testAnimal2);
        map.move(testAnimal2, MoveDirection.BACKWARD);
        assertEquals(new Vector2d(3, -1), testAnimal2.getPosition());
    }

    @Test
    void getElements() {
        GrassField map = new GrassField(10);
        assertEquals(10, map.getElements().size());

        Animal testAnimal = new Animal(new Vector2d(1, 2));
        map.place(testAnimal);
        assertEquals(11, map.getElements().size());
        assertTrue(map.getElements().contains(testAnimal));
    }
}