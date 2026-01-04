package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationApp;
import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapVisualizer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class SimulationPresenter implements MapChangeListener {
    WorldMap worldMap;
    @FXML
    private Label moveDescriptionLabel;
    @FXML
    private TextField moveListText;
    @FXML
    private GridPane mapGrid;

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

        for (int i = 0; i<width; i++){
            for (int j = height; j > -1; j--){
                Vector2d postion = new Vector2d(i + bottomLeftEdgeOfTheMap.getX(), topRightEdgeOfTheMap.getY()-j);
                if (worldMap.isOccupied(postion)){
                    Label label = new Label(worldMap.objectAt(postion).toString());
                    mapGrid.add(label, i + 1, j + 1);
                    GridPane.setHalignment(label, HPos.CENTER);
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
        String moveList = moveListText.getText();
        List<MoveDirection> directions = OptionsParser.parse(moveList.split("\\s+"));
        List<Vector2d> positions = List.of(new Vector2d(2,2),new Vector2d(3,4));
        AbstractWorldMap map = new GrassField(10);
        map.addObserver((worldMap, message) -> {
            String timeOfDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh-mm-ss"));
            System.out.printf("%s %s%n", timeOfDay, message);
        });
        try {
            SimulationApp simulationApp = new SimulationApp();
            simulationApp.launchSimulationWindow(map);
            Simulation simulation = new Simulation(positions, directions, map);
            Thread thread = new Thread(simulation);
            thread.start();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().getFirst());
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            moveDescriptionLabel.setText(message);
            drawMap();
        });
    }

}
