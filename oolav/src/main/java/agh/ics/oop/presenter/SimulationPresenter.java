package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SimulationPresenter implements MapChangeListener {
    GrassField worldMap;
    Thread thread;
    private Simulation simulation;
    private Movement clock;
    @FXML
    private Label moveDescriptionLabel;
    @FXML
    private Label animalNumberLabel;
    @FXML
    private Label grassNumberLabel;
    @FXML
    private Label freeSlotsLabel;
    @FXML
    private Label lifeSpanLabel;
    @FXML
    private Label childrenNumberLabel;
    @FXML
    private Label energyLabel;
    @FXML
    private Label gnomeLabel;
    @FXML
    private Canvas mapCanvas;
    @FXML
    private TextField mapWidth;
    @FXML
    private TextField mapHeight;
    @FXML
    private TextField numberOfGrassAtTheStart;
    @FXML
    private TextField energyPerGrassEaten;
    @FXML
    private TextField numberOfGrassEachDay;
    @FXML
    private TextField startingNumberOfAnimals;
    @FXML
    private TextField startEnergyOfAnimals;
    @FXML
    private TextField lostEnergyAtTheEndOfTheDay;
    @FXML
    private TextField maxEnergy;
    @FXML
    private TextField energyRequiredToCharge;
    @FXML
    private TextField energyIntervalToCharge;
    @FXML
    private TextField maxChargeRange;
    @FXML
    private TextField energyRequiredToMate;
    @FXML
    private TextField energyLostDuringMating;
    @FXML
    private TextField mutationsInChildren;
    @FXML
    private TextField gnomes;
    @FXML
    private TextField chanceToGenerateGrassInTheJungle;
    @FXML
    private TextField jungleHeight;
    @FXML
    private TextField framesPerSecond;

    private GraphicsContext gc;

    @FXML
    private void initialize(){
        if(mapCanvas != null)gc=mapCanvas.getGraphicsContext2D();
    }

    private final int defaultMapValue = 8;
    private final int defaultEnergyValue = 1;

    public void setMap(GrassField worldMap) {
        this.worldMap=worldMap;
    }

    public void drawMap(){

        Boundary bounds = worldMap.getCurrentBounds();
        Vector2d topRightEdgeOfTheMap = bounds.topRightEdgeOfTheMap();
        Vector2d bottomLeftEdgeOfTheMap = bounds.bottomLeftEdgeOfTheMap();

        int width = topRightEdgeOfTheMap.getX()-bottomLeftEdgeOfTheMap.getX() +1;
        int height = topRightEdgeOfTheMap.getY()- bottomLeftEdgeOfTheMap.getY() +1;
        double canvasHeight=mapCanvas.getHeight();
        double canvasWidth=mapCanvas.getWidth();
        gc.setFill(Color.BURLYWOOD);
        gc.clearRect(0,0,canvasWidth,canvasHeight);
        gc.fillRect(0,0,canvasWidth,canvasHeight);
        double margin=20;
        double cellWidth=(canvasWidth-margin)/width;
        double cellHeight=(canvasHeight-margin)/height;

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(10));
        for (int i = 0; i<width+1;i++){
            String value=String.valueOf(i+bottomLeftEdgeOfTheMap.getX());
            double x= margin + i*cellWidth + cellWidth/2;
            double y= margin*0.75;
            gc.fillText(value,x,y);
        }
        for (int i = 0; i<height+1;i++){
            String value=String.valueOf(i+bottomLeftEdgeOfTheMap.getY());
            double x=margin*0.25;
            double y= margin + i*cellHeight + cellHeight/2;
            gc.fillText(value,x,y);
        }
        gc.setStroke(Color.BLACK);
        for(int i=0; i<width;i++){
            double x = margin + i*cellWidth;
            gc.strokeLine(x,margin,x,canvasHeight);
        }
        for(int i=0; i<height;i++){
            double y = margin + i*cellHeight;
            gc.strokeLine(margin,y,canvasWidth,y);
        }
        List<Animal> animals= simulation.getAnimals();
        List<Grass> grasses = worldMap.getGrasses();
        for (Grass grass:grasses){
            Vector2d position = grass.getPosition();
            double x = margin + (position.getX() - bottomLeftEdgeOfTheMap.getX())*cellWidth;
            double y = margin + (topRightEdgeOfTheMap.getY() - position.getY())*cellHeight;
            WorldElementBox grassBox = new WorldElementBox(grass);
            x=x+cellWidth*0.1;
            y=y+cellHeight*0.1;
            grassBox.draw(gc,x,y,cellWidth*0.8,cellHeight*0.8);
        }
        for (Animal animal:animals){
            Vector2d position = animal.getPosition();
            double x = margin + (position.getX() - bottomLeftEdgeOfTheMap.getX())*cellWidth;
            double y = margin + (topRightEdgeOfTheMap.getY() - position.getY())*cellHeight;
            WorldElementBox animalBox = new WorldElementBox(animal);
            x=x+cellWidth*0.1;
            y=y+cellHeight*0.1;
            animalBox.draw(gc,x+cellWidth*0.05,y+cellHeight*0.05,cellWidth*0.8,cellHeight*0.8);
        }
        gc.fillText(
                "y\\x",
                margin/2,
                margin/2
        );
    }

    @FXML
    public void onSimulationStartClicked(){
        int width = getValueFromTextField(mapWidth) < 0 ? defaultMapValue : getValueFromTextField(mapWidth);
        int height = getValueFromTextField(mapHeight) < 0 ? defaultMapValue : getValueFromTextField(mapHeight);
        if (height > 20 || width > 20)
            throw new IllegalArgumentException("The map is too big");
        int numberOfGrassAtTheStartValue = getValueFromTextField(numberOfGrassAtTheStart) < 0 ? defaultMapValue : getValueFromTextField(numberOfGrassAtTheStart);
        int energyPerGrassEatenValue = getValueFromTextField(energyPerGrassEaten) < 0 ? defaultEnergyValue*2 : getValueFromTextField(energyPerGrassEaten);
        int numberOfGrassEachDayValue = getValueFromTextField(numberOfGrassEachDay) < 0 ? defaultEnergyValue*2 : getValueFromTextField(numberOfGrassEachDay);
        int startingNumberOfAnimalsValue = getValueFromTextField(startingNumberOfAnimals) < 0 ? defaultMapValue : getValueFromTextField(startingNumberOfAnimals);
        int startEnergyOfAnimalsValue = getValueFromTextField(startEnergyOfAnimals) < 0 ? defaultMapValue : getValueFromTextField(startEnergyOfAnimals);
        int lostEnergyAtTheEndOfTheDayValue = getValueFromTextField(lostEnergyAtTheEndOfTheDay) < 0 ? defaultEnergyValue : getValueFromTextField(lostEnergyAtTheEndOfTheDay);
        int maxEnergyValue = getValueFromTextField(maxEnergy) < 0 ? defaultMapValue*defaultMapValue : getValueFromTextField(maxEnergy);
        int energyRequiredToChargeValue = getValueFromTextField(energyRequiredToCharge) < 0 ? defaultMapValue*defaultMapValue : getValueFromTextField(energyRequiredToCharge);
        int energyIntervalToChargeValue = getValueFromTextField(energyIntervalToCharge) < 0 ? defaultMapValue*defaultMapValue : getValueFromTextField(energyIntervalToCharge);
        int maxChargeRangeValue = getValueFromTextField(maxChargeRange) < 0 ? defaultMapValue*defaultMapValue : getValueFromTextField(maxChargeRange);
        int energyRequiredToMateValue = getValueFromTextField(energyRequiredToMate) < 0 ? defaultMapValue : getValueFromTextField(energyRequiredToMate);
        int energyLostDuringMatingValue = getValueFromTextField(energyLostDuringMating) < 0 ? defaultMapValue : getValueFromTextField(energyLostDuringMating);
        int mutationsInChildrenValue = getValueFromTextField(mutationsInChildren) < 0 ? defaultMapValue : getValueFromTextField(mutationsInChildren);
        int gnomesValue = getValueFromTextField(gnomes) < 0 ? defaultMapValue : getValueFromTextField(gnomes);
        if (mutationsInChildrenValue>gnomesValue)
            throw new IllegalArgumentException("Number or mutating genes can't be larger than the number of genes");
        int chanceToGenerateGrassInTheJungleValue = getValueFromTextField(chanceToGenerateGrassInTheJungle) < 0 ? 80 : getValueFromTextField(chanceToGenerateGrassInTheJungle);
        if (chanceToGenerateGrassInTheJungleValue>100)
            throw new IllegalArgumentException("Percent chance can't be greater than 100%");
        int jungleHeightValue = getValueFromTextField(jungleHeight) < 0 ? (int) (defaultMapValue * 0.2) : getValueFromTextField(jungleHeight);
        if (jungleHeightValue>height)
            throw new IllegalArgumentException("The jungle can't be bigger than the map");
        int framesPerSecondValue = getValueFromTextField(framesPerSecond) <= 0 ? (int) (defaultMapValue / 8 * 5 ) : getValueFromTextField(framesPerSecond);
        GrassField map = new GrassField(width,height,lostEnergyAtTheEndOfTheDayValue);
        map.addObserver((worldMap, message, vector2d) -> {
            String timeOfDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh-mm-ss"));
            System.out.printf("%s %s%n", timeOfDay, message);
        });
        try {
            clock = new Movement(framesPerSecondValue);
            SimulationApp simulationApp = new SimulationApp();
            simulationApp.launchSimulationWindow(map,this);
            this.simulation = new Simulation(map,numberOfGrassAtTheStartValue,numberOfGrassEachDayValue,
                    startingNumberOfAnimalsValue,startEnergyOfAnimalsValue,gnomesValue,maxEnergyValue,energyRequiredToChargeValue,
                    energyIntervalToChargeValue,maxChargeRangeValue,energyPerGrassEatenValue,
                    chanceToGenerateGrassInTheJungleValue,jungleHeightValue,energyRequiredToMateValue,energyLostDuringMatingValue,mutationsInChildrenValue);
            if(mapCanvas != null && gc == null)gc=mapCanvas.getGraphicsContext2D();
            this.thread = new Thread(simulation);
            clock.start();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void step(){
        thread.run();
    }

    public void resumeClicked(){
        clock.start();
    }

    public void pauseClicked(){
        clock.stop();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message, Vector2d vector2d) {
        Platform.runLater(() -> {
            moveDescriptionLabel.setText(message);
            String text;
            text = String.format("Średnia energia: " + simulation.getAverageEnergy());
            energyLabel.setText(text);
            text = String.format("Srednia energia: " + simulation.getAverageAnimalChildrenNumber());
            childrenNumberLabel.setText(text);
            text = String.format("Liczba zwierzat: " +worldMap.numberOfAnimals());
            animalNumberLabel.setText(text);
            text = String.format("Liczba traw: " + worldMap.numberOfGrasses());
            grassNumberLabel.setText(text);
            text = String.format("Pustych pol: " + worldMap.getEmptySquares());
            freeSlotsLabel.setText(text);
            text = String.format("Srednia dlugosc zycia: " + simulation.getAverageAnimalLiveSpan());
            lifeSpanLabel.setText(text);
            text = String.format("Sredni genom: " + simulation.getGnomes());
            gnomeLabel.setText(text);
            drawMap();
        });
    }

    private int getValueFromTextField(TextField test) throws RuntimeException{
        try {
            if (test.getText() == null || test.getText().trim().isEmpty()) return -1;
            return Integer.parseInt(test.getText());
        } catch (Exception e) {
            throw new RuntimeException("put in Int");
        }
    }

    private class Movement extends AnimationTimer{

        private Movement(long framesPerSecond){
            this.framesPerSecond=framesPerSecond;
        }
        private long framesPerSecond = 5L;
        private long interval = 1000000000L;
        private long last = 0;
        @Override
        public void handle(long now){
            if (now - last > interval/framesPerSecond){
                step();
                last = now;
            }
        }
    }

}
