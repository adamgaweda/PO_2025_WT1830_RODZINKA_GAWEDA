package agh.ics.oop.model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileMapDisplay implements MapChangeListener{

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        String fileName = "map_" + worldMap.getId() + ".log";
        File file = new File(fileName);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file,true))) {
            writer.write("---------\n");
            writer.write("Nastąpiła zmiana w mapie:\n");
            writer.write("Ruch zwierzak:"+message+"\n");
            writer.write("Stan mapy:\n");
            writer.write(worldMap.toString());
            writer.write("\n");

        } catch(Exception e){
            System.err.println("Error writing to log file" + fileName);
            e.printStackTrace();
        }
    }
}
