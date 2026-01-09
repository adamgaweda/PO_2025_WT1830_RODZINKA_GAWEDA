package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class World {
    public static void main(String[] args){
        System.out.println("System started");
        /* run(args);
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2)); // sprawdzanie czy wektory sa tworzone poprawnie
        MapDirection.MapDirections[] tab = new MapDirection.MapDirections[3];
        tab = MapDirection.MapDirections.values();
        for(MapDirection.MapDirections d : tab){System.out.println(d.toString());}
        System.out.println();
        for(MapDirection.MapDirections d : tab){System.out.println(d.next());}
        System.out.println();
        for(MapDirection.MapDirections d : tab){System.out.println(d.prev());}
        System.out.println();
        for(MapDirection.MapDirections d : tab){System.out.println(d.toUnitVector());} //komedy działaja tylko bez polskich znaków
        MoveDirection[] moveDirections;
        moveDirections=OptionsParser.parse(args);
        run(moveDirections);
        System.out.println("system zakonczyl dzialnie"); //pierwsze laby
        Animal zwierzatko = new Animal();
        System.out.println(zwierzatko);*/ // trzecie laby
        /* for(String aa: args){
            System.out.print("\"");
            System.out.print(aa);
            System.out.print("\"");
            System.out.print(", ");
        }*/ // kopiowanie argumentow wejsciowych do testow
        /*List<MoveDirection> directions = OptionsParser.parse(args);
        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        GrassField map = new GrassField(10);
        map.addObserver(new ConsoleMapDisplay());
        Simulation simulation = new Simulation(positions, directions,map);
        simulation.run();*/
        List<MoveDirection> directions;
        try {
            directions = OptionsParser.parse(args);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return;
        }

        List<Vector2d> positions = List.of(new Vector2d(2,2), new Vector2d(3,4));
        List<Simulation> simulations = new ArrayList<>();

        for (int i =0; i<1000;i++){
            AbstractWorldMap map = i % 2 == 0 ? new RectangularMap(4,4) : new GrassField(4,4);
            map.addObserver(new ConsoleMapDisplay());
            simulations.add(new Simulation(positions,directions,map));
        }

        SimulationEngine engine = new SimulationEngine(simulations);
        engine.runAsync();
        try {
            engine.awaitSimulationsEnd();
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted");
        }
        System.out.println("System stopped");
    }
    /*static void run(MoveDirection[] a){
        for(MoveDirection b :a){
            switch(b){
                case North:
                    System.out.println("zwierzak idzie do przodu");
                    break;
                case South:
                    System.out.println("zwierzak idzie w tyl");
                    break;
                case West:
                    System.out.println("zwierzak skreca w lewo");
                    break;
                case East:
                    System.out.println("zwierzak skreca w prawo");
                    break;
            }
        }
    }*/
}

