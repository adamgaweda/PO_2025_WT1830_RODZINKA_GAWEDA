package agh.ics.oop.model;

import java.util.concurrent.atomic.AtomicInteger;

public class ConsoleMapDisplay implements MapChangeListener {
    private Long numberOfChanges = 0L;
    public void mapChanged(WorldMap worldMap, String message, Vector2d vector2d) {
        synchronized (System.out) {
        System.out.println(message);
        System.out.println(worldMap.toString());
        System.out.printf("Number of changes: %s\n", numberOfChanges++);
        System.out.printf("Map id: %s\n", worldMap.getId());
        }
    }
}
