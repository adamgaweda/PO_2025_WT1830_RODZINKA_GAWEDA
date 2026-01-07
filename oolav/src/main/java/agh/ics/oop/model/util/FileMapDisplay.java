package agh.ics.oop.model.util;

import agh.ics.oop.model.MapChangeListener;
import agh.ics.oop.model.WorldMap;

import java.io.File;
import java.io.FileWriter;

public class FileMapDisplay implements MapChangeListener {

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        File file=new File(String.format("map_%s",worldMap.getId()));
        try (FileWriter fileWriter = new FileWriter(file,true)){
            fileWriter.write("\n");
            fileWriter.write(message);
            fileWriter.write("\n");
            fileWriter.write(worldMap.toString());
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
