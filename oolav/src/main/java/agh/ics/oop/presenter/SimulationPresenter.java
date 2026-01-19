package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SimulationPresenter implements MapChangeListener {
    WorldMap worldMap;
    Thread thread;
    private Movement clock;
    @FXML
    private Label moveDescriptionLabel;
    @FXML
    private GridPane mapGrid;
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

    private final int defaultMapValue = 8;
    private final int defaultEnergyValue = 1;

    private final int CELL_HIGHT=33;
    private final int CELL_WIDTH=33;

    public void setMap(WorldMap worldMap) {
        this.worldMap=worldMap;
    }

    public void drawMap(){
        clearGrid();
        Boundary bounds = worldMap.getCurrentBounds();
        Vector2d topRightEdgeOfTheMap = bounds.topRightEdgeOfTheMap();
        Vector2d bottomLeftEdgeOfTheMap = bounds.bottomLeftEdgeOfTheMap();

        int width = topRightEdgeOfTheMap.getX()-bottomLeftEdgeOfTheMap.getX();
        int height = topRightEdgeOfTheMap.getY()- bottomLeftEdgeOfTheMap.getY();

        for (int i = 0; i<width+1;i++){
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            Label label = new Label(Integer.toString(i + bottomLeftEdgeOfTheMap.getX()));
            mapGrid.add(label, i+1 , 0);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = 0; i<height+1;i++){
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HIGHT));
            Label label = new Label(Integer.toString(topRightEdgeOfTheMap.getY()-i));
            mapGrid.add(label, 0 , i+1);
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = 0; i<width +1; i++){
            for (int j = height; j > -1; j--){
                Vector2d postion = new Vector2d(i + bottomLeftEdgeOfTheMap.getX(), topRightEdgeOfTheMap.getY()-j);
                if (worldMap.isOccupied(postion)){
                    Optional<WorldElement> optElement = worldMap.objectAt(postion);
                    final int  col = i+1;
                    final int row = j+1;
                    optElement.ifPresent(element -> {
                        WorldElementBox box = new WorldElementBox(element);
                        mapGrid.add(box, col, row);
                        GridPane.setHalignment(box, HPos.CENTER);
                    });
                }
            }
        }
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_HIGHT));
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_WIDTH));
        Label label = new Label("y\\x");
        mapGrid.add(label, 0, 0);
        GridPane.setHalignment(label, HPos.CENTER);
    }

    @FXML
    public void onSimulationStartClicked(){
        int width = getValueFromTextField(mapWidth) < 0 ? defaultMapValue : getValueFromTextField(mapWidth);
        int height = getValueFromTextField(mapHeight) < 0 ? defaultMapValue : getValueFromTextField(mapHeight);
        int numberOfGrassAtTheStartValue = getValueFromTextField(numberOfGrassAtTheStart) < 0 ? defaultMapValue : getValueFromTextField(numberOfGrassAtTheStart);
        int energyPerGrassEatenValue = getValueFromTextField(energyPerGrassEaten) < 0 ? defaultMapValue : getValueFromTextField(energyPerGrassEaten);
        int numberOfGrassEachDayValue = getValueFromTextField(numberOfGrassEachDay) < 0 ? defaultMapValue : getValueFromTextField(numberOfGrassEachDay);
        int startingNumberOfAnimalsValue = getValueFromTextField(startingNumberOfAnimals) < 0 ? defaultMapValue : getValueFromTextField(startingNumberOfAnimals);
        int startEnergyOfAnimalsValue = getValueFromTextField(startEnergyOfAnimals) < 0 ? defaultMapValue*defaultMapValue : getValueFromTextField(startEnergyOfAnimals);
        int lostEnergyAtTheEndOfTheDayValue = getValueFromTextField(lostEnergyAtTheEndOfTheDay) < 0 ? defaultEnergyValue : getValueFromTextField(lostEnergyAtTheEndOfTheDay);
        int maxEnergyValue = getValueFromTextField(maxEnergy) < 0 ? defaultMapValue*defaultMapValue : getValueFromTextField(maxEnergy);
        int energyRequiredToChargeValue = getValueFromTextField(energyRequiredToCharge) < 0 ? defaultMapValue*defaultMapValue : getValueFromTextField(energyRequiredToCharge);
        int energyIntervalToChargeValue = getValueFromTextField(energyIntervalToCharge) < 0 ? defaultMapValue*defaultMapValue : getValueFromTextField(energyIntervalToCharge);
        int maxChargeRangeValue = getValueFromTextField(maxChargeRange) < 0 ? defaultMapValue*defaultMapValue : getValueFromTextField(maxChargeRange);
        int energyRequiredToMateValue = getValueFromTextField(energyRequiredToMate) < 0 ? defaultMapValue : getValueFromTextField(energyRequiredToMate);
        int energyLostDuringMatingValue = getValueFromTextField(energyLostDuringMating) < 0 ? defaultMapValue : getValueFromTextField(energyLostDuringMating);
        int mutationsInChildrenValue = getValueFromTextField(mutationsInChildren) < 0 ? defaultMapValue : getValueFromTextField(mutationsInChildren);
        int gnomesValue = getValueFromTextField(gnomes) < 0 ? defaultMapValue : getValueFromTextField(gnomes);
        int chanceToGenerateGrassInTheJungleValue = getValueFromTextField(chanceToGenerateGrassInTheJungle) < 0 ? 80 : getValueFromTextField(chanceToGenerateGrassInTheJungle);
        int jungleHeightValue = getValueFromTextField(jungleHeight) < 0 ? (int) (defaultMapValue * 0.2) : getValueFromTextField(jungleHeight);
        int framesPerSecondValue = getValueFromTextField(framesPerSecond) < 0 ? (int) (defaultMapValue / 8 * 5 ) : getValueFromTextField(framesPerSecond);
        GrassField map = new GrassField(width,height,lostEnergyAtTheEndOfTheDayValue);
        map.addObserver((worldMap, message, vector2d) -> {
            String timeOfDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh-mm-ss"));
            System.out.printf("%s %s%n", timeOfDay, message);
        });
        try {
            clock = new Movement();
            SimulationApp simulationApp = new SimulationApp();
            simulationApp.launchSimulationWindow(map,this);
            Simulation simulation = new Simulation(map,numberOfGrassAtTheStartValue,numberOfGrassEachDayValue,
                    startingNumberOfAnimalsValue,startEnergyOfAnimalsValue,gnomesValue,maxEnergyValue,energyRequiredToChargeValue,
                    energyIntervalToChargeValue,maxChargeRangeValue,energyPerGrassEatenValue,
                    chanceToGenerateGrassInTheJungleValue,jungleHeightValue,energyRequiredToMateValue,energyLostDuringMatingValue,mutationsInChildrenValue);
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

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message, Vector2d vector2d) {
        Platform.runLater(() -> {
            moveDescriptionLabel.setText(message);
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

        private long framesPerSecond = 5L;
        private long interval = 1000000000L / framesPerSecond;
        private long last = 0;
        @Override
        public void handle(long now){
            if (now - last > interval){
                step();
                last = now;
            }
        }
    }

//    private boolean isRunning(){
//        return executorService != null && !executorService.isShutdown();
//    }
//
//    public void start(Thread thread){
//        if (isRunning()) throw new RuntimeException("Simulation is already running");
//        executorService = Executors.newSingleThreadScheduledExecutor();
//        executorService.scheduleWithFixedDelay(thread,500/speedOfSimulation,
//                1000/speedOfSimulation, TimeUnit.MILLISECONDS);
//    }
//
//    public void setSpeedOfSimulation(int speed,Thread thread){
//        this.speedOfSimulation = speed;
//        if (isRunning()) {
//            executorService.shutdown();
//            thread.start();
//        }
//    }


}
