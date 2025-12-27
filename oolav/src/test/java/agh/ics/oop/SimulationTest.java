package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void  testRun() {
        //given
        List<MoveDirection> directions = OptionsParser.parse(new String[] {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        WorldMap map = new RectangularMap(4,4);
        WorldMap testMap = new RectangularMap(4,4);
        Animal testAnimal = new Animal(new Vector2d(2,0));
        testMap.move(testAnimal,MoveDirection.LEFT);
        testMap.move(testAnimal,MoveDirection.LEFT);
        testMap.place(testAnimal);
        Animal testAnimal2 = new Animal(new Vector2d(3,4));
        testMap.place(testAnimal2);
        //when
        Simulation simulation = new Simulation(positions, directions,map);
        WorldMap wynik =  simulation.run()  ;
        //then
        assertEquals(testMap.toString(),wynik.toString());
   }

   @Test
   void test2Moving(){
       //given
       List<MoveDirection> directions = OptionsParser.parse(new String[] {"f","f","f","f","f","f"});
       List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
       WorldMap map = new RectangularMap(4,4);
       WorldMap testMap = new RectangularMap(4,4);
       Animal testAnimal = new Animal(new Vector2d(2,4));
       testMap.place(testAnimal);
       testAnimal.setPos(new Vector2d(3,4));
       testMap.place(testAnimal);
       //when
       Simulation simulation = new Simulation(positions, directions,map);
       WorldMap wynik = simulation.run();
       //then
       assertEquals(testMap.toString(),wynik.toString());
   }

   @Test
    void test3Directions(){
       //given
       List<MoveDirection> directions = OptionsParser.parse(new String[] {"r","r","l","l"});
       List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
       WorldMap map = new RectangularMap(4,4);
       WorldMap testMap = new RectangularMap(4,4);
       Animal testAnimal = new Animal(new Vector2d(2,2));
       testMap.place(testAnimal);
       testAnimal.setPos(new Vector2d(3,4));
       testMap.place(testAnimal);
       //when
       Simulation simulation = new Simulation(positions, directions,map);
       WorldMap wynik =  simulation.run();
       //then
       assertEquals(testMap.toString(),wynik.toString());
   }


   @Test
   void test4GoingInCircle(){
       //given
       List<MoveDirection> directions = OptionsParser.parse(new String[]
               {"f","b","r","l","f","b","r","l","f","b","r","l","f","b","r","l"});
       List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
       WorldMap map = new RectangularMap(4,4);
       WorldMap testMap = new RectangularMap(4,4);
       Animal testAnimal = new Animal(new Vector2d(1,2));
       testMap.place(testAnimal);
       testAnimal.setPos(new Vector2d(3,4));
       testMap.place(testAnimal);
       //when
       Simulation simulation = new Simulation(positions, directions,map);
       WorldMap wynik =  simulation.run();
       //then
       assertEquals(testMap.toString(),wynik.toString());
   }

   @Test
    void test5GoingWayOffTheMap(){
       //given
       List<MoveDirection> directions = OptionsParser.parse(new String[]
               {"f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f", "f"});
       List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
       WorldMap map = new RectangularMap(4,4);
       WorldMap testMap = new RectangularMap(4,4);
       Animal testAnimal = new Animal(new Vector2d(2,4));
       testMap.place(testAnimal);
       testAnimal.setPos(new Vector2d(3,4));
       testMap.place(testAnimal);
       //when
       Simulation simulation = new Simulation(positions, directions,map);
       WorldMap wynik =  simulation.run();
       //then
       assertEquals(testMap.toString(),wynik.toString());
   }

    @Test
    void test9ALotOfAnimals(){
        List<MoveDirection> directions = OptionsParser.parse(new String[]
                {"f","b","r","l","f",
                        "b","r","l","f","b",
                        "r","l","f","b","r","l"});
        List<Vector2d> positions = List.of(new Vector2d(2,2),
                new Vector2d(3,4), new Vector2d(1,1),
                new Vector2d(3,2), new Vector2d(4,1));
        WorldMap map = new RectangularMap(4,4);
        WorldMap testMap = new RectangularMap(4,4);
        Animal testAnimal = new Animal(new Vector2d(2,2));
        testMap.place(testAnimal);
        Animal testAnimal2 = new Animal(new Vector2d(3,3));
        testMap.place(testAnimal2);
        Animal testAnimal3 = new Animal(new Vector2d(1,2));
        testMap.place(testAnimal3);
        Animal testAnimal4 = new Animal(new Vector2d(4,2));
        testMap.place(testAnimal4);
        testMap.move(testAnimal4,MoveDirection.LEFT);
        Animal testAnimal5 = new Animal(new Vector2d(4,1));
        testMap.place(testAnimal5);
        testMap.move(testAnimal5,MoveDirection.RIGHT);
        //when
        Simulation simulation = new Simulation(positions, directions,map);
        WorldMap wynik =  simulation.run();
        //then
        assertEquals(testMap.toString(),wynik.toString());
    }
}