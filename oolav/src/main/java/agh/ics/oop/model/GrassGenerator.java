package agh.ics.oop.model;

import agh.ics.oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GrassGenerator implements MapChangeListener {
    WorldMap map;
    private final List<Vector2d> jungle = new ArrayList<>();
    private final List<Vector2d> nonJungle = new ArrayList<>();
    private final int mapJungleHeight;
    private final int mapJungleWidth;
    private final int chanceToGenerateInJungle;

    public GrassGenerator(WorldMap map,int jungleHeight, int jungleWidth, int chanceToGenerateInJungle){
        this.map=map;
        map.addObserver(this);
        mapJungleHeight=jungleHeight;
        mapJungleWidth=jungleWidth;
        this.chanceToGenerateInJungle=chanceToGenerateInJungle;
        for (int x = 0; x<map.getWidth()+2; x++)
            for (int y = 0; y<map.getHeight()+1; y++)
                add(new Vector2d(x,y));

    }

    public Grass generateGrass(){
        if (jungle.isEmpty() && nonJungle.isEmpty())
            throw new IllegalStateException("Every tile on the map is covered in grass. \nCan't generate grass");
        List<Vector2d> positions = selectPosition(jungle,nonJungle);
        int index = ThreadLocalRandom.current().nextInt(positions.size());
        return new Grass(positions.get(index));
    }

    public List<Vector2d> selectPosition(List<Vector2d> preferred, List<Vector2d> notPreferred){
        if (preferred.isEmpty() && notPreferred.isEmpty())
            throw new IllegalStateException("All tiles are occupied");
        if (preferred.isEmpty())
            return notPreferred;
        if (notPreferred.isEmpty())
            return preferred;
        if (Simulation.getTruePercentOfTheTime(chanceToGenerateInJungle))
            return preferred;
        return notPreferred;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message, Vector2d vector2d) {
        if (map.grassAt(vector2d))
            remove(vector2d);
        else
            add(vector2d);
    }

    public void add(Vector2d vector2d){
        if (isPreferred(vector2d))
            jungle.add(vector2d);
        else
            nonJungle.add(vector2d);

    }

    public void remove(Vector2d vector2d){
        if (isPreferred(vector2d))
            jungle.remove(vector2d);
        else
            nonJungle.remove(vector2d);
    }

    protected boolean isPreferred(Vector2d position) {
        return position.getY() >= map.getHeight()/2 - mapJungleHeight && position.getY() < map.getHeight()/2 + mapJungleHeight;
    }
}
