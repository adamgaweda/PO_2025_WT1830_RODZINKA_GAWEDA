package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrassfieldTest {
    @Test
    void grassCountAndGeneratingGrass(){
        GrassField map = new GrassField(4,4,0);
        GrassGenerator generator = new GrassGenerator(map, map.getHeight()/2,map.getWidth(), 80 );
        int toGenerate = 5;
        Grass grass;
        for (int i = 0; i < toGenerate; i++) {
            grass = generator.generateGrass();
            map.place(grass);
            assertTrue(map.isGrassAt(grass.getPosition()));
        }
        assertEquals(5,map.numberOfGrasses());
    }

    @Test
    void removeGrass(){
        GrassField map = new GrassField(4,4,0);
        GrassGenerator generator = new GrassGenerator(map, map.getHeight()/2,map.getWidth(), 80 );
        Grass grass = generator.generateGrass();
        map.place(grass);
        assertTrue(map.isGrassAt(grass.getPosition()));
        map.remove(grass);
        assertFalse(map.isGrassAt(grass.getPosition()));
    }

    @Test
    void placeAndRemoveAnimal(){
        GrassField map = new GrassField(4,4,0);
        Animal animal = new Animal(new Vector2d(1,1),0,0,0,0);
        map.place(animal);
        assertTrue(map.isAnimalAt(animal.getPosition()));
        map.remove(animal);
        assertFalse(map.isAnimalAt(animal.getPosition()));
    }

    @Test
    void animalOnGrass(){
        GrassField map = new GrassField(4,4,0);
        GrassGenerator generator = new GrassGenerator(map, map.getHeight()/2,map.getWidth(), 80 );
        Grass grass = generator.generateGrass();
        Vector2d vector2d = grass.getPosition();
        Animal animal = new Animal(grass.getPosition(),4,4,4,4);
        animal.setPos(vector2d);
        map.place(grass);
        map.place(animal);
        assertTrue(map.isAnimalAt(vector2d));
        assertTrue(map.isGrassAt(vector2d));
    }
}